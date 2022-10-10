package model;

//import java.util.ArrayList;
import java.util.*;
import java.util.stream.Collectors;


// Represents a group of plants and their respective information
public class Garden {

    private ArrayList<Plant> listOfPlants;

    // EFFECTS: Generates a new empty garden
    public Garden() {
        listOfPlants = new ArrayList<Plant>();
    }

    // REQUIRES: A plant with the same name is not already in the garden
    // MODIFIES: this
    // EFFECTS: Adds another plant to the garden at the end of listOfPlants
    public void addPlant(Plant plant) {
        listOfPlants.add(plant);
    }

    // REQUIRES: listOfPlants must not be empty
    // MODIFIES: this
    // EFFECTS: Removes the last element of the garden listOfPlants
    public void removePlant() {
        listOfPlants.remove(listOfPlants.size() - 1);
    }

    // REQUIRES: listOfPlants must have a plant with name plantName
    // MODIFIES: this
    // EFFECTS: Removes the plant instance with name plantName from the listOfPlants
    public void removePlant(String plantName) {
        listOfPlants.remove(indexOfNameInGarden(plantName));
        //ArrayList<String> nameList = (ArrayList<String>)
        //                                listOfPlants.stream().map(Plant::getName).collect(Collectors.toList());
        //for (int i = 0; i < nameList.size(); i++) {
        //    if (plantName.equals(nameList.get(i))) {
        //        listOfPlants.remove(i);
        //        break;
        //    }
        //}
    }

    // REQUIRES: listOfPlants must have a plant with name plantName
    // MODIFIES: this
    // EFFECTS: Waters the named plant
    public void waterPlant(String name) {
        Plant plant = listOfPlants.get(indexOfNameInGarden(name));
        plant.water();
    }

    // EFFECTS: Returns true if the given plant name is within the garden
    public Boolean nameInGarden(String name) {
        ArrayList<String> nameList = (ArrayList<String>)
                getListOfPlants().stream().map(Plant::getName).collect(Collectors.toList());
        return nameList.contains(name);
    }

    // REQUIRES: listOfPlants must have a plant with name plantName
    // EFFECTS: Returns the index of where the given plant name is within the garden
    private int indexOfNameInGarden(String name) {
        ArrayList<String> nameList = (ArrayList<String>)
                getListOfPlants().stream().map(Plant::getName).collect(Collectors.toList());
        return nameList.indexOf(name);
    }

    public ArrayList<Plant> getListOfPlants() {
        return listOfPlants;
    }
}
