package main.utils;

public class Constante {
    // Taille par défaut du plateau de jeu (19x19)
    public static final int TAILLE_DEFAULT_BOARD = 19;

    // Taille minimale autorisée pour un plateau de jeu (3x3).
    public static final int MIN_BOARD = 3;

    // Taille maximale autorisée pour un plateau de jeu (26x26).
    public static final int MAX_BOARD = 26;

    // Alignement requis pour gagner sur un petit plateau
    public static final int ALIGNMENT_TO_WIN_SMALL = 3;

    // Alignement requis pour gagner sur un plateau moyen (entre 5 et 8 inclus)
    public static final int ALIGNMENT_TO_WIN_MEDIUM = 4;

    // Alignement requis pour gagner sur un grand plateau
    public static final int ALIGNMENT_TO_WIN_LARGE = 5;

    /**
     * Méthode utilitaire pour déterminer le nombre d'alignements requis
     * en fonction de la taille du plateau.
     *
     * @param taille taille du plateau
     * @return nombre d'alignements requis pour gagner
     */
    public static int getAlignmentToWin(int taille) {
        if (taille < 5) {
            return ALIGNMENT_TO_WIN_SMALL;
        } else if (taille <= 8) {
            return ALIGNMENT_TO_WIN_MEDIUM;
        } else {
            return ALIGNMENT_TO_WIN_LARGE;
        }
    }
}
