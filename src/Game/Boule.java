package Game;

import Utils.Color;

public class Boule {

    private Coordonnees coordonnees;
    private Color color;

    Boule(Coordonnees coordonnees, Color color) {
        this.coordonnees = coordonnees;
        this.color = color;
    }

    public Coordonnees getCoordonnees() {
        return coordonnees;
    }

    public void setCoordonnees(Coordonnees coordonnees) {
        this.coordonnees = coordonnees;
    }

    public Color getColor() {
        return color;
    }


}
