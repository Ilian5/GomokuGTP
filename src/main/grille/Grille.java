package main.grille;

import main.boules.Boule;
import main.boules.Coordonnees;
import main.utils.Color;

public class Grille {

    private final char[][] grille;   // Représentation du plateau.
    private int taille;

    public Grille(int taille) {
        this.taille = taille;
        grille = new char[taille][taille];
        initialiserGrille();
    }

    public void initialiserGrille() {
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                grille[i][j] = '.';
            }
        }
    }

    public void addBoule(Boule boule) {
        grille[boule.getCoordonnees().getX()][boule.getCoordonnees().getY()] = boule.getColor().toChar();
    }

    public void remove(Coordonnees coord) {
        grille[coord.getX()][coord.getY()] = '.';
    }

    public boolean isOccupied(Coordonnees coord) {
        return grille[coord.getX()][coord.getY()] != '.';
    }

    public int getTaille() {
        return grille.length;
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

    public boolean hasWinner(int nbAlignementWin) {
        for (int x = 0; x < taille; x++) {
            for (int y = 0; y < taille; y++) {
                char current = this.getEmplacement(new Coordonnees(x, y));
                if (current != '.') {
                    // Vérification des alignements
                    if (checkDirection(x, y, 1, 0, current, nbAlignementWin) || // Horizontal
                        checkDirection(x, y, 0, 1, current, nbAlignementWin) || // Vertical
                        checkDirection(x, y, 1, 1, current, nbAlignementWin) || // Diagonale principale
                        checkDirection(x, y, 1, -1, current, nbAlignementWin)) { // Diagonale secondaire
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean checkDirection(int startX, int startY, int dx, int dy, char boule, int nbAlignementWin) {
        int count = 0;
        for (int i = 0; i < nbAlignementWin; i++) {
            int x = startX + i * dx;
            int y = startY + i * dy;
            if (x < 0 || x >= taille || y < 0 || y >= taille) { // taille = dimension de la grille
                break;
            }
            if(this.getEmplacement(new Coordonnees(x, y)) == boule) {
                count++;
            } else {
                break;
            }
        }
        return count == nbAlignementWin;
    }

    public Color getColorWin(int nbAlignementWin) {
        for (int x = 0; x < taille; x++) {
            for (int y = 0; y < taille; y++) {
                char current = this.getEmplacement(new Coordonnees(x, y));
                if (current != '.') {
                    if (checkDirection(x, y, 1, 0, current, nbAlignementWin) || // Horizontal
                        checkDirection(x, y, 0, 1, current, nbAlignementWin) || // Vertical
                        checkDirection(x, y, 1, 1, current, nbAlignementWin) || // Diagonale principale
                        checkDirection(x, y, 1, -1, current, nbAlignementWin)) { // Diagonale secondaire
                        return (current == 'X' ? Color.Black : Color.White);
                    }
                }
            }
        }
        return null;
    }


}