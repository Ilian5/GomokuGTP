package Appli;

import java.util.Scanner;
import Game.Game;

public class Appli {
    private static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        System.out.println("Veuillez entrer une taille de tableau :");
        int taille = 0;
        while(true) {
            if(sc.hasNextInt()) {
                taille = sc.nextInt();
                break;
            }
            System.out.println("Veuillez entrer uniquement un nombre");
            sc.next();
        }
        new Game(taille).startSession();
    }
}