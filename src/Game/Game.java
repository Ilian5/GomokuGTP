package Game;

import Board.Board;
import Boules.Boule;
import Boules.Coordonnees;
import Utils.Color;
import Utils.IO;

import java.util.Random;


public class Game {

    private static final int TAILLE_DEFAULT = 19; // Taille par défaut du plateau (19x19).


    private Board board; //Plateau de jeu.
    private final Random random; //Générateur de nombres aléatoires pour les mouvements automatiques.
    private final IO io; //Gestionnaire des entrées/sorties.
    private int taille; //Taille actuelle du plateau.
    private int nbCommande;

    public Game() {
        this.taille = TAILLE_DEFAULT;
        this.board = new Board(taille);
        this.io = new IO();
        this.random = new Random();
        this.nbCommande = 1;
    }

    /**
     * Démarre une session GTP. Attend les commandes du joueur via la console
     * et répond en fonction des actions demandées.
     */
    public void startSession() {
        boolean running = true;
        while (running) {
            String command = io.getCommande(nbCommande).trim();
            if (command.isEmpty()) continue;
            try {
                running = executeCommand(command);
                if(!command.startsWith("genmove"))
                    io.sendResponse("=" + nbCommande);
            } catch (IllegalArgumentException e) {
                io.sendError(e.getMessage(), nbCommande);
            }
            nbCommande++;
        }
    }

    /**
     * Exécute une commande GTP reçue en entrée.
     * @param command La commande sous forme de chaîne (exemple : "play B D5").
     * @return {@code false} si la commande est `quit` (fin de session), {@code true} sinon.
     * @throws IllegalArgumentException si la commande est invalide.
     */
    private boolean executeCommand(String command) {
        String[] parts = command.split(" ");
        String action = parts[0].toLowerCase(); // Première partie de la commande (action).
        String argument = parts.length > 1 ? parts[1] : null;

        switch (action) {
            case "boardsize" -> setBoardSize(argument);
            case "clear_board" -> clearBoard();
            case "play" -> playMove(parts);
            case "genmove" -> generateMove(argument);
            case "showboard" -> showBoard();
            case "quit" -> {return false;}
            default -> throw new IllegalArgumentException("invalid command");
        }
        return true;
    }

    /**
     * Définit la taille du plateau de jeu.
     * @param argument Taille du plateau sous forme de chaîne (exemple : "19").
     * @throws IllegalArgumentException si la taille est invalide (en dehors de [5, 19]).
     */
    private void setBoardSize(String argument) {
        try {
            int newTaille = Integer.parseInt(argument);
            if (newTaille < 5 || newTaille > 19) {
                throw new IllegalArgumentException("board size outside engine's limits");
            }
            this.taille = newTaille;
            this.board = new Board(taille);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("board size outside engine's limits");
        }
    }

    /**
     * Réinitialise le plateau à un état vide.
     */
    private void clearBoard() {
        this.board = new Board(taille);
    }

    /**
     * Joue un coup sur le plateau.
     * @param parts Commande décomposée en parties (exemple : ["play", "B", "D5"]).
     * @throws IllegalArgumentException si la commande est mal formée ou si la position est occupée.
     */
    private void playMove(String[] parts) {
        if (parts.length < 3) {
            throw new IllegalArgumentException("Invalid command. Use : play <color> <vertex>");
        }
        Color color = checkColorValid(parts[1]); // Analyse la couleur (B ou W).
        Coordonnees coord = checkCoordinatesValid(parts[2].toUpperCase()); // Analyse les coordonnées.

        if (board.isOccupied(coord)) {
            throw new IllegalArgumentException("Illegal move");
        }
        board.addBoule(new Boule(coord, color));
    }

    /**
     * Génère un mouvement aléatoire pour une couleur donnée.
     * @param colorArg Couleur pour laquelle générer un mouvement (exemple : "B").
     * @throws IllegalArgumentException si la couleur est invalide.
     */
    private void generateMove(String colorArg) {
        Color color = checkColorValid(colorArg);
        Coordonnees randomMove = findRandomEmptyCell(); // Trouve une case vide au hasard.
        board.addBoule(new Boule(randomMove, color)); // Place une boule à cette position.
        io.sendResponse("=" + nbCommande + " " + formatCoordinates(randomMove));
    }

    /**
     * Affiche le plateau dans le format GTP.
     */
    private void showBoard() {
        io.sendResponse(board.toString());
    }

    // --- Méthodes utilitaires ---

    /**
     * Analyse la couleur à partir d'une chaîne.
     * @param colorArg Chaîne représentant la couleur (exemple : "B" ou "W").
     * @return {@code Color.Black} ou {@code Color.White}.
     * @throws IllegalArgumentException si la couleur est invalide.
     */
    private Color checkColorValid(String colorArg) {
        return switch (colorArg.toUpperCase()) {
            case "BLACK" -> Color.Black;
            case "WHITE" -> Color.White;
            default -> throw new IllegalArgumentException("Invalid Color");
        };
    }

    /**
     * Analyse une position sous forme de chaîne en coordonnées.
     * @param input Position en notation GTP (exemple : "D5").
     * @return Objet {@code Coordonnees} correspondant à la position.
     * @throws IllegalArgumentException si la position est invalide.
     */
    private Coordonnees checkCoordinatesValid(String input) {
        if (input.isEmpty() || input.charAt(0) < 'A' || input.charAt(0) >= 'A' + taille - 1) {
            throw new IllegalArgumentException("Invalid vertex");
        }
        int x = input.charAt(0) - 'A'; // Colonne.
        try {
            int y = Integer.parseInt(input.substring(1)) - 1; // Ligne.
            if (y < 0 || y >= taille) {
                throw new IllegalArgumentException("Invalid vertex");
            }
            return new Coordonnees(x, y);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid vertex");
        }
    }

    /**
     * Formate des coordonnées en notation GTP.
     * @param coord Objet {@code Coordonnees}.
     * @return Position au format GTP (exemple : "D5").
     */
    private String formatCoordinates(Coordonnees coord) {
        return (char) ('A' + coord.getX()) + Integer.toString(coord.getY() + 1);
    }

    /**
     * Trouve une cellule vide aléatoire sur le plateau.
     * @return Coordonnées d'une cellule vide.
     */
    private Coordonnees findRandomEmptyCell() {
        int x, y;
        do {
            x = random.nextInt(taille);
            y = random.nextInt(taille);
        } while (board.isOccupied(new Coordonnees(x, y)));
        return new Coordonnees(x, y);
    }
}
