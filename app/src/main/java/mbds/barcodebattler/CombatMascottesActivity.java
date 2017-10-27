package mbds.barcodebattler;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class CombatMascottesActivity extends AppCompatActivity {

    ImageView imageMascotte1;
    ImageView imageMascotte2;
    Mascotte mascotte1;
    Mascotte mascotte2;
    TextView texteResultat;

    int nbTours = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combat_mascottes);

        mascotte1 = new Mascotte("Mascotte1", 10, 20, 5, 3);
        //getIntent().getExtras().getParcelable("mascotte_1");
        mascotte2 = new Mascotte("Mascotte1", 10, 20, 5, 3);

                //new Mascotte("Mascotte2", 10, 30, 10, 3);
                //getIntent().getExtras().getParcelable("mascotte_2");

        imageMascotte1 = (ImageView) findViewById(R.id.imagemascotte1);
        //imageMascotte1.setImageBitmap(mascotte1.getImage());
        Bitmap i1 = BitmapFactory.decodeResource(getResources(), R.drawable.ame_des_aspects);
        imageMascotte1.setImageBitmap(i1);

        imageMascotte2 = (ImageView) findViewById(R.id.imagemascotte2);
        //imageMascotte2.setImageBitmap(mascotte2.getImage());
        Bitmap i2 = BitmapFactory.decodeResource(getResources(), R.drawable.albie);
        imageMascotte2.setImageBitmap(i2);

        Mascotte vainqueur = lancerCombat();

        texteResultat = (TextView) findViewById(R.id.vainqueur);
        texteResultat.setText(texteResultat.getText() + vainqueur.getNom() + "");

        // Afficher le nb de tours
        // Afficher les actions dans un log en listviewÒ

    }

    /**
     * Combat au tour par tour : attaquant tire un nombre aléatoire entre 0 et sa capacité d'attaque
     * adversaire devra tire un nombre aléatoire entre 0 et sa capacité de défense,
     * adversaire sera blessé (d’attaque - défense) point de vie
     * @return vainqueur
     */
    public Mascotte lancerCombat() {

        Log.d("COMBAT", "---- DEBUT COMBAT ENTRE " + mascotte1.getNom() + " " + mascotte2.getNom());

        Random rand = new Random();

        // Vitesse pour déterminer le premier ?
        Mascotte attaquant = mascotte1;
        Mascotte adversaire = mascotte2;

        int attaqueTour;
        int defenseTour;

        while(true) {

            Log.d("COMBAT", "-- TOUR " + nbTours);
            Log.d("COMBAT", "Attaquant : " + attaquant.getNom() + " avec " + attaquant.getVie() + "PV");
            Log.d("COMBAT", "Adversaire : " + adversaire.getNom() + " avec " + adversaire.getVie() + "PV");

            nbTours++;

            attaqueTour = rand.nextInt(attaquant.getAttaque() + 1);
            defenseTour = rand.nextInt(adversaire.getDefense() + 1);

            Log.d("COMBAT", "Attaque : " + attaqueTour);
            Log.d("COMBAT", "Défense : " + defenseTour);

            int attaqueTourApresDefense = attaqueTour - defenseTour;
            Log.d("COMBAT", "attaqueTourApresDefense : " + attaqueTourApresDefense);
            if (attaqueTourApresDefense > -1) {

                Log.d("COMBAT", "Perd " + attaqueTourApresDefense + "PV");

                adversaire.setVie(adversaire.getVie() - attaqueTourApresDefense);

                Log.d("COMBAT", "Adversaire passe à : " + adversaire.getVie() + " PV");
            }
            else {
                Log.d("COMBAT", "Pas de PV perdus");
            }

            if (adversaire.getVie() <= 0) {
                Log.d("COMBAT", "-> Vainqueur : " + attaquant.getNom());
                return attaquant;
            }

            Mascotte tmpAdversaire = adversaire;
            adversaire = attaquant;
            attaquant = tmpAdversaire;


        }

    }
}
