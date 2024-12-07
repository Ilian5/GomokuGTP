package main.board;

import main.boules.Boule;
import main.boules.Coordonnees;
import main.utils.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Représente le plateau de jeu pour Gomoku.
 */
public class Board {

    private static final int NBR_BOULES_POUR_GAGNER = 5; // Le nombre de boule à aligner pour gagner
    private final List<Boule> boules; // Liste des main.boules placées sur le plateau.
    private final char[][] grille;   // Représentation du plateau.
    private final int taille;        // Taille du plateau.

    /**
     * Constructeur de la classe {@code Board}.
     *
     * @param taille Taille du plateau.
     */
    public Board(int taille) {
        this.taille = taille;
        this.boules = new ArrayList<>();
        this.grille = new char[taille][taille];
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

    /**
     * Ajoute une boule sur le plateau.
     *
     * @param boule La boule à ajouter.
     * @throws IllegalArgumentException si la case est déjà occupée.
     */
    public void addBoule(Boule boule) {
        Coordonnees coord = boule.getCoordonnees();
        if (isOccupied(coord)) {
            throw new IllegalArgumentException("invalid vertex");
        }
        boules.add(boule);
        grille[coord.getX()][coord.getY()] = boule.getColor().getColorChar(); // Marque la case sur la grille.
    }

    public void removeBoule(Coordonnees coord) {
        if(!isOccupied(coord)) {
            throw new IllegalArgumentException("i can't remove a boule");
        }
        boules.removeIf(boule -> boule.getCoordonnees().equal(coord));
        grille[coord.getX()][coord.getY()] = '.';
    }

    /**
     * Vérifie si une case est occupée.
     * @param coord Coordonnées de la case à vérifier.
     * @return {@code true} si la case est occupée, {@code false} sinon.
     */
    public boolean isOccupied(Coordonnees coord) {
        if (!isWithinBounds(coord)) {
            throw new IllegalArgumentException("invalid vertex, invalid move");
        }
        return grille[coord.getX()][coord.getY()] != '.';
    }

    /**
     * Vérifie si les coordonnées sont dans les limites du plateau.
     * @param coord Coordonnées à vérifier.
     * @return {@code true} si les coordonnées sont valides, {@code false} sinon.
     */
    private boolean isWithinBounds(Coordonnees coord) {
        return coord.getX() >= 0 && coord.getX() < taille &&
                coord.getY() >= 0 && coord.getY() < taille;
    }

    public List<Coordonnees> getMovePossible() {
        List<Coordonnees> moves = new ArrayList<>();
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
             if(!isOccupied(new Coordonnees(i, j)))
                 moves.add(new Coordonnees(i, j));
            }
        }
        return moves;
    }

    /**
     * Retourne une représentation en chaîne de caractères du plateau.
     * @return Une chaîne représentant l'état du plateau.
     */
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

    public boolean hasWinner() {
        for (int x = 0; x < grille.length; x++) {
            for (int y = 0; y < grille[x].length; y++) {
                char boule = grille[x][y];
                if (boule != '.') {
                    if (checkDirection(x, y, 1, 0, boule) || // Horizontal
                        checkDirection(x, y, 0, 1, boule) || // Vertical
                        checkDirection(x, y, 1, 1, boule) || // Diagonale principale
                        checkDirection(x, y, 1, -1, boule)) { // Diagonale secondaire
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean checkDirection(int startX, int startY, int dx, int dy, char boule) {
        int count = 0;
        for (int i = 0; i < NBR_BOULES_POUR_GAGNER; i++) {
            int x = startX + i * dx;
            int y = startY + i * dy;
            if (x < 0 || x >= grille.length || y < 0 || y >= grille[0].length) {
                break;
            }
            if (grille[x][y] == boule) {
                count++;
            } else {
                break;
            }
        }
        return count == NBR_BOULES_POUR_GAGNER;
    }

    // --- Getters ---
    public char[][] getGrille() {
        return grille;
    }

    public int getTaille() {
        return taille;
    }

    public Boule getBouleAt(Coordonnees coordonnees) {
        if (!isWithinBounds(coordonnees)) {
            throw new IllegalArgumentException("Invalid Coordonnees ");
        }
        for (Boule boule : boules) {
            if (boule.getCoordonnees().equal(coordonnees)) {
                return boule;
            }
        }
        return null;
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
