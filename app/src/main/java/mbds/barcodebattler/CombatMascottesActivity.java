package mbds.barcodebattler;

import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

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
            Log.d("LOGCOMBAT", "On est dans le if");
            // Marche qu'avec list ?
            this.logsCombat = (ArrayList<LogCombat>) getIntent().getSerializableExtra("logsCombat");
            Log.d("LOGCOMBAT", "Ok compris");
            Log.d("LOGCOMBAT", logsCombat.size()+"");

            mascotte1 = this.logsCombat.get(0).attaquant;
            mascotte2 = this.logsCombat.get(1).attaquant;

            adapterNomsSiIdentiques();

            // vainqueur = dernier attaquant
            vainqueur = this.logsCombat.get(this.logsCombat.size()-1).attaquant;
        }
        else {

            mascotte1 = getIntent().getExtras().getParcelable("mascotte1");
            mascotte2 = getIntent().getExtras().getParcelable("mascotte2");

            adapterNomsSiIdentiques();

            vainqueur = ServiceCombat.lancerCombat(mascotte1, mascotte2, this.logsCombat);
        }

        nbTours = this.logsCombat.size();

        attributionCouleurs.put(mascotte1.getNom(), Color.LTGRAY);
        attributionCouleurs.put(mascotte2.getNom(), Color.GRAY);

        imageMascotte1 = (ImageView) findViewById(R.id.imagemascotte1);
        Drawable img = getResources().getDrawable( mascotte1.getIdImage());
        Bitmap imgBitmap = ((BitmapDrawable) img).getBitmap();
        imageMascotte1.setImageBitmap(imgBitmap);
        imageMascotte1.setBackgroundColor(Color.LTGRAY);

        imageMascotte2 = (ImageView) findViewById(R.id.imagemascotte2);
        Drawable img2 = getResources().getDrawable( mascotte2.getIdImage());
        Bitmap imgBitmap2 = ((BitmapDrawable) img2).getBitmap();
        imageMascotte2.setImageBitmap(imgBitmap2);
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
        Drawable img = getResources().getDrawable( logsCombat.get(i).getAttaquant().getIdImage());
        Bitmap imgBitmap = ((BitmapDrawable) img).getBitmap();

        imageViewAttaquant.setImageBitmap(imgBitmap);

        ImageView imageViewAdversaire = (ImageView) returnView.findViewById(R.id.imageadversaire);
        img = getResources().getDrawable( logsCombat.get(i).getAdversaireApresAttaque().getIdImage());
        imgBitmap = ((BitmapDrawable) img).getBitmap();
        imageViewAdversaire.setImageBitmap(imgBitmap);

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

    public void adapterNomsSiIdentiques() {
        if (Objects.equals(mascotte1.getNom(), mascotte2.getNom())) {
            mascotte1.setNom(mascotte1.getNom() + " (1)");
            mascotte2.setNom(mascotte2.getNom() + " (2)");
        }
    }
}
