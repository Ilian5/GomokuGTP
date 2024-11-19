package Game;

import java.util.Scanner;

import Utils.Color;

public class Game {

    private Board board;
    private Scanner sc;

    public Game(int taille) {
        board = new Board(taille);
        sc = new Scanner(System.in);
    }


    public void startGame() {
        System.out.println("Début de la partie !");
        System.out.println(board.toString());
        board.addBoule(new Boule(new Coordonnees(5, 5), Color.WHITE));
        board.reloadBoule();
        System.out.println(board.toString());
    }


    public void getCommande() {
        System.out.println("Veuillez entrer une commande :");
        String commande = sc.nextLine();
        while(true) {
            if(commande.startsWith("play")) {

            }
        }
    }

}
