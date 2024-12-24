package main.bot;

import main.board.Board;
import main.boules.Coordonnees;
import main.utils.Color;

/**
 * Interface représentant un bot dans le jeu Gomoku.
 */
public interface IBot {
    /**
     * Génère le prochain coup pour le bot en fonction de l'état actuel du plateau.
     * @param board L'état actuel du plateau de jeu.
     * @param color La couleur associée au bot (noir ou blanc).
     * @return Les coordonnées du coup à jouer.
     */
    Coordonnees genMove(Board board, Color color);
}