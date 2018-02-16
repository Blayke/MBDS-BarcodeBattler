package mbds.barcodebattler;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailsEquipement extends AppCompatActivity {

    Equipement equipement;
    TextView textViewNomEquipement;
    TextView textViewVieEquipement;
    TextView textViewAttaqueEquipement;
    TextView textViewDefenseEquipement;
    ImageView imageViewImageEquipement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_equipement);

        equipement = getIntent().getExtras().getParcelable("equipement");

        textViewNomEquipement = (TextView) findViewById(R.id.nom);
        textViewNomEquipement.setText(equipement.getNom() + "");


        textViewVieEquipement = (TextView) findViewById(R.id.vie);
        textViewVieEquipement.setText("+" + equipement.getVie() + "");

        textViewAttaqueEquipement = (TextView) findViewById(R.id.attaque);
        textViewAttaqueEquipement.setText("+" + equipement.getAttaque() + "");

        textViewDefenseEquipement = (TextView) findViewById(R.id.defense);
        textViewDefenseEquipement.setText("+" + equipement.getDefense() + "");

        imageViewImageEquipement = (ImageView) findViewById(R.id.image);

        Drawable img = getResources().getDrawable( equipement.getIdImage());
        Bitmap imgBitmap = ((BitmapDrawable) img).getBitmap();
        imageViewImageEquipement.setImageBitmap(imgBitmap);

        Button btnRetour = (Button) findViewById(R.id.retour);
        btnRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(DetailsEquipement.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
