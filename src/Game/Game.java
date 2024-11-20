package Game;

import Utils.Color;

public class Game {

    private int taille;
    private Board board;

    public Game(int taille) {
        this.taille = taille;
        this.board = new Board(taille);
    }

    public void startSession() {
        System.out.println(board.toString());
        board.addBoule(new Boule(new Coordonnees(1,5), Color.Black));
        System.out.println(board.toString());
    }
}
