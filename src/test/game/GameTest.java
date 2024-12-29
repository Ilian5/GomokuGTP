package test.game;

import main.game.Game;
import main.grille.Grille;
import main.utils.Color;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    private static final int TAILLE_GAMOKU_TEST = 8;
    private final Game gameTest;

    public GameTest() {
        gameTest = new Game();
    }

    @Test
    public void testSetBoardSizeValid() {
        gameTest.executeCommand("boardsize " + TAILLE_GAMOKU_TEST);
        assertEquals(TAILLE_GAMOKU_TEST, gameTest.getBoardSize(), "La taille du plateau doit être mise à jour.");
    }

    @Test
    public void testSetBoardSizeInvalidNumber() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                gameTest.executeCommand("boardsize 2")
        );
        assertEquals("size outside engine's limits", exception.getMessage());
    }

    @Test
    public void testClearGrille() {
        gameTest.executeCommand("boardsize " + TAILLE_GAMOKU_TEST);
        gameTest.executeCommand("play black D5");
        gameTest.executeCommand("clear_board");
        Grille grille = gameTest.getGrille();
        char[][] grilleTest = new char[TAILLE_GAMOKU_TEST][TAILLE_GAMOKU_TEST];
        for (int x = 0; x < TAILLE_GAMOKU_TEST; x++) {
            for (int y = 0; y < TAILLE_GAMOKU_TEST; y++) {
                char actual = grille.getBouleAt(x, y);
                assertEquals(Color.Blank.toChar(), actual, "Toutes les cases du plateau doivent être vides après le clear_board."); // Adapte '.' au caractère utilisé pour une case vide
            }
        }
    }

    @Test
    public void testPlayMoveValid() {
        gameTest.executeCommand("boardsize " + TAILLE_GAMOKU_TEST);
        gameTest.executeCommand("play black D5");
        Grille grille = gameTest.getGrille();
        assertNotNull(grille.getBouleAt(3, 4), "Une boule doit être ajoutée à la position D5.");
        assertEquals(Color.Black.toChar(), grille.getBouleAt(3, 4), "La boule ajoutée doit être noire.");
    }

    @Test
    public void testPlayMoveInvalidPosition() {
        gameTest.executeCommand("boardsize " + TAILLE_GAMOKU_TEST);
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                gameTest.executeCommand("play black Z1")
        );
        assertEquals("Invalid vertex", exception.getMessage());
    }

    @Test
    public void testPlayMoveOccupiedPosition() {
        gameTest.executeCommand("boardsize " + TAILLE_GAMOKU_TEST);
        gameTest.executeCommand("play black D5");
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                gameTest.executeCommand("play white D5")
        );
        assertEquals("Illegal move", exception.getMessage());
    }

    @Test
    public void testQuitCommand() {
        boolean running = gameTest.executeCommand("quit");
        assertFalse(running, "La commande quit doit arrêter la session.");
    }

    @Test
    public void testInvalidCommand() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                gameTest.executeCommand("invalidcommand")
        );
        assertEquals("invalid command", exception.getMessage());
    }
}