import Utils.Color;
import Utils.IO;
import java.io.*;
import Game.Game;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GTPCommandsTest {
    private final int TAILLE_GOMOKU_MAX = 19;
    private final int TAILLE_GOMOKU_MIN = 5;

    private Game gameTest = new Game (7);
    @Test
    public void testBoardSize() {
        gameTest.testExecuteCommande("boardsize 5");
        assertEquals(TAILLE_GOMOKU_MIN, gameTest.getTaille(), "La taille devrait être 5.");
    }
    @Test public void testBoardSizePetite(){
        assertThrows(NumberFormatException.class, () ->{
            gameTest.testExecuteCommande("boardsize 1");
        },"La taille du plateau est trop petit 1 la taille doit etre superieur = a 5");
    }

    @Test public void testBoardSizeGrand(){
        assertThrows(NumberFormatException.class, () ->{
            gameTest.testExecuteCommande("boardsize 25");
        },"La taille du plateau est trop grand 25 la taille doit etre inferieur = a 19");
    }

    @Test
    public void testTailleInvalide() {
        assertThrows(NumberFormatException.class, () ->{
            gameTest.testExecuteCommande("boardsize d");
        },"taille de plateau invalide.");
    }

    @Test
    public void testPlay(){
        gameTest.testExecuteCommande("play black D5");
        String plateau = "   0  1  2  3  4  5  6 \n" +
                "A  .  .  .  .  .  .  . \n" +
                "B  .  .  .  .  .  .  . \n" +
                "C  .  .  .  .  .  .  . \n" +
                "D  .  .  .  .  .  B  . \n" +
                "E  .  .  .  .  .  .  . \n" +
                "F  .  .  .  .  .  .  . \n" +
                "G  .  .  .  .  .  .  .";

       assertEquals(plateau, gameTest.getBoard());
    }

    @Test
   public void testInvalideFormatPlayCommand() {
        assertThrows(IllegalArgumentException.class, () -> {
            gameTest.testExecuteCommande("play D5");
        }, "Une IllegalArgumentException devrait être levée pour une commande mal ecrite (color).");
    }
    @Test
    public void testInvalidePlay() {
        assertThrows(NumberFormatException.class, () -> {
            gameTest.testExecuteCommande("play black Dd5");
        }, "Une NumberFormatException devrait être levée play black Dd5 est invalide .");
    }



    @Test
    public void testPlayMoveOccupe() {
        Game gameTest = new Game(7);
        gameTest.testExecuteCommande("play black D5");

        assertThrows(IllegalArgumentException.class, () -> {
            gameTest.testExecuteCommande("play white D5");
        }, "La case D5 est déjà occupée et ne devrait pas permettre un second mouvement.");
    }


    @Test
   public void testClearBoard() {
        gameTest.testExecuteCommande("play black D5");
        gameTest.testExecuteCommande("clearboard");
       String f = "   0  1  2  3  4  5  6 \n" +
               "A  .  .  .  .  .  .  . \n" +
               "B  .  .  .  .  .  .  . \n" +
               "C  .  .  .  .  .  .  . \n" +
               "D  .  .  .  .  .  .  . \n" +
               "E  .  .  .  .  .  .  . \n" +
               "F  .  .  .  .  .  .  . \n" +
               "G  .  .  .  .  .  .  . \n";

       assertEquals(f, gameTest.getBoard());
    }


    @Test
    public void testShowBoard() {
        gameTest.testExecuteCommande("boardsize 5");
        gameTest.testExecuteCommande("showboard");
        assertNotNull(gameTest.getBoard().toString(), "Le plateau devrait être affiché.");
    }

    @Test
    public void testQuitCommand() {
        gameTest.testExecuteCommande("quit");
        assertTrue(gameTest.testcheckPartieFinie(), "La commande 'quit' devrait terminer la partie.");
    }

}