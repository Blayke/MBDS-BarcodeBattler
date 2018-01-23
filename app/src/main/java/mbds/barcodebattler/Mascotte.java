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
import java.util.ArrayList;

/**
 * Created by Alex on 25/10/2017.
 */

@Root
public class Mascotte implements Cloneable, Parcelable {
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
    private Bitmap image;
    private ArrayList<Equipement> equipements;

    public Mascotte() {

    }

    public Mascotte(String nom, int niveau, int vie, int attaque, int defense) {
        this.nom = nom;
        this.niveau = niveau;
        this.vie = vie;
        this.attaque = attaque;
        this.defense = defense;
        this.equipements = new ArrayList<>();
    }

    protected Mascotte(Parcel in) {
        this.id = in.readInt();
        this.nom = in.readString();
        this.niveau = in.readInt();
        this.vie = in.readInt();
        this.attaque = in.readInt();
        this.defense = in.readInt();
        /*try {
            this.image = in.readParcelable(Bitmap.class.getClassLoader());
        } catch (Exception e) {
            Log.e("error", "parcelable image");
        }*/
        this.image = null;

        //TODO
//        this.equipements = new ArrayList<>();
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

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public ArrayList<Equipement> getEquipements() {
        return equipements;
    }

    public void setEquipements(ArrayList<Equipement> equipements) {
        this.equipements = equipements;
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
                ", equipements=" + equipements +
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
        if (this.image != null) parcel.writeParcelable(this.image, i);

        //TODO equipements
    }

    public String serialize(Context context) {
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        this.image.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
//        byte[] byteArray = byteArrayOutputStream.toByteArray();
//        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
//        String ret = this.nom + "||" + this.attaque + "||" + encoded;
        Persister persister = new Persister();
        File file = new File(context.getFilesDir(), "mascotte.xml");
        StringBuilder mascotteText = new StringBuilder();
        try {
            persister.write(this, file);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                mascotteText.append(line);
                mascotteText.append('\n');
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mascotteText.toString();
    }

    public static Mascotte deserialize(String encode) {
        Mascotte m = new Mascotte();
        Serializer serializer = new Persister();
        try {
            m = serializer.read(Mascotte.class, encode);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        m.nom = serialezMascotte[0];
//        m.attaque = serialezMascotte[1];
//        byte[] decodedString = Base64.decode(serialezMascotte[2], Base64.DEFAULT);
//        m.image = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return m;
    }

//    public void testXml(Context context) {
//        Persister persister = new Persister();
//        File file = new File(context.getFilesDir(), "mascotte.xml");
//        StringBuilder mascotteText = new StringBuilder();
//        try {
//            persister.write(this, file);
//            BufferedReader br = new BufferedReader(new FileReader(file));
//            String line;
//            while ((line = br.readLine()) != null) {
//                mascotteText.append(line);
//                mascotteText.append('\n');
//            }
//            br.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        Log.i("MascotteString", mascotteText.toString());
//
//        Mascotte m = new Mascotte();
//        Serializer serializer = new Persister();
//        try
//        {
//            m = serializer.read(Mascotte.class, mascotteText.toString());
//            Log.i("Mascotte deserialize",m.getNom());
//        } catch (Exception e)
//
//        {
//            e.printStackTrace();
//        }
//    }
}
