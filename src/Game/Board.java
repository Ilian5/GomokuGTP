package Game;

import java.util.ArrayList;

public class Board {

    private int taille; //La largeur et la hauteur sont similaires
    private ArrayList<Boule> boule;
    private char[][] grille;


    public Board(int taille) {
        this.taille = taille;
        boule = new ArrayList<>();
        initialiseGrille();
    }

    private void initialiseGrille() {
        grille = new char[taille][taille];
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                grille[i][j] = '.'; // '.' représente une case vide
            }
        }
    }

    public void reloadBoule() {
        for(Boule b : boule) {
            grille[b.getCoordonnees().getX()][b.getCoordonnees().getY()] = 'X';
        }
    }

    public void addBoule(Boule boule) {
        this.boule.add(boule);
    }

    public int getTaille() {
        return taille;
    }

    public void setLargeur(int taille) {
        this.taille = taille;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("  ");
        for (int i = 0; i < taille; i++) {
            s.append((i < 10 ? " " : "") + i + " "); // Indices de colonnes
        }
        s.append("\n");

        for (int i = 0; i < grille.length; i++) {
            s.append((i < 10 ? " " : "") + i + " "); // Indices de lignes
            for (int j = 0; j < grille[i].length; j++) {
                s.append(grille[i][j] + "  ");
            }
            s.append("\n");
        }
        return s.toString();
    }

}
