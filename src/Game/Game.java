package Game;

import Utils.Color;
import Utils.IO;

public class Game {

    private static final int NB_BOULLE_ALIGNE = 5; //Le nombre de boulle à alligné pour gagner
    private static final int TAILLE_DEFAULT = 19; //La taille du tableau par défaut si celui-ci n'est pas configuré

    private Board board;
    private IO io;
    private int taille;
    private boolean gameFinished;
    private boolean gameStarted;

    public Game() {
        this.taille = TAILLE_DEFAULT;
        this.board = new Board(taille);
        this.io = new IO(); //IO pour Input/Output
        gameFinished = false;
        gameStarted = false;
    }

    public void startSession() {
        taille = io.getTailleDebutGame();
        jouePartie();
    }

    public void jouePartie() { //Permet de jouer une partie
        do {
            executeCommande(io.getCommande());
        } while (!checkPartieFinie());
        System.out.println("Partie finie");
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

    private void playTour(String commande) {
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

        if(!isMouvementPossible(mouvement)) { //Vérifie si le mouvement n'est pas en dehors du board
            System.out.println("Mouvement incorrect, Mouvement disponible compris entre A0 et " + ((char) ('A' + taille - 1)) + (taille - 1) + "\n" + getMouvementDisponible());
            return;
        }

        if(!isPositionNonOccupee(mouvement)) { //Vérifie qu'on peut placer une boulle au niveau du mouvement
            System.out.println("Mouvement impossible, une boule est déjà à ces coordonnées !");
            return;
        }
        Coordonnees coord = new Coordonnees((int) mouvement.charAt(0) - 'A', Integer.parseInt(mouvement.substring(1)));
        Color bouleColor = (color == 'B') ? Color.Black : Color.White;
        board.addBoule(new Boule(coord, bouleColor));
    }

    private boolean isMouvementPossible(String mouvement) {
        return (mouvement.charAt(0) - 'A' <= (taille - 1) &&
                Integer.parseInt(mouvement.substring(1)) <= taille - 1);
    }

    private boolean isPositionNonOccupee(String mouvement) {
        int mouvementLettre = mouvement.charAt(0) - 'A';
        int mouvementChiffre;
        try {
            mouvementChiffre = Integer.parseInt(mouvement.substring(1));
        } catch (NumberFormatException e) {
            return false;
        }
        return board.getBoule().stream()
                .noneMatch(b -> b.getCoordonnees().getX() == mouvementLettre &&
                        b.getCoordonnees().getY() == mouvementChiffre);
    }

    private void commandBoardSize(String argument) {
        if (!gameStarted) {
            if (argument != null) {
                try {
                    taille = Integer.parseInt(argument);
                    board = new Board(taille);
                    gameStarted = true;
                } catch (NumberFormatException e) {
                    System.out.println("Erreur : Taille invalide. Veuillez entrer un entier.");
                }
            } else {
                System.out.println("Erreur : Aucun argument fourni pour la taille.");
            }
        } else {
            System.out.println("Une partie est déjà en cours ! Veuillez la terminer ou y mettre fin (partiestop) !");
        }
    }

    private boolean checkPartieFinie() { //Vérifie que la partie est fini
        if(checkFiveBoules(Color.Black.toChar()) || checkFiveBoules(Color.White.toChar())) {
            gameFinished = true;
        }
        return gameFinished;
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
        s.append("]\n").append(board.toString());
        return s.toString();
    }

    private boolean checkFiveBoules(char pion) { //Vérifie s'il y a 5 boules allignés dans le plateau
        for(int i = 0; i <= NB_BOULLE_ALIGNE; ++i) {
            for(int j = 0; j <= NB_BOULLE_ALIGNE; ++j) {
                if(checkDirection(i, j, 0, 1, pion) ||
                   checkDirection(i, j, 1, 0, pion) ||
                   checkDirection(i, j, 1, 1, pion) ||
                   checkDirection(i, j, 1, -1, pion)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkDirection(int x, int y, int dx, int dy, char pion) { //Vérifie si à partir de x et y j'ai n boulles allignés en fonction de dx et dy
        int count = 0;
        for (int k = 0; k <= NB_BOULLE_ALIGNE; k++) {
            int nx = x + k * dx;
            int ny = y + k * dy;
            if (nx >= 0 && nx < taille && ny >= 0 && ny < taille && board.getGrille()[nx][ny] == pion) {
                count++;
            } else {
                break;
            }
        }
        return count == NB_BOULLE_ALIGNE;
    }
}