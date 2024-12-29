package main.player;

import main.grille.Grille;
import main.utils.Color;

public class BotMinimax extends Player {

    private final int MAX_DEPTH; // Profondeur de l'algorithme
    private Color opponentColor; // Couleur de l'adversaire
    private int nbALignementWin;

    // Constructeur
    public BotMinimax(int depth, int nbALignementWin, Color botColor) {
        super(botColor);
        this.nbALignementWin = nbALignementWin;
        this.MAX_DEPTH = depth;
        this.opponentColor = (botColor == Color.Black) ? Color.White : Color.Black;
    }

    @Override
    public int[] playMove(Grille grille) {
        return findBestMove(grille);
    }

    public int[] findBestMove(Grille grille) {
        int[] bestMove = new int[]{-1, -1};
        int bestValue = Integer.MIN_VALUE;

        for (int row = 0; row < grille.getTaille(); row++) {
            for (int col = 0; col < grille.getTaille(); col++) {
                if (!grille.isOccupied(row,col)) {
                    grille.addBouleAt(row, col, super.getColor());
                    int moveValue = miniMax(grille, MAX_DEPTH, Integer.MIN_VALUE,
                            Integer.MAX_VALUE, false);
                    grille.addBouleAt(row, col, Color.Blank);
                    if (moveValue > bestValue) {
                        bestMove[0] = row;
                        bestMove[1] = col;
                        bestValue = moveValue;
                    }
                }
            }
        }

        return bestMove;
    }

    public int miniMax(Grille grille, int depth, int alpha, int beta, boolean isMax) {

        int boardVal = evaluateBoard(grille, depth);

        // Terminal node (win/lose/draw) or max depth reached.
        if (Math.abs(boardVal) > 0 || depth == 0
                || grille.isFull()) {
            return boardVal;
        }

        // Maximising player, find the maximum attainable value.
        if (isMax) {
            int highestVal = Integer.MIN_VALUE;
            for (int row = 0; row < grille.getTaille(); row++) {
                for (int col = 0; col < grille.getTaille(); col++) {
                    if (!grille.isOccupied(row,col)) {
                        grille.addBouleAt(row, col, super.getColor());
                        highestVal = Math.max(highestVal, miniMax(grille,
                                depth - 1, alpha, beta, false));
                        grille.addBouleAt(row, col, Color.Blank);
                        alpha = Math.max(alpha, highestVal);
                        if (alpha >= beta) {
                            return highestVal;
                        }
                    }
                }
            }
            return highestVal;
            // Minimising player, find the minimum attainable value;
        } else {
            int lowestVal = Integer.MAX_VALUE;
            for (int row = 0; row < grille.getTaille(); row++) {
                for (int col = 0; col < grille.getTaille(); col++) {
                    if (!grille.isOccupied(row,col)) {
                        grille.addBouleAt(row, col, opponentColor);
                        lowestVal = Math.min(lowestVal, miniMax(grille,
                                depth - 1, alpha, beta, true));
                        grille.addBouleAt(row, col, Color.Blank);
                        beta = Math.min(beta, lowestVal);
                        if (beta <= alpha) {
                            return lowestVal;
                        }
                    }
                }
            }
            return lowestVal;
        }
    }


    private int evaluateBoard(Grille grille, int depth) {

        char winner = grille.getWinner(nbALignementWin);

        if(winner == super.getColor().toChar())
            return 10;
        else if (winner == opponentColor.toChar())
            return -10;

        else
            return 0;

    }


}



