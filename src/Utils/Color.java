package Utils;
public enum Color {

    Black('B'),
    White('W');

    private char color;
    Color(char color) {
        this.color = color;
    }

    public char getColorChar() {
        return color;
    }
}