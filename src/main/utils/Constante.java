package main.utils;

public class Constante {
    //Taille par défaut du plateau de jeu (19x19)
    public static final int TAILLE_DEFAULT_BOARD = 3;

    //Taille minimale autorisée pour un plateau de jeu (3x3).
    public static final int MIN_BOARD = 3;

    //Taille maximale autorisée pour un plateau de jeu (26x26).
    public static final int MAX_BOARD = 26;

    // Taille minimale du plateau pour nécessiter 5 alignements pour gagner
    public static final int MIN_SIZE_FOR_FIVE_ALIGNMENT = 8;

    // Alignement requis pour gagner sur un petit plateau
    public static final int ALIGNMENT_TO_WIN_SMALL = 3;

    // Alignement requis pour gagner sur un grand plateau
    public static final int ALIGNMENT_TO_WIN_LARGE = 5;

    // Score attribué pour un alignement gagnant
    public static final int WINNING_ALIGNMENT_SCORE = 1000000;

    // Score attribué pour bloquer un alignement gagnant adverse
    public static final int BLOCK_WINNING_ALIGNMENT_SCORE = 900000;

    // Score attribué pour un alignement presque gagnant (e.g., 4/5 boules alignées)
    public static final int ALMOST_WINNING_ALIGNMENT_SCORE = 10000;

    // Score attribué pour bloquer un alignement presque gagnant
    public static final int BLOCK_ALMOST_WINNING_ALIGNMENT_SCORE = 9000;

    // Score pour une victoire
    public static final int WIN_SCORE = 1000000;

    // Score pour bloquer une victoire imminente
    public static final int BLOCK_WIN_SCORE = 900000;

}
