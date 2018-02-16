package mbds.barcodebattler;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    Button scanner, myMonster, equipements, localBattle, networkBattle;
    ZXingScannerView mScannerView;
    RelativeLayout videoView;
    LinearLayout contentView;
    boolean isCameraOn = false;
    MediaPlayer mBackgroundSound;
    int currentMediaTime;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scanner = (Button) findViewById(R.id.scanner);
        myMonster = (Button) findViewById(R.id.myMonster);
        equipements = (Button) findViewById(R.id.equipements);
        localBattle = (Button) findViewById(R.id.localBattle);
        networkBattle = (Button) findViewById(R.id.networkBattle);
        this.LaunchVideo();
        mBackgroundSound = MediaPlayer.create(this,R.raw.homemusic);
        mBackgroundSound.start();
        scanner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    float x = (float) 1.25;
                    float y = (float) 1.25;
                    scanner.setScaleX(x);
                    scanner.setScaleY(y);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    float x = 1;
                    float y = 1;
                    scanner.setScaleX(x);
                    scanner.setScaleY(y);
                    mScannerView = new ZXingScannerView(MainActivity.this);
                    setContentView(mScannerView);
                    mScannerView.setResultHandler(MainActivity.this);
                    mScannerView.startCamera();
                    isCameraOn = true;
                }
                return false;
            }
        });


//        scanner.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mScannerView = new ZXingScannerView(MainActivity.this);
//                setContentView(mScannerView);
//                mScannerView.setResultHandler(MainActivity.this);
//                mScannerView.startCamera();
//            }
//        });


        Navigation(myMonster, MainActivity.this, MonstersActivity.class);

//        myMonster.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, MonstersActivity.class);
//                startActivity(intent);
//            }
//        });

        Navigation(equipements, MainActivity.this, EquipementsActivity.class);

//        equipements.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, EquipementsActivity.class);
//                startActivity(intent);
//            }
//        });

        Navigation(localBattle, MainActivity.this, CombatLocalSelect.class);

//        localBattle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, CombatLocalSelect.class);
//                startActivity(intent);
//
//            }
//        });

        Navigation(networkBattle, MainActivity.this, CombatReseauSelect.class);

