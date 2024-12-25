package main.board;

import main.boules.Boule;
import main.boules.Coordonnees;
import main.grille.Grille;
import main.utils.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Représente le plateau de jeu pour Gomoku.
 */
public class Board {

    private final List<Boule> boules; // Liste des main.boules placées sur le plateau.
    private final Grille grille;

    /**
     * Constructeur de la classe {@code Board}.
     *
     * @param taille Taille du plateau.
     */
    public Board(int taille) {
        this.boules = new ArrayList<>();
        grille = new Grille(taille);
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
        grille.addBoule(boule); // Marque la case sur la grille.
    }

    public void removeBoule(Coordonnees coord) {
        if(!isOccupied(coord)) {
            throw new IllegalArgumentException("i can't remove a boule");
        }
        boules.removeIf(boule -> boule.getCoordonnees().equal(coord));
        grille.remove(coord);
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
        return grille.isOccupied(coord);
    }

    /**
     * Vérifie si les coordonnées sont dans les limites du plateau.
     * @param coord Coordonnées à vérifier.
     * @return {@code true} si les coordonnées sont valides, {@code false} sinon.
     */
    private boolean isWithinBounds(Coordonnees coord) {
        return coord.getX() >= 0 && coord.getX() < grille.getTaille() &&
                coord.getY() >= 0 && coord.getY() < grille.getTaille();
    }

    // --- Getters ---
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

    public List<Coordonnees> getMovePossible() {
        List<Coordonnees> moves = new ArrayList<>();
        for (int i = 0; i < grille.getTaille(); i++) {
            for (int j = 0; j < grille.getTaille(); j++) {
                if(!isOccupied(new Coordonnees(i, j)))
                    moves.add(new Coordonnees(i, j));
            }
        }
        return moves;
    }

    public boolean gameOver() {
        return getMovePossible().isEmpty();
    }

    public Grille getGrille() {
        return grille;
    }
}
