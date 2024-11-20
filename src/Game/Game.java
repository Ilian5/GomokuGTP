package Game;

import Utils.Color;
import Utils.IO;

public class Game {

    private int taille;
    private Board board;
    private IO io;
    private boolean partieFinie = false;

    public Game(int taille) {
        this.taille = taille;
        this.board = new Board(taille);
        this.io = new IO();
    }

    public void startSession() {
        jouePartie();
    }

    public void jouePartie() { //Permet de jouer une partie
        while (true) {
            System.out.println(board.toString());
            executeCommande(io.getCommande());
            if(checkPartieFinie())
                break;
        }
    }

    private void executeCommande(String commande) {
        if(commande.startsWith("quit")) {
            partieFinie = true;
        } else if(commande.startsWith("boardsize")) {
            taille = Integer.parseInt(commande.split(" ")[1]);
            board = new Board(taille);
        }
    }

    private boolean checkPartieFinie() { //vérifie que la partie est fini
        return partieFinie;
    }

}
