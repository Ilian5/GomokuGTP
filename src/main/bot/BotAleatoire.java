package main.bot;

import main.board.Board;
import main.boules.Boule;
import main.boules.Coordonnees;
import main.utils.Color;

import java.util.Random;

public class BotAleatoire extends Bot {

    private final Random random; //Générateur de nombres aléatoires pour les mouvements automatiques.

    public BotAleatoire() {
        super();
        random = new Random();
    }

    @Override
    public Coordonnees genMove(Board board, Color color) {
        if(board.getGrille().isFull())
            throw new ArrayIndexOutOfBoundsException("Board is Full");
        return findRandomEmptyCell(board);  // Trouve une case vide au hasard et la renvoie.
    }

    /**
     * Trouve une cellule vide aléatoire sur le plateau.
     * @return Coordonnées d'une cellule vide.
     */
    private Coordonnees findRandomEmptyCell(Board board) {
        int x, y;
        do {
            x = random.nextInt(board.getGrille().getTaille());
            y = random.nextInt(board.getGrille().getTaille());
        } while (board.isOccupied(new Coordonnees(x, y)));
        return new Coordonnees(x, y);
    }
}