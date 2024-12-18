package main.boules;

import main.utils.Color;

public class Boule {
    private Coordonnees coordonnees;
    private Color color;

    public Boule(Coordonnees coordonnees, Color color) {
        this.coordonnees = coordonnees;
        this.color = color;
    }
    public Color getColor() {
        return color;
    }
    public Coordonnees getCoordonnees() {
        return coordonnees;
    }
}