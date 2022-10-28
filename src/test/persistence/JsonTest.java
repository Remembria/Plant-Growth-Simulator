package persistence;

import model.Plant;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkPlant(Plant plant, String name, String axiom, int growthRate, float progressToGrow,
                              float thirst) {
        assertEquals(name, plant.getName());
        assertEquals(axiom, plant.getLindenString());
        assertEquals(growthRate, plant.growthRate);
        assertEquals(progressToGrow, plant.getProgressToGrow());
    }
}
