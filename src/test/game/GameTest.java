package test.game;

import main.boules.Boule;
import main.boules.Coordonnees;
import main.game.Game;
import main.utils.Color;
import org.junit.Test;
import org.junit.jupiter.api.ClassOrderer;
//import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    private static final int TAILLE_GOMOKU_MAX = 19;
    private static final int TAILLE_GOMOKU_MIN = 5;
    private static final int TAILLE_GAMOKU_TEST = 8;

    private Game gameTest;
    public GameTest() {
        this.gameTest = new Game();
    }

    @Test
    public void testBoardSize() {
        gameTest.executeCommand("boardsize " + TAILLE_GAMOKU_TEST);
        assertEquals(TAILLE_GAMOKU_TEST, gameTest.getBoardSize(), "La taille devrait être à 8");
    }

    @Test
    public void testShowBoard() {
        gameTest.executeCommand("boardsize " + TAILLE_GAMOKU_TEST);
        String plateau =
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
        assertEquals(plateau, gameTest.getBoard().toString(), "La grille doit être vide");
    }

    @Test
    public void testPlay() {
        gameTest.executeCommand("boardsize " + TAILLE_GAMOKU_TEST);
        gameTest.executeCommand("play black d5");
        String plateauAfterPlay =
                "   A  B  C  D  E  F  G  H  \n" +
                "1  .  .  .  .  .  .  .  .  1\n" +
                "2  .  .  .  .  .  .  .  .  2\n" +
                "3  .  .  .  .  .  .  .  .  3\n" +
                "4  .  .  .  .  .  .  .  .  4\n" +
                "5  .  .  .  X  .  .  .  .  5\n" +
                "6  .  .  .  .  .  .  .  .  6\n" +
                "7  .  .  .  .  .  .  .  .  7\n" +
                "8  .  .  .  .  .  .  .  .  8\n" +
                "   A  B  C  D  E  F  G  H  ";
        assertEquals(plateauAfterPlay, gameTest.getBoard().toString(), "Une boule noir (X) devrait être placée en D5");
    }

    @Test
    public void testClearBoard() {
        gameTest.executeCommand("boardsize " + TAILLE_GAMOKU_TEST);
        gameTest.executeCommand("play black d5");
        String plateauAfterClear =
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
        gameTest.executeCommand("clear_board");
        assertEquals(plateauAfterClear, gameTest.getBoard().toString(), "Le plateau devrait être totalement vide");
    }


//
//
//    @Test public void testBoardSizeGrand(){
//        assertThrows(AssertionError.class, () ->{
//            gameTest.executeCommande("partiestop");
//            gameTest.executeCommande("boardsize 25");
//        },"La taille du plateau est trop grand 25 la taille doit etre inferieur = a 19");
//    }
//
//    @Test
//    public void testTailleInvalide() {
//        assertThrows(NumberFormatException.class, () ->{
//            gameTest.executeCommande("play b d5");
//            gameTest.executeCommande("partiestop");
//            gameTest.executeCommande("boardsize d");
//        },"Taille de plateau invalide.");
//    }
//
//
//
//    @Test
//    public void testShowBoard() {
//        gameTest.executeCommande("boardsize " + TAILLE_BOARD_TEST);
//        gameTest.executeCommande("showboard");
//        assertNotNull(gameTest.getBoard().toString(), "Le plateau devrait être affiché.");
//    }
//
//
//    @Test
//    public void testQuitCommand() {
//        gameTest.executeCommande("quit");
//        assertTrue(gameTest.checkPartieFinie(), "La commande 'quit' devrait terminer la partie.");
//    }
//
//    @Test
//    public void testInvalideFormatPlayCommand() {
//        assertThrows(IllegalArgumentException.class, () -> {
//            gameTest.executeCommande("play D5");
//        }, "Une IllegalArgumentException devrait être levée pour une commande mal ecrite (color).");
//    }
//
//
//
//

//    @Test
//    public void testPlay(){
//        gameTest.executeCommande("boardsize 6");
//        gameTest.executeCommande("play black D5");
//        String plateau = "   0  1  2  3  4  5 \n" +
//                "A  .  .  .  .  .  . \n" +
//                "B  .  .  .  .  .  . \n" +
//                "C  .  .  .  .  .  . \n" +
//                "D  .  .  .  .  .  B \n" +
//                "E  .  .  .  .  .  . \n" +
//                "F  .  .  .  .  .  .\n  ";
//        assertEquals(plateau, gameTest.getBoard());
//    }

//
//
//
//
//
//
//    @Test
//    public void testInvalidePlay() {
//        assertThrows(IllegalArgumentException.class, () -> {
//            gameTest.executeCommande("play black Dd5");
//        }, "Une NumberFormatException devrait être levée play black Dd5 est invalide .");
//    }
//
//
//    @Test
//    public void testPlayMoveOccupe() {
//        gameTest.executeCommande("play black D5");
//
//        assertThrows(IllegalArgumentException.class, () -> {
//            gameTest.executeCommande("play white D5");
//        }, "La case D5 est déjà occupée et ne devrait pas permettre un second mouvement.");
//    }
//
//
//
//    @Test
//    public void testClearBoard() {
//        gameTest.executeCommande("play black D5");
//        gameTest.executeCommande("clearboard");
//        Board newBoard = new Board(TAILLE_BOARD_TEST);
//        assertEquals(newBoard.toString(), gameTest.getBoard().toString());
//    }

}