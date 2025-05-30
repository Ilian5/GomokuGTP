package main.game;

import main.grille.Grille;
import main.player.BotAleatoire;
import main.player.BotMinimax;
import main.player.Human;
import main.player.Player;
import main.grille.GrilleAffichage;
import main.utils.Color;
import main.utils.Constante;
import main.utils.IO;


public class Game {

    private Grille grille; //Plateau de jeu.
    private final IO io; //Gestionnaire des entrées/sorties.
    private int nbAlignement;
    private Player p1, p2;
    private int currentPlayerIndex;

    public Game() {
        this.grille = new Grille(Constante.TAILLE_DEFAULT_BOARD);
        nbAlignement = Constante.getAlignmentToWin(Constante.TAILLE_DEFAULT_BOARD);
        this.io = new IO();
        this.p1 = new Human(Color.Black);
        this.p2 = new Human(Color.White);
    }

    /**
     * Démarre une session GTP. Attend les commandes du joueur via la console
     * et répond en fonction des actions demandées.
     */
    public void startSession() {
        boolean running = true;
        while (running) {
            Player currentPlayer = (currentPlayerIndex == 0) ? p1 : p2; // Déterminer le joueur actuel
            String command = io.getCommande().trim();
            if (command.isEmpty()) continue;
            try {
                running = executeCommand(command);
                if (currentPlayer instanceof BotMinimax && command.startsWith("genmove")) {
                    currentPlayer.playMove(grille);
                }
                if (isGameOver()) {
                    break;
                }

            } catch (IllegalArgumentException e) {
                io.sendError(e.getMessage());
            }
        }
        gameEnd();
    }

    private boolean isGameOver() {
        return grille.isFull() || (grille.getWinner(nbAlignement)!=Color.Blank.toChar());
    }

    private void gameEnd() {
        if(isGameOver()) //Je vérifie que la partie est fini
            io.sendResponse("Le joueur " + grille.getWinner(nbAlignement) + " a gagné la partie !");
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
            case "set_player" -> reponse = setPlayer(parts);
            case "quit" -> {return false;}
            default -> throw new IllegalArgumentException("invalid command");
        }
        if(nbCommande != 0)
            System.out.println("=" + nbCommande + " " + reponse);
        else
            System.out.println("= " + reponse);
        return true;
    }

    public String setPlayer(String[] parts) {
        if (parts.length < 3 || parts.length > 4) {
            throw new IllegalArgumentException("Commande invalide. Utilisez : set_player <couleur> <type> [profondeur]");
        }
        Color color = checkColorValid(parts[1]);
        Player newPlayer = checkPlayerTypeValid(parts[2], color, parts.length == 4 ? parts[3] : null);
        if (color == Color.Black)
            p1 = newPlayer;
        else
            p2 = newPlayer;
        return "Le joueur " + parts[1] + " a été défini comme : " + parts[2] + (parts.length == 4 ? " avec une profondeur de " + parts[3] : "");
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
            this.grille = new Grille(newTaille);
            nbAlignement = Constante.getAlignmentToWin(newTaille); //Si la taille est inférieur à 8 on mets un alignement de 3 sinon c'est 5
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
        this.grille = new Grille(taille);
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
        int[] moves = checkCoordinatesValid(parts[2].toUpperCase()); // Analyse les coordonnées.

        if (grille.isOccupied(moves[0], moves[1])) {
            throw new IllegalArgumentException("Illegal move");
        }
        grille.addBouleAt(moves[0], moves[1], color);
        currentPlayerIndex = (currentPlayerIndex + 1) % 2;
        return "";
    }

    private String generateMove(String colorArg) {
        if (colorArg == null) {
            throw new IllegalArgumentException("Invalid command. Use : genmove <color>");
        }
        Color color = checkColorValid(colorArg);
        Player player = (color == Color.Black) ? p1 : p2;

        if (!(player instanceof BotAleatoire) && !(player instanceof BotMinimax)) {
            throw new IllegalArgumentException("The specified player is not a bot.");
        }
        int[] move = player.playMove(grille);
        grille.addBouleAt(move[0], move[1], color);
        currentPlayerIndex = (currentPlayerIndex + 1) % 2;
        return formatCoordinates(move);
    }


    private String showBoard() {
        io.sendResponse(GrilleAffichage.afficherGrille(grille));
        return "";
    }

    // --- Méthodes utilitaires ---

    private Color checkColorValid(String colorArg) {
        return switch (colorArg.toUpperCase()) {
            case "BLACK" -> Color.Black;
            case "WHITE" -> Color.White;
            default -> throw new IllegalArgumentException("Invalid Color");
        };
    }

    private int[] checkCoordinatesValid(String input) {
        if (input.isEmpty() || input.charAt(0) < 'A' || input.charAt(0) >= 'A' + getBoardSize()) {
            throw new IllegalArgumentException("Invalid vertex");
        }
        int x = input.charAt(0) - 'A';
        try {
            int y = Integer.parseInt(input.substring(1)) - 1;
            if (y < 0 || y >= getBoardSize()) {
                throw new IllegalArgumentException("Invalid vertex");
            }
            return new int[]{x, y};
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid vertex");
        }
    }

    private Player checkPlayerTypeValid(String playerType, Color color, String profondeur) {
        switch (playerType.toLowerCase()) {
            case "humain":
                return new Human(color);
            case "minimax":
                if (profondeur == null) {
                    throw new IllegalArgumentException("Invalid depth");
                }
                try {
                    int depth = Integer.parseInt(profondeur);
                    return new BotMinimax(depth, nbAlignement, color);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid depth");
                }
            case "aleatoire":
                return new BotAleatoire(color);
            default:
                throw new IllegalArgumentException("Invalid type");
        }
    }

    private String formatCoordinates(int[] move) {
        return (char) ('A' + move[0]) + Integer.toString(move[1] + 1);
    }

    public int getBoardSize() {
        return grille.getTaille();
    }

    public Grille getGrille() {
        return grille;
    }
}
