package main.bot;

import main.board.Board;
import main.boules.Boule;
import main.boules.Coordonnees;
import main.utils.Color;

import java.util.List;
import java.util.Random;

public class BotAleatoire extends Bot {

    private final Random random; //Générateur de nombres aléatoires pour les mouvements automatiques.

    public BotAleatoire() {
        super();
        this.random = new Random();
    }

    @Override
    public Coordonnees genMove(Board board, Color color) {
        List<Coordonnees> moves = board.getMovePossible();
        if (moves.isEmpty()) {
            throw new IllegalStateException("No possible moves");
        }
        return moves.get(random.nextInt(moves.size()));
    }
}