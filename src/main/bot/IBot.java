package main.bot;

import main.board.Board;
import main.boules.Coordonnees;

public interface IBot {

    public Coordonnees genMove(Board board);

}
