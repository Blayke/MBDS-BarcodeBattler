package mbds.barcodebattler;

import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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
import java.util.HashMap;
import java.util.Objects;
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
    HashMap attributionCouleurs;

    int nbTours = 0;
    ArrayList<LogCombat> logsCombat;

    ListView logsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combat_mascottes);
        logsCombat = new ArrayList<>();
        attributionCouleurs = new HashMap();

        Mascotte vainqueur;

        // Si des logs sont envoyés, on les charge
        if (getIntent().getSerializableExtra("logsCombat") != null) {
            // Marche qu'avec list ?
            this.logsCombat = (ArrayList<LogCombat>) getIntent().getSerializableExtra("logsCombat");
            mascotte1 = this.logsCombat.get(0).attaquant;
            mascotte2 = this.logsCombat.get(1).attaquant;

            // Si les deux mascottes sont du même type, on les différencie
            if (Objects.equals(mascotte1.getNom(), mascotte2.getNom())) {
                mascotte1.setNom(mascotte1.getNom() + " (1)");
                mascotte2.setNom(mascotte2.getNom() + " (2)");
            }

            // vainqueur = dernier attaquant
            vainqueur = this.logsCombat.get(this.logsCombat.size()-1).attaquant;
        }
        else {

            mascotte1 = getIntent().getExtras().getParcelable("mascotte1");
            Bitmap b = BitmapFactory.decodeByteArray(
                    getIntent().getByteArrayExtra("Image1"), 0, getIntent().getByteArrayExtra("Image1").length);
            mascotte1.setImage(b);

            mascotte2 = getIntent().getExtras().getParcelable("mascotte2");
            b = BitmapFactory.decodeByteArray(
                    getIntent().getByteArrayExtra("Image2"), 0, getIntent().getByteArrayExtra("Image2").length);
            mascotte2.setImage(b);

            // Si les deux mascottes sont du même type, on les différencie
            if (Objects.equals(mascotte1.getNom(), mascotte2.getNom())) {
                mascotte1.setNom(mascotte1.getNom() + " (1)");
                mascotte2.setNom(mascotte2.getNom() + " (2)");
            }

            vainqueur = ServiceCombat.lancerCombat(mascotte1, mascotte2, this.logsCombat);
        }

        nbTours = this.logsCombat.size();

        attributionCouleurs.put(mascotte1.getNom(), Color.LTGRAY);
        attributionCouleurs.put(mascotte2.getNom(), Color.GRAY);

        imageMascotte1 = (ImageView) findViewById(R.id.imagemascotte1);
        //Bitmap i1 = BitmapFactory.decodeResource(getResources(), R.drawable.ame_des_aspects);
        //mascotte1.setImage(i1);
        imageMascotte1.setImageBitmap(mascotte1.getImage());
        imageMascotte1.setBackgroundColor(Color.LTGRAY);

        imageMascotte2 = (ImageView) findViewById(R.id.imagemascotte2);
        //Bitmap i2 = BitmapFactory.decodeResource(getResources(), R.drawable.albie);
        //mascotte2.setImage(i2);
        imageMascotte2.setImageBitmap(mascotte2.getImage());
        imageMascotte2.setBackgroundColor(Color.GRAY);


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

        returnView.setBackgroundColor((Integer) attributionCouleurs.get(logsCombat.get(i).getAttaquant().getNom()));

        ImageView imageViewAttaquant = (ImageView) returnView.findViewById(R.id.imageattaquant);
        Bitmap image = logsCombat.get(i).getAttaquant().getImage();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 0, stream);
        Bitmap b = BitmapFactory.decodeByteArray(stream.toByteArray(), 0, stream.toByteArray().length);
        imageViewAttaquant.setImageBitmap(Bitmap.createScaledBitmap(b, 80, 80, false));

        ImageView imageViewAdversaire = (ImageView) returnView.findViewById(R.id.imageadversaire);
        image = logsCombat.get(i).getAdversaireApresAttaque().getImage();
        stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 0, stream);
        b = BitmapFactory.decodeByteArray(stream.toByteArray(), 0, stream.toByteArray().length);
        imageViewAdversaire.setImageBitmap(Bitmap.createScaledBitmap(b, 80, 80, false));

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
