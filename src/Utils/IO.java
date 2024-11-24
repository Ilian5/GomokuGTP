package Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class IO {//IO pour Input/Output

    private Scanner scanner;
    private static final List<String> LISTE_COMMANDE = List.of(
            "quit",
            "boardsize",
            "clearboard",
            "showboard",
            "play",
            "partiestop"
    );

    public IO() {
        scanner = new Scanner(System.in);
    }

    public List<String> getListeCommande() {
        return LISTE_COMMANDE;
    }

    public String getCommande() {
        System.out.println("Veuillez saisir le commande : ");
        while (true) {
            String command = scanner.nextLine();
            boolean commandeAvailable = getListeCommande().stream().anyMatch(command::startsWith);

            if (commandeAvailable) {
                return command;
            }
            System.out.print("Commande invalide. Veuillez réessayer.\n(Commandes existantes : ");
            System.out.println(String.join(", ", getListeCommande()) + ")");
        }
    }

    public int getTailleDebutGame() {
        System.out.println("Veuillez entrer une taille de tableau :");
        while (!scanner.hasNextInt()) {
            System.out.println("Entrée invalide. Veuillez entrer uniquement un nombre :");
            scanner.next();
        }
        return scanner.nextInt();

    }

}
