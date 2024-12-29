package main.grille;

public class GrilleAffichage {
    public static String afficherGrille(Grille grille) {
        StringBuilder s = new StringBuilder("   ");
        int taille = grille.getTaille();

        // Ligne supérieure des chiffres
        for (int i = 0; i < taille; i++) {
            s.append(i + 1).append((i < 9 ? "  " : " "));
        }
        s.append("\n");

        // Contenu de la grille avec les lettres sur les côtés
        for (int i = 0; i < taille; i++) {
            char letter = (char) ('A' + i);
            s.append(letter).append(" "); // Lettre sur le côté gauche
            for (int j = 0; j < taille; j++) {
                s.append(" ").append(grille.getBouleAt(i, j)).append(" ");
            }
            s.append(letter).append("\n"); // Lettre sur le côté droit
        }

        // Ligne inférieure des chiffres
        s.append("   ");
        for (int i = 0; i < taille; i++) {
            s.append(i + 1).append((i < 9 ? "  " : " "));
        }

        return s.toString();
    }

}