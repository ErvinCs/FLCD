package Model;

import Exceptions.InvalidGrammarException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Grammar {
    private Set<String> terminals;
    private Set<String> nonTerminals;
    private Set<Production> productions;
    private String startingSymbol;

    public Grammar() {
        this.terminals = new HashSet<>();
        this.nonTerminals = new HashSet<>();
        this.productions = new HashSet<>();
        this.startingSymbol = "";
    }

    public Grammar(Set<String> terminals, Set<String> nonTerminals, Set<Production> productions, String startingSymbol) {
        this.terminals = terminals;
        this.nonTerminals = nonTerminals;
        this.productions = productions;
        this.startingSymbol = startingSymbol;
    }

    public Set<Production> nonTerminalProductions(String nonTerminal) throws Exception {
        if (this.terminals.contains(nonTerminal))
            throw new Exception("The given element is a Terminal!");

        Set<Production> nonTermProds = new HashSet<>();
        for(Production p : this.productions) {
            if (p.getFrom().equals(nonTerminal)) {
                nonTermProds.add(p);
            }
        }

        return  nonTermProds;
    }

    public String setOfProductionsToString(Set<Production> prods) {
        String output = "Set of Productions: ";
        for(Production p : prods)
            output += p.toString();
        return output;
    }

    public boolean isRegular() {
        /*
        Daca non-terminalii din stanga, adica din N, implica Epsilon si is diferiti de S atunci nu-i Regular.
        Daca nici unul nu implica Epsilon atunci ii bine cat timp se gata intr-o litera mica i.e. terminal.
        */
        for(Production p : productions) {
            if (p.impliesEps() && !p.getFrom().equals(this.startingSymbol))
                return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String output = "";
        output += "Terminals: " + terminals.toString() + "\n";
        output += "Non-terminals: " + nonTerminals.toString() + "\n";
        output += "Productions: ";
        for(Production p : productions)
            output += p.toString();
        output += "Starting Symbol: " + startingSymbol + "\n";
        return output;
    }

    public String terminalsToString() {
        return "Terminals: " + terminals.toString();
    }

    public String nonTerminalsToString() {
        return "Non-terminals: " + nonTerminals.toString();
    }

    public String productionsToString() {
        String output = "Productions: ";
        for(Production p : productions)
            output += p.toString();
        return output;
    }

    public FiniteAutomata toFiniteAutomata() throws InvalidGrammarException{
        if (!this.isRegular())
            throw new InvalidGrammarException("Grammar is NOT regular!");
        FiniteAutomata fa = new FiniteAutomata();

        for(String nonTerm : this.getNonTerminals())
            fa.addState(nonTerm);

        for(String terminal : this.getTerminals())
            fa.addSymbol(terminal);

        fa.setInitState(this.getStartingSymbol());

        for(Production p : this.getProductions()) {
            Transition transition = new Transition();
            transition.setFromState(p.getFrom());
            if (p.getTo().size() < 2) {
                //if there is a nonTerm that implies only terminals, that's a final state
                fa.addFinalState("K/Eps");
                transition.setToState("K/Eps");
                transition.setPath(p.getTo().get(0));
                fa.addTransition(transition);
            } else {
                for (String to : p.getTo()) {
                    if (this.getTerminals().contains(to))
                        transition.setPath(to);
                    else if (this.getNonTerminals().contains(to))
                        transition.setToState(to);
                }
                fa.addTransition(transition);
            }
        }

        return fa;
    }

    public String getStartingSymbol() {
        return startingSymbol;
    }

    public Set<String> getTerminals() {
        return terminals;
    }

    public Set<String> getNonTerminals() {
        return nonTerminals;
    }

    public Set<Production> getProductions() {
        return productions;
    }

    public void setStartingSymbol(String startingSymbol) {
        this.startingSymbol = startingSymbol;
    }

    public void addTerminal(String terminal){
        this.terminals.add(terminal);
    }

    public void addNonTerminal(String nonTerminal){
        this.nonTerminals.add(nonTerminal);
    }

    public void addProduction(Production production){
        this.productions.add(production);
    }
}
