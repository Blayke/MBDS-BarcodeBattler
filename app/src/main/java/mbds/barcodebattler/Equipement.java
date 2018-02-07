package mbds.barcodebattler;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Alex on 25/10/2017.
 */

public class Equipement implements Cloneable, Parcelable {

    private int id;
    private String nom;
    private int attaque;
    private int defense;
    private int vie;
    private int idImage;

    public Equipement(){

    }
    public Equipement(String nom, int boostAttaque, int boostDefense, int boostVie, int idImage){
        this.nom = nom;
        this.attaque = boostAttaque;
        this.defense = boostDefense;
        this.vie = boostVie;
        this.idImage = idImage;
    }

    protected Equipement(Parcel in) {
        this.id = in.readInt();
        this.nom = in.readString();
        this.attaque = in.readInt();
        this.defense = in.readInt();
        this.vie = in.readInt();
        this.idImage = in.readInt();

    }
    public static final Creator<Equipement> CREATOR = new Creator<Equipement>() {
        @Override
        public Equipement createFromParcel(Parcel in) {
            return new Equipement(in);
        }

        @Override
        public Equipement[] newArray(int size) {
            return new Equipement[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getVie() {
        return vie;
    }

    public void setVie(int vie) {
        this.vie = vie;
    }

    public int getIdImage() {
        return idImage;
    }

    public void setIdImage(int idImage) {
        this.idImage = idImage;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.id);
        parcel.writeString(this.nom);
        parcel.writeInt(this.vie);
        parcel.writeInt(this.attaque);
        parcel.writeInt(this.defense);
        parcel.writeInt(this.idImage);
    }
}
