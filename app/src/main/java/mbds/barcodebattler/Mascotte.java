package mbds.barcodebattler;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;
import android.util.Log;
import android.webkit.ConsoleMessage;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Console;
import java.io.File;
import java.io.FileReader;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;

/**
 * Created by Alex on 25/10/2017.
 */

@Root
public class Mascotte implements Cloneable, Parcelable, Serializable {
    private int id;
    @Element
    private String nom;
    @Element
    private int niveau;
    @Element
    private int vie;
    @Element
    private int attaque;
    @Element
    private int defense;
    @Element
    private int idImage;
    @Element
    private int idEquipement;

    public Mascotte() {

    }

    public Mascotte(String nom, int niveau, int vie, int attaque, int defense, int idImage) {
        this.nom = nom;
        this.niveau = niveau;
        this.vie = vie;
        this.attaque = attaque;
        this.defense = defense;
        this.idImage = idImage;
    }

    protected Mascotte(Parcel in) {
        this.id = in.readInt();
        this.nom = in.readString();
        this.niveau = in.readInt();
        this.vie = in.readInt();
        this.attaque = in.readInt();
        this.defense = in.readInt();
        this.idImage = in.readInt();
    }

    public static final Creator<Mascotte> CREATOR = new Creator<Mascotte>() {
        @Override
        public Mascotte createFromParcel(Parcel in) {
            return new Mascotte(in);
        }

        @Override
        public Mascotte[] newArray(int size) {
            return new Mascotte[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getNiveau() {
        return niveau;
    }

    public void setNiveau(int niveau) {
        this.niveau = niveau;
    }

    public int getVie() {
        return vie;
    }

    public void setVie(int vie) {
        this.vie = vie;
    }

    public int getAttaque() {
        return attaque;
    }

    public void setAttaque(int attaque) {
        this.attaque = attaque;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getIdImage() {
        return idImage;
    }

    public void setIdImage(int idImage) {
        this.idImage = idImage;
    }

    public int getIdEquipement() {
        return idEquipement;
    }

    public void setIdEquipement(int idEquipement) {
        this.idEquipement = idEquipement;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return "Mascotte{" +
                "nom='" + nom + '\'' +
                ", niveau=" + niveau +
                ", vie=" + vie +
                ", attaque=" + attaque +
                ", defense=" + defense +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.id);
        parcel.writeString(this.nom);
        parcel.writeInt(this.niveau);
        parcel.writeInt(this.vie);
        parcel.writeInt(this.attaque);
        parcel.writeInt(this.defense);
        parcel.writeInt(this.idImage);
    }

    public String serialize() {
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        this.image.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
//        byte[] byteArray = byteArrayOutputStream.toByteArray();
//        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
//        String ret = this.nom + "||" + this.attaque + "||" + encoded;
        Persister persister = new Persister();

        StringWriter mascotteText = new StringWriter();
        try {
            persister.write(this, mascotteText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String mascotteFormat = mascotteText.getBuffer().toString();
        mascotteFormat = mascotteFormat.replace("\n", "").replace("\r", "");
        return mascotteFormat;
    }

    public static Mascotte deserialize(String encode) {
        Mascotte m = new Mascotte();
        Serializer serializer = new Persister();
        try {
            m = serializer.read(Mascotte.class, encode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return m;
    }

    public void setId(int id) {
        this.id = id;
    }

//    public void testXml(Context context) {
//        Persister persister = new Persister();
////        File file = new File(context.getFilesDir(), "mascotte.xml");
//        StringWriter mascotteTxt = new StringWriter();
////        StringBuilder mascotteText = new StringBuilder();
//        try {
//            persister.write(this, mascotteTxt);
////            BufferedReader br = new BufferedReader(new FileReader(mascotteTxt));
////            String line;
////            while ((line = br.readLine()) != null) {
////                mascotteText.append(line);
////                mascotteText.append('\n');
////            }
////            br.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        Log.i("MascotteString", mascotteTxt.getBuffer().toString());
//
//        Mascotte m = new Mascotte();
//        Serializer serializer = new Persister();
//        try
//        {
//            m = serializer.read(Mascotte.class, mascotteTxt.getBuffer().toString());
//            Log.i("Mascotte deserialize",m.getNom());
//        } catch (Exception e)
//
//        {
//            e.printStackTrace();
//        }
//    }
}
