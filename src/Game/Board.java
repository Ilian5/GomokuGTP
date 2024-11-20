package Game;

import java.util.ArrayList;

public class Board {

    private ArrayList<Boule> boule;
    private char[][] grille;
    private int taille;

    public Board(int taille) {
        boule = new ArrayList<Boule>();
        this.taille = taille;
        grille = new char[taille][taille];
        initialiseGrille();
    }


    public void initialiseGrille() {
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                grille[i][j] = '.';
            }
        }
    }

    public void reloadBoule() {
        for(Boule b : boule) {
            grille[b.getCoordonnees().getX()][b.getCoordonnees().getY()] = b.getColor().getColorChar();
        }
    }

    public String toString() {
        StringBuilder s = new StringBuilder("   ");
        reloadBoule();
        for(int i = 0; i < taille; i++) {
            s.append(i < 10 ? " " : "").append(i).append(" ");
        }
        s.append("\n");

        for (int i = 0; i < taille; i++) {
            s.append(i < 10 ? " " : "").append(i).append(" ");
            for (int j = 0; j < taille; j++) {
                s.append(" ").append(grille[i][j]).append(" ");
            }
            s.append("\n");
        }
        return s.toString();
    }

    public void addBoule(Boule b) {
        boule.add(b);
    }

}
