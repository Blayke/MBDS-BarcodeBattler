package mbds.barcodebattler;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;


public class CombatLocalSelect extends AppCompatActivity {

    ImageView imageMascotte1;
    ImageView imageMascotte2;
    Mascotte mascotte1;
    Mascotte mascotte2;
    Equipement equipement1;
    Equipement equipement2;
    Button btnCombat;
    Button btnRetour;
    Button btnEquipement1;
    Button btnEquipement2;
    Button btnDeleteEquipement1;
    Button btnDeleteEquipement2;

    TextView equipementAjoutText1;
    TextView equipementAjoutText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combat_local_select);
        imageMascotte1 = (ImageView) findViewById(R.id.imagemascotte1);
        imageMascotte2 = (ImageView) findViewById(R.id.imagemascotte2);
        btnEquipement1 = (Button) findViewById(R.id.equipement1);
        btnEquipement2 = (Button) findViewById(R.id.equipement2);
        equipementAjoutText1 = (TextView) findViewById(R.id.equipement1Ajout);
        equipementAjoutText2 = (TextView) findViewById(R.id.equipement2Ajout);
        btnDeleteEquipement1 = (Button) findViewById(R.id.deleteEquipement1);
        btnDeleteEquipement2 = (Button) findViewById(R.id.deleteEquipement2);

        btnCombat = (Button) findViewById(R.id.combat);
        btnRetour = (Button) findViewById(R.id.retour);
        this.hideButtonMascotte(true);
        this.hideButtonEquipement(true,0);

        imageMascotte1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CombatLocalSelect.this, ListMascotte.class);
                startActivityForResult(intent, 1);
            }
        });
        imageMascotte2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CombatLocalSelect.this, ListMascotte.class);
                startActivityForResult(intent, 2);
            }
        });

        btnEquipement1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CombatLocalSelect.this, ListEquipement.class);
                startActivityForResult(intent, 3);
            }
        });

        btnEquipement2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CombatLocalSelect.this, ListEquipement.class);
                startActivityForResult(intent, 4);
            }
        });

        btnCombat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CombatLocalSelect.this, CombatMascottesActivity.class);


                intent.putExtra("mascotte1", (Parcelable) mascotte1);

                intent.putExtra("mascotte2", (Parcelable) mascotte2);

                startActivity(intent);
            }
        });

        btnDeleteEquipement1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mascotte1.setVie(mascotte1.getVie() - equipement1.getVie());
                mascotte1.setAttaque(mascotte1.getAttaque() - equipement1.getAttaque());
                mascotte1.setDefense(mascotte1.getDefense() - equipement1.getDefense());
                equipementAjoutText1.setText("");
                btnDeleteEquipement1.setVisibility(View.GONE);
                equipement1 = null;
            }
        });

        btnDeleteEquipement2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mascotte2.setVie(mascotte2.getVie() - equipement2.getVie());
                mascotte2.setAttaque(mascotte2.getAttaque() - equipement2.getAttaque());
                mascotte2.setDefense(mascotte2.getDefense() - equipement2.getDefense());
                equipementAjoutText2.setText("");
                btnDeleteEquipement2.setVisibility(View.GONE);
                equipement2 = null;
            }
        });

        btnRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == 1) {
                mascotte1 = data.getExtras().getParcelable("Mascotte");
                if (mascotte1 != null) {
                    TextView txtMascotte1 = (TextView) findViewById(R.id.nommascotte1);
                    txtMascotte1.setText(mascotte1.getNom());

                    Drawable img = getResources().getDrawable(mascotte1.getIdImage());
                    Bitmap imgBitmap = ((BitmapDrawable) img).getBitmap();
                    imageMascotte1.setImageBitmap(imgBitmap);
                }
            }
        }
        if (requestCode == 2) {
            if (resultCode == 1) {
                mascotte2 = data.getExtras().getParcelable("Mascotte");
                if (mascotte2 != null) {
                    TextView txtMascotte2 = (TextView) findViewById(R.id.nommascotte2);
                    txtMascotte2.setText(mascotte2.getNom());

                    Drawable img = getResources().getDrawable(mascotte2.getIdImage());
                    Bitmap imgBitmap = ((BitmapDrawable) img).getBitmap();
                    imageMascotte2.setImageBitmap(imgBitmap);
                }
            }
        }
        if (requestCode == 3) {
            if (resultCode == 1) {
                equipement1 = data.getExtras().getParcelable("Equipement");
                equipementAjoutText1.setText(equipement1.getNom());
                mascotte1.setAttaque(mascotte1.getAttaque() + equipement1.getAttaque());
                mascotte1.setDefense(mascotte1.getDefense() + equipement1.getDefense());
                mascotte1.setVie(mascotte1.getVie() + equipement1.getVie());
            }
        }
        if (requestCode == 4) {
            if (resultCode == 1) {
                equipement2 = data.getExtras().getParcelable("Equipement");
                equipementAjoutText2.setText(equipement2.getNom());
                mascotte2.setAttaque(mascotte2.getAttaque() + equipement2.getAttaque());
                mascotte2.setDefense(mascotte2.getDefense() + equipement2.getDefense());
                mascotte2.setVie(mascotte2.getVie() + equipement2.getVie());
            }
        }
        if (mascotte1 != null && mascotte2 != null) {
            hideButtonMascotte(false);
        }
        if (equipement1 != null) {
            hideButtonEquipement(false,1);
        }
        if (equipement2 != null) {
            hideButtonEquipement(false,2);
        }
    }

    private void hideButtonMascotte(boolean bool) {
        btnCombat = (Button) findViewById(R.id.combat);
        if (bool) {
            btnCombat.setVisibility(View.GONE);
            btnEquipement1.setVisibility(View.GONE);
            btnEquipement2.setVisibility(View.GONE);
        } else {
            btnCombat.setVisibility(View.VISIBLE);
            btnEquipement1.setVisibility(View.VISIBLE);
            btnEquipement2.setVisibility(View.VISIBLE);
        }
    }

    private void hideButtonEquipement(boolean bool, int equipementNumero) {
        if (bool) {
            if (equipementNumero == 1) {
                equipementAjoutText1.setVisibility(View.GONE);
                btnDeleteEquipement1.setVisibility(View.GONE);
            } else if (equipementNumero == 2) {
                equipementAjoutText2.setVisibility(View.GONE);
                btnDeleteEquipement2.setVisibility(View.GONE);
            }else{
                equipementAjoutText1.setVisibility(View.GONE);
                btnDeleteEquipement1.setVisibility(View.GONE);
                equipementAjoutText2.setVisibility(View.GONE);
                btnDeleteEquipement2.setVisibility(View.GONE);
            }
        } else {
            if (equipementNumero == 1) {
                equipementAjoutText1.setVisibility(View.VISIBLE);
                btnDeleteEquipement1.setVisibility(View.VISIBLE);
            } else if (equipementNumero == 2) {
                equipementAjoutText2.setVisibility(View.VISIBLE);
                btnDeleteEquipement2.setVisibility(View.VISIBLE);
            }
        }
    }
}
