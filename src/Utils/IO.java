package Utils;

import java.util.Scanner;

/**
 * La classe {@code IO} gère les entrées et sorties de commandes GTP de l'utilisateur dans la console.
 * Elle permet d'afficher des messages et de récupérer des commandes saisies par l'utilisateur.
 * Elle implémente le protocole GTP pour interagir avec le moteur Gomoku.
 */
public class IO {

    private final Scanner scanner;

    /**
     * Constructeur de la classe {@code IO}.
     * Initialise le scanner pour lire les entrées de l'utilisateur.
     */
    public IO() {
        scanner = new Scanner(System.in);
    }

    /**
     * Demande à l'utilisateur de saisir une commande et retourne la saisie de l'utilisateur.
     * Le protocole GTP utilise des commandes spécifiques pour interagir avec le moteur.
     *
     * @return La commande saisie par l'utilisateur sous forme de chaîne.
     */
    public String getCommande(int nbCommande) {
        System.out.print(nbCommande + " ");  // Affiche un prompt pour l'utilisateur
        return scanner.nextLine().trim();
    }

    /**
     * Envoie une réponse au moteur selon le format GTP.
     * Le moteur Gomoku doit répondre à une commande GTP de manière appropriée.
     * @param response La réponse à envoyer au moteur (par exemple, "= move played").
     */
    public void sendResponse(String response) {
        System.out.println(response + "\n");
    }

    /**
     * Envoie une erreur au moteur si la commande est invalide ou a échoué.
     * @param errorMessage Le message d'erreur à envoyer.
     */
    public void sendError(String errorMessage, int nbCommande) {
        System.out.println("?" + nbCommande + " " + errorMessage + "\n");
    }
}
