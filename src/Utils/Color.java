package Utils;

public enum Color {

    Black('B'),
    White('W');

    private char color;

    /**
     * Constructeur de l'énumération {@code Color}.
     * Initialise l'énumération avec le caractère représentant la couleur.
     *
     * @param color Le caractère représentant la couleur ('B' pour noir, 'W' pour blanc).
     */
    Color(char color) {
        this.color = color;
    }

    /**
     * Retourne le caractère associé à la couleur.
     *
     * @return Le caractère représentant la couleur ('B' ou 'W').
     */
    public char getColorChar() {
        return color;
    }

    /**
     * Retourne le caractère associé à la couleur.
     * Cette méthode est identique à {@code getColorChar()}.
     *
     * @return Le caractère représentant la couleur ('B' ou 'W').
     */
    public char toChar() {
        return color;
    }
}