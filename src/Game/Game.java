package Game;

import Utils.Color;
import Utils.IO;

/**
 * La classe {@code Game} gère une session de jeu, permettant aux joueurs de jouer au jeu Gomoku.
 * Elle permet de démarrer une partie, exécuter des commandes, et de gérer l'état du jeu.
 */
public class Game {

    private static final int TAILLE_DEFAULT = 19; //Taille du plateau par défaut
    private static final int NB_BOULE_ALIGNE = 5; //Nombre de boules à aligner pour gagner

    public Board getBoard() {
        return board;
    }

    private Board board;
    private final IO io;
    private int taille;
    private boolean gameFinished;
    private boolean gameStarted;

    /**
     * Constructeur de la classe {@code Game}. Initialise le plateau de jeu, la taille par défaut,
     * et crée une instance de {@code IO} pour la gestion des entrées utilisateur.
     */
    public Game() {
        this.taille = TAILLE_DEFAULT;
        this.io = new IO();
        this.gameFinished = false;
        this.gameStarted = false;
    }

    /**
     * Démarre une nouvelle session de jeu. La taille du plateau est déterminée par l'utilisateur.
     */
    public void startSession() {
        taille = io.getTailleDebutPartie();
        this.board = new Board(taille);
        jouePartie();
    }

    /**
     * Permet de jouer une partie en boucle, en exécutant les commandes de l'utilisateur jusqu'à ce que la partie soit terminée.
     */
    public void jouePartie() { //Permet de jouer une partie
        do {
            executeCommande(io.getCommande());
        } while (!checkPartieFinie());
    }

    /**
     * Exécute la commande entrée par l'utilisateur en fonction de l'action spécifiée.
     * @param commande La commande entrée par l'utilisateur.
     */
    protected void executeCommande(String commande) {
        String[] parts = commande.split(" ", 2);
        String action = parts[0];
        String argument = parts.length > 1 ? parts[1] : null;
        switch (action) {
            case "quit":
                gameFinished = true;
                break;
            case "boardsize":
                commandBoardSize(argument);
                break;
            case "clearboard":
                board = new Board(taille);
                break;
            case "showboard":
                System.out.println(board);
                break;
            case "play":
                if (argument != null) {
                    gameStarted = true;
                    playTour(commande);
                } else {
                    System.out.println("Erreur : Argument manquant pour la commande 'play'.");
                }
                break;
            case "partiestop":
                if (gameStarted) {
                    gameStarted = false;
                    System.out.println("Partie arrêtée.");
                } else {
                    System.out.println("Erreur : Aucune partie en cours à arrêter.");
                }
                break;
            default:
                System.out.println("Commande inconnue : " + action);
                break;
        }
    }

    /**
     * Définit la taille du plateau de jeu si aucune partie n'a commencé.
     * @param argument Taille du plateau sous forme de chaîne de caractères.
     */
    private void commandBoardSize(String argument) throws NumberFormatException {

        if (gameStarted) {
            System.out.println("Une partie est déjà en cours ! Veuillez la terminer ou y mettre fin (partiestop) !");
            return;
        }
        if (argument == null) {
            System.out.println("Erreur : Aucun argument fourni pour la taille.");
            return;
        }
        try {
            taille = Integer.parseInt(argument);
            assert  taille <= TAILLE_DEFAULT && taille >= NB_BOULE_ALIGNE;
            board = new Board(taille);
            gameStarted = true;
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Erreur : " + argument + " pour la taille.Veuillez entrer un entier.");
        }
    }

    /**
     * Joue un tour de jeu en vérifiant la validité du mouvement avant de l'appliquer.
     * @param commande La commande contenant la couleur et la position du mouvement.
     */
    private void playTour(String commande) throws IllegalArgumentException {
        try {
            String[] parts = commande.toUpperCase().split(" ");
        if(parts.length != 3) {
            System.out.println("Commande incorrecte, veuillez entrer une couleur et un mouvement !");
            return;
        }
        char color = parts[1].charAt(0);
        String mouvement = parts[2];

        if(color != 'B' && color != 'W') {
            System.out.println("Couleur incorrect, couleur possible : B (Black) | W (White)");
            return;
        }
        if(!isMouvementPossible(mouvement)) {
            System.out.println("Mouvement incorrect, Mouvement disponible compris entre A0 et " +
                    ((char) ('A' + taille - 1)) + (taille - 1) + "\n" + getMouvementDisponible());
            return;
        }
        if(!isPositionNonOccupee(mouvement)) {
            System.out.println("Mouvement impossible, une boule est déjà à ces coordonnées !");
            return;
        }
        Coordonnees coord = new Coordonnees((int) mouvement.charAt(0) - 'A', Integer.parseInt(mouvement.substring(1)));
        Color bouleColor = (color == 'B' ? Color.Black : Color.White);
        board.addBoule(new Boule(coord, bouleColor));
        }
        catch(IllegalArgumentException e) {
            throw  new IllegalArgumentException("Commande inexistant : " + commande);
        }
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
     * Génère et retourne une chaîne représentant les mouvements disponibles sur le plateau.
     * @return Une chaîne contenant les mouvements disponibles.
     */
    private String getMouvementDisponible() {
        StringBuilder s = new StringBuilder("[\n");
        for(int i = 0; i < taille; ++i){
            for(int j = 0; j < taille; ++j) {
                if(board.getGrille()[i][j] == '.') {
                    s.append((char) ('A' + i)).append(j).append((j == taille - 1 ? "" : ","));
                }
            }
            s.append("\n");
        }
        s.append("]\n");
        return s.toString();
    }


    protected int getTaille() {
        return taille;
    }

}
