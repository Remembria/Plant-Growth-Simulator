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

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Garden g = new Garden();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyGarden() {
        try {
            Garden g = new Garden();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyGarden.json");
            writer.open();
            writer.write(g);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyGarden.json");
            g = reader.read();
            assertEquals(0, g.getListOfPlants().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        } catch (InvalidSeedAlphabetException e) {
            System.out.println("InvalidSeedAlphabetException exception");
        } catch (NameAlreadyInGardenException e) {
            System.out.println("NameAlreadyInGardenException exception");
        }
    }

    @Test
    void testWriterGeneralGarden() {
            try {
                Garden garden = new Garden();
                garden.addPlant(new Plant("Rebecca", "FF[F+F]F-F", 8, (float) 4,
                        (float) -0.3));
                garden.addPlant(new Plant("John", "FF+", 3, (float) 1,
                        (float) 9));
                garden.addPlant(new Plant("Lily", "F+F+F+F+F+F", 10, (float) 0.1,
                        (float) 3));
                JsonWriter writer = new JsonWriter("./data/testWriterGeneralGarden.json");
                writer.open();
                writer.write(garden);
                writer.close();
                JsonReader reader = new JsonReader("./data/testWriterGeneralGarden.json");
                garden = reader.read();
                List<Plant> listOfPlants = garden.getListOfPlants();
                assertEquals(3, listOfPlants.size());
                checkPlant(listOfPlants.get(0), "Rebecca", "FF[F+F]F-F", 8, (float) 4,
                        (float) -0.3);
                checkPlant(listOfPlants.get(1), "John", "FF+", 3, (float) 1,
                        (float) 9);
                checkPlant(listOfPlants.get(2), "Lily", "F+F+F+F+F+F", 10, (float) 0.1,
                        (float) 3);
            } catch (NameAlreadyInGardenException e) {
                System.out.println("Name cannot already be in garden");
            } catch (InvalidSeedAlphabetException e) {
                System.out.println("Invalid seed alphabet given");
            } catch (IOException e) {
                fail("Exception should not have been thrown");
            }
        }

}
