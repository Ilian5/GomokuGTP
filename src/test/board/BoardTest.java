package test.board;

import main.board.Board;
import main.boules.Boule;
import main.boules.Coordonnees;
import main.utils.Color;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    private static final int TAILLE_GOMOKU_TEST = 8;

    private Board boardTest;

    public BoardTest() {
        boardTest = new Board(TAILLE_GOMOKU_TEST);
    }

    @Test
    public void testAddBoule_Success() {
        Coordonnees coordonnees = new Coordonnees(2, 3);
        Boule boule = new Boule(coordonnees, Color.Black); // Boule rouge.

        boardTest.addBoule(boule);

        assertTrue(boardTest.isOccupied(coordonnees), "La case devrait être occupée après l'ajout.");
        assertEquals('X', boardTest.getGrille().getEmplacement(new Coordonnees(2, 3)), "La grille devrait contenir le caractère 'R' à ces coordonnées.");
        assertEquals(boule, boardTest.getBouleAt(coordonnees), "La boule ajoutée devrait être retrouvée à ces coordonnées.");
    }

    @Test
    public void testAddBoule_CaseAlreadyOccupied() {
        Coordonnees coordonnees = new Coordonnees(5, 5);
        Boule boule1 = new Boule(coordonnees, Color.Black); // Boule bleue.
        Boule boule2 = new Boule(coordonnees, Color.White); // Boule rouge.

        boardTest.addBoule(boule1);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> boardTest.addBoule(boule2));
        assertEquals("invalid vertex", exception.getMessage(), "Une exception devrait être levée si la case est déjà occupée.");
    }

    @Test
    public void testAddBoule_InvalidCoordinates() {
        Coordonnees invalidCoord1 = new Coordonnees(-1, 0); // Coordonnées hors limites.
        Coordonnees invalidCoord2 = new Coordonnees(0, 10); // Coordonnées hors limites.

        Boule boule1 = new Boule(invalidCoord1, Color.Black);
        Boule boule2 = new Boule(invalidCoord2, Color.White);

        assertThrows(IllegalArgumentException.class, () -> boardTest.addBoule(boule1), "Une exception devrait être levée pour des coordonnées négatives.");
        assertThrows(IllegalArgumentException.class, () -> boardTest.addBoule(boule2), "Une exception devrait être levée pour des coordonnées hors limites.");
    }

    @Test
    public void testIsOccupied_ValidAndEmpty() {
        Coordonnees coordonnees = new Coordonnees(6, 7);
        assertFalse(boardTest.isOccupied(coordonnees), "La case devrait être vide par défaut.");
    }

    @Test
    public void testIsOccupied_InvalidCoordinates() {
        Coordonnees invalidCoord = new Coordonnees(10, 10); // Hors limites.

        Exception exception = assertThrows(IllegalArgumentException.class, () -> boardTest.isOccupied(invalidCoord));
        assertEquals("invalid vertex, invalid move", exception.getMessage(), "Une exception devrait être levée pour des coordonnées invalides.");
    }

    @Test
    public void testToString() {
        String expectedInitial =
                "   A  B  C  D  E  F  G  H  \n" +
                "1  .  .  .  .  .  .  .  .  1\n" +
                "2  .  .  .  .  .  .  .  .  2\n" +
                "3  .  .  .  .  .  .  .  .  3\n" +
                "4  .  .  .  .  .  .  .  .  4\n" +
                "5  .  .  .  .  .  .  .  .  5\n" +
                "6  .  .  .  .  .  .  .  .  6\n" +
                "7  .  .  .  .  .  .  .  .  7\n" +
                "8  .  .  .  .  .  .  .  .  8\n" +
                "   A  B  C  D  E  F  G  H  ";

        assertEquals(expectedInitial, boardTest.getGrille().toString(), "La représentation initiale du plateau devrait être correcte.");

        boardTest.addBoule(new Boule(new Coordonnees(0, 0), Color.Black));
        boardTest.addBoule(new Boule(new Coordonnees(7, 7), Color.White));

        String expectedAfterAdd =
                "   A  B  C  D  E  F  G  H  \n" +
                "1  X  .  .  .  .  .  .  .  1\n" +
                "2  .  .  .  .  .  .  .  .  2\n" +
                "3  .  .  .  .  .  .  .  .  3\n" +
                "4  .  .  .  .  .  .  .  .  4\n" +
                "5  .  .  .  .  .  .  .  .  5\n" +
                "6  .  .  .  .  .  .  .  .  6\n" +
                "7  .  .  .  .  .  .  .  .  7\n" +
                "8  .  .  .  .  .  .  .  O  8\n" +
                "   A  B  C  D  E  F  G  H  ";

        assertEquals(expectedAfterAdd, boardTest.getGrille().toString(), "La représentation du plateau après ajout devrait être correcte.");
       }

}
