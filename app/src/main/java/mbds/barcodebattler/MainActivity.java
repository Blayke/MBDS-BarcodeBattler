package mbds.barcodebattler;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.zxing.Result;

import java.io.ByteArrayOutputStream;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    Button scanner, myMonster, equipements, localBattle, networkBattle;
    ZXingScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scanner = (Button) findViewById(R.id.scanner);
        myMonster = (Button) findViewById(R.id.myMonster);
        equipements = (Button) findViewById(R.id.equipements);
        localBattle = (Button) findViewById(R.id.localBattle);
        networkBattle = (Button) findViewById(R.id.networkBattle);

        scanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mScannerView = new ZXingScannerView(MainActivity.this);
                setContentView(mScannerView);
                mScannerView.setResultHandler(MainActivity.this);
                mScannerView.startCamera();
            }
        });

        myMonster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MonstersActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        localBattle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CombatLocalSelect.class);
                startActivity(intent);
            }
        });

        networkBattle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CombatReseauSelect.class);
                startActivity(intent);
            }
        });
//        Mascotte testMascotteString = new Mascotte("Laeticia",67,1,500,0);
//        testMascotteString.testXml(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mScannerView != null && mScannerView.isActivated()) mScannerView.stopCamera();
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

        // Mascotte
        //if (Character.getNumericValue(hashCodeBarre.charAt(0)) <= 5) {

        String nom = "Albie";
        Bitmap image = BitmapFactory.decodeResource(this.getResources(),
                R.drawable.albie);
        if (Character.getNumericValue(hashCodeBarre.charAt(1)) <= 5) {
            nom = "Alex";
            image = BitmapFactory.decodeResource(this.getResources(),
                    R.drawable.alex);
        }

        String stringNiveau = String.valueOf(hashCodeBarre.charAt(2) + String.valueOf(hashCodeBarre.charAt(3)));
        int niveau = Integer.parseInt(stringNiveau);

        String stringVie = String.valueOf(hashCodeBarre.charAt(4) + String.valueOf(hashCodeBarre.charAt(5)));
        int vie = Integer.parseInt(stringVie);

        int attaque = Character.getNumericValue(hashCodeBarre.charAt(6));
        int defense = Character.getNumericValue(hashCodeBarre.charAt(7));

        Mascotte mascotteGagnee = new Mascotte(nom, niveau, vie, attaque, defense);

        //mascotteGagnee.setImage(image);
        Log.d("codeBarre", mascotteGagnee.toString());

        BarcodeBattlerBDD barCoderMaster = new BarcodeBattlerBDD(this);
        barCoderMaster.addMascotte(mascotteGagnee);

        Intent intent = new Intent(MainActivity.this, DetailsMascotte.class);
        intent.putExtra("mascotte", mascotteGagnee);

        Bitmap b = image;
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG, 50, bs);
        intent.putExtra("Image",bs.toByteArray());
        startActivity(intent);

        //}
        // Equipement
        /*else {

        }*/

        //Resume scanning
        //mScannerView.resumeCameraPreview(this);
    }
}
