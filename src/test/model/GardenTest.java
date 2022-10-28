package model;

import exceptions.EmptyGardenException;
import exceptions.InvalidSeedAlphabetException;
import exceptions.NameAlreadyInGardenException;
import exceptions.NameNotInGardenException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class GardenTest {
    private Garden testGarden;
    private Plant plant;

    @BeforeEach
    public void setup() {
        testGarden = new Garden();
        try {
            plant = new Plant("Poppy", "F");
        } catch (InvalidSeedAlphabetException e) {
            fail("Plant needs to have valid alphabet");
        }
    }

    @Test
    public void testAddPlantSingular() {
        ArrayList<Plant> listOfPlants = new ArrayList<Plant>();
        listOfPlants.add(plant);
        try {
            testGarden.addPlant(plant);
        } catch (NameAlreadyInGardenException e) {
            fail("Name should not already be in garden");
        }
        assertEquals(listOfPlants, testGarden.getListOfPlants());
    }

    @Test
    public void testAddPlantMultiple() {
        try {
            plant = new Plant("Poppy", "F");
            Plant plantTwo = new Plant("Alexander", "F");
            Plant plantThree = new Plant("Lily", "F");
            Plant plantFour = new Plant("Pretzel", "F-F-F");
            ArrayList<Plant> listOfPlants = new ArrayList<Plant>();
            listOfPlants.add(plant);
            listOfPlants.add(plantTwo);
            listOfPlants.add(plantThree);
            testGarden.addPlant(plant);
            testGarden.addPlant(plantTwo);
            testGarden.addPlant(plantThree);
            assertEquals(listOfPlants, testGarden.getListOfPlants());
            testGarden.addPlant(plantFour);
            listOfPlants.add(plantFour);
            assertEquals(listOfPlants, testGarden.getListOfPlants());
        } catch (InvalidSeedAlphabetException e) {
            fail("Invalid plant alphabet given");
        } catch (NameAlreadyInGardenException e) {
            fail("Name should not already be in garden");
        }
    }

    @Test
    public void testAddPlantNameAlreadyInGardenException() {
        ArrayList<Plant> listOfPlants = new ArrayList<Plant>();
        listOfPlants.add(plant);
        try {
            testGarden.addPlant(plant);
            Plant lily = new Plant("Lily", "F");
            testGarden.addPlant(lily);
            listOfPlants.add(lily);
            testGarden.addPlant(new Plant("Poppy", "FF+"));
            fail("No exception given");
        } catch (NameAlreadyInGardenException e) {
            //Perfect
        } catch (InvalidSeedAlphabetException e) {
            fail("Seed should be valid");
        }
        assertEquals(listOfPlants, testGarden.getListOfPlants());
    }

    @Test
    public void testRemovePlantBasic() {
        try {
            Plant plantTwo = new Plant("Evergreen", "F+");
            testGarden.addPlant(plant);
            testGarden.addPlant(plantTwo);
            assertEquals(2, testGarden.getListOfPlants().size());
            assertEquals(plant, testGarden.getListOfPlants().get(0));
            assertEquals(plantTwo, testGarden.getListOfPlants().get(1));
            testGarden.removePlant();
            assertEquals(1, testGarden.getListOfPlants().size());
            assertEquals(plant, testGarden.getListOfPlants().get(0));
        } catch (InvalidSeedAlphabetException e) {
            fail("Invalid plant alphabet given");
        } catch (NameAlreadyInGardenException e) {
            fail("Name should not already be in garden");
        } catch (EmptyGardenException e) {
            fail("Cannot remove a plant from an empty garden");
        }

    }

    @Test
    public void testRemovePlantSingular() {
        try {
            testGarden.addPlant(plant);
            assertEquals(1, testGarden.getListOfPlants().size());
            assertEquals(plant, testGarden.getListOfPlants().get(0));
            testGarden.removePlant("Poppy");
            assertEquals(0, testGarden.getListOfPlants().size());
        } catch (NameAlreadyInGardenException e) {
            fail("Name should not already be in garden");
        } catch (NameNotInGardenException e) {
            fail("Name cannot be found");
        }
    }

    @Test
    public void testRemovePlantOneFromMultiple() {
        try {
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
        } catch (InvalidSeedAlphabetException e) {
            fail("Invalid plant alphabet given");
        } catch (NameAlreadyInGardenException e) {
            fail("Name should not already be in garden");
        } catch (NameNotInGardenException e) {
            fail("Name cannot be found");
        }
    }

    @Test
    public void testRemovePlantSingularEmptyGardenException() {
        try {
            testGarden.removePlant();
            assertEquals(0, testGarden.getListOfPlants().size());
            fail("No exception given");
        } catch (EmptyGardenException e) {
            //Perfect
        }
        assertEquals(0, testGarden.getListOfPlants().size());
    }

    @Test
    public void testRemovePlantNameNotInGardenException() {
        try {
            testGarden.removePlant("Popp");
            assertEquals(0, testGarden.getListOfPlants().size());
            fail("No exception given");
        } catch (NameNotInGardenException e) {
            //Perfect
        }
        assertEquals(0, testGarden.getListOfPlants().size());
    }

    @Test
    public void testWaterPlantSingularName() {
        plant.setThirst(5);
        try {
            testGarden.addPlant(plant);
            assertEquals(1, testGarden.getListOfPlants().size());
            assertEquals(plant, testGarden.getListOfPlants().get(0));
            testGarden.waterPlant("Poppy", 1);
            assertEquals(4, plant.getThirst());
        } catch (NameAlreadyInGardenException e) {
            fail("Name should not already be in garden");
        } catch (NameNotInGardenException e) {
            fail("Name should have already been added to garden");
        }
    }

    @Test
    public void testWaterPlantMultiple() {
        try {
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
            testGarden.waterPlant("Poppy", 2);
            testGarden.waterPlant("Charlie", 1);
            testGarden.waterPlant("Candice", 1);
            assertEquals(3, plant.getThirst());
            assertEquals(8, plantTwo.getThirst());
            assertEquals(-1, plantThree.getThirst());
        } catch (InvalidSeedAlphabetException e) {
            fail("Invalid plant alphabet given");
        } catch (NameAlreadyInGardenException e) {
            fail("Name should not already be in garden");
        } catch (NameNotInGardenException e) {
            fail("Name should have already been added to garden");
        }
    }

    @Test
    public void testWaterPlantSingularNameNotInGardenException() {
        plant.setThirst(5);
        try {
            testGarden.addPlant(plant);
            assertEquals(1, testGarden.getListOfPlants().size());
            assertEquals(plant, testGarden.getListOfPlants().get(0));
            testGarden.waterPlant("Oak", 1);
            fail("No exception given");
        } catch (NameAlreadyInGardenException e) {
            fail("Name should not already be in garden");
        } catch (NameNotInGardenException e) {
            //Perfect
        }
        assertEquals(5, plant.getThirst());
    }

    @Test
    public void testNameInGardenEmpty() {
        assertFalse(testGarden.nameInGarden("Charlie"));
    }

    @Test
    public void testNameInGardenTrueSingular() {
        try {
            testGarden.addPlant(plant);
        } catch (NameAlreadyInGardenException e) {
            fail("Name should not already be in garden");
        }
        assertTrue(testGarden.nameInGarden("Poppy"));
    }

    @Test
    public void testNameInGardenFalseSingular() {
        try {
            testGarden.addPlant(plant);
        } catch (NameAlreadyInGardenException e) {
            fail("Name should not already be in garden");
        }
        assertFalse(testGarden.nameInGarden("Popp"));
    }

    @Test
    public void testNameInGardenMultiple() {
        try {
            Plant plantTwo = new Plant("Gogo", "F+F");
            Plant plantThree = new Plant("Bobo", "F--");
            testGarden.addPlant(plant);
            testGarden.addPlant(plantTwo);
            testGarden.addPlant(plantThree);
            assertTrue(testGarden.nameInGarden("Bobo"));
            assertFalse(testGarden.nameInGarden("Baba"));
        } catch (InvalidSeedAlphabetException e) {
            fail("Invalid plant alphabet given");
        } catch (NameAlreadyInGardenException e) {
            fail("Name should not already be in garden");
        }
    }

    @Test
    public void testToJsonSingular() {
        try {
            testGarden.addPlant(new Plant("Rob", "FFF", 9, (float) 3, (float) 5.5));
            testGarden.addPlant(new Plant("Lily", "F+F", 2, (float) 2, (float) 4));
            StringBuilder contentBuilder = new StringBuilder();

            try (Stream<String> stream = Files.lines(Paths.get("./data/testGardenToJsonGeneral.json"),
                    StandardCharsets.UTF_8)) {
                stream.forEach(s -> contentBuilder.append(s));
            }
            String jsonData = contentBuilder.toString();
            JSONObject jsonObject = new JSONObject(jsonData);

            JSONObject gardenJson = testGarden.toJson();
            JSONArray gardenJsonArray = gardenJson.getJSONArray("Plants");

            JSONArray jsonArray = jsonObject.getJSONArray("Plants");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject plantOne = (JSONObject) jsonArray.get(i);
                JSONObject plantTwo = (JSONObject) gardenJsonArray.get(i);
                assertEquals(plantOne.getString("name"), plantTwo.getString("name"));
                assertEquals(plantOne.getString("linden string"), plantTwo.getString("linden string"));
                assertEquals(plantOne.getInt("growth rate"), plantTwo.getInt("growth rate"));
                assertEquals(plantOne.getFloat("progress to grow"), plantTwo.getFloat("progress to grow"));
                assertEquals(plantOne.getFloat("thirst"), plantTwo.getFloat("thirst"));
            }
        } catch (IOException e) {
            fail("Couldn't read from file");
        } catch (InvalidSeedAlphabetException e) {
            fail("Seed alphabet incorrect");
        } catch (NameAlreadyInGardenException e) {
            fail("Name is already in garden");
        }
    }

}