package main.bot;

import main.board.Board;
import main.boules.Boule;
import main.boules.Coordonnees;
import main.utils.Color;
import main.utils.Constante;

import java.util.List;

public class BotMinimax extends Bot {

    private final int depth; // Profondeur de l'algorithme
    private static Color botColor; //La couleur du bot
    private static final int MAX_SCORE = 1000; // Score élevé pour une victoire
    private static final int MIN_SCORE = -1000; // Score faible pour une défaite

    // Constructeur
    public BotMinimax(int depth) {
        this.depth = depth;
    }

    public Coordonnees genMove(Board board, Color color) {
        this.botColor = color;
        return getBestMove(board, botColor, this.depth);
    }



    public static Coordonnees getBestMove(Board board, Color botColor, int depth) {
        int bestScore = Integer.MIN_VALUE;
        Coordonnees bestMove = null;

        for (Coordonnees move : board.getMovePossible()) {
            board.addBoule(new Boule(move, botColor));
            int score = minimax(board, switchColor(botColor), depth - 1, false);
            board.removeBoule(move);
            if (score > bestScore) {
                bestScore = score;
                bestMove = move;
            }
        }
        return bestMove;
    }

    private static int minimax(Board board, Color turn, int depth, boolean isMaximizing) {
        if (depth == 0 || board.getGrille().hasWinner(Constante.ALIGNMENT_TO_WIN_SMALL)) {
            return (turn == botColor ? depth - MAX_SCORE : MAX_SCORE - depth);
        } else if (board.getGrille().isFull()) {
            return 0;
        }
        // Maximisation (bot)
        if (isMaximizing) {
            int maxEval = Integer.MIN_VALUE;

            for (Coordonnees move : board.getMovePossible()) {
                board.addBoule(new Boule(move, turn));
                int eval = minimax(board, switchColor(turn), depth - 1, false);
                maxEval = Math.max(maxEval, eval);
                board.removeBoule(move);
            }

            return maxEval;
        }
        // Minimisation (adversaire)
        else {
            int minEval = Integer.MAX_VALUE;

            for (Coordonnees move : board.getMovePossible()) {
                board.addBoule(new Boule(move, turn));
                int eval = minimax(board, switchColor(turn), depth - 1, true);
                minEval = Math.min(minEval, eval);
                board.removeBoule(move);
            }

            return minEval;
        }
    }

    private static Color switchColor(Color color) {
        return (color == Color.Black) ? Color.White : Color.Black;
    }




}