package main.boules;

public class Grille {
    private final char[][] grille;   // Représentation du plateau.
    private int taille;

    public Grille(int taille) {
        this.taille = taille;
        grille = new char[taille][taille];
        initialiserGrille();
    }

    /**
     * Initialise la grille avec des cases vides représentées par '.'.
     */
    private void initialiserGrille() {
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                grille[i][j] = '.';
            }
        }
    }

    public void addBoule(Boule boule) {
        grille[boule.getCoordonnees().getX()][boule.getCoordonnees().getY()] = boule.getColor().getColorChar();
    }

    public void remove(Coordonnees coord) {
        grille[coord.getX()][coord.getY()] = '.';
    }

    public boolean isOccupied(Coordonnees coord) {
        return grille[coord.getX()][coord.getY()] != '.';
    }


    public String toString() {
        StringBuilder s = new StringBuilder("   ");
        for(int i = 0; i < taille; i++) {
            char letter = (char) ('A' + i);
            s.append(letter).append("  ");
        }
        s.append("\n");
        for (int i = 0; i < taille; i++) {
            s.append(i + 1).append(i < 9 ? " " : "");
            for (int j = 0; j < taille; j++) {
                s.append(" ").append(grille[j][i]).append(" ");
            }
            s.append(i < 9 ? " " : "").append(i + 1).append("\n");
        }
        s.append("   ");
        for(int i = 0; i < taille; i++) {
            char letter = (char) ('A' + i);
            s.append(letter).append("  ");
        }
        return s.toString();
    }

    //=-=-=-= Getter =-=-=-=
    public int getTaille() {
        return taille;
    }

    public char getEmplacement(Coordonnees coord) {
        return grille[coord.getX()][coord.getY()];
    }

    public boolean isFull() {
        for (char[] row : grille) {
            for (char c : row) {
                if (c == '.')
                    return false;
            }
        }
        return true;
    }
}