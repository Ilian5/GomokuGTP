package main.game;

import main.board.Board;
import main.bot.Bot;
import main.bot.BotAleatoire;
import main.bot.BotMinimax;
import main.boules.Boule;
import main.boules.Coordonnees;
import main.grille.GrilleAffichage;
import main.utils.Color;
import main.utils.Constante;
import main.utils.IO;

import java.util.Random;


public class Game {

    private Bot bot;
    private Board board; //Plateau de jeu.
    private final IO io; //Gestionnaire des entrées/sorties.
    private int nbAlignement;

    public Game() {
        this.board = new Board(Constante.TAILLE_DEFAULT_BOARD);
        this.io = new IO();
        this.bot = new BotMinimax(3);
        nbAlignement = Constante.ALIGNMENT_TO_WIN_LARGE;
    }

    /**
     * Démarre une session GTP. Attend les commandes du joueur via la console
     * et répond en fonction des actions demandées.
     */
    public void startSession() {
        boolean running = true;
        while (running) {
            String command = io.getCommande().trim();
            if (command.isEmpty()) continue;
            try {
                running = executeCommand(command);
                if(isGameOver())
                    break;
            } catch (IllegalArgumentException e) {
                io.sendError(e.getMessage());
            }
        }
        gameEnd();
    }

    private boolean isGameOver() {
        return board.getGrille().isFull() || board.getGrille().hasWinner(nbAlignement);
    }

    private void gameEnd() {
        if(isGameOver()) //Je vérifie que la partie est fini
            io.sendResponse("Le joueur " + board.getGrille().getColorWin(nbAlignement) + " a gagné la partie !");
    }

    /**
     * Exécute une commande GTP reçue en entrée.
     * @param command La commande sous forme de chaîne (exemple : "play B D5").
     * @return {@code false} si la commande est `quit` (fin de session), {@code true} sinon.
     * @throws IllegalArgumentException si la commande est invalide.
     */
    public boolean executeCommand(String command) {
        String[] parts = command.split(" ");
        String action = "";
        String argument = "";
        int nbCommande = 0;
        try {
            nbCommande = Integer.parseInt(parts[0]);
            action = parts[1].toLowerCase();
            argument = parts.length > 2 ? parts[2] : null;
        } catch (NumberFormatException e) {
            action = parts[0].toLowerCase();
            argument = parts.length > 1 ? parts[1] : null;
        }

        String reponse = "";
        switch (action) {
            case "boardsize" -> reponse = setBoardSize(argument);
            case "play" -> reponse = playMove(parts);
            case "clear_board" -> reponse = clearBoard();
            case "genmove" -> reponse = generateMove(argument);
            case "showboard" -> reponse = showBoard();
            case "quit" -> {return false;}
            default -> throw new IllegalArgumentException("invalid command");
        }
        if(nbCommande != 0)
            System.out.println("=" + nbCommande + " " + reponse);
        else
            System.out.println("= " + reponse);
        return true;
    }

    /**
     * Définit la taille du plateau de jeu.
     * @param argument Taille du plateau sous forme de chaîne (exemple : "19").
     * @throws IllegalArgumentException si la taille est invalide (en dehors de [5, 19]).
     */
    private String setBoardSize(String argument) {
        try {
            int newTaille = Integer.parseInt(argument);
            if (newTaille < Constante.MIN_BOARD || newTaille > Constante.MAX_BOARD) {
                throw new IllegalArgumentException("size outside engine's limits");
            }
            this.board = new Board(newTaille);
            nbAlignement = (newTaille < Constante.MIN_SIZE_FOR_FIVE_ALIGNMENT ? Constante.ALIGNMENT_TO_WIN_SMALL : Constante.ALIGNMENT_TO_WIN_LARGE); //Si la taille est inférieur à 8 on mets un alignement de 3 sinon c'est 5
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("size outside engine's limits");
        }
        return "";
    }

    /**
     * Réinitialise le plateau à un état vide.
     */
    private String clearBoard() {
        int taille = this.getBoardSize();
        this.board = new Board(taille);
        return "";
    }

    /**
     * Joue un coup sur le plateau.
     * @param parts Commande décomposée en parties (exemple : ["play", "B", "D5"]).
     * @throws IllegalArgumentException si la commande est mal formée ou si la position est occupée.
     */
    private String playMove(String[] parts) {
        if (parts.length < 3) {
            throw new IllegalArgumentException("Invalid command. Use : play <color> <vertex>");
        }
        Color color = checkColorValid(parts[1]); // Analyse la couleur (B ou W).
        Coordonnees coord = checkCoordinatesValid(parts[2].toUpperCase()); // Analyse les coordonnées.

        if (board.isOccupied(coord)) {
            throw new IllegalArgumentException("Illegal move");
        }
        board.addBoule(new Boule(coord, color));
        return "";
    }

    /**
     * Génère un mouvement aléatoire pour une couleur donnée.
     * @param colorArg Couleur pour laquelle générer un mouvement (exemple : "B").
     * @throws IllegalArgumentException si la couleur est invalide.
     */
    private String generateMove(String colorArg) {
        if(colorArg == null)
            throw new IllegalArgumentException("Invalid command. Use : genmove <color>");
        Color color = checkColorValid(colorArg);
        Coordonnees randomMove = bot.genMove(board, color);
        board.addBoule(new Boule(randomMove, color)); // Place une boule à cette position.
        return formatCoordinates(randomMove);

    }

    /**
     * Affiche le plateau dans le format GTP.
     */
    private String showBoard() {
        io.sendResponse(GrilleAffichage.afficherGrille(board.getGrille()));
        return "";
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
        if (input.isEmpty() || input.charAt(0) < 'A' || input.charAt(0) >= 'A' + getBoardSize()) {
            throw new IllegalArgumentException("Invalid vertex");
        }
        int x = input.charAt(0) - 'A'; // Colonne.
        try {
            int y = Integer.parseInt(input.substring(1)) - 1; // Ligne.
            if (y < 0 || y >= getBoardSize()) {
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

    public int getBoardSize() {
        return board.getGrille().getTaille();
    }

    public Board getBoard() {
        return board;
    }
}
