package ui;

import exceptions.*;
import model.Garden;
import model.Plant;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//The gardening game application
public class GameApp {
    private Scanner input;
    private Garden mainGarden;

    //EFFECTS: Instantiates the garden and runs the game
    public GameApp() {
        //listOfGardens = new ArrayList<Garden>();
        mainGarden = new Garden();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        runGame();
    }

    // MODIFIES: this
    // EFFECTS: Processes user input and presents the tui
    private void runGame() {
        boolean playing = true;
        String command;

        while (playing) {
            long startTime = System.nanoTime();
            displayMenu();

            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                playing = false;
                System.out.println("See you next time!");
            } else {
                processCommand(command);
            }

            long elapsedTimeNs = System.nanoTime() - startTime;

            update(elapsedTimeNs);
        }
    }

    // MODIFIES: this
    // EFFECTS: Updates the status of every plant in the garden over time
    private void update(long elapsedTimeNS) {
        double elapsedTime = elapsedTimeNS / Math.pow(10, 9);
        ArrayList<String> diedOfThirst = new ArrayList<String>();
        ArrayList<String> diedOfWater = new ArrayList<String>();
        for (Plant p : mainGarden.getListOfPlants()) {
            p.grow(elapsedTime, (float) 0.1);
            if (p.getThirst() > 10) {
                diedOfThirst.add(p.getName());
            } else if (p.getThirst() < -1) {
                diedOfWater.add(p.getName());
            }
        }
        killPlants(diedOfThirst, "a lack of water");
        killPlants(diedOfWater, "over-watering");
    }

    private void killPlants(ArrayList<String> plants, String cause) {
        for (String p : plants) {
            try {
                mainGarden.removePlant(p);
            } catch (NameNotInGardenException e) {
                System.out.println("Plant Removal Error");
            }
            System.out.println("\n" + p + " Has died from " + cause);
        }
    }

    // EFFECTS: Prints all menu options for the user
    private void displayMenu() {
        System.out.println("\nOptions:");
        System.out.println("\tv -> view all plants");
        System.out.println("\ta -> add plant");
        System.out.println("\tr -> remove plant");
        System.out.println("\tw -> water plant");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: Processes the input command and selects the appropriate menu action
    private void processCommand(String c) { //switch statement replaceable
        if (c.equals("v")) {
            viewAllPlants();
        } else if (c.equals("a")) {
            addingPlant();
        } else if (c.equals("r")) {
            removingPlant();
        } else {
            waterPlant();
        }
    }

    //MODIFIES: this
    // EFFECTS: Waters the chosen plant
    private void waterPlant() {
        System.out.println("What is the name of the plant you'd like to water?:");
        String name = input.next();
        System.out.println("How many times would you like to water " + name + "?");
        int amount = input.nextInt();

        try {
            mainGarden.waterPlant(name, amount);
        } catch (NameNotInGardenException e) {
            System.out.println("There is not plant of name " + name + " in your garden...");
        }
    }

    // MODIFIES: this
    // EFFECTS: Removes a named plant from the garden, or the last plant added if 0 is given
    private void removingPlant() {
        System.out.println("What is the name of the plant you'd like to remove?:");
        System.out.println("(type 0 to remove the last added plant)");
        String name = input.next();

        if (name.equals("0")) {
            try {
                mainGarden.removePlant();
            } catch (EmptyGardenException e) {
                System.out.println("Your garden is already empty");
            }
        } else {
            try {
                mainGarden.removePlant(name);
            } catch (NameNotInGardenException e) {
                System.out.println("There is not plant of name " + name + " in your garden...");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: Guides the user through adding a new plant to the garden
    private void addingPlant() {
        //Boolean naming = true;
        //Boolean seeding = true;
        Boolean makingPlant = true;

        while (makingPlant) {
            System.out.println("What is the name of the plant you'd like to add?:");
            String name = input.next();
            System.out.println("What seed would you like to use? ");
            String seed = input.next();
            try {
                mainGarden.addPlant(new Plant(name, seed));
            } catch (NameAlreadyInGardenException e) {
                System.out.println("There already exists a plant with that name. Try another one");
            } catch (InvalidSeedAlphabetException e) {
                invalidSeed();
            }
            makingPlant = false;
        }
    }

            //if (mainGarden.nameInGarden(name)) {
            //    System.out.println("There already exists a plant with that name. Try another one");
            //} else {
            //    naming = false;
            //    while (seeding) {
            //        System.out.println("What seed would you like to use? ");
            //        String seed = input.next();
            //
            //        if (isValidSeed(seed)) {
            //            addPlant(name, seed);
            //            seeding = false;
            //        } else {
            //            invalidSeed();
            //        }
            //    }
            //}

    // EFFECTS: Prints out the return message for when an invalid seed is given
    public void invalidSeed() {
        System.out.println("Sorry, but this is not a valid seed. A valid seed contains only elements of:");
        System.out.println("{F, +, -, [, ]} \n" + "Additionally, each start brace [ has its own end brace ]");
    }

    // EFFECTS: Prints out every plant for the user, or notifies them if their garden is empty
    private void viewAllPlants() {
        if (mainGarden.getListOfPlants().size() == 0) {
            System.out.println("Your garden is empty...");
        } else {
            System.out.println("\nPlants:");
            for (Plant p : mainGarden.getListOfPlants()) {
                try {
                    String health = p.health();
                    System.out.println("\n" + p.getName());
                    System.out.println("\tShape: " + p.getLindenString());
                    System.out.println("\tHealth: " + health);
                    System.out.println("\tGrows every " + p.growthRate + " moons");
                } catch (ImpossibleThirstException e) {
                    System.out.println("ERROR: Health not in valid range");
                }
            }
        }
    }

}
