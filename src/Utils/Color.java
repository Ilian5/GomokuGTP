package Utils;

public enum Color {

    BLACK("B"),
    WHITE("W");

    private final String color;

    Color(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}
