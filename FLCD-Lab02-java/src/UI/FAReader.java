package UI;

import Exceptions.InvalidFAException;
import Model.FiniteAutomata;
import Model.Grammar;
import Model.Transition;

import java.io.*;
import java.util.Scanner;

public class FAReader {
    private FiniteAutomata fa;
    private Scanner reader;
    private File file;

    public FAReader() throws FileNotFoundException {
        this.fa = new FiniteAutomata();
        this.reader = new Scanner(System.in);
        this.file = new File("res/input/inputFA.txt");
    }

    public void setFilePath(String path) {
        this.file = new File(path);
    }

    public FiniteAutomata getFa() {
        return this.fa;
    }

    private String faActionsMenu() {
        String menu = "Menu:\n\t0.Exit\n";
        menu += "\t1.Set of states\n";
        menu += "\t2.The alphabet\n";
        menu += "\t3.Set of transitions\n";
        menu += "\t4.Set of final states\n";
        menu += "\t5.Initial state\n";
        menu += "\t6.Read a finite automata\n";
        menu += "\t7.Load a finite automata\n";
        menu += "\t8.Convert to regular grammar\n";
        return menu;
    }

    private boolean checkInputFA(FiniteAutomata fa) throws InvalidFAException {
        if (fa.getSetOfStates().isEmpty())
            throw new InvalidFAException("Empty set of states!");
        else if (fa.getAlphabet().isEmpty())
            throw new InvalidFAException("Empty alphabet!");
        else if (fa.getTransitions().isEmpty())
            throw new InvalidFAException("Empty set of transitions!");
        else if (fa.getInitState().equals(""))
            throw new InvalidFAException("No initial state specified!");
        else if (fa.getSetOfFinals().isEmpty())
            throw new InvalidFAException("Empty set of final states");
        else
            return true;
    }

    private void readStates() {
        System.out.println("Enter number of states: ");
        int number = reader.nextInt();
        for(int i = 0; i < number; i++) {
            System.out.println("Enter state #" + i);
            String state = reader.next();
            fa.addState(state);
        }
    }

    private void readAlphabet() {
        System.out.println("Enter number of symbols: ");
        int number = reader.nextInt();
        for(int i = 0; i < number; i++) {
            System.out.println("Enter symbol #" + i);
            String symbol = reader.next();
            fa.addSymbol(symbol);
        }
    }

    private void readTransitions() throws InvalidFAException {
        System.out.println("Enter number of transitions: ");
        int number = reader.nextInt();
        for(int i = 0; i < number; i++) {
            System.out.print("S(");
            String from = reader.next();
            System.out.print(",");
            String path = reader.next();
            System.out.print(")=");
            String to = reader.next();
            if (!(this.fa.getSetOfStates().contains(from) || this.fa.getAlphabet().contains(path)))
                throw new InvalidFAException("No such path!");
            this.fa.addTransition(new Transition(from, to, path));
        }
    }

    private void readFinalStates() {
        System.out.println("Enter number of final states: ");
        int number = reader.nextInt();
        for(int i = 0; i < number; i++) {
            System.out.println("Enter final state #" + i);
            String state = reader.next();
            fa.addFinalState(state);
        }
    }

    private void readInitialState() throws InvalidFAException{
        System.out.println("Enter initial state: ");
        String initState = reader.next();
        fa.setInitState(initState);
        if (!(fa.getSetOfStates().contains(initState)))
            throw new InvalidFAException("No such symbol!");
    }

    private void printStates() {
        System.out.println("States: ");
        for(String state : this.fa.getSetOfStates())
            System.out.println(state + " ");
    }

    private void printAlphabet() {
        System.out.println("Alphabet: ");
        for(String symbol : this.fa.getAlphabet())
            System.out.println(symbol + " ");
    }

    private void printTransitions() {
        System.out.println("Transitions: ");
        for(Transition transition : this.fa.getTransitions())
            System.out.println(transition.toString() + "\n");
    }

    private void printFinalStates() {
        System.out.println("Final States: ");
        for(String fstate : this.fa.getSetOfFinals())
            System.out.println(fstate + " ");
    }

    private void printInitialState() {
        System.out.println("Initial State: " + this.fa.getInitState());
    }

    public void run() throws InvalidFAException, IOException {
        int option;
        boolean loop = true;
        while(loop) {
            System.out.println(this.faActionsMenu());
            System.out.println("Enter option: ");
            option = reader.nextInt();
            switch (option) {
                case 0:
                    loop = false;
                    break;
                case 1:
                    checkInputFA(fa);
                    printStates();
                    break;
                case 2:
                    checkInputFA(fa);
                    printAlphabet();
                    break;
                case 3:
                    checkInputFA(fa);
                    printTransitions();
                    break;
                case 4:
                    checkInputFA(fa);
                    printFinalStates();
                    break;
                case 5:
                    checkInputFA(fa);
                    printInitialState();
                    break;
                case 6:
                    fa = new FiniteAutomata();
                    readFA();
                    System.out.println(this.fa.toString());
                    break;
                case 7:
                    fa = new FiniteAutomata();
                    loadFA();
                    System.out.println(this.fa.toString());
                    break;
                case 8:
                    Grammar g = fa.toGrammar();
                    System.out.println(g.toString());
                    break;
            }
        }
    }

    private void readFA() throws InvalidFAException {
        readStates();
        readAlphabet();
        readTransitions();
        readInitialState();
        readFinalStates();
    }

    private void loadFA() throws IOException, InvalidFAException {
        BufferedReader br = new BufferedReader(new FileReader(file));

        br.readLine();  //read Q=
        String[] tokens = br.readLine().split(" ");
        for(String t : tokens)
            this.fa.addState(t);

        br.readLine();  //read E=
        tokens = br.readLine().split(" ");
        for(String t : tokens)
            this.fa.addSymbol(t);

        br.readLine();  //read q0=
        tokens = br.readLine().split(" ");
        if (tokens.length != 1)
            throw new InvalidFAException("Invalid initial state!");
        this.fa.setInitState(tokens[0]);

        br.readLine();  //read F=
        tokens = br.readLine().split(" ");
        for(String t : tokens)
            this.fa.addFinalState(t);

        //read Transitions:
        String line = br.readLine();
        while (line != null) {
            tokens = line.split(" ");
            String from = tokens[1];
            String path = tokens[2];
            String to = tokens[4];
            this.fa.addTransition(new Transition(from, to, path));
            line = br.readLine();
        }
    }
}
