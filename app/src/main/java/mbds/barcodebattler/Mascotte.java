package mbds.barcodebattler;

import java.util.ArrayList;

/**
 * Created by Alex on 25/10/2017.
 */

public class Mascotte {
    public String nom;
    public int niveau;
    public int vie;
    public int attaque;
    public int defense;
    public ArrayList<Equipement> equipements;

    public Mascotte(String nom, int niveau, int vie, int attaque, int defense) {
        this.nom = nom;
        this.niveau = niveau;
        this.vie = vie;
        this.attaque = attaque;
        this.defense = defense;
        equipements = new ArrayList<>();
    }
}
