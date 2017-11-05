package mbds.barcodebattler;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Alex on 25/10/2017.
 */

public class Mascotte implements Cloneable, Parcelable {
    private int id;
    private String nom;
    private int niveau;
    private int vie;
    private int attaque;
    private int defense;
    private Bitmap image;
    private ArrayList<Equipement> equipements;

    public Mascotte(){

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
        try {
            this.image = in.readParcelable(Bitmap.class.getClassLoader());
        }
        catch(Exception e) {
            Log.e("error", "parcelable image");
        }

        //TODO
        this.equipements = new ArrayList<>();
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
}
