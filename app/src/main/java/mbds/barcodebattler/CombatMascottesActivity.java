package mbds.barcodebattler;

import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Random;

public class CombatMascottesActivity extends AppCompatActivity implements ListAdapter {

    ImageView imageMascotte1;
    ImageView imageMascotte2;
    Mascotte mascotte1;
    Mascotte mascotte2;
    TextView texteResultat;
    TextView nomMascotte1;
    TextView nomMascotte2;
    TextView resume;

    int nbTours = 0;
    ArrayList<LogCombat> logsCombat;

    ListView logsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combat_mascottes);

        logsCombat = new ArrayList<>();

        mascotte1 = new Mascotte("Mascotte1", 10, 20, 5, 3);
        //getIntent().getExtras().getParcelable("mascotte_1");
        mascotte2 = new Mascotte("Mascotte2", 10, 20, 5, 3);
        //getIntent().getExtras().getParcelable("mascotte_2");

        imageMascotte1 = (ImageView) findViewById(R.id.imagemascotte1);
        Bitmap i1 = BitmapFactory.decodeResource(getResources(), R.drawable.ame_des_aspects);
        mascotte1.setImage(i1);
        imageMascotte1.setImageBitmap(mascotte1.getImage());

        imageMascotte2 = (ImageView) findViewById(R.id.imagemascotte2);
        Bitmap i2 = BitmapFactory.decodeResource(getResources(), R.drawable.albie);
        mascotte2.setImage(i2);
        imageMascotte2.setImageBitmap(mascotte2.getImage());

        Mascotte vainqueur = lancerCombat();

        texteResultat = (TextView) findViewById(R.id.vainqueur);
        texteResultat.setText(texteResultat.getText() + vainqueur.getNom() + "");

        nomMascotte1 = (TextView) findViewById(R.id.nommascotte1);
        nomMascotte1.setText(mascotte1.getNom() + "");

        nomMascotte2 = (TextView) findViewById(R.id.nommascotte2);
        nomMascotte2.setText(mascotte2.getNom() + "");

        resume = (TextView) findViewById(R.id.resume);
        resume.setText(resume.getText() + "(" +  nbTours + " tours)");

        logsView = (ListView) findViewById(R.id.logs);
        logsView.setAdapter(this);

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

            int attaqueFinale = attaqueTourApresDefense;

            if (attaqueTourApresDefense > -1) {

                Log.d("COMBAT", "Perd " + attaqueTourApresDefense + "PV");

                adversaire.setVie(adversaire.getVie() - attaqueTourApresDefense);

                Log.d("COMBAT", "Adversaire passe à : " + adversaire.getVie() + " PV");
            }
            else {
                attaqueFinale = 0;
                Log.d("COMBAT", "Pas de PV perdus");
            }

            try {
                logsCombat.add(new LogCombat(nbTours, (Mascotte)attaquant.clone(), attaqueFinale, (Mascotte)adversaire.clone()));
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
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

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int i) {
        return false;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public int getCount() {
        return logsCombat.size();
    }

    @Override
    public Object getItem(int i) {
        return logsCombat.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View returnView;

        if (view == null)
            returnView = View.inflate(this, R.layout.cellule_logs, null);
        else
            returnView = view;

        ImageView imageViewAttaquant = (ImageView) returnView.findViewById(R.id.imageattaquant);
        Bitmap image = logsCombat.get(i).getAttaquant().getImage();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 0, stream);
        Bitmap b = BitmapFactory.decodeByteArray(stream.toByteArray(), 0, stream.toByteArray().length);
        imageViewAttaquant.setImageBitmap(Bitmap.createScaledBitmap(b, 200, 200, false));

        ImageView imageViewAdversaire = (ImageView) returnView.findViewById(R.id.imageadversaire);
        image = logsCombat.get(i).getAdversaireApresAttaque().getImage();
        stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 0, stream);
        b = BitmapFactory.decodeByteArray(stream.toByteArray(), 0, stream.toByteArray().length);
        imageViewAdversaire.setImageBitmap(Bitmap.createScaledBitmap(b, 200, 200, false));

        TextView textViewAttaque = (TextView) returnView.findViewById(R.id.attaque);
        textViewAttaque.setText(logsCombat.get(i).attaque + "");

        Log.d("VIEW", "Attaque : " + logsCombat.get(i).attaque + "");

        TextView textViewPv = (TextView) returnView.findViewById(R.id.pv);
        textViewPv.setText(logsCombat.get(i).getAdversaireApresAttaque().getVie() + "");

        Log.d("VIEW", "pv : " + logsCombat.get(i).getAdversaireApresAttaque().getVie() + "");

        return returnView;
    }

    @Override
    public int getItemViewType(int i) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return (logsCombat.size() == 0);
    }
}
