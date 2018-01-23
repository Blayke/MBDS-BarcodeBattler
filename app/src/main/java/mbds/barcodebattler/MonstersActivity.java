package mbds.barcodebattler;

import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MonstersActivity extends AppCompatActivity implements ListAdapter {

    BarcodeBattlerBDD barCoderMaster;
    ListView lv;
    ArrayList<Mascotte> listeMascotte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mymonster);
        barCoderMaster = new BarcodeBattlerBDD(this);
        listeMascotte = barCoderMaster.getMascottes();

        lv = (ListView) findViewById(R.id.listeMonstre);
        lv.setAdapter(this);

        Button btnRetour = (Button) findViewById(R.id.retour);
        btnRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(int i) {
        return true;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public int getCount() {
//        return barCoderMaster.getMascottes().size();
       return listeMascotte.size();
    }

    @Override
    public Object getItem(int i) {
//        return barCoderMaster.getMascottes().get(i);
        return listeMascotte.get(i);

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
        if (view == null) {
//            on va glonfer le xml
            returnView = View.inflate(this, R.layout.liste_monstre, null);
        } else {
            returnView = view;
        }
        returnView.setId(i);

        TextView niveau = (TextView) returnView.findViewById(R.id.niveau);
//        niveau.setText(barCoderMaster.getMascottes().get(i).getNiveau());
        niveau.setText(listeMascotte.get(i).getNiveau()+"");

        TextView nom = (TextView) returnView.findViewById(R.id.nom);
//        nom.setText(barCoderMaster.getMascottes().get(i).getNom());
        nom.setText(listeMascotte.get(i).getNom()+"");

        TextView vie = (TextView) returnView.findViewById(R.id.vie);
//        vie.setText(barCoderMaster.getMascottes().get(i).getVie());
        vie.setText(listeMascotte.get(i).getVie()+"");

        TextView attaque = (TextView) returnView.findViewById(R.id.attaque);
//        attaque.setText(barCoderMaster.getMascottes().get(i).getAttaque());
        attaque.setText(listeMascotte.get(i).getAttaque()+"");

        TextView defense = (TextView) returnView.findViewById(R.id.defense);
//        defense.setText(barCoderMaster.getMascottes().get(i).getDefense());
        defense.setText(listeMascotte.get(i).getDefense()+"");

        ImageView image = (ImageView) returnView.findViewById(R.id.IV);

        if (listeMascotte.get(i).getImage() != null) {
            image.setImageBitmap(listeMascotte.get(i).getImage());
        } else {
            image.setImageBitmap(null);
        }

//        if (barCoderMaster.getMascottes().get(i).getImage() != null) {
//            image.setImageBitmap(barCoderMaster.getMascottes().get(i).getImage());
//        } else {
//            image.setImageBitmap(null);
//        }
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
//        return barCoderMaster.getMascottes().size() == 0;
        return listeMascotte.size() == 0;
    }
}
