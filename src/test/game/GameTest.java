package test.game;

import main.game.Game;
import org.junit.Test;
//import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    private final int TAILLE_GOMOKU_MAX = 19;
    private final int TAILLE_GOMOKU_MIN = 5;
    private static final int TAILLE_BOARD_TEST = 6;

    private Game gameTest;
//    public GameTest() {
//        this.gameTest = new Game();
//        gameTest.setTaille(TAILLE_BOARD_TEST);
//    }
//
//    @Test
//    public void testBoardSize() {
//        gameTest.executeCommande("boardsize 5");
//        assertEquals(5, gameTest.getTaille(), "La taille devrait être 5.");
//    }
//
//
//    @Test public void testBoardSizePetite(){
//        assertThrows(AssertionError.class, () ->{
//            gameTest.executeCommande("partiestop");
//            gameTest.executeCommande("boardsize 1");
//        },"La taille du plateau est trop petit 1 la taille doit etre superieur = a 5");
//    }
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