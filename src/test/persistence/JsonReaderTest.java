package persistence;

import exceptions.InvalidSeedAlphabetException;
import exceptions.NameAlreadyInGardenException;
import model.Garden;
import model.Plant;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Garden g = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        } catch (InvalidSeedAlphabetException e) {
            fail("InvalidSeedAlphabetException not expected here");
        } catch (NameAlreadyInGardenException e) {
            fail("NameAlreadyInGardenException not expected here");
        }
    }

    @Test
    void testReaderEmptyGarden() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyGarden.json");
        try {
            Garden garden = reader.read();
            assertEquals(0, garden.getListOfPlants().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        } catch (InvalidSeedAlphabetException e) {
            fail("InvalidSeedAlphabetException not expected here");
        } catch (NameAlreadyInGardenException e) {
            fail("NameAlreadyInGardenException not expected here");
        }
    }

    @Test
    void testReaderGeneralGarden() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralGarden.json");
        try {
            Garden garden = reader.read();
            List<Plant> listOfPlants = garden.getListOfPlants();
            assertEquals(2, listOfPlants.size());
            checkPlant(listOfPlants.get(0), "Lily", "FF-[-F+F+F]+[+F-F-F]",
                    2, 2, (float) 4.2, (float) 10.0, (float) 10.0, 30);
            checkPlant(listOfPlants.get(1), "Brentwood",
                    "FF-[-F+F+F]+[+F-F-F]FF-[-F+F+F]+[+F-F-F]FF-[-F+F+F]+[+F-F-F]+",
                    2, (float) 2, (float) 3.2, (float) 20.0, (float) 20.0, 30);
        } catch (IOException e) {
            fail("Couldn't read from file");
        } catch (InvalidSeedAlphabetException e) {
            fail("InvalidSeedAlphabetException not expected here");
        } catch (NameAlreadyInGardenException e) {
            fail("NameAlreadyInGardenException not expected here");
        }
    }

    @Test
    void testReaderSeedError() {
        JsonReader reader = new JsonReader("./data/testReaderSeedErrorGarden.json");
        try {
            Garden garden = reader.read();
            assertEquals(2, garden.getListOfPlants().size());
            fail("Exception expected");
        } catch (IOException e) {
            fail("IOException not expected here");
        } catch (InvalidSeedAlphabetException e) {
            //Perfect!
        } catch (NameAlreadyInGardenException e) {
            fail("NameAlreadyInGardenException not expected here");
        }
    }

    @Test
    void testReaderNameAlreadyInGardenException() {
        JsonReader reader = new JsonReader("./data/testReaderDoubleNameGarden.json");
        try {
            Garden garden = reader.read();
            assertEquals(2, garden.getListOfPlants().size());
            fail("Exception expected");
        } catch (IOException e) {
            fail("IOException not expected here");
        } catch (InvalidSeedAlphabetException e) {
            fail("InvalidSeedAlphabetException not expected here");
        } catch (NameAlreadyInGardenException e) {
            //Perfect!
        }
    }
}
