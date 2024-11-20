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
        } else if(commande.startsWith("clearboard")) {
            board = new Board(taille);
        } else if(commande.startsWith("showboard")) {
            System.out.println(board.toString());
        } else if(commande.startsWith("play")) {
            playTour(commande);
        }
    }

    private void playTour(String commande) {
        if(commande.split(" ").length == 3) {
            commande = commande.toUpperCase();
            char color = commande.split(" ")[1].charAt(0);
            String mouvement = commande.split(" ")[2];
            if(!(color == 'B' || color == 'W')) {
                System.out.println("Couleur incorrect, couleur possible : B (Black) | W (White)");
                return;
            }
            if(!(mouvement.charAt(0) - 'A' <= (taille - 1) && Integer.parseInt(mouvement.substring(1)) <= taille - 1)) {
                System.out.println("Mouvement incorrect, Mouvement disponible compris entre A0 et " + ((char) ('A' + taille - 1)) + (taille - 1));
                return;
            }
            board.addBoule(new Boule(new Coordonnees((int) mouvement.charAt(0) - 'A', Integer.parseInt(mouvement.substring(1))), (color == 'B' ? Color.Black : Color.White)));
        } else {
            System.out.println("Commande incorrecte, veuillez entrer une couleur et un mouvement !");
        }
    }

    private boolean checkPartieFinie() { //vérifie que la partie est fini
        return partieFinie;
    }

}
