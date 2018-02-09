package mbds.barcodebattler;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;


public class CombatLocalSelect extends AppCompatActivity {

    ImageView imageMascotte1;
    ImageView imageMascotte2;
    Mascotte mascotte1;
    Mascotte mascotte2;
    TextView nomMascotte1;
    TextView nomMascotte2;
    Button btnCombat;
    Button btnRetour;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combat_local_select);
        imageMascotte1 = (ImageView) findViewById(R.id.imagemascotte1);
        imageMascotte2 = (ImageView) findViewById(R.id.imagemascotte2);
        btnCombat = (Button) findViewById(R.id.combat);
        btnRetour = (Button) findViewById(R.id.retour);
        this.hideButton(true);

        imageMascotte1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CombatLocalSelect.this, ListMascotte.class);
                startActivityForResult(intent, 1);
            }
        });
        imageMascotte2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CombatLocalSelect.this, ListMascotte.class);
                startActivityForResult(intent, 2);
            }
        });

//        Bitmap i1 = BitmapFactory.decodeResource(getResources(), R.drawable.ame_des_aspects);
//        mascotte1.setImage(i1);
//        imageMascotte1.setImageBitmap(mascotte1.getImage());

//        Bitmap i2 = BitmapFactory.decodeResource(getResources(), R.drawable.albie);
//        mascotte2.setImage(i2);
//        imageMascotte2.setImageBitmap(mascotte2.getImage());

        btnCombat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CombatLocalSelect.this, CombatMascottesActivity.class);


                intent.putExtra("mascotte1", (Parcelable)mascotte1);

                intent.putExtra("mascotte2", (Parcelable) mascotte2);

                startActivity(intent);
            }
        });

        btnRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == 1) {
                mascotte1 = data.getExtras().getParcelable("Mascotte");
                if (mascotte1 != null) {
                    TextView txtMascotte1 = (TextView) findViewById(R.id.nommascotte1);
                    txtMascotte1.setText(mascotte1.getNom());

                    Drawable img = getResources().getDrawable( mascotte1.getIdImage());
                    Bitmap imgBitmap = ((BitmapDrawable) img).getBitmap();
                    imageMascotte1.setImageBitmap(imgBitmap);
                }
            }
        }
        if (requestCode == 2) {
            if (resultCode == 1) {
                mascotte2 = data.getExtras().getParcelable("Mascotte");
                if (mascotte2 != null) {
                    TextView txtMascotte2 = (TextView) findViewById(R.id.nommascotte2);
                    txtMascotte2.setText(mascotte2.getNom());

                    Drawable img = getResources().getDrawable( mascotte2.getIdImage());
                    Bitmap imgBitmap = ((BitmapDrawable) img).getBitmap();
                    imageMascotte2.setImageBitmap(imgBitmap);
                }
            }
        }
        if (mascotte1 != null && mascotte2 != null) {
            hideButton(false);
        }
    }

    private void hideButton(boolean bool) {
        btnCombat = (Button) findViewById(R.id.combat);
        if (bool) {
            btnCombat.setVisibility(View.GONE);
        } else {
            btnCombat.setVisibility(View.VISIBLE);
        }
    }
}
