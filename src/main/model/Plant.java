package model;

import exceptions.ImpossibleThirstException;
import exceptions.GameSpeedZeroException;
import exceptions.InvalidSeedAlphabetException;

import java.util.ArrayList;

//A representation of the plant agent, including its L-System
public class Plant extends LindenmayerSystem {

    public final int growthRate;

    // CONSTRAINT: 0 <= thirst <= 10, where 0 is well watered, and 10 deathly dry
    private float thirst;
    private float progressToGrow;
    private String name;

    // (Deprecated) REQUIRES: The alphabet of the axiom is valid (within the set {"F", "+", "-", "[", "]"})
    // EFFECTS: Generates a plant seed
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

    // (Deprecated) REQUIRES: gameSpeed != 0 (or else no growth will ever occur) (REMOVE)
    // MODIFIES: this
    // EFFECTS: Grows the plant given a certain elapsedTime has passed at a given rate
    public void grow(double elapsedTime, float gameSpeed) throws GameSpeedZeroException { //gameSpeed = 0.05
        if (gameSpeed == 0) {
            throw new GameSpeedZeroException();
        }
        setProgressToGrow(getProgressToGrow() - ((float) elapsedTime * gameSpeed));
        if (getProgressToGrow() <= 0) {
            setProgressToGrow(growthRate);
            if (thirst <= 5 && super.getLindenString().length() < 100) { // Caps plant size
                super.updateLindenSystem();
            }
        }
        setThirst((float) (getThirst() + (elapsedTime * gameSpeed)));
    }


    // MODIFIES: this
    // EFFECTS: Decreases the value of thirst by 1
    public void water(int amount) {
        //setThirst(Math.max((thirst - 1), 0));
        setThirst(thirst - amount);
    }

    // (Deprecated) REQUIRES: 0 <= thirst <= 10
    // EFFECTS: Returns how healthy the plant currently is
    public String health() throws ImpossibleThirstException {
        if (thirst < -1 || thirst > 10) {
            throw new ImpossibleThirstException();
        }
        if (thirst == -1) {
            return "Too much water...";
        } else if (thirst == 0) {
            return "Perfectly watered";
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
        int forwardsBrackets = 0;
        int backwardsBrackets = 0;
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
                forwardsBrackets++;
            } else if (seed.charAt(i) == '[') {
                backwardsBrackets--;
            }
        }
        if (forwardsBrackets == backwardsBrackets) {
            return true;
        } else {
            return false;
        }
    }

}
