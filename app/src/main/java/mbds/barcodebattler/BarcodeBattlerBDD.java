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
                "int int not null" +
                "image BLOB);";
        db.execSQL(CREATE_MASCOTTE);
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
                m.setNom(cursor.getString(1));
                m.setNiveau(cursor.getInt(2));
                m.setVie(cursor.getInt(3));
                m.setAttaque(cursor.getInt(4));
                m.setDefense(cursor.getInt(5));


                if(cursor.getBlob(6) != null){
                    Bitmap bitmap = BitmapFactory.decodeByteArray(cursor.getBlob(3), 0, cursor.getBlob(3).length);
                    m.setImage(bitmap);
                }
                mList.add(m);
            } while (cursor.moveToNext());

        }
        return mList;
    }
    protected void deleteBDD(){
        SQLiteDatabase db = this.getWritableDatabase();
        String sqlDelete = "delete from MASCOTTE;";
        db.execSQL(sqlDelete);
    }
}
