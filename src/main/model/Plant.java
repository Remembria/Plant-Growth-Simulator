package model;

//A representation of the plant agent, including its L-System
public class Plant extends LindenmayerSystem {

    // CONSTRAINT: 0 <= thirst <= 10, where 0 is well watered, and 10 deathly dry
    private float thirst;
    private final int growthRate;
    private String name;

    // REQUIRES: The alphabet of the axiom is within the set {"F", "+", "-", "[", "]"}
    // EFFECTS: Generates a plant seed
    public Plant(String name, String axiom) {
        this.name = name;
        super.lindenString = axiom;
        growthRate = (int) (Math.round(Math.random() * 100));
        super.setPredecessorsAndSuccessors("F", "FF-[-F+F+F]+[+F-F-F]");
    }

    // MODIFIES: this
    // EFFECTS: If the plant is healthy, updates its linden system to grow new branches
    public void grow() {
        if (thirst <= 5) {
            super.updateLindenSystem();
        }
    }

    // MODIFIES: this
    // EFFECTS: Decreases the value of thirst by 1
    public void water() {
        thirst = Math.max((thirst - 1), 0);
    }

    public void setThirst(float t) {
        thirst = t;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getThirst() {
        return thirst;
    }

    public String getName() {
        return name;
    }

}
