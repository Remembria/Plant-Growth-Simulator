package model;

import java.util.ArrayList;
import java.util.List;

//An abstract class for the L-Systems that the Plant class applies
public abstract class LindenmayerSystem {

    protected String lindenString; // The current string representing the system
    // CONSTRAINT: The grammarPredecessors list and grammarSuccessors list are of the same size
    protected List<String> grammarPredecessors; //The production predecessors of the grammar
    protected List<String> grammarSuccessors; //The production successors of the grammar

    public LindenmayerSystem() {
        grammarPredecessors = new ArrayList<>();
        grammarSuccessors = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: Applies every production rule in parallel to the L-system
    protected void updateLindenSystem() {
        String newIteration = "";
        for (int i = 0; i < lindenString.length(); i++) {
            String currentLetter = String.valueOf(lindenString.charAt(i));
            if (grammarPredecessors.contains(currentLetter)) {
                newIteration = newIteration.concat(grammarSuccessors.get(grammarPredecessors.indexOf(currentLetter)));
            } else {
                newIteration = newIteration.concat(String.valueOf(lindenString.charAt(i)));
            }
        }
        lindenString = newIteration;
    }

    public String getLindenString() {
        return lindenString;
    }

    public List<String> getGrammarPredecessors() {
        return grammarPredecessors;
    }

    public List<String> getGrammarSuccessors() {
        return grammarSuccessors;
    }

    public void setPredecessorsAndSuccessors(String p, String s) {
        grammarPredecessors.add(p);
        grammarSuccessors.add(s);
    }

}
