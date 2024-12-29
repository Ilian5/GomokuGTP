package main.player;

import main.grille.Grille;
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
    public int[] playMove(Grille grille) {
        List<int[]> moves = grille.getMovePossible();
        if (moves.isEmpty()) {
            throw new IllegalStateException("No possible moves");
        }
        return moves.get(random.nextInt(moves.size()));
    }
}