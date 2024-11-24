package Game;

import Utils.Color;
import Utils.IO;

public class Game {

    private static final int TAILLE_DEFAULT = 19; //Taille du plateau par défaut
    private static final int NB_BOULLE_ALIGNE = 5; //Nombre de boulle à aligner pour gagner

    private Board board;
    private IO io;
    private int taille;
    private boolean gameFinished;
    private boolean gameStarted;

    public Game() {
        this.taille = TAILLE_DEFAULT;
        this.board = new Board(taille);
        this.io = new IO();
        this.gameFinished = false;
        this.gameStarted = false;
    }

    public void startSession() {
        taille = io.getTailleDebutPartie();
        jouePartie();
    }

    public void jouePartie() { //Permet de jouer une partie
        do {
            executeCommande(io.getCommande());
        } while (!checkPartieFinie());
    }

    private void executeCommande(String commande) {
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

    private void commandBoardSize(String argument) {
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
            board = new Board(taille);
            gameStarted = true;
        } catch (NumberFormatException e) {
            System.out.println("Erreur : Taille invalide. Veuillez entrer un entier.");
        }
    }

    private void playTour(String commande) {
        if(commande.split(" ").length == 3) {
            commande = commande.toUpperCase();
            char color = commande.split(" ")[1].charAt(0);
            String mouvement = commande.split(" ")[2];
            if(!(color == 'B' || color == 'W')) {
                System.out.println("Couleur incorrect, couleur possible : B (Black) | W (White)");
                return;
            }
            if(!(mouvement.charAt(0) - 'A' <= (taille - 1) && Integer.parseInt(mouvement.substring(1)) <= taille - 1)) {
                System.out.println("Mouvement incorrect, Mouvement disponible compris entre A0 et " + ((char) ('A' + taille - 1)) + (taille - 1) + "\n" + getMouvementDisponible());
                return;
            }
            if(!mouvementPossible(mouvement)) {
                System.out.println("Mouvement impossible, une boule est déjà à ces coordonnées !");
                return;
            }

            board.addBoule(new Boule(new Coordonnees((int) mouvement.charAt(0) - 'A', Integer.parseInt(mouvement.substring(1))), (color == 'B' ? Color.Black : Color.White)));
        } else {
            System.out.println("Commande incorrecte, veuillez entrer une couleur et un mouvement !");
        }
    }

    private boolean mouvementPossible(String mouvement) {
        int mouvementLettre = mouvement.charAt(0) - 'A';
        int mouvementChiffre = Integer.parseInt(mouvement.substring(1));
        boolean mouvementPossible = true;
        for(Boule b : board.getBoule()) {
            if(b.getCoordonnees().getX() == mouvementLettre && b.getCoordonnees().getY() == mouvementChiffre) {
                mouvementPossible = false;
            }
        }
        return mouvementPossible;
    }

    private boolean checkPartieFinie() { //vérifie que la partie est fini
        return partieFinie;
    }

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
        s.append(board.toString());
        return s.toString();
    }

}
