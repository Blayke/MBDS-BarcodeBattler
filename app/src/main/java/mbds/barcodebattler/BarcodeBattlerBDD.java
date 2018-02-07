package mbds.barcodebattler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created by Alex on 25/10/2017.
 */

public class BarcodeBattlerBDD extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "GESTION_ETUDIANT.db";
    private Context context;

    public BarcodeBattlerBDD(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        this.remplirBase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MASCOTTE = "CREATE TABLE IF NOT EXISTS MASCOTTE " +
                "(id integer primary key autoincrement," +
                "nom text not null ," +
                "niveau int not null," +
                "vie int not null," +
                "attaque int not null," +
                "defense int not null," +
                "image BLOB);";

        String CREATE_EQUIPEMENT = "CREATE TABLE IF NOT EXISTS EQUIPEMENT " +
                "(id integer primary key autoincrement," +
                "nom text not null ," +
                "idImage int not null ," +
                "attaque int not null," +
                "defense int not null," +
                "vie int not null);";

        db.execSQL(CREATE_MASCOTTE);
        db.execSQL(CREATE_EQUIPEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addMascotte(Mascotte mascotte) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nom", mascotte.getNom());
        values.put("niveau", mascotte.getNiveau());
        values.put("vie", mascotte.getVie());
        values.put("attaque", mascotte.getAttaque());
        values.put("defense", mascotte.getDefense());

        if(mascotte.getImage() != null){
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            mascotte.getImage().compress(Bitmap.CompressFormat.PNG, 100, bos);
            byte[] bArray = bos.toByteArray();
            values.put("image", bArray);
        }

        db.insert("MASCOTTE", null, values);
        db.close();
    }

    public void addEquipement(Equipement equipement) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nom", equipement.getNom());
        values.put("vie", equipement.getVie());
        values.put("attaque", equipement.getAttaque());
        values.put("defense", equipement.getDefense());
        values.put("idImage", equipement.getIdImage());

        db.insert("EQUIPEMENT", null, values);
        db.close();
    }

    public void deleteMascotte(Mascotte mascotte) {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = "ID=" + mascotte.getId();
        Log.d("DELETE", "Vrai id de la mascotte : " + mascotte.getId());
        db.delete("MASCOTTE", where, null);
        db.close();
    }

    public void deleteEquipement(Equipement equipement) {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = "ID=" + equipement.getId();
        Log.d("DELETE", "Vrai id de l'équipement : " + equipement.getId());
        db.delete("EQUIPEMENT", where, null);
        db.close();
    }

    public void UpdateMacotte(Mascotte mascotte)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nom", mascotte.getNom());
        values.put("niveau", mascotte.getNiveau());
        values.put("vie", mascotte.getVie());
        values.put("attaque", mascotte.getAttaque());
        values.put("defense", mascotte.getDefense());
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        mascotte.getImage().compress(Bitmap.CompressFormat.PNG, 100, bos);
        byte[] bArray = bos.toByteArray();
        values.put("image", bArray);
        db.update("MASCOTTE", values, "id" + " = ?", new String[]{String.valueOf(mascotte.getId())});
        db.close();

    }

    public void UpdateEquipement(Equipement equipement)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nom", equipement.getNom());
        values.put("vie", equipement.getVie());
        values.put("attaque", equipement.getAttaque());
        values.put("defense", equipement.getDefense());
        values.put("idImage", equipement.getIdImage());
        db.update("EQUIPEMENT", values, "id" + " = ?", new String[]{String.valueOf(equipement.getId())});
        db.close();

    }

    protected ArrayList<Mascotte> getMascottes() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Mascotte> mList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT id, nom, niveau, vie, attaque, defense, image FROM MASCOTTE", null);

        if (cursor != null && cursor.moveToFirst())
        {
            do
            {
                //traiter la ligne
                Mascotte m = new Mascotte();
                m.setId(cursor.getInt(0));
                m.setNom(cursor.getString(1));
                m.setNiveau(cursor.getInt(2));
                m.setVie(cursor.getInt(3));
                m.setAttaque(cursor.getInt(4));
                m.setDefense(cursor.getInt(5));


                if(cursor.getBlob(6) != null){
                    Bitmap bitmap = BitmapFactory.decodeByteArray(cursor.getBlob(6), 0, cursor.getBlob(6).length);
                    m.setImage(bitmap);
                }
                mList.add(m);
            } while (cursor.moveToNext());

        }
        return mList;
    }

    protected ArrayList<Equipement> getEquipements() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Equipement> eList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT id, nom, vie, attaque, defense, idImage FROM EQUIPEMENT", null);

        if (cursor != null && cursor.moveToFirst())
        {
            do
            {
                Equipement e = new Equipement();
                e.setId(cursor.getInt(0));
                e.setNom(cursor.getString(1));
                e.setVie(cursor.getInt(2));
                e.setAttaque(cursor.getInt(3));
                e.setDefense(cursor.getInt(4));
                e.setIdImage(cursor.getInt(5));
                eList.add(e);
            } while (cursor.moveToNext());

        }
        return eList;
    }

    protected void deleteBDD(){
        SQLiteDatabase db = this.getWritableDatabase();
        String sqlDeleteMascotte = "delete from MASCOTTE;";
        String sqlDeleteEquipement = "delete from EQUIPEMENT";
        db.execSQL(sqlDeleteMascotte);
        db.execSQL(sqlDeleteEquipement);
    }

    public void remplirBase() {
        if (this.getMascottes().size() == 0) {

            Mascotte m1 = new Mascotte("Albie", 15, 20, 50, 15);
            Bitmap bm = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.albie);
            m1.setImage(bm);

            this.addMascotte(m1);

            m1 = new Mascotte("Alex", 20, 65, 40 , 30);
            bm = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.alex);
            m1.setImage(bm);

            this.addMascotte(m1);

            m1 = new Mascotte("Âme des aspects", 40, 100, 30 , 20);
            bm = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.ame_des_aspects);
            m1.setImage(bm);

            this.addMascotte(m1);
        }
        if(this.getEquipements().size() == 0){
            Equipement e1 = new Equipement("Épée courte basique",10,20,15,1);
            Equipement e2 = new Equipement("Pugilat légendaire",30,0,15,2);
        }
    }
}
