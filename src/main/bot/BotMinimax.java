package main.bot;

import main.board.Board;
import main.boules.Boule;
import main.boules.Coordonnees;
import main.utils.Color;

import java.util.List;

public class BotMinimax extends Bot {
    private int max_depth; // Profondeur maximale pour l'exploration

    public BotMinimax(int max_depth) {
        this.max_depth = max_depth;
    }
    /**
     * Trouve le meilleur coup à jouer pour le bot.
     * @param board Le plateau de jeu actuel.
     * @param color La couleur du joueur maximisant (bot).
     * @return Les coordonnées du meilleur coup.
     */
    public Coordonnees genMove(Board board, Color color) {
        int bestValue = Integer.MIN_VALUE;
        Coordonnees bestMove = null;

        for (Coordonnees move : board.getMovePossible()) {
            board.addBoule(new Boule(move, color));
            int moveValue = minimax(board, max_depth, Integer.MIN_VALUE, Integer.MAX_VALUE, false, color);
            board.removeBoule(move); // Annule le coup simulé

            if (moveValue > bestValue) {
                bestValue = moveValue;
                bestMove = move;
            }
        }

        return bestMove;
    }

    /**
     * Implémente l'algorithme Minimax avec élagage alpha-bêta.
     * @param board Le plateau de jeu actuel.
     * @param depth La profondeur actuelle de l'arbre.
     * @param alpha La meilleure valeur pour le joueur maximisant.
     * @param beta La meilleure valeur pour le joueur minimisant.
     * @param isMaximizingPlayer Indique si le joueur actuel maximise ou minimise.
     * @param maximizingPlayerColor La couleur du joueur maximisant.
     * @return La valeur évaluée du plateau.
     */
    private int minimax(Board board, int depth, int alpha, int beta, boolean isMaximizingPlayer, Color maximizingPlayerColor) {
        if (depth == 0 || board.hasWinner(5) || board.isFull()) {
            return evaluateBoard(board, maximizingPlayerColor);
        }

        if (isMaximizingPlayer) {
            int maxEval = Integer.MIN_VALUE;
            for (Coordonnees move : board.getMovePossible()) {
                board.addBoule(new Boule(move, maximizingPlayerColor));
                int eval = minimax(board, depth - 1, alpha, beta, false, maximizingPlayerColor);
                board.removeBoule(move);

                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);
                if (beta <= alpha) break;
            }
            return maxEval;
        } else {
            Color minimizingPlayerColor = maximizingPlayerColor == Color.Black ? Color.White : Color.Black;
            int minEval = Integer.MAX_VALUE;
            for (Coordonnees move : board.getMovePossible()) {
                board.addBoule(new Boule(move, minimizingPlayerColor));
                int eval = minimax(board, depth - 1, alpha, beta, true, maximizingPlayerColor);
                board.removeBoule(move);

                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);
                if (beta <= alpha) break;
            }
            return minEval;
        }
    }

    /**
     * Évalue le plateau de jeu pour déterminer la qualité d'une position.
     *
     * @param board Le plateau à évaluer.
     * @param maximizingPlayerColor La couleur du joueur maximisant.
     * @return Un score représentant la qualité de la position.
     */
    private int evaluateBoard(Board board, Color maximizingPlayerColor) {
        Color minimizingPlayerColor = maximizingPlayerColor == Color.Black ? Color.White : Color.Black;

        int score = 0;

        // Exemples d'évaluation (à affiner pour un meilleur bot) :
        // +10 pour 2 boules alignées sans interruption, +50 pour 3, +100 pour 4
        // Pénalités similaires pour l'adversaire
        score += evaluateAlignments(board, maximizingPlayerColor) * 10;
        score -= evaluateAlignments(board, minimizingPlayerColor) * 10;

        return score;
    }

    /**
     * Évalue les alignements pour une couleur donnée.
     *
     * @param board Le plateau à évaluer.
     * @param color La couleur pour laquelle on évalue.
     * @return Un score basé sur les alignements trouvés.
     */
    private int evaluateAlignments(Board board, Color color) {
        int alignments = 0;

        // Parcourir la grille pour évaluer les alignements
        for (int x = 0; x < board.getGrille().length; x++) {
            for (int y = 0; y < board.getGrille()[x].length; y++) {
                char boule = board.getGrille()[x][y];
                if (boule == color.getColorChar()) {
                    alignments += countAlignments(board, x, y, color);
                }
            }
        }

        return alignments;
    }

    /**
     * Compte les alignements pour une boule à une position donnée.
     *
     * @param board Le plateau.
     * @param startX La position X de départ.
     * @param startY La position Y de départ.
     * @param color La couleur pour laquelle on compte.
     * @return Le nombre d'alignements détectés.
     */
    private int countAlignments(Board board, int startX, int startY, Color color) {
        int count = 0;

        // Directions à évaluer : horizontal, vertical, diagonale principale, diagonale secondaire
        int[][] directions = {{1, 0}, {0, 1}, {1, 1}, {1, -1}};
        for (int[] dir : directions) {
            int consecutive = 0;
            for (int i = 0; i < 5; i++) {
                int x = startX + i * dir[0];
                int y = startY + i * dir[1];
                if (x >= 0 && x < board.getTaille() && y >= 0 && y < board.getTaille() &&
                        board.getGrille()[x][y] == color.getColorChar()) {
                    consecutive++;
                } else {
                    break;
                }
            }
            if (consecutive == 5) count++; // Compte un alignement complet
        }

        return count;
    }
}

