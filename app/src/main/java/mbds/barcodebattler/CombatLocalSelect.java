package mbds.barcodebattler;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
        this.hideButton(true);
        imageMascotte1 = (ImageView) findViewById(R.id.imagemascotte1);
        imageMascotte2 = (ImageView) findViewById(R.id.imagemascotte2);
        btnCombat = (Button) findViewById(R.id.combat);
        btnRetour = (Button) findViewById(R.id.retour);

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

                Bitmap b = mascotte1.getImage();
                ByteArrayOutputStream bs = new ByteArrayOutputStream();
                b.compress(Bitmap.CompressFormat.PNG, 50, bs);

                mascotte1.setImage(null);
                intent.putExtra("mascotte1", mascotte1);
                intent.putExtra("Image1", bs.toByteArray());

                b = mascotte2.getImage();
                bs = new ByteArrayOutputStream();
                b.compress(Bitmap.CompressFormat.PNG, 50, bs);

                mascotte2.setImage(null);
                intent.putExtra("mascotte2", mascotte2);
                intent.putExtra("Image2", bs.toByteArray());

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
                    Bitmap b = BitmapFactory.decodeByteArray(
                            data.getByteArrayExtra("Image"), 0, data.getByteArrayExtra("Image").length);
                    mascotte1.setImage(b);
                    imageMascotte1.setImageBitmap(b);
                }
            }
        }
        if (requestCode == 2) {
            if (resultCode == 1) {
                mascotte2 = data.getExtras().getParcelable("Mascotte");
                if (mascotte2 != null) {
                    TextView txtMascotte2 = (TextView) findViewById(R.id.nommascotte2);
                    txtMascotte2.setText(mascotte2.getNom());
                    Bitmap b = BitmapFactory.decodeByteArray(
                            data.getByteArrayExtra("Image"), 0, data.getByteArrayExtra("Image").length);
                    mascotte2.setImage(b);
                    imageMascotte2.setImageBitmap(b);
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
