package mbds.barcodebattler;

import java.io.Serializable;

/**
 * Created by laeticiapierre on 27/10/2017.
 */

public class LogCombat implements Serializable {

    int numTour;
    Mascotte attaquant;
    int attaque;
    Mascotte adversaireApresAttaque;

    public LogCombat(int numTour, Mascotte attaquant, int attaque, Mascotte adversaireApresAttaque) {
        this.numTour = numTour;
        this.attaquant = attaquant;
        this.attaque = attaque;
        this.adversaireApresAttaque = adversaireApresAttaque;
    }

    public int getNumTour() {
        return numTour;
    }

    public Mascotte getAttaquant() {
        return attaquant;
    }

    public int getAttaque() {
        return attaque;
    }

    public Mascotte getAdversaireApresAttaque() {
        return adversaireApresAttaque;
    }
}
