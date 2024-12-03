package main.bot;

public abstract class Bot implements IBot {

    private int points;

    public Bot() {
        this.points = 0;
    }

    public int getPoints() {
        return points;
    }

    public void addPoints(int points) {
        this.points += points;
    }

}

