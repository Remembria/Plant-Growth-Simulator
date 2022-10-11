package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GardenTest {
    private Garden testGarden;
    private Plant plant;

    @BeforeEach
    public void setup() {
        testGarden = new Garden();
        plant = new Plant("Poppy", "F");
    }

    @Test
    public void testAddPlantSingular() {
        ArrayList<Plant> listOfPlants = new ArrayList<Plant>();
        listOfPlants.add(plant);
        testGarden.addPlant(plant);
        assertEquals(listOfPlants, testGarden.getListOfPlants());
    }

    @Test
    public void testAddPlantMultiple() {
        Plant plant = new Plant("Poppy", "F");
        Plant plantTwo = new Plant("Alexander", "F");
        Plant plantThree = new Plant("Lily", "F");
        ArrayList<Plant> listOfPlants = new ArrayList<Plant>();
        listOfPlants.add(plant);
        listOfPlants.add(plantTwo);
        listOfPlants.add(plantThree);
        testGarden.addPlant(plant);
        testGarden.addPlant(plantTwo);
        testGarden.addPlant(plantThree);
        assertEquals(listOfPlants, testGarden.getListOfPlants());
        Plant plantFour = new Plant("Pretzel", "F-F-F");
        testGarden.addPlant(plantFour);
        listOfPlants.add(plantFour);
        assertEquals(listOfPlants, testGarden.getListOfPlants());

    }

    @Test
    public void testRemovePlantBasic() {
        Plant plantTwo = new Plant("Evergreen", "F+A");
        testGarden.addPlant(plant);
        testGarden.addPlant(plantTwo);
        assertEquals(2, testGarden.getListOfPlants().size());
        assertEquals(plant, testGarden.getListOfPlants().get(0));
        assertEquals(plantTwo, testGarden.getListOfPlants().get(1));
        testGarden.removePlant();
        assertEquals(1, testGarden.getListOfPlants().size());
        assertEquals(plant, testGarden.getListOfPlants().get(0));
    }

    @Test
    public void testRemovePlantSingular() {
        testGarden.addPlant(plant);
        assertEquals(1, testGarden.getListOfPlants().size());
        assertEquals(plant, testGarden.getListOfPlants().get(0));
        testGarden.removePlant("Poppy");
        assertEquals(0, testGarden.getListOfPlants().size());
    }

    @Test
    public void testRemovePlantOneFromMultiple() {
        Plant plantTwo = new Plant("Alexander", "F");
        Plant plantThree = new Plant("Lily", "F");
        testGarden.addPlant(plant);
        testGarden.addPlant(plantTwo);
        testGarden.addPlant(plantThree);
        assertEquals(3, testGarden.getListOfPlants().size());
        assertEquals(plant, testGarden.getListOfPlants().get(0));
        assertEquals(plantTwo, testGarden.getListOfPlants().get(1));
        assertEquals(plantThree, testGarden.getListOfPlants().get(2));
        testGarden.removePlant("Alexander");
        assertEquals(2, testGarden.getListOfPlants().size());
        assertEquals(plant, testGarden.getListOfPlants().get(0));
        assertEquals(plantThree, testGarden.getListOfPlants().get(1));
    }

    @Test
    public void testRemovePlantMultipleFromMultiple() {
        Plant plantTwo = new Plant("Alexander", "F");
        Plant plantThree = new Plant("Lily", "F");
        Plant plantFour = new Plant("Poppy", "F");
        Plant plantFive = new Plant("Wilson", "FF");
        testGarden.addPlant(plant);
        testGarden.addPlant(plantTwo);
        testGarden.addPlant(plantThree);
        testGarden.addPlant(plantFour);
        testGarden.addPlant(plantFive);
        assertEquals(5, testGarden.getListOfPlants().size());
        assertEquals(plant, testGarden.getListOfPlants().get(0));
        assertEquals(plantTwo, testGarden.getListOfPlants().get(1));
        assertEquals(plantThree, testGarden.getListOfPlants().get(2));
        assertEquals(plantFour, testGarden.getListOfPlants().get(3));
        assertEquals(plantFive, testGarden.getListOfPlants().get(4));
        testGarden.removePlant("Poppy");
        assertEquals(4, testGarden.getListOfPlants().size());
        assertEquals(plantTwo, testGarden.getListOfPlants().get(0));
        assertEquals(plantThree, testGarden.getListOfPlants().get(1));
        assertEquals(plantFour, testGarden.getListOfPlants().get(2));
        assertEquals(plantFive, testGarden.getListOfPlants().get(3));
    }

    @Test
    public void testWaterPlantSingularName() {
        plant.setThirst(5);
        testGarden.addPlant(plant);
        assertEquals(1, testGarden.getListOfPlants().size());
        assertEquals(plant, testGarden.getListOfPlants().get(0));
        testGarden.waterPlant("Poppy");
        assertEquals(4, plant.getThirst());
    }

    @Test
    public void testWaterPlantMultiple() {
        Plant plantTwo = new Plant("Candice", "FF");
        Plant plantThree = new Plant("Charlie", "F+");
        testGarden.addPlant(plant);
        testGarden.addPlant(plantTwo);
        testGarden.addPlant(plantThree);
        plant.setThirst(5);
        plantTwo.setThirst(9);
        plantThree.setThirst(0);
        assertEquals(3, testGarden.getListOfPlants().size());
        assertEquals(plant, testGarden.getListOfPlants().get(0));
        assertEquals(plantTwo, testGarden.getListOfPlants().get(1));
        assertEquals(plantThree, testGarden.getListOfPlants().get(2));
        testGarden.waterPlant("Poppy");
        testGarden.waterPlant("Poppy");
        testGarden.waterPlant("Charlie");
        testGarden.waterPlant("Candice");
        assertEquals(3, plant.getThirst());
        assertEquals(8, plantTwo.getThirst());
        assertEquals(0, plantThree.getThirst());
    }

    @Test
    public void testNameInGardenEmpty() {
        assertFalse(testGarden.nameInGarden("Charlie"));
    }

    @Test
    public void testNameInGardenTrueSingular() {
        testGarden.addPlant(plant);
        assertTrue(testGarden.nameInGarden("Poppy"));
    }

    @Test
    public void testNameInGardenFalseSingular() {
        testGarden.addPlant(plant);
        assertFalse(testGarden.nameInGarden("Popp"));
    }

    @Test
    public void testNameInGardenMultiple() {
        Plant plantTwo = new Plant("Gogo", "F+F");
        Plant plantThree = new Plant("Bobo", "F--");
        testGarden.addPlant(plant);
        testGarden.addPlant(plantTwo);
        testGarden.addPlant(plantThree);
        assertTrue(testGarden.nameInGarden("Bobo"));
        assertFalse(testGarden.nameInGarden("Baba"));
    }
}