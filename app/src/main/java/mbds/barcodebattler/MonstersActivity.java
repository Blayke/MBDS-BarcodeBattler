package mbds.barcodebattler;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
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
            returnView = View.inflate(this, R.layout.liste_monstre_etendue, null);
        } else {
            returnView = view;
        }
        //returnView.setId(i);

        TextView niveau = returnView.findViewById(R.id.niveau);
        niveau.setText(String.format("%s", listeMascotte.get(i).getNiveau()));

        TextView nom = returnView.findViewById(R.id.nom);
        nom.setText(String.format("%s", listeMascotte.get(i).getNom()));

        TextView vie = returnView.findViewById(R.id.vie);
        vie.setText(String.format("%s", listeMascotte.get(i).getVie()));

        TextView attaque = returnView.findViewById(R.id.attaque);
        attaque.setText(String.format("%s", listeMascotte.get(i).getAttaque()));

        TextView defense = returnView.findViewById(R.id.defense);
        defense.setText(String.format("%s", listeMascotte.get(i).getDefense()));

        ImageView image = returnView.findViewById(R.id.IV);
        if (listeMascotte.get(i).getIdImage() != 0) {
            Drawable img = getResources().getDrawable( listeMascotte.get(i).getIdImage());
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
                        .setTitle("Supprimer " + listeMascotte.get(v.getId()).getNom())
                        .setMessage("Voulez-vous vraiment supprimer ce monstre ?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {

                                Log.d("DELETE", "id : " + ID);
                                Mascotte mascotte = listeMascotte.get(ID);
                                Log.d("DELETE", mascotte.toString());
                                Log.d("DELETE", "Nb mascottes avant : " + listeMascotte.size());
                                barCoderMaster.deleteMascotte(mascotte);
                                Log.d("DELETE", "Après delete");
                                listeMascotte = barCoderMaster.getMascottes();
                                Log.d("DELETE", "Nb mascottes après : " + listeMascotte.size());
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
        return listeMascotte.size() == 0;
    }
}
