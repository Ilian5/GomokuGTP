package main.bot;

import main.board.Board;
import main.boules.Coordonnees;

public interface IBot {

    Coordonnees genMove(Board board);

}
