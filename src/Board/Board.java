package Board;

import Boules.Boule;

import java.util.ArrayList;

public class Board {

    private final ArrayList<Boule> boule;
    private final char[][] grille;
    private final int taille;

    public Board(int taille) {
        boule = new ArrayList<Boule>();
        this.taille = taille;
        grille = new char[taille][taille];
        initialiseGrille();
    }

    public ArrayList<Boule> getBoule() {
        return boule;
    }

    public void reloadBoule() {
        for(Boule b : boule) {
            grille[b.getCoordonnees().getX()][b.getCoordonnees().getY()] = b.getColor().getColorChar();
        }
    }

    public void addBoule(Boule b) {
        boule.add(b);
    }

    public void initialiseGrille() {
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                grille[i][j] = '.';
            }
        }
    }

    public String toString() {
        StringBuilder s = new StringBuilder("   ");
        reloadBoule();
        for(int i = 0; i < taille; i++) {
            char letter = (char) ('A' + i);
            s.append(letter).append("  ");
        }
        s.append("\n");

        for (int i = 0; i < taille; i++) {
            s.append(i).append(i < 10 ? " " : "");
            for (int j = 0; j < taille; j++) {
                s.append(" ").append(grille[i][j]).append(" ");
            }
            s.append(i < 10 ? " " : "").append(i).append("\n");
        }
        s.append("   ");
        for(int i = 0; i < taille; i++) {
            char letter = (char) ('A' + i);
            s.append(letter).append("  ");
        }
        return s.toString();
    }

    public char[][] getGrille() {
        return grille;
    }
}
