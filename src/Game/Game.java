package Game;

import Board.Board;
import Boules.Boule;
import Boules.Coordonnees;
import Utils.Color;
import Utils.IO;

import java.util.Random;

/**
 * La classe {@code Game} gère une session de jeu, permettant aux joueurs de jouer au jeu Gomoku.
 * Elle permet de démarrer une partie, exécuter des commandes, et de gérer l'état du jeu.
 */
public class Game {

    private static final int TAILLE_DEFAULT = 19; //Taille du plateau par défaut
    private static final int NB_BOULE_ALIGNE = 5; //Nombre de boules à aligner pour gagner

    private Board board;
    private final IO io;
    private final Random random;
    private int taille;
    private int nbrMoves;
    private boolean gameFinished;
    private boolean gameStarted;

    /**
     * Constructeur de la classe {@code Game}. Initialise le plateau de jeu, la taille par défaut,
     * et crée une instance de {@code IO} pour la gestion des entrées utilisateur.
     */
    public Game() {
        this.taille = TAILLE_DEFAULT;
        this.io = new IO();
        this.random = new Random();
        this.gameFinished = false;
        this.gameStarted = false;
        nbrMoves = 0;
    }

    /**
     * Démarre une nouvelle session de jeu. La taille du plateau est déterminée par l'utilisateur.
     */
    public void startSession() {
        this.board = new Board(taille);
        jouePartie();
    }


    /**
     * Permet de jouer une partie en boucle, en exécutant les commandes de l'utilisateur jusqu'à ce que la partie soit terminée.
     */
    public void jouePartie() { //Permet de jouer une partie
        do {
            try {
                ++nbrMoves;
                executeCommande(io.getCommande());
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } while (!checkPartieFinie());
    }

    /**
     * Exécute la commande entrée par l'utilisateur en fonction de l'action spécifiée.
     * @param commande La commande entrée par l'utilisateur.
     */
    protected void executeCommande(String commande) throws IllegalArgumentException {
        String[] parts = commande.split(" ", 2);
        String action = parts[0];
        String argument = parts.length > 1 ? parts[1] : null;
        switch (action) {
            case "quit":
                System.exit(0);
            case "boardsize":
                try {
                    commandBoardSize(argument);
                } catch (NumberFormatException e) {
                    System.out.println(e.getMessage());
                }
                break;
            case "clearboard":
                board = new Board(taille);
                break;
            case "showboard":
                System.out.println(board);
                break;
            case "play":
                gameStarted = true;
                try {
                    playTour(commande);
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
                break;
            case "partiestop":
                if (gameStarted) {
                    gameStarted = false;
                    board = new Board(taille);
                    System.out.println("Partie arrêtée.");
                } else {
                    throw new IllegalArgumentException("Erreur : Aucune partie en cours à arrêter.");
                }
                break;
            case "genmove":
                try {
                    genMove();
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
                break;
            default:
                throw new IllegalArgumentException("Commande inconnue : " + action);
        }
    }

    /**
     * Définit la taille du plateau de jeu si aucune partie n'a commencé.
     * @param argument Taille du plateau sous forme de chaîne de caractères.
     */
    private void commandBoardSize(String argument) throws NumberFormatException {
        if (gameStarted)
            throw new NumberFormatException("Une partie est déjà en cours ! Veuillez la terminer ou y mettre fin (partiestop) !");
        if (argument == null)
            throw new NumberFormatException(nbrMoves + "? board size outside engine's limits");
        try {
            taille = Integer.parseInt(argument);
            board = new Board(taille);
        } catch (NumberFormatException e) {
            throw new NumberFormatException(nbrMoves + "? board size outside engine's limits");
        }
    }

    /**
     * Joue un tour de jeu en vérifiant la validité du mouvement avant de l'appliquer.
     * @param commande La commande contenant la couleur et la position du mouvement.
     */
    private void playTour(String commande) throws IllegalArgumentException {
        String[] parts = commande.toUpperCase().split(" ");
        if(parts.length != 3)
            throw new IllegalArgumentException(nbrMoves + "? invalid vertex, illegal move");
        char color = parts[1].charAt(0);
        String mouvement = parts[2];

        if(color != 'B' && color != 'W')
            throw new IllegalArgumentException(nbrMoves + "? invalid color");
        if(!isMouvementPossible(mouvement))
            throw new IllegalArgumentException(nbrMoves + "? invalid vertex, illegal move");
        if(!isPositionNonOccupee(mouvement))
            throw new IllegalArgumentException(nbrMoves + "? invalid vertex, illegal move");

        Coordonnees coord = new Coordonnees(Integer.parseInt(mouvement.substring(1)), (int) mouvement.charAt(0) - 'A');
        Color bouleColor = (color == 'B' ? Color.Black : Color.White);
        board.addBoule(new Boule(coord, bouleColor));
    }

    /**
     * Joue un mouvement aléatoire sur le plateau.
     */
    private void genMove() {
        Coordonnees coor = createMoveRandom();
        Color color = (random.nextInt(2) == 0 ? Color.Black : Color.White);
        board.addBoule(new Boule(coor, color));
    }

    /**
     * Vérifie si le mouvement est possible sur le plateau, c'est-à-dire si les coordonnées sont valides.
     * @param mouvement Le mouvement sous forme de chaîne (ex: "A1").
     * @return {@code true} si le mouvement est valide, {@code false} sinon.
     */
    private boolean isMouvementPossible(String mouvement) {
        if (mouvement.isEmpty() || mouvement.charAt(0) < 'A' || mouvement.charAt(0) > 'A' + taille - 1) {
            return false;
        }
        try {
            int mouvementChiffre = Integer.parseInt(mouvement.substring(1));
            return mouvementChiffre >= 0 && mouvementChiffre < taille;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    /**
     * Vérifie si la position donnée par le mouvement est déjà occupée par une boule.
     * @param mouvement Le mouvement sous forme de chaîne (ex: "A1").
     * @return {@code true} si la position est libre, {@code false} si elle est occupée.
     */
    private boolean isPositionNonOccupee(String mouvement) {
        int mouvementLettre = mouvement.charAt(0) - 'A';
        int mouvementChiffre;

        try {
            mouvementChiffre = Integer.parseInt(mouvement.substring(1));
        } catch (NumberFormatException e) {
            // Si le mouvement est invalide, retourne false immédiatement
            return false;
        }

        return board.getBoule().stream()
                .noneMatch(b -> b.getCoordonnees().getX() == mouvementLettre &&
                        b.getCoordonnees().getY() == mouvementChiffre);
    }

    /**
     * Vérifie si la partie est terminée.
     * @return {@code true} si la partie est terminée, {@code false} sinon.
     */
    protected boolean checkPartieFinie() { //vérifie que la partie est fini
        return gameFinished;
    }

    /**
     * Génère et retourne un tableau de valeur représentant les mouvements disponibles sur le plateau.
     * @return Tableau contenant les mouvements disponibles.
     */
    private Coordonnees createMoveRandom() {
        int randomX, randomY;
        do {
            randomX = random.nextInt(taille);
            randomY = random.nextInt(taille);
        } while (board.getGrille()[randomX][randomY] != '.');
        return new Coordonnees(randomX, randomY);
    }

    /**
     * Récupérer la taille du Board.
     */
    protected int getTaille() {
        return taille;
    }

    /**
     * Définir une taille pour le Board.
     */
    protected void setTaille(int taille) {
        this.taille = taille;
    }

    protected Board getBoard() {
        return board;
    }
}
