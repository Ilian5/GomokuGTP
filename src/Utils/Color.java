package Utils;

public enum Color {

    Black('B'),
    White('W'),

    private char color;

    public Color(char color) {
        this.color = color;
    }

    public char getColor() {
        return color;
    }
}