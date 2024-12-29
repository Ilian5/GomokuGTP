package main.player;

import main.utils.Color;

public abstract class Player implements IPlayer {
    private int points;
    private Color color;

    public Player(Color color) {
        this.points = 0;
        this.color = color;

    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void addPoints(int points) {
        this.points += points;
    }

    public int getPoints() {
        return points;
    }
}
