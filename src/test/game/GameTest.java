package test.game;

import main.board.Board;
import main.boules.Coordonnees;
import main.game.Game;
import main.utils.Color;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    private static final int TAILLE_GAMOKU_TEST = 8;
    private Game gameTest;

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
                gameTest.executeCommand("boardsize 3")
        );
        assertEquals("size outside engine's limits", exception.getMessage());
    }

    @Test
    public void testClearBoard() {
        gameTest.executeCommand("boardsize " + TAILLE_GAMOKU_TEST);
        gameTest.executeCommand("play black D5");
        gameTest.executeCommand("clear_board");
        Board board = gameTest.getBoard();
        char[][] tt = new char[5][5];
        for (char[] row : tt) {
            for (char cell : row) {
                assertEquals('.', cell, "Toutes les cases du plateau doivent être vides après le clear_board.");
            }
        }
    }

    @Test
    public void testPlayMoveValid() {
        gameTest.executeCommand("boardsize " + TAILLE_GAMOKU_TEST);
        gameTest.executeCommand("play black D5");
        Board board = gameTest.getBoard();
        assertNotNull(board.getBouleAt(new Coordonnees(3, 4)), "Une boule doit être ajoutée à la position D5.");
        assertEquals(Color.Black, board.getBouleAt(new Coordonnees(3, 4)).getColor(), "La boule ajoutée doit être noire.");
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