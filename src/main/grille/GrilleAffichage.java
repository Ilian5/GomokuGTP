package main.grille;

import main.boules.Coordonnees;

public class GrilleAffichage {
    public static String afficherGrille(Grille grille) {
        StringBuilder s = new StringBuilder("   ");
        for(int i = 0; i < grille.getTaille(); i++) {
            char letter = (char) ('A' + i);
            s.append(letter).append("  ");
        }
        s.append("\n");
        for (int i = 0; i < grille.getTaille(); i++) {
            s.append(i + 1).append(i < 9 ? " " : "");
            for (int j = 0; j < grille.getTaille(); j++) {
                s.append(" ").append(grille.getEmplacement(new Coordonnees(j, i))).append(" ");
            }
            s.append(i < 9 ? " " : "").append(i + 1).append("\n");
        }
        s.append("   ");
        for(int i = 0; i < grille.getTaille(); i++) {
            char letter = (char) ('A' + i);
            s.append(letter).append("  ");
        }
        return s.toString();
    }
}