//        networkBattle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, CombatReseauSelect.class);
//                startActivity(intent);
//            }
//        });
//        Mascotte testMascotteString = new Mascotte("Laeticia",67,1,500,0);
//        testMascotteString.testXml(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mScannerView != null && mScannerView.isActivated()){
            mScannerView.stopCamera();
        }
        if(mBackgroundSound.isPlaying()){
            mBackgroundSound.pause();
            currentMediaTime = mBackgroundSound.getCurrentPosition();
        }
    }
    public void onResume() {
        super.onResume();
        if(!mBackgroundSound.isPlaying()){
            if(currentMediaTime != 0){
                mBackgroundSound.seekTo(currentMediaTime);
                mBackgroundSound.start();
            }
        }
    }

    @Override
    public void handleResult(Result result) {
        String codeBarre = result.getText();

        Log.w("codeBarre", codeBarre);

        String hashCodeBarre = String.valueOf(Math.abs(codeBarre.hashCode()));
        Log.w("codeBarre", "Hash obtenu : " + hashCodeBarre);

        /*AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Scan result");
        builder.setMessage(result.getText());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();*/

        // Besoin de 8 chiffres au minimum
        while (hashCodeBarre.length() < 8) {
            hashCodeBarre = hashCodeBarre.concat(hashCodeBarre);
            Log.d("codeBarre", "Nouvelle valeur : " + hashCodeBarre);
        }

        // Exemple : 8 6 9 56 49 9 6
        int idImage;
        String nom;
        // Mascotte
        if (Character.getNumericValue(hashCodeBarre.charAt(3)) <= 5) {
            if (Character.getNumericValue(hashCodeBarre.charAt(0)) <= 1) {
                nom = "Birgorneau";
                idImage = getResources().getIdentifier("birgorneau", "drawable", this.getPackageName());
            }
            if (Character.getNumericValue(hashCodeBarre.charAt(0)) == 2 || Character.getNumericValue(hashCodeBarre.charAt(0)) == 6) {
                nom = "Cricri";
                idImage = getResources().getIdentifier("cricri", "drawable", this.getPackageName());
            }
            if (Character.getNumericValue(hashCodeBarre.charAt(0)) == 3 || Character.getNumericValue(hashCodeBarre.charAt(0)) == 7) {
                nom = "Groopy";
                idImage = getResources().getIdentifier("groopy", "drawable", this.getPackageName());
            }
            if (Character.getNumericValue(hashCodeBarre.charAt(0)) == 4) {
                nom = "Jeannot";
                idImage = getResources().getIdentifier("jeannot", "drawable", this.getPackageName());
            }
            if (Character.getNumericValue(hashCodeBarre.charAt(0)) == 5) {
                nom = "Albie";
                idImage = getResources().getIdentifier("albie", "drawable", this.getPackageName());
            } else {
                nom = "Alex";
                idImage = getResources().getIdentifier("draco", "drawable", this.getPackageName());
            }

            String stringNiveau = String.valueOf(hashCodeBarre.charAt(2) + String.valueOf(hashCodeBarre.charAt(3)));
            int niveau = Integer.parseInt(stringNiveau);

            String stringVie = String.valueOf(hashCodeBarre.charAt(4) + String.valueOf(hashCodeBarre.charAt(5)));
            int vie = Integer.parseInt(stringVie);

            int attaque = Character.getNumericValue(hashCodeBarre.charAt(6));
            int defense = Character.getNumericValue(hashCodeBarre.charAt(7));

            Mascotte mascotteGagnee = new Mascotte(nom, niveau, vie, attaque, defense, idImage);

            Log.d("codeBarre", mascotteGagnee.toString());

            BarcodeBattlerBDD barCoderMaster = new BarcodeBattlerBDD(this);
            barCoderMaster.addMascotte(mascotteGagnee);

            Intent intent = new Intent(MainActivity.this, DetailsMascotte.class);
            intent.putExtra("mascotte", (Parcelable) mascotteGagnee);

            startActivity(intent);

        }
        // Equipement
        else {
            nom = "Epee courte";
            idImage = getResources().getIdentifier("epeecourte", "drawable", this.getPackageName());
            if (Character.getNumericValue(hashCodeBarre.charAt(1)) <= 5) {
                nom = "Pugilat légendaire";
                idImage = getResources().getIdentifier("pugilatlegendaire", "drawable", this.getPackageName());
            }

            String stringVie = String.valueOf(hashCodeBarre.charAt(2) + String.valueOf(hashCodeBarre.charAt(3)));
            int vie = Integer.parseInt(stringVie);

            int attaque = Character.getNumericValue(hashCodeBarre.charAt(4));
            int defense = Character.getNumericValue(hashCodeBarre.charAt(5));

            Equipement equipementGagnee = new Equipement(nom, attaque, defense, vie, idImage);

            Log.d("codeBarre", equipementGagnee.toString());

            BarcodeBattlerBDD barCoderMaster = new BarcodeBattlerBDD(this);
            barCoderMaster.addEquipement(equipementGagnee);

            Intent intent = new Intent(MainActivity.this, DetailsEquipement.class);
            intent.putExtra("equipement", equipementGagnee);
            startActivity(intent);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        AudioManager mAudioManager = (AudioManager)  getSystemService(Context.AUDIO_SERVICE);
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            onBackPressed();
        }
        if(keyCode == KeyEvent.KEYCODE_VOLUME_DOWN){
            mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_LOWER,AudioManager.FX_FOCUS_NAVIGATION_UP);
        }
        if(keyCode == KeyEvent.KEYCODE_VOLUME_UP){
            mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_RAISE, AudioManager.FX_FOCUS_NAVIGATION_UP);
        }
        return true;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(isCameraOn){
        startActivity(new Intent(MainActivity.this,MainActivity.class));
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LaunchVideo();
    }

    public static void Navigation(final Button buttonNav, final Context ctx, final Class activitylaunch) {
        final Activity ctxA = (Activity) ctx;
        buttonNav.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    float x = (float) 1.25;
                    float y = (float) 1.25;
                    buttonNav.setScaleX(x);
                    buttonNav.setScaleY(y);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    float x = 1;
                    float y = 1;
                    buttonNav.setScaleX(x);
                    buttonNav.setScaleY(y);
                    Intent intent = new Intent(ctx, activitylaunch);
                    ctxA.startActivity(intent);
                }
                return false;
            }
        });
    }

    public void LaunchVideo(){
        VideoView videoview = (VideoView) findViewById(R.id.videoview);
        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.arenebackground);
        videoview.setVideoURI(uri);
        videoview.start();
        videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);
            }
        });
    }

}
