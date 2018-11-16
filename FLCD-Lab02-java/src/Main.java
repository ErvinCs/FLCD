import Exceptions.InvalidFAException;
import Exceptions.InvalidGrammarException;
import UI.FAReader;
import UI.GrammarReader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static String printMainMenu() {
        String menu = "Menu:\n\t0.Exit\n";
        menu += "\t1.Grammar Actions\n";
        menu += "\t2.Finite Automata Actions\n";
        return menu;
    }

    public static void main(String[] args) {
        //Explica structura Gramaticii
        //Structura FA
        //Si cum faci conversia

        boolean loop = true;
        try {
            FAReader far = new FAReader();
            GrammarReader gr = new GrammarReader();
            Scanner reader = new Scanner(System.in);

            while(loop) {
                System.out.println(Main.printMainMenu() + "Enter option:");
                try {
                    int option = reader.nextInt();
                    switch (option) {
                        case 0:
                            loop = false;
                            break;
                        case 1:
                            gr.run();
                            break;
                        case 2:
                            far.run();
                            break;
                    }
                } catch (InvalidGrammarException ex) {
                    System.out.println("Catched:" + ex.toString());
                    System.out.println(far.getFa().toString());
                } catch (InvalidFAException ex) {
                    System.out.println("Catched:" + ex.toString());
                    System.out.println(gr.getGrammar().toString());
                } catch (IOException ex) {
                    System.out.println("Catched:" + ex.toString());
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex.toString());
        }
    }
}
