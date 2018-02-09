package mbds.barcodebattler;

import android.util.Log;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

/**
 * Created by Laeticia on 07/02/2018.
 */

public abstract class ServiceCombat {

    /**
     * Combat au tour par tour : attaquant tire un nombre aléatoire entre 0 et sa capacité d'attaque
     * adversaire devra tire un nombre aléatoire entre 0 et sa capacité de défense,
     * adversaire sera blessé (d’attaque - défense) point de vie
     * @param logsCombat se remplit
     * @return vainqueur
     */
    public static Mascotte lancerCombat(Mascotte mascotte1, Mascotte mascotte2, ArrayList<LogCombat> logsCombat) {

        int nbTours = 0;

        Log.d("COMBAT", "---- DEBUT COMBAT ENTRE " + mascotte1.getNom() + " " + mascotte2.getNom());

        Random rand = new Random();

        // Vitesse pour déterminer le premier ?
        Mascotte attaquant = mascotte1;
        Mascotte adversaire = mascotte2;

        int attaqueTour;
        int defenseTour;

        // Adapte si nom identique
        if (Objects.equals(attaquant.getNom(), adversaire.getNom())) {
            attaquant.setNom(attaquant.getNom() + " (1)");
            adversaire.setNom(adversaire.getNom() + " (2)");
            Log.d("COMBAT", "NOMS CHANGES : " + attaquant.getNom() + " " + adversaire.getNom());
        }

        while(true) {

            Log.d("COMBAT", "-- TOUR " + nbTours);
            Log.d("COMBAT", "Attaquant : " + attaquant.getNom() + " avec " + attaquant.getVie() + "PV");
            Log.d("COMBAT", "Adversaire : " + adversaire.getNom() + " avec " + adversaire.getVie() + "PV");

            nbTours++;

            attaqueTour = rand.nextInt(attaquant.getAttaque() + 1);
            defenseTour = rand.nextInt(adversaire.getDefense() + 1);

            Log.d("COMBAT", "Attaque : " + attaqueTour);
            Log.d("COMBAT", "Défense : " + defenseTour);

            int attaqueTourApresDefense = attaqueTour - defenseTour;
            Log.d("COMBAT", "attaqueTourApresDefense : " + attaqueTourApresDefense);

            int attaqueFinale = attaqueTourApresDefense;

            if (attaqueTourApresDefense > -1) {

                Log.d("COMBAT", "Perd " + attaqueTourApresDefense + "PV");

                adversaire.setVie(adversaire.getVie() - attaqueTourApresDefense);

                Log.d("COMBAT", "Adversaire passe à : " + adversaire.getVie() + " PV");
            }
            else {
                attaqueFinale = 0;
                Log.d("COMBAT", "Pas de PV perdus");
            }

            try {
                logsCombat.add(new LogCombat(nbTours, (Mascotte)attaquant.clone(), attaqueFinale, (Mascotte)adversaire.clone()));
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }

            if (adversaire.getVie() <= 0) {
                Log.d("COMBAT", "-> Vainqueur : " + attaquant.getNom());
                return attaquant;
            }

            Mascotte tmpAdversaire = adversaire;
            adversaire = attaquant;
            attaquant = tmpAdversaire;


        }

    }
}
