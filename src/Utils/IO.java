package Utils;

import java.util.List;
import java.util.Scanner;

/**
 * La classe {@code IO} gère les entrées et sorties de commandes de l'utilisateur dans la console.
 * Elle permet d'afficher des messages et de récupérer des commandes saisies par l'utilisateur,
 * tout en vérifiant leur validité à partir d'une liste prédéfinie de commandes valides.
 */
public class IO {

    private final Scanner scanner;

    /**
     * Liste statique des commandes valides que l'utilisateur peut saisir.
     */
    private static final List<String> LISTE_COMMANDE = List.of(
            "quit",
            "boardsize",
            "clearboard",
            "showboard",
            "play",
            "partiestop",
            "genmove"
    );

    /**
     * Constructeur de la classe {@code IO}.
     * Initialise le scanner pour lire les entrées de l'utilisateur.
     */
    public IO() {
        scanner = new Scanner(System.in);
    }

    /**
     * Retourne la liste des commandes valides.
     *
     * @return La liste des commandes valides.
     */
    public List<String> getListeCommande() {
        return LISTE_COMMANDE;
    }

    /**
     * Demande à l'utilisateur de saisir une commande et vérifie si la commande est valide.
     * Si la commande est invalide, l'utilisateur est invité à réessayer.
     * La commande doit commencer par l'une des commandes valides de {@code LISTE_COMMANDE}.
     *
     * @return La commande saisie et validée par l'utilisateur.
     */
    public String getCommande() throws IllegalArgumentException {
        System.out.println("Veuillez saisir une commande :");
        String command = scanner.nextLine().trim();

        // Vérifie si la commande saisie commence par l'une des commandes valides
        boolean commandeAvailable = getListeCommande().stream()
                    .anyMatch(command::startsWith);

        if (commandeAvailable) {
            return command;
        }
        throw new IllegalArgumentException("Commande invalide. Veuillez réessayer.\n(Commandes existantes : " + String.join(", ", getListeCommande()) + ")");
    }
}