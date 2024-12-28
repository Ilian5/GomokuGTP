package main.bot;

import main.board.Board;
import main.boules.Boule;
import main.boules.Coordonnees;
import main.utils.Color;
import main.utils.Constante;

import java.lang.constant.Constable;
import java.util.List;

public class BotMinimax extends Bot {

    private final int depth; // Profondeur de l'algorithme
    private static Color botColor; //La couleur du bot

    // Constructeur
    public BotMinimax(int depth) {
        this.depth = depth;
    }

    public Coordonnees genMove(Board board, Color color) {
        this.botColor = color;
        int[] bestMove = minimax(board, depth, true, Integer.MIN_VALUE, Integer.MAX_VALUE);

        if (bestMove[1] != -1 && bestMove[2] != -1) {
            return new Coordonnees(bestMove[1], bestMove[2]);
        } else {
            System.out.println("Aucun coup possible pour le bot !");
            return null;
        }
    }

    /**
     * Fonction Minimax avec Alpha-Beta pruning.
     *
     * @param board           L'état actuel du plateau.
     * @param depth           La profondeur maximale de recherche.
     * @param isMaximizing    True si c'est au tour du joueur maximisant (AI), false sinon.
     * @param alpha           La meilleure valeur déjà trouvée pour le maximisateur.
     * @param beta            La meilleure valeur déjà trouvée pour le minimisateur.
     * @return Un tableau contenant le score et le meilleur mouvement [score, x, y].
     */
    public static int[] minimax(Board board, int depth, boolean isMaximizing, int alpha, int beta) {
        if (depth == 0 || board.gameOver()) {
            return new int[]{evaluate(board), -1, -1};
        }

        List<Coordonnees> moves = board.getMovePossible();
        int bestScore = isMaximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        Coordonnees bestMove = null;

        for (Coordonnees move : moves) {
            Boule boule = new Boule(move, isMaximizing ? Color.Black : Color.White);
            board.addBoule(boule);
            int score = minimax(board, depth - 1, !isMaximizing, alpha, beta)[0];
            board.removeBoule(move);
            if (isMaximizing) {
                if (score > bestScore) {
                    bestScore = score;
                    bestMove = move;
                }
                alpha = Math.max(alpha, score);
            } else {
                if (score < bestScore) {
                    bestScore = score;
                    bestMove = move;
                }
                beta = Math.min(beta, score);
            }
            if (beta <= alpha) {
                break;
            }
        }

        return new int[]{bestScore, bestMove != null ? bestMove.getX() : -1, bestMove != null ? bestMove.getY() : -1};
    }

    private static int evaluate(Board board) {
        final int ALIGNMENT_SCORE = board.getGrille().getTaille() < 8 ? 3 : 5;
        final int WINNING_SCORE = 100000;
        final int BLOCKING_SCORE = 90000;
        final int IN_PROGRESS_SCORE = 10;

        int score = 0;

        // Évaluation des alignements sur le plateau
        for (int x = 0; x < board.getGrille().getTaille(); x++) {
            for (int y = 0; y < board.getGrille().getTaille(); y++) {
                Coordonnees coord = new Coordonnees(x, y);
                Boule currentBoule = board.getBouleAt(coord);

                // Ignore les cases vides
                if (currentBoule == null) continue;

                Color currentColor = currentBoule.getColor();
                boolean isBot = (currentColor == botColor);

                // Vérifie les alignements dans toutes les directions
                score += evaluateDirection(board, coord, 1, 0, isBot, ALIGNMENT_SCORE, WINNING_SCORE, BLOCKING_SCORE, IN_PROGRESS_SCORE); // Horizontal
                score += evaluateDirection(board, coord, 0, 1, isBot, ALIGNMENT_SCORE, WINNING_SCORE, BLOCKING_SCORE, IN_PROGRESS_SCORE); // Vertical
                score += evaluateDirection(board, coord, 1, 1, isBot, ALIGNMENT_SCORE, WINNING_SCORE, BLOCKING_SCORE, IN_PROGRESS_SCORE); // Diagonale \
                score += evaluateDirection(board, coord, 1, -1, isBot, ALIGNMENT_SCORE, WINNING_SCORE, BLOCKING_SCORE, IN_PROGRESS_SCORE); // Diagonale /
            }
        }

        return score;
    }

    /**
     * Évalue une direction spécifique pour un point donné.
     *
     * @param board Le plateau de jeu.
     * @param coord Les coordonnées actuelles.
     * @param dx    Direction X.
     * @param dy    Direction Y.
     * @param isBot True si la couleur appartient au bot.
     * @param alignmentScore Nombre requis pour un alignement gagnant.
     * @param winningScore Score pour un alignement gagnant.
     * @param blockingScore Score pour bloquer un alignement gagnant.
     * @param progressScore Score pour un alignement en progression.
     * @return Le score correspondant à l'alignement dans cette direction.
     */
    private static int evaluateDirection(Board board, Coordonnees coord, int dx, int dy, boolean isBot,
                                         int alignmentScore, int winningScore, int blockingScore, int progressScore) {
        int count = 0;
        int emptySpaces = 0;
        Color color = board.getBouleAt(coord).getColor();

        for (int i = 0; i < alignmentScore; i++) {
            int nx = coord.getX() + i * dx;
            int ny = coord.getY() + i * dy;
            Coordonnees nextCoord = new Coordonnees(nx, ny);

            if (!board.isWithinBounds(nextCoord)) break;

            Boule nextBoule = board.getBouleAt(nextCoord);
            if (nextBoule == null) {
                emptySpaces++;
            } else if (nextBoule.getColor() == color) {
                count++;
            } else {
                break;
            }
        }

        if (count == alignmentScore) return isBot ? winningScore : -blockingScore;
        if (count == alignmentScore - 1 && emptySpaces > 0) return isBot ? progressScore : -progressScore;

        return 0;
    }



}