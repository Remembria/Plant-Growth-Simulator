package model;

//import java.util.ArrayList;
import exceptions.EmptyGardenException;
import exceptions.NameAlreadyInGardenException;
import exceptions.NameNotInGardenException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.*;
import java.util.stream.Collectors;


// Represents a group of plants and their respective information
public class Garden implements Writable {

    private ArrayList<Plant> listOfPlants;

    // EFFECTS: Generates a new empty garden
    public Garden() {
        listOfPlants = new ArrayList<Plant>();
    }

    // MODIFIES: this
    // EFFECTS: Adds another plant to the garden at the end of listOfPlants
    // Throws a NameAlreadyInGardenException if user tries to add plant of same name to garden
    public void addPlant(Plant plant) throws NameAlreadyInGardenException {
        ArrayList<String> nameList = (ArrayList<String>)
                getListOfPlants().stream().map(Plant::getName).collect(Collectors.toList());
        if (nameList.contains(plant.getName())) {
            throw new NameAlreadyInGardenException();
        }
        listOfPlants.add(plant);
    }

    // MODIFIES: this
    // EFFECTS: Removes the last element of the garden listOfPlants
    // Throws an EmptyGardenException if garden is empty, as cannot remove the last plant of an empty garden
    public void removePlant() throws EmptyGardenException {
        if (getListOfPlants().isEmpty()) {
            throw new EmptyGardenException();
        }
        listOfPlants.remove(listOfPlants.size() - 1);
    }

    // MODIFIES: this
    // EFFECTS: Removes the plant instance with name plantName from the listOfPlants
    // Throws a NameNotInGardenException if the given name to search for is not in the garden
    public void removePlant(String plantName) throws NameNotInGardenException {
        if (!nameInGarden(plantName)) {
            throw new NameNotInGardenException();
        }
        listOfPlants.remove(indexOfNameInGarden(plantName));

    }

    // MODIFIES: this
    // EFFECTS: Waters the named plant
    // Throws a NameNotInGardenException if the given name to search for is not in the garden
    public void waterPlant(String name, int amount) throws NameNotInGardenException {
        if (!nameInGarden(name)) {
            throw new NameNotInGardenException();
        }
        Plant plant = listOfPlants.get(indexOfNameInGarden(name));
        plant.water(amount);
    }

    // EFFECTS: Returns true if the given plant name is within the garden
    public Boolean nameInGarden(String name) {
        ArrayList<String> nameList = (ArrayList<String>)
                getListOfPlants().stream().map(Plant::getName).collect(Collectors.toList());
        return nameList.contains(name);
    }

    // EFFECTS: Returns the index of where the given plant name is within the garden (or -1 if name not found)
    public int indexOfNameInGarden(String name) throws NameNotInGardenException {
        ArrayList<String> nameList = (ArrayList<String>)
                getListOfPlants().stream().map(Plant::getName).collect(Collectors.toList());
        return nameList.indexOf(name);
    }

    public ArrayList<Plant> getListOfPlants() {
        return this.listOfPlants;
    }

    // EFFECTS: Returns this garden in the form of a JSONObject
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Plants", plantsToJson());
        return json;
    }

    // EFFECTS: Returns the list of plants in the form of a JSONArray
    private JSONArray plantsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Plant p : this.listOfPlants) {
            jsonArray.put(p.toJson());
        }
        return jsonArray;
    }

}
