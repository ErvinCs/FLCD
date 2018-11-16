package Model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FiniteAutomata {
    private Set<String> setOfStates;
    private Set<String> alphabet;
    private Set<Transition> transitions;
    private String initState;
    private Set<String> setOfFinals;

    public FiniteAutomata() {
        this.setOfStates = new HashSet<>();
        this.alphabet = new HashSet<>();
        this.transitions = new HashSet<>();
        this.initState = "";
        this.setOfFinals = new HashSet<>();
    }

    public FiniteAutomata(Set<String> listOfStates, Set<String> alphabet, Set<Transition> transitions,
                          String initState, Set<String> setOfFinals) {
        this.setOfStates = listOfStates;
        this.alphabet = alphabet;
        this.transitions = transitions;
        this.initState = initState;
        this.setOfFinals = setOfFinals;
    }

    @Override
    public String toString() {
        String output = "";
        output += "States: " + setOfStates.toString() + "\n";
        output += "Alphabet: " + alphabet.toString() + "\n";
        output += "Transitions: \n";
        for(Transition t : transitions)
            output += t.toString() + "\n";
        output += "InitialState: " + initState + "\n";
        output += "FinalStates" + setOfFinals.toString() + "\n";
        return output;
    }

    public String statesToString() {
        return "States: " + setOfStates.toString();
    }

    public String alphabetToString() {
        return "Alphabet: " + alphabet.toString();
    }

    public String transitionsToString() {
        String output = "Transitions: ";
        for(Transition t : transitions)
            output += t.toString();
        return output;
    }

    public Grammar toGrammar() {
        Grammar grammar = new Grammar();

        for(String state : this.getSetOfStates())
            grammar.addNonTerminal(state);

        for(String symbol : this.getAlphabet())
            grammar.addTerminal(symbol);

        grammar.setStartingSymbol(this.getInitState());

        for(Transition t : this.getTransitions()) {
            Production p = new Production();
            p.setFrom(t.getFromState());
            if (!(this.getSetOfFinals().contains(t.getToState()))) {
                p.addTo(t.getPath());
                p.addTo(t.getToState());
                grammar.addProduction(p);
            } else if (this.getSetOfFinals().contains(t.getToState())) {
                p.addTo(t.getPath());
                p.addTo(t.getToState());

                List<String> to = new ArrayList<>();
                to.add(t.getPath());
                Production finalProd = new Production(t.getFromState(), to);

                grammar.addProduction(p);
                grammar.addProduction(finalProd);
            }
        }

        return grammar;
    }

    public String finalsToString() {
        return "FinalStates: " + setOfFinals.toString();
    }

    public String getInitState() {
        return initState;
    }

    public Set<String> getSetOfStates() {
        return setOfStates;
    }

    public Set<String> getAlphabet() {
        return alphabet;
    }

    public Set<Transition> getTransitions() {
        return transitions;
    }

    public Set<String> getSetOfFinals() {
        return setOfFinals;
    }

    public void addState(String state) {
        this.setOfStates.add(state);
    }

    public void addSymbol(String symbol) {
        this.alphabet.add(symbol);
    }

    public void addTransition(Transition transition) {
        this.transitions.add(transition);
    }

    public void setInitState(String initState) {
        this.initState = initState;
    }

    public void addFinalState(String finalState) {
        this.getSetOfFinals().add(finalState);
    }
}
