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
    public void addPlantTestSingular() {
        ArrayList<Plant> listOfPlants = new ArrayList<Plant>();
        listOfPlants.add(plant);
        testGarden.addPlant(plant);
        assertEquals(listOfPlants, testGarden.getListOfPlants());
    }

    @Test
    public void addPlantTestMultiple() {
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
    }

    @Test
    public void removePlantTestBasic() {
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
    public void removePlantTestSingular() {
        testGarden.addPlant(plant);
        assertEquals(1, testGarden.getListOfPlants().size());
        assertEquals(plant, testGarden.getListOfPlants().get(0));
        testGarden.removePlant("Poppy");
        assertEquals(0, testGarden.getListOfPlants().size());
    }

    @Test
    public void removePlantTestOneFromMultiple() {
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
    public void removePlantTestMultipleFromMultiple() {
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


}