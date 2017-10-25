package mbds.barcodebattler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

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

    public BarcodeBattlerBDD(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MASCOTTE = "CREATE TABLE IF NOT EXISTS MASCOTTE " +
                "(id integer primary key autoincrement," +
                "nom text not null ," +
                "niveau int not null," +
                "vie int not null," +
                "attaque int not null," +
                "int int not null;";
        db.execSQL(CREATE_MASCOTTE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addPersonne(Personne p) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nom", p.nom);
        values.put("prenom", p.prenom);
        if(p.image != null){
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            p.image.compress(Bitmap.CompressFormat.PNG, 100, bos);
            byte[] bArray = bos.toByteArray();
            values.put("image", bArray);
        }

        db.insert("PERSONNE", null, values);
        db.close();
    }

    public void UpdatePersonne(Personne p)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nom", p.nom);
        values.put("prenom", p.prenom);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        p.image.compress(Bitmap.CompressFormat.PNG, 100, bos);
        byte[] bArray = bos.toByteArray();
        values.put("image", bArray);
        db.update("PERSONNE", values, "id" + " = ?", new String[]{String.valueOf(p.id)});
        db.close();

    }

    protected ArrayList<Personne> getPersonnes() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Personne> pList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT id, nom, prenom, image FROM PERSONNE", null);

        if (cursor != null && cursor.moveToFirst())
        {
            do
            {
                //traiter la ligne
                Personne p = new Personne();
                p.id = cursor.getInt(0);
                p.nom = cursor.getString(1);
                p.prenom = cursor.getString(2);

//                ByteArrayOutputStream bos = new ByteArrayOutputStream();
//                p.image.compress(Bitmap.CompressFormat.PNG, 100, bos);
//                byte[] bArray = bos.toByteArray();
                if(cursor.getBlob(3) != null){
                    Bitmap bitmap = BitmapFactory.decodeByteArray(cursor.getBlob(3), 0, cursor.getBlob(3).length);
                    p.image = bitmap;
                }
                pList.add(p);
            } while (cursor.moveToNext());

        }
        return pList;
    }
    protected void deleteBDD(){
        SQLiteDatabase db = this.getWritableDatabase();
        String sqlDelete = "delete from Personne;";
        db.execSQL(sqlDelete);
    }
}
