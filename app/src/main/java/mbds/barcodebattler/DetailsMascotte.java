package mbds.barcodebattler;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailsMascotte extends AppCompatActivity {

    Mascotte mascotte;
    TextView textViewNomMascotte;
    TextView textViewNiveauMascotte;
    TextView textViewVieMascotte;
    TextView textViewAttaqueMascotte;
    TextView textViewDefenseMascotte;
    ImageView imageViewImageMascotte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_mascotte);

        mascotte = getIntent().getExtras().getParcelable("mascotte");

        textViewNomMascotte = (TextView) findViewById(R.id.nom);
        textViewNomMascotte.setText(mascotte.getNom() + "");

        textViewNiveauMascotte = (TextView) findViewById(R.id.niveau);
        textViewNiveauMascotte.setText("Niveau : " + mascotte.getNiveau());

        textViewVieMascotte = (TextView) findViewById(R.id.vie);
        textViewVieMascotte.setText(mascotte.getVie() + "");

        textViewAttaqueMascotte = (TextView) findViewById(R.id.attaque);
        textViewAttaqueMascotte.setText(mascotte.getAttaque() + "");

        textViewDefenseMascotte = (TextView) findViewById(R.id.defense);
        textViewDefenseMascotte.setText(mascotte.getDefense() + "");

        imageViewImageMascotte = (ImageView) findViewById(R.id.image);

        Drawable img = getResources().getDrawable( mascotte.getIdImage());
        Bitmap imgBitmap = ((BitmapDrawable) img).getBitmap();
        imageViewImageMascotte.setImageBitmap(imgBitmap);

        Button btnRetour = (Button) findViewById(R.id.retour);
        btnRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(DetailsMascotte.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
