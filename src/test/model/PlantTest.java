package model;

import exceptions.ImpossibleThirstException;
import exceptions.InvalidSeedAlphabetException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class PlantTest {

    private Plant plant;

    @BeforeEach
    public void setup() {
        try {
            plant = new Plant("Gerald", "F");
        } catch (InvalidSeedAlphabetException e) {
            fail("Invalid Seed Given");
        }
    }


    @Test
    public void testPlant() {
        assertEquals("Gerald", plant.getName());
        assertEquals("F", plant.getLindenString());
        assertEquals(0, plant.getThirst());
    }

    @Test
    public void testPlantInvalidSeedAlphabetException() {
        Plant plantTwo;
        try {
            plantTwo = new Plant("Jerome", "FFa");
            fail("No exception caught");
        } catch (InvalidSeedAlphabetException e) {
            //Perfect
        }
    }

    @Test
    public void testWaterMiddleCase() {
        plant.setThirst(5);
        plant.water(1);
        assertEquals(4, plant.getThirst());
    }

    @Test
    public void testWaterBoundaryCase() {
        plant.setThirst((float) 0.9);
        plant.water(1);
        assertEquals(0, plant.getThirst());
    }

    @Test
    public void testHealthNoExceptions() {
        try {
            plant.setThirst(-1);
            assertEquals("Too much water...", plant.health());
            plant.setThirst(0);
            assertEquals("Perfectly Watered", plant.health());
            plant.setThirst(1);
            assertEquals("Freshly Watered!!", plant.health());
            plant.setThirst(2);
            assertEquals("Healthy", plant.health());
            plant.setThirst(3);
            assertEquals("Healthy", plant.health());
            plant.setThirst(4);
            assertEquals("Thirsty...", plant.health());
            plant.setThirst(5);
            assertEquals("Thirsty...", plant.health());
            plant.setThirst(6);
            assertEquals("Thirsty...", plant.health());
            plant.setThirst(7);
            assertEquals("VERY THIRSTY", plant.health());
            plant.setThirst(8);
            assertEquals("VERY THIRSTY", plant.health());
            plant.setThirst(9);
            assertEquals("VERY THIRSTY", plant.health());
            plant.setThirst(10);
            assertEquals("S.O.S.", plant.health());
        } catch (ImpossibleThirstException e) {
            fail("No thirst exception expected");
        }
    }

    @Test
    public void testHealthBelowRange() {
        plant.setThirst(-2);
        try {
            plant.health();
            fail("No exception given");
        } catch (ImpossibleThirstException e) {
            //Perfect
        }
        assertEquals(-2, plant.getThirst());
    }

    @Test
    public void testHealthAboveRange() {
        plant.setThirst(11);
        try {
            plant.health();
            fail("No exception given");
        } catch (ImpossibleThirstException e) {
            //Perfect
        }
        assertEquals(11, plant.getThirst());
    }

    @Test
    public void testGrowTooThirstyCanUpdate() {
        plant.setThirst(6);
        plant.setProgressToGrow(0);
        plant.grow(1, 1);
        assertEquals("F", plant.getLindenString());
        assertEquals(7, plant.getThirst());
        assertEquals(plant.growthRate, plant.getProgressToGrow());
    }

    @Test
    public void testGrowJustEnoughThirstJustEnoughUpdate() {
        plant.setThirst(5);
        plant.setProgressToGrow(1);
        plant.grow(1, 1);
        assertEquals("FF-[-F+F+F]+[+F-F-F]", plant.getLindenString());
        assertEquals(6, plant.getThirst());
        assertEquals(plant.growthRate, plant.getProgressToGrow());
    }

    @Test
    public void testGrowthNotThirstyNoUpdate() {
        plant.setThirst(0);
        plant.setProgressToGrow((float) 2.1);
        plant.grow(2, 1);
        assertEquals("F", plant.getLindenString());
        assertEquals(2, plant.getThirst());
        assertEquals(((float) 2.1) - ((float) 2 * 1), plant.getProgressToGrow());
    }

    @Test
    public void testPlantGrowDifferentGameSpeed() {
        plant.setThirst(3);
        plant.setProgressToGrow((float) 6);
        plant.grow(2, 3);
        assertEquals("FF-[-F+F+F]+[+F-F-F]", plant.getLindenString());
        assertEquals(9, plant.getThirst());
        assertEquals(plant.growthRate, plant.getProgressToGrow());
    }

    @Test
    public void testPlantUpdateLindenSystemMany() {
        plant.setThirst(0);
        plant.setProgressToGrow(0);
        plant.grow(1, 1);
        assertEquals("FF-[-F+F+F]+[+F-F-F]", plant.getLindenString());
        assertEquals(1, plant.getThirst());
        assertEquals(plant.growthRate, plant.getProgressToGrow());
        plant.setProgressToGrow(1);
        plant.grow(1, 1);
        assertEquals("FF-[-F+F+F]+[+F-F-F]FF-[-F+F+F]+[+F-F-F]-" +
                "[-FF-[-F+F+F]+[+F-F-F]+FF-[-F+F+F]+[+F-F-F]+FF-" +
                "[-F+F+F]+[+F-F-F]]+[+FF-[-F+F+F]+[+F-F-F]-FF-" +
                "[-F+F+F]+[+F-F-F]-FF-[-F+F+F]+[+F-F-F]]", plant.getLindenString());
        assertEquals(2, plant.getThirst());
        assertEquals(plant.growthRate, plant.getProgressToGrow());
    }
}
