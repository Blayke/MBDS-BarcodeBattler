package mbds.barcodebattler;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by Alex on 25/10/2017.
 */

public class Mascotte {
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
        equipements = new ArrayList<>();
    }

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
}
