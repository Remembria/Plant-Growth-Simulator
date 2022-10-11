package ui;

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
        ArrayList<String> plantsToRemove = new ArrayList<String>();
        for (Plant p : mainGarden.getListOfPlants()) {
            p.setThirst((float) (p.getThirst() + (elapsedTime * 0.05)));
            p.setProgressToGrow(p.getProgressToGrow() - (float) elapsedTime);
            if (p.getThirst() > 10) {
                plantsToRemove.add(p.getName());
            }
            if (p.getProgressToGrow() < 0) {
                p.setProgressToGrow(p.growthRate);
                p.grow();
            }
        }
        for (String p : plantsToRemove) {
            mainGarden.removePlant(p);
            System.out.println(p + " Has died from lack of water :(");
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

        if (mainGarden.nameInGarden(name)) {
            mainGarden.waterPlant(name);
        } else {
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
            if (mainGarden.getListOfPlants().size() > 0) {
                mainGarden.removePlant();
            } else {
                System.out.println("Your garden is already empty");
            }
        } else if (mainGarden.nameInGarden(name)) {
            mainGarden.removePlant(name);
        } else {
            System.out.println("There is not plant of name " + name + " in your garden...");
        }
    }

    // MODIFIES: this
    // EFFECTS: Guides the user through adding a new plant to the garden
    private void addingPlant() {
        Boolean naming = true;
        Boolean seeding = true;

        while (naming) {
            System.out.println("What is the name of the plant you'd like to add?:");
            String name = input.next();

            if (mainGarden.nameInGarden(name)) {
                System.out.println("There already exists a plant with that name. Try another one");
            } else {
                naming = false;
                while (seeding) {
                    System.out.println("What seed would you like to use? ");
                    String seed = input.next();

                    if (isValidSeed(seed)) {
                        addPlant(name, seed);
                        seeding = false;
                    } else {
                        invalidSeed();
                    }
                }
            }
        }
    }

    // EFFECTS: Prints out the return message for when an invalid seed is given
    public void invalidSeed() {
        System.out.println("Sorry, but this is not a valid seed. A valid seed contains only elements of:");
        System.out.println("{F, +, -, [, ]} \n" + "Additionally, each start brace [ has its own end brace ]");
    }

    // MODIFIES: this
    // EFFECTS: Adds a plant to the mainGarden and prints an addition message
    public void addPlant(String name, String seed) {
        System.out.println("Perfect! " + name + " has been added to the garden.");
        mainGarden.addPlant(new Plant(name, seed));
    }

    // EFFECTS: Prints out every plant for the user, or notifies them if their garden is empty
    private void viewAllPlants() {
        if (mainGarden.getListOfPlants().size() == 0) {
            System.out.println("Your garden is empty...");
        } else {
            System.out.println("\nPlants:");
            for (Plant p : mainGarden.getListOfPlants()) {
                System.out.println("\n" + p.getName());
                System.out.println("\tShape: " + p.getLindenString());
                System.out.println("\tHealth: " + p.health());
                System.out.println("\tGrows every " + p.growthRate + " seconds");
                System.out.println("\n");
            }
        }
    }

    // EFFECTS: Returns true if the given seed is alphabet (within the lindenSystem's alphabet), false otherwise
    private Boolean isValidSeed(String seed) {
        ArrayList<Character> alphabet = new ArrayList<Character>();
        alphabet.add('F');
        alphabet.add('+');
        alphabet.add('-');
        alphabet.add('[');
        alphabet.add(']');
        for (int i = 0; i < (seed.length() - 1); i++) {
            if (!alphabet.contains(seed.charAt(i))) {
                return false;
            }
        }
        return true;
    }

}
