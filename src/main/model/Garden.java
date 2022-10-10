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
        //ArrayList<String> nameList = (ArrayList<String>) this.listOfPlants.stream().map(p -> p.getName());
        //if (nameList.contains(plant.getName())) {
        //    nameAlreadyInList(plant.getName());
        //} else {
        listOfPlants.add(plant);
        //}
    }

    // REQUIRES: listOfPlants must not be empty
    // MODIFIES: this
    // EFFECTS: Removes the last element of the garden listOfPlants
    public void removePlant() {
        listOfPlants.remove(listOfPlants.size() - 1);
    }

    // MODIFIES: this
    // EFFECTS: Removes the plant instance with name plantName from the listOfPlants
    public void removePlant(String plantName) {
        ArrayList<String> nameList = (ArrayList<String>)
                                        listOfPlants.stream().map(Plant::getName).collect(Collectors.toList());
        for (int i = 0; i < nameList.size(); i++) {
            if (plantName.equals(nameList.get(i))) {
                listOfPlants.remove(i);
                break;
            }
        }
        /*
        ArrayList<Integer> indicesToPop = new ArrayList<Integer>();
        int index = 0;
        for (Plant p : listOfPlants) {
            if (p.getName() == plantName) {
                indicesToPop.add(index);
            }
            index++;
        }
        for (Integer i : indicesToPop) {
            listOfPlants.remove(i);
        }
         */
    }

    //private void nameAlreadyInList(String name) {
    //    System.out.println("You've already named a plant " + name + ". Try a new name this time.");
    //}

    // EFFECTS: Returns the list of plants in the garden
    public ArrayList<Plant> getListOfPlants() {
        return listOfPlants;
    }
}
