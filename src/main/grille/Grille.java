package main.grille;

import main.utils.Color;

import java.util.ArrayList;
import java.util.List;

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
                grille[i][j] = Color.Blank.toChar();
            }
        }
    }

    public void addBouleAt(int row, int col, Color color) {
        grille[row][col]=color.toChar();
    }

    public void remove(int row, int col) {
        grille[row][col] = Color.Blank.toChar();
    }

    public boolean isOccupied(int row, int col) {
        return grille[row][col] != Color.Blank.toChar();
    }

    public int getTaille() {
        return grille.length;
    }

    public char getBouleAt(int row, int col) {
        return grille[row][col];
    }

    public boolean isFull() {
        for (char[] row : grille) {
            for (char c : row) {
                if (c == Color.Blank.toChar())
                    return false;
            }
        }
        return true;
    }

    public char getWinner(int nbAlignementWin) {
        for (int x = 0; x < taille; x++) {
            for (int y = 0; y < taille; y++) {
                char current = this.getBouleAt(x, y);
                if (current != Color.Blank.toChar()) {
                    if (checkDirection(x, y, 1, 0, current, nbAlignementWin) || // Horizontal
                        checkDirection(x, y, 0, 1, current, nbAlignementWin) || // Vertical
                        checkDirection(x, y, 1, 1, current, nbAlignementWin) || // Diagonale principale
                        checkDirection(x, y, 1, -1, current, nbAlignementWin)) { // Diagonale secondaire
                        return current; // Retourne la couleur gagnante
                    }
                }
            }
        }
        return Color.Blank.toChar(); // Aucun gagnant
    }

    public boolean checkDirection(int startX, int startY, int dx, int dy, char boule, int nbAlignementWin) {
        int count = 0;
        for (int i = 0; i < nbAlignementWin; i++) {
            int x = startX + i * dx;
            int y = startY + i * dy;
            if (x < 0 || x >= taille || y < 0 || y >= taille) { // taille = dimension de la grille
                break;
            }
            if(this.getBouleAt(x, y) == boule) {
                count++;
            } else {
                break;
            }
        }
        return count == nbAlignementWin;
    }

    public List<int[]> getMovePossible() {
        List<int[]> availableMoves = new ArrayList<>();
        for (int x = 0; x < this.taille; x++) {
            for (int y = 0; y < this.taille; y++) {
                if (this.getBouleAt(x, y) == Color.Blank.toChar()) { // Vérifie si l'emplacement est vide (ajustez selon votre code)
                    availableMoves.add(new int[]{x, y});
                }
            }
        }
        return availableMoves;
    }

}