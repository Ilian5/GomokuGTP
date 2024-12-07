package main.bot;

import main.board.Board;
import main.boules.Coordonnees;
import main.utils.Color;

public interface IBot {

    Coordonnees genMove(Board board, Color color);

}
