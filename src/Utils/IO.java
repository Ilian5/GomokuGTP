package Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class IO {

    private Scanner scanner;
    private ArrayList<String> listeCommande;

    public IO() {
        scanner = new Scanner(System.in);
        initialiseCommande();
    }

    public void initialiseCommande() {
        listeCommande = new ArrayList<>();
        listeCommande.add("quit");
        listeCommande.add("boardsize");
        listeCommande.add("clearboard");
        listeCommande.add("showboard");
        listeCommande.add("play");
    }

    public String getCommande() {
        System.out.println("Veuillez saisir le commande : ");
        String command = " ";
        Boolean commandeAvailable = false;
        while(true) {
            command = scanner.nextLine();
            for (String s : listeCommande) {
                if(command.startsWith(s))
                    commandeAvailable = true;
            }
            if(commandeAvailable == true)
                break;
            System.out.print("Veuillez saisir une commande existante : \n(Commande existante : ");
            for(String s : listeCommande) {
                System.out.print(s + ", ");
            }
            System.out.println(")");
        }
        return command;
    }


}
