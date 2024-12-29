package main.player;

import main.board.Board;
import main.boules.Coordonnees;
import main.utils.Color;

public interface IPlayer {
    Coordonnees playMove(Board board);
}
