package mbds.barcodebattler;

import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
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

public class ListEquipement extends AppCompatActivity  implements ListAdapter {

    BarcodeBattlerBDD barCoderMaster;
    ArrayList<Equipement> listeEquipement;
    ListView lv;
    Button btnRetour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myequipement);
        barCoderMaster = new BarcodeBattlerBDD(this);
        listeEquipement = barCoderMaster.getEquipements();
        lv = (ListView) findViewById(R.id.listeEquipement);
        lv.setAdapter(this);
        btnRetour = (Button) findViewById(R.id.retour);
        btnRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        return listeEquipement.size();
    }

    @Override
    public Object getItem(int i) {
        return listeEquipement.get(i);

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
            returnView = View.inflate(this, R.layout.liste_equipement, null);
        } else {
            returnView = view;
        }
        returnView.setId(i);

        TextView nom = (TextView) returnView.findViewById(R.id.nom);
//        nom.setText(barCoderMaster.getMascottes().get(i).getNom());
        nom.setText(listeEquipement.get(i).getNom() + "");

        TextView vie = (TextView) returnView.findViewById(R.id.vie);
//        vie.setText(barCoderMaster.getMascottes().get(i).getVie());
        vie.setText(listeEquipement.get(i).getVie() + "");

        TextView attaque = (TextView) returnView.findViewById(R.id.attaque);
//        attaque.setText(barCoderMaster.getMascottes().get(i).getAttaque());
        attaque.setText(listeEquipement.get(i).getAttaque() + "");

        TextView defense = (TextView) returnView.findViewById(R.id.defense);
//        defense.setText(barCoderMaster.getMascottes().get(i).getDefense());
        defense.setText(listeEquipement.get(i).getDefense() + "");

        final ImageView image = (ImageView) returnView.findViewById(R.id.IV);

        if (listeEquipement.get(i).getIdImage() != 0) {
            Drawable img = getResources().getDrawable( listeEquipement.get(i).getIdImage());
            Bitmap imgBitmap = ((BitmapDrawable) img).getBitmap();
            image.setImageBitmap(imgBitmap);
        } else {
            image.setImageBitmap(null);
        }

//        if (barCoderMaster.getMascottes().get(i).getImage() != null) {
//            image.setImageBitmap(barCoderMaster.getMascottes().get(i).getImage());
//        } else {
//            image.setImageBitmap(null);
//        }
        returnView.setOnClickListener(new View.OnClickListener() {
            @Override
            //Sur une Cell
            public void onClick(View v) {
                Intent intent = new Intent();
                Equipement equipement = listeEquipement.get(v.getId());
                intent.putExtra("Equipement", (Parcelable) equipement);
                setResult(1, intent);
                finish();
            }
        });
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
        return listeEquipement.size() == 0;
    }
}
