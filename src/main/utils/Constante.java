package main.utils;

public class Constante {
    //Taille par défaut du plateau de jeu (19x19)
    public static final int TAILLE_DEFAULT_BOARD = 19;

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

}
