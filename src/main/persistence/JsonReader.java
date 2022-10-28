package persistence;

import exceptions.InvalidSeedAlphabetException;
import exceptions.NameAlreadyInGardenException;
import model.Garden;
import model.Plant;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class JsonReader {
    private String sourceFile;

    public JsonReader(String sourceFile) {
        this.sourceFile = sourceFile;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Garden read() throws IOException {
        String jsonData = readFile(sourceFile);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseGarden(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses garden from JSON object and returns it
    private Garden parseGarden(JSONObject jsonObject) {
        Garden garden = new Garden();
        addPlants(garden, jsonObject);
        return garden;
    }

    // MODIFIES: g
    // EFFECTS: parses thingies from JSON object and adds them to workroom
    private void addPlants(Garden g, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Plants");
        for (Object json : jsonArray) {
            JSONObject nextPlant = (JSONObject) json;
            addPlant(g, nextPlant);
        }
    }

    // MODIFIES: g
    // EFFECTS: parses plant from JSON object and adds it to garden
    private void addPlant(Garden g, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String lindenString = jsonObject.getString("linden string");
        int growthRate = jsonObject.getInt("growth rate");
        Float progressToGrow = jsonObject.getFloat("progress to grow");
        Float thirst = jsonObject.getFloat("thirst");
        try {
            Plant plant = new Plant(name, lindenString, growthRate, progressToGrow, thirst);
            g.addPlant(plant);
        } catch (InvalidSeedAlphabetException e) {
            System.out.println("Invalid seed");
        } catch (NameAlreadyInGardenException e) {
            System.out.println("Name already in garden exception");
        }
    }

}
