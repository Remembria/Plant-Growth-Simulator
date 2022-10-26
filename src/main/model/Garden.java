package model;

//import java.util.ArrayList;
import exceptions.EmptyGardenException;
import exceptions.NameAlreadyInGardenException;
import exceptions.NameNotInGardenException;

import javax.naming.Name;
import java.util.*;
import java.util.stream.Collectors;


// Represents a group of plants and their respective information
public class Garden {

    private ArrayList<Plant> listOfPlants;

    // EFFECTS: Generates a new empty garden
    public Garden() {
        listOfPlants = new ArrayList<Plant>();
    }

    // (Deprecated) REQUIRES: A plant with the same name is not already in the garden
    // MODIFIES: this
    // EFFECTS: Adds another plant to the garden at the end of listOfPlants
    public void addPlant(Plant plant) throws NameAlreadyInGardenException {
        ArrayList<String> nameList = (ArrayList<String>)
                getListOfPlants().stream().map(Plant::getName).collect(Collectors.toList());
        if (nameList.contains(plant.getName())) {
            throw new NameAlreadyInGardenException();
        }
        listOfPlants.add(plant);
    }

    // (Deprecated) REQUIRES: listOfPlants must not be empty
    // MODIFIES: this
    // EFFECTS: Removes the last element of the garden listOfPlants
    public void removePlant() throws EmptyGardenException {
        if (getListOfPlants().isEmpty()) {
            throw new EmptyGardenException();
        }
        listOfPlants.remove(listOfPlants.size() - 1);
    }

    // (Deprecated) REQUIRES: listOfPlants must have a plant with name plantName
    // MODIFIES: this
    // EFFECTS: Removes the plant instance with name plantName from the listOfPlants
    public void removePlant(String plantName) throws NameNotInGardenException {
        if (!nameInGarden(plantName)) {
            throw new NameNotInGardenException();
        }
        listOfPlants.remove(indexOfNameInGarden(plantName));

    }

    // (Deprecated) REQUIRES: listOfPlants must have a plant with name plantName
    // MODIFIES: this
    // EFFECTS: Waters the named plant
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

    // REQUIRES: listOfPlants must have a plant with name plantName
    // EFFECTS: Returns the index of where the given plant name is within the garden
    private int indexOfNameInGarden(String name) throws NameNotInGardenException {
        if (!nameInGarden(name)) {
            throw new NameNotInGardenException();
        }
        ArrayList<String> nameList = (ArrayList<String>)
                getListOfPlants().stream().map(Plant::getName).collect(Collectors.toList());
        return nameList.indexOf(name);
    }

    public ArrayList<Plant> getListOfPlants() {
        return listOfPlants;
    }

}
