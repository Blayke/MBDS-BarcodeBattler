package mbds.barcodebattler;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class EquipementsActivity extends AppCompatActivity implements ListAdapter {

    BarcodeBattlerBDD barCoderMaster;
    ListView lv;
    ArrayList<Equipement> listeEquipement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myequipement);
        barCoderMaster = new BarcodeBattlerBDD(this);
        listeEquipement = barCoderMaster.getEquipements();

        lv = (ListView) findViewById(R.id.listeEquipement);
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
        return listeEquipement.size();
    }

    @Override
    public Object getItem(int i) {
//        return barCoderMaster.getMascottes().get(i);
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
            returnView = View.inflate(this, R.layout.liste_equipement_etendue, null);
        } else {
            returnView = view;
        }
        //returnView.setId(i);

        TextView nom = returnView.findViewById(R.id.nom);
        nom.setText(String.format("%s", listeEquipement.get(i).getNom()));

        TextView vie = returnView.findViewById(R.id.vie);
        vie.setText(String.format("%s", listeEquipement.get(i).getVie()));

        TextView attaque = returnView.findViewById(R.id.attaque);
        attaque.setText(String.format("%s", listeEquipement.get(i).getAttaque()));

        TextView defense = returnView.findViewById(R.id.defense);
        defense.setText(String.format("%s", listeEquipement.get(i).getDefense()));

        ImageView image = returnView.findViewById(R.id.IV);
        if (listeEquipement.get(i).getIdImage() != 0) {
            Drawable img = getResources().getDrawable( listeEquipement.get(i).getIdImage());
            Bitmap imgBitmap = ((BitmapDrawable) img).getBitmap();
            image.setImageBitmap(imgBitmap);
        } else {
            image.setImageBitmap(null);
        }

        Button btnDelete = returnView.findViewById(R.id.delete);
        if (btnDelete != null) {
            btnDelete.setId(i);
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {

                    final int ID = v.getId();

                    new AlertDialog.Builder(v.getContext())
                            .setTitle("Supprimer " + listeEquipement.get(v.getId()).getNom())
                            .setMessage("Voulez-vous vraiment supprimer ce monstre ?")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int whichButton) {

                                    Log.d("DELETE", "id : " + ID);
                                    Equipement equipement = listeEquipement.get(ID);
                                    Log.d("DELETE", equipement.toString());
                                    Log.d("DELETE", "Nb equipements avant : " + listeEquipement.size());
                                    barCoderMaster.deleteEquipement(equipement);
                                    Log.d("DELETE", "Après delete");
                                    listeEquipement = barCoderMaster.getEquipements();
                                    Log.d("DELETE", "Nb equipements après : " + listeEquipement.size());
                                    lv.invalidateViews();

                                }})
                            .setNegativeButton(android.R.string.no, null).show();

                }
            });
        }

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
        return listeEquipement.size() == 0;
    }
}
