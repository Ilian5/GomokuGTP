package main.player;

import main.board.Board;
import main.boules.Coordonnees;
import main.utils.Color;

import java.util.List;
import java.util.Random;

public class BotAleatoire extends Player {

    private final Random random; //Générateur de nombres aléatoires pour les mouvements automatiques.

    public BotAleatoire(Color color) {
        super(color);
        this.random = new Random();
    }

    @Override
    public Coordonnees playMove(Board board) {
        List<Coordonnees> moves = board.getMovePossible();
        if (moves.isEmpty()) {
            throw new IllegalStateException("No possible moves");
        }
        return moves.get(random.nextInt(moves.size()));
    }
}