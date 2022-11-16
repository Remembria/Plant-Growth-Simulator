package model;

import exceptions.ImpossibleThirstException;
import exceptions.GameSpeedZeroException;
import exceptions.InvalidSeedAlphabetException;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

//A representation of the plant agent, including its L-System
public class Plant extends LindenmayerSystem implements Writable {

    public final int growthRate;

    // CONSTRAINT: -1 <= thirst <= 10, where -1 is over-watered, and 10 deathly dry
    private float thirst;
    private float progressToGrow;
    private String name;

    // (Deprecated) REQUIRES: The alphabet of the axiom is valid (within the set {"F", "+", "-", "[", "]"})
    // EFFECTS: Generates a plant seed
    // Throws an InvalidSeedAlphabetException error if seed is invalid – so either alphabet {"F", "+", "-", "[", "]"}
    // is wrong or brackets mismatched
    public Plant(String name, String axiom) throws InvalidSeedAlphabetException {
        if (!isValidSeed(axiom)) {
            throw new InvalidSeedAlphabetException();
        }
        this.name = name;
        super.lindenString = axiom;
        growthRate = (int) Math.max(1, (Math.round(Math.random() * 10)));
        progressToGrow = growthRate;
        super.setPredecessorsAndSuccessors("F", "FF-[-F+F+F]+[+F-F-F]");
    }

    // EFFECTS: Generates a plant seed with control over every parameter
    public Plant(String name, String axiom, int growthRate, float progressToGrow, float thirst)
            throws InvalidSeedAlphabetException {
        if (!isValidSeed(axiom)) {
            throw new InvalidSeedAlphabetException();
        }
        this.name = name;
        super.lindenString = axiom;
        this.growthRate = growthRate;
        this.progressToGrow = progressToGrow;
        this.thirst = thirst;
        super.setPredecessorsAndSuccessors("F", "FF-[-F+F+F]+[+F-F-F]");
    }

    // (Deprecated) REQUIRES: gameSpeed != 0 (or else no growth will ever occur) (REMOVE)
    // MODIFIES: this
    // EFFECTS: Grows the plant given a certain elapsedTime has passed at a given rate
    // Throws an unchecked exception if gameSpeed is zero as the plants won't grow: but is unchecked as may be useful
    // for testing or for user enjoyment
    public void grow(double elapsedTime, float gameSpeed) throws GameSpeedZeroException { //gameSpeed = 0.05
        if (gameSpeed == 0) {
            throw new GameSpeedZeroException();
        }
        setProgressToGrow(getProgressToGrow() - ((float) elapsedTime * gameSpeed));
        System.out.println((float) elapsedTime * gameSpeed);
        if (getProgressToGrow() <= 0) {
            setProgressToGrow(growthRate);
            if (thirst <= 5 && super.getLindenString().length() < 1000000) { // Caps plant size
                super.updateLindenSystem();
            }
        }
        setThirst((float) (getThirst() + ((elapsedTime * gameSpeed) / 4)));
    }


    // MODIFIES: this
    // EFFECTS: Decreases the value of thirst by 1
    public void water(int amount) {
        //setThirst(Math.max((thirst - 1), 0));
        setThirst(thirst - amount);
    }

    // EFFECTS: Returns how healthy the plant currently is
    // Throws an ImpossibleThirstException if thirst not in range -1 <= thirst <= 10
    public String health() throws ImpossibleThirstException {
        if (thirst < -1 || thirst > 10) {
            throw new ImpossibleThirstException();
        }
        if (thirst == -1) {
            return "Too Much Water...";
        } else if (thirst == 0) {
            return "Perfectly Watered";
        } else if (thirst == 1) {
            return "Freshly Watered!!";
        } else if (thirst <= 3) {
            return "Healthy";
        } else if (thirst <= 6) {
            return "Thirsty...";
        } else if (thirst <= 9) {
            return "VERY THIRSTY";
        } else {
            return "S.O.S.";
        }
    }

    public void setThirst(float t) {
        thirst = t;
    }

    public void setProgressToGrow(float v) {
        progressToGrow = v;
    }

    public float getProgressToGrow() {
        return progressToGrow;
    }

    public float getThirst() {
        return thirst;
    }

    public String getName() {
        return name;
    }

    // EFFECTS: Returns true if the given seed is within the lindenSystem alphabet and bracket syntax is correct
    private Boolean isValidSeed(String seed) {
        int bracketsBalance = 0;
        ArrayList<Character> alphabet = new ArrayList<Character>();
        alphabet.add('F');
        alphabet.add('+');
        alphabet.add('-');
        alphabet.add('[');
        alphabet.add(']');
        for (int i = 0; i < seed.length(); i++) {
            if (!alphabet.contains(seed.charAt(i))) {
                return false;
            } else if (seed.charAt(i) == '[') {
                bracketsBalance++;
            } else if (seed.charAt(i) == ']') {
                bracketsBalance--;
            }
        }
        if (bracketsBalance == 0) {
            return true;
        } else {
            return false;
        }
    }

    // EFFECTS: Returns this plant in the form of a JSONObject
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("growth rate", this.growthRate);
        json.put("thirst", this.thirst);
        json.put("progress to grow", this.progressToGrow);
        json.put("name", this.name);
        json.put("linden string", super.lindenString);
        return json;
    }
}
