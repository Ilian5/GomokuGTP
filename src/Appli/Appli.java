package Appli;

import java.util.Scanner;

import Jeu.Game;

public class Appli {

    private static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        System.out.println("Veuillez entrer la taille du tableau : (5 pour 5x5, 6 pour 6x6...)");
        int taille;
        while(true) {
            if(sc.hasNextInt()) {
                taille = sc.nextInt();
                break;
            }
            System.out.println("Erreur ! Veuillez rentrer uniquement un nombre !");
            sc.next();

        }

        new Game(taille).startGame();
    }
}
