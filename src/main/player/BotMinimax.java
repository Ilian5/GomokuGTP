package main.player;

import main.board.Board;
import main.boules.Boule;
import main.boules.Coordonnees;
import main.utils.Color;
import main.utils.Constante;

public class BotMinimax extends Player {

    private final int depth; // Profondeur de l'algorithme
    private static final int MAX_SCORE = 1000; // Score élevé pour une victoire
    private static final int MIN_SCORE = -1000; // Score faible pour une défaite
    private int nbAlignementWin;

    // Constructeur
    public BotMinimax(int depth, Color color, int nbAlignementWin) {
        super(color);
        this.depth = depth;
        this.nbAlignementWin = nbAlignementWin;
    }

    public Coordonnees playMove(Board board) {
        return getBestMove(board, super.getColor(), this.depth);
    }



    public Coordonnees getBestMove(Board board, Color botColor, int depth) {
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

    private int minimax(Board board, Color turn, int depth, boolean isMaximizing) {
        if (depth == 0 || board.getGrille().hasWinner(nbAlignementWin)) {
            return (turn == super.getColor() ? depth - MAX_SCORE : MAX_SCORE - depth);
        } else if (board.getGrille().isFull()) {
            return 0;
        }
        // Maximisation (bot)
        if (isMaximizing) {
            int maxEval = Integer.MIN_VALUE;

            for (Coordonnees move : board.getMovePossible()) {
                board.addBoule(new Boule(move, turn));
                int eval = evaluateMove(board, move, turn);
                maxEval = Math.max(eval, minimax(board, switchColor(turn), depth - 1, false));
                board.removeBoule(move);
            }

            return maxEval;
        }
        // Minimisation (adversaire)
        else {
            int minEval = Integer.MAX_VALUE;

            for (Coordonnees move : board.getMovePossible()) {
                board.addBoule(new Boule(move, turn));
                int eval = evaluateMove(board, move, turn);
                minEval = Math.min(eval, minimax(board, switchColor(turn), depth - 1, true));

                board.removeBoule(move);
            }

            return minEval;
        }
    }

    private static Color switchColor(Color color) {
        return (color == Color.Black) ? Color.White : Color.Black;
    }

    public int evaluateMove(Board board, Coordonnees move, Color turn) {
        int score = 0;
        score += countBouleAligne(board, move.getX(), move.getY(), 0, 1, turn.toChar());
        score += countBouleAligne(board, move.getX(), move.getY(), 1, 0, turn.toChar());
        score += countBouleAligne(board, move.getX(), move.getY(), -1, 1, turn.toChar());

        return score;
    }


    public int countBouleAligne(Board board, int startX, int startY, int dx, int dy, char boule) {
        int count = 0;
        int taille = board.getGrille().getTaille();
        // Compter les boules alignées dans la direction donnée
        for (int i = 0; i < nbAlignementWin; i++) {
            int x = startX + i * dx;
            int y = startY + i * dy;

            // Vérifier si on sort de la grille
            if (x < 0 || x >= taille || y < 0 || y >= taille) {
                break;
            }

            // Vérifier si la boule correspond
            if (board.getGrille().getEmplacement(new Coordonnees(x, y)) == boule) {
                count++;
            } else {
                break;  // Si une boule n'est pas de la même couleur, arrêter de compter
            }
        }

        return count;
    }




}