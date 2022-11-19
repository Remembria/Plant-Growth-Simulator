package ui;

import exceptions.*;
import model.Garden;
import model.Plant;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

//The gardening game application
public class GameApp extends JFrame {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 800;

    private Scanner input;
    private Garden mainGarden;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private long repaintTimer = 0;

    private ArrayList<Plant> plantsToAdd;
    private ArrayList<String> plantsToRemove;

    //private JButton addPlantButton;
    //private JButton removePlantButton;
    //private AddPlant addPlant = new AddPlant(this);
    //private RemovePlant removePlant = new RemovePlant(this);

    private DrawingCanvas drawing;
    public static final String JSON_STORE = "./data/garden.json";

    private ViewAllPlantsTool viewAllPlantsTool;
    private AddPlantTool addPlantTool;
    private RemovePlantTool removePlantTool;
    private WaterPlantTool waterPlantTool;
    private LoadGardenTool loadGardenTool;
    private SaveGardenTool saveGardenTool;

    //EFFECTS: Instantiates the garden and runs the game
    public GameApp() throws FileNotFoundException {
        super("Gardening Simulator");
        //try {
        //    plantsToAdd = new ArrayList<Plant>();
        //    plantsToAdd.add(new Plant("", ""));
        //    //plantToRemove = new Plant("", "");
        //    plantsToRemove = new ArrayList<String>();
        //    plantsToRemove.add("");
        //} catch (InvalidSeedAlphabetException e) {
        //    System.out.println("plant add error");
        //}
        //plantToAdd.setUse(false);
        //plantToRemove.setUse(false);
        //plantToRemove = "";
        plantsToAdd = new ArrayList<Plant>();
        plantsToRemove = new ArrayList<String>();
        mainGarden = new Garden();
        setup();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runGame();
    }

    public void initializeTools(JPanel jpanel) {
        this.viewAllPlantsTool = new ViewAllPlantsTool(jpanel, this);
        this.addPlantTool = new AddPlantTool(jpanel, this);
        this.removePlantTool = new RemovePlantTool(jpanel, this);
        this.waterPlantTool = new WaterPlantTool(jpanel, this);
        this.loadGardenTool = new LoadGardenTool(jpanel, this);
        this.saveGardenTool = new SaveGardenTool(jpanel, this);
        jpanel.add(this.viewAllPlantsTool);
        jpanel.add(this.addPlantTool);
        jpanel.add(this.removePlantTool);
        jpanel.add(this.waterPlantTool);
        jpanel.add(this.loadGardenTool);
        jpanel.add(this.saveGardenTool);
    }

    // MODIFIES: this
    // EFFECTS: Sets up all the buttons to be used by the GameApp
    private void setup() {
        int panelWidth = 800;
        int panelHeight = 300;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(GameApp.WIDTH, GameApp.HEIGHT));
        setLocationRelativeTo(null);

        DrawingCanvas newDrawing = new DrawingCanvas();
        drawing = newDrawing;

        JPanel jpanel = new JPanel();
        jpanel.setBounds(0, HEIGHT - panelHeight, panelWidth, panelHeight);
        jpanel.setBackground(new Color(75, 162, 100));

        initializeTools(jpanel);

        //JButton viewAllPlantsButton = new JButton("View All Plants");
        //viewAllPlantsButton.setBorderPainted(true);
        //viewAllPlantsButton.setFocusPainted(true);
        //viewAllPlantsButton.setContentAreaFilled(true);
        //jpanel.add(viewAllPlantsButton);
        //viewAllPlantsButton.addActionListener(new ViewAllPlants());
        //viewAllPlantsButton.setPreferredSize(new Dimension(700, 40));


//        addPlantButton = new JButton("Add Plant");
//        addPlantButton.setBorderPainted(true);
//        addPlantButton.setFocusPainted(true);
//        addPlantButton.setContentAreaFilled(true);
//        jpanel.add(addPlantButton);
//        addPlantButton.addActionListener(addPlant);
//        addPlantButton.setPreferredSize(new Dimension(700, 40));

//        removePlantButton = new JButton("Remove Plant");
//        removePlantButton.setBorderPainted(true);
//        removePlantButton.setFocusPainted(true);
//        removePlantButton.setContentAreaFilled(true);
//        jpanel.add(removePlantButton);
//        removePlantButton.addActionListener(removePlant);
//        removePlantButton.setPreferredSize(new Dimension(700, 40));

//        JButton waterPlantButton = new JButton("Water Plant");
//        waterPlantButton.setBorderPainted(true);
//        waterPlantButton.setFocusPainted(true);
//        waterPlantButton.setContentAreaFilled(true);
//        jpanel.add(waterPlantButton);
//        waterPlantButton.addActionListener(new WaterPlant());
//        waterPlantButton.setPreferredSize(new Dimension(700, 40));

//        JButton loadGardenButton = new JButton("Load Garden");
//        loadGardenButton.setBorderPainted(true);
//        loadGardenButton.setFocusPainted(true);
//        loadGardenButton.setContentAreaFilled(true);
//        jpanel.add(loadGardenButton);
//        loadGardenButton.addActionListener(new LoadGarden());
//        loadGardenButton.setPreferredSize(new Dimension(700, 40));

//        JButton saveGardenButton = new JButton("Save Garden");
//        saveGardenButton.setBorderPainted(true);
//        saveGardenButton.setFocusPainted(true);
//        saveGardenButton.setContentAreaFilled(true);
//        jpanel.add(saveGardenButton);
//        saveGardenButton.addActionListener(new SaveGarden());
//        saveGardenButton.setPreferredSize(new Dimension(700, 40));

        add(drawing, BorderLayout.NORTH);
        validate();
        pack();
        add(jpanel, BorderLayout.SOUTH);
        setLayout(null);
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: Processes user input and presents the tui
    private void runGame() {
        //boolean playing = true;
        long startTime = System.nanoTime();
        update(startTime);
//        String command;
//
        //while (playing) {
            //long startTime = System.nanoTime();
            //long elapsedTimeNs = System.nanoTime();
            //update(startTime);
        //}
//            long startTime = System.nanoTime();
//            displayMenu();
//
//            command = input.next();
//            command = command.toLowerCase();
//
//            if (command.equals("q")) {
//                playing = false;
//                System.out.println("See you next time!");
//            } else {
//                processCommand(command);
//            }
//
//            long elapsedTimeNs = System.nanoTime() - startTime;

            //update(elapsedTimeNs);
        //}
    }

    // MODIFIES: this
    // EFFECTS: Updates the status of every plant in the garden over time
    private void update(long elapsedTimeNS) {
        while (true) {
            double elapsedTime = (System.nanoTime() - elapsedTimeNS) / Math.pow(10, 15);
            ArrayList<String> diedOfThirst = new ArrayList<String>();
            ArrayList<String> diedOfWater = new ArrayList<String>();
            for (int i = 0; i < mainGarden.getListOfPlants().size(); i++) {
                Plant p = mainGarden.getListOfPlants().get(i);
                p.grow(elapsedTime, (float) 0.05);
                if (p.getThirst() >= 10) {
                    diedOfThirst.add(p.getName());
                } else if (p.getThirst() < -1) {
                    diedOfWater.add(p.getName());
                }
            }
            addPlants();
            removePlants();
            killPlants(diedOfThirst, "a lack of water");
            killPlants(diedOfWater, "over-watering");

            //System.out.println(repaintTimer);
            repaintTimer -= elapsedTime;
            if (repaintTimer <= 0) {
                repaint();
                //repaintTimer = (long) 100 * (long) (Math.sqrt((drawing.getPlant().getLindenString().length())));
                repaintTimer = (long) 100 * (long) (Math.sqrt((drawing.getPlant().getLindenString().length())));
                //repaintTimer = 50;
            }
        }
    }

    private void removePlants() {
        if (!plantsToRemove.isEmpty()) {
            JFrame jframe = new JFrame();
            try {
                mainGarden.removePlant(plantsToRemove.get(0));
                drawing.getDrawer().setDead(true);
                repaint();
                JOptionPane.showMessageDialog(jframe,
                        plantsToRemove.get(0) + " removed",
                        "Groundskeeper:",
                        JOptionPane.PLAIN_MESSAGE);
            } catch (NameNotInGardenException error) {
                JOptionPane.showMessageDialog(jframe,
                        "ERROR: There already exists a plant with that name. Try another one",
                        "Groundskeeper:",
                        JOptionPane.PLAIN_MESSAGE);
            }
            plantsToRemove.remove(0);
        }
    }

    private void addPlants() {
        if (!plantsToAdd.isEmpty()) {
            JFrame jframe = new JFrame();
            try {
                mainGarden.addPlant(plantsToAdd.get(0));
            } catch (NameAlreadyInGardenException error) {
                JOptionPane.showMessageDialog(jframe,
                        "ERROR: There already exists a plant with that name. Try another one",
                        "Groundskeeper:",
                        JOptionPane.PLAIN_MESSAGE);
            }
            plantsToAdd.remove(0);
        }
    }

    private void killPlants(ArrayList<String> plants, String cause) {
        for (String p : plants) {
            try {
                if (drawing.getPlant().getName().equals(p)) {
                    drawing.getDrawer().setDead(true);
                    repaint();
                }
                mainGarden.removePlant(p);
            } catch (NameNotInGardenException e) {
                System.out.println("Plant Removal Error");
            }
            //System.out.println("\n" + p + " Has died from " + cause);
            JFrame jframe = new JFrame();
            JOptionPane.showMessageDialog(jframe,
                    p + " Has died from " + cause,
                    "Groundskeeper:",
                    JOptionPane.PLAIN_MESSAGE);
        }
    }

    // EFFECTS: Prints all menu options for the user
//    private void displayMenu() {
//        System.out.println("\nOptions:");
//        System.out.println("\tv -> view all plants");
//        System.out.println("\ta -> add plant");
//        System.out.println("\tr -> remove plant");
//        System.out.println("\tw -> water plant");
//        System.out.println("\tl -> load garden");
//        System.out.println("\ts -> save garden");
//        System.out.println("\tq -> quit");
//    }
//
//    // MODIFIES: this
//    // EFFECTS: Processes the input command and selects the appropriate menu action
//    private void processCommand(String c) { //switch statement replaceable
//        if (c.equals("v")) {
//            viewAllPlants();
//        } else if (c.equals("a")) {
//            addingPlant();
//        } else if (c.equals("r")) {
//            removingPlant();
//        } else if (c.equals("w")) {
//            waterPlant();
//        } else if (c.equals("l")) {
//            loadGardenFromJson();
//        } else {
//            writeGardenToJson();
//        }
//    }
//
//    //MODIFIES: this
//    // EFFECTS: Waters the chosen plant
//    private void waterPlant() {
//        System.out.println("What is the name of the plant you'd like to water?:");
//        String name = input.next();
//        System.out.println("How many times would you like to water " + name + "?");
//        int amount = input.nextInt();
//
//        try {
//            mainGarden.waterPlant(name, amount);
//        } catch (NameNotInGardenException e) {
//            System.out.println("There is not plant of name " + name + " in your garden...");
//        }
//    }

    // MODIFIES: this
    // EFFECTS: Removes a named plant from the garden, or the last plant added if 0 is given
//    private void removingPlant() {
//        System.out.println("What is the name of the plant you'd like to remove?:");
//        System.out.println("(type 0 to remove the last added plant)");
//        String name = input.next();
//
//        if (name.equals("0")) {
//            try {
//                mainGarden.removePlant();
//            } catch (EmptyGardenException e) {
//                System.out.println("Your garden is already empty");
//            }
//        } else {
//            try {
//                mainGarden.removePlant(name);
//            } catch (NameNotInGardenException e) {
//                System.out.println("There is not plant of name " + name + " in your garden...");
//            }
//        }
//    }

    // MODIFIES: this
    // EFFECTS: Guides the user through adding a new plant to the garden
//    private void addingPlant() {
//        Boolean makingPlant = true;
//
//        while (makingPlant) {
//            System.out.println("What is the name of the plant you'd like to add?:");
//            String name = input.next();
//            System.out.println("What seed would you like to use? ");
//            String seed = input.next();
//            try {
//                mainGarden.addPlant(new Plant(name, seed));
//            } catch (NameAlreadyInGardenException e) {
//                System.out.println("There already exists a plant with that name. Try another one");
//            } catch (InvalidSeedAlphabetException e) {
//                invalidSeed();
//            }
//            makingPlant = false;
//        }
//    }

    // EFFECTS: Prints out the return message for when an invalid seed is given
//    public void invalidSeed() {
//        System.out.println("Sorry, but this is not a valid seed. A valid seed contains only elements of:");
//        System.out.println("{F, +, -, [, ]} \n" + "Additionally, each start brace [ has its own end brace ]");
//    }
//
//    // EFFECTS: Prints out every plant for the user, or notifies them if their garden is empty
//    private void viewAllPlants() {
//        if (mainGarden.getListOfPlants().size() == 0) {
//            System.out.println("Your garden is empty...");
//        } else {
//            System.out.println("\nPlants:");
//            for (Plant p : mainGarden.getListOfPlants()) {
//                try {
//                    String health = p.health();
//                    System.out.println("\n" + p.getName());
//                    System.out.println("\tShape: " + p.getLindenString());
//                    System.out.println("\tHealth: " + health);
//                    System.out.println("\tGrows every " + p.growthRate + " moons");
//                } catch (ImpossibleThirstException e) {
//                    System.out.println("ERROR: Health not in valid range");
//                }
//            }
//        }
//    }

    // MODIFIES: this
    // EFFECTS: Loads a garden from the given JSON file
//    private void loadGardenFromJson() {
//        try {
//            mainGarden.getListOfPlants().clear();
//            //mainGarden = jsonReader.read();
//            plantsToAdd = jsonReader.read().getListOfPlants();
//            System.out.println("Garden re-cultivated from " + JSON_STORE);
//        } catch (IOException e) {
//            System.out.println("ERROR: File not found at " + JSON_STORE);
//        } catch (InvalidSeedAlphabetException e) {
//            System.out.println("InvalidSeedAlphabetException exception");
//        } catch (NameAlreadyInGardenException e) {
//            System.out.println("NameAlreadyInGardenException exception");
//        }
//    }
//
//    // EFFECTS: Writes the current garden to the JSON_STORE file directory
//    private void writeGardenToJson() {
//        try {
//            jsonWriter.open();
//            jsonWriter.write(mainGarden);
//            jsonWriter.close();
//            System.out.println("Garden checkpoint saved to " + JSON_STORE);
//        } catch (FileNotFoundException e) {
//            System.out.println("ERROR: File not found at " + JSON_STORE);
//        }
//    }

    public Garden getMainGarden() {
        return mainGarden;
    }

    public DrawingCanvas getDrawing() {
        return drawing;
    }

    public void setPlantsToAdd(Plant plant) {
        plantsToAdd.add(plant);
    }

    public void setPlantsToRemove(String plant) {
        plantsToRemove.add(plant);
    }

    //An ActionListener class for viewing all plants
//    private class ViewAllPlants implements ActionListener {
//
//        // Effects: Shows the selected plant on screen
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            JFrame jframe = new JFrame();
//
//            String[] possibilities = ((ArrayList<String>)
//                    mainGarden.getListOfPlants().stream().
//                    map(Plant::getName).collect(Collectors.toList())).toArray(new String[0]);
//
//            //Object[] possibilities = mainGarden.getListOfPlants().toArray(new Plant[0]);
//            String plantToShow = (String) JOptionPane.showInputDialog(jframe,
//                    "What is the name of the plant you'd like to view?:", "Groundskeeper:",
//                    JOptionPane.PLAIN_MESSAGE, null, possibilities, null);
//
//            if (mainGarden.nameInGarden(plantToShow)) {
//                try {
//                    drawing.setPlant(mainGarden.getListOfPlants().get(mainGarden.indexOfNameInGarden(plantToShow)));
//                    drawing.getDrawer().setDead(false);
//                    repaint();
//                } catch (NameNotInGardenException error) {
//                    System.out.println("NameNotInGardenException unexpected");
//                }
//            }
//        }
//    }
//
//    // An ActionListener class for when the add plant button is pressed
//
//    private class AddPlant implements ActionListener {
//
//        private GameApp gameApp;
//
//        public AddPlant(GameApp gameApp) {
//            this.gameApp = gameApp;
//        }
//
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            JFrame jframe = new JFrame();
//
//            String plantName = (String) JOptionPane.showInputDialog(jframe,
//                    "What is the name of the plant you'd like to add?:", "Groundskeeper:",
//                    JOptionPane.PLAIN_MESSAGE, null, null, null);
//            //System.out.println(plantName);
//            System.out.println((plantName != null) && (plantName.length() > 0));
//            if ((plantName != null) && (plantName.length() > 0)) {
//                String seed = (String) JOptionPane.showInputDialog(jframe,
//                        "What seed would you like to use?:", "Groundskeeper:",
//                        JOptionPane.PLAIN_MESSAGE, null, null, null);
//                if ((seed != null) && (seed.length() > 0)) {
//                    try {
//                        gameApp.plantToAdd = new Plant(plantName, seed);
//                    } catch (InvalidSeedAlphabetException error) {
//                        JOptionPane.showMessageDialog(jframe,
//                                "ERROR: Sorry, but this is not a valid seed. "
//                                        + "A valid seed contains only elements of: \n"
//                                + "{F, +, -, [, ]} \n" + "Additionally, each start brace [ has its own end brace ]",
//                                "Groundskeeper:", JOptionPane.PLAIN_MESSAGE);
//                    }
//                }
//            }
//        }
//    }

//    private class RemovePlant implements ActionListener {
//
//        private GameApp gameApp;
//
//        public RemovePlant(GameApp gameApp) {
//            this.gameApp = gameApp;
//        }
//
//        @Override
//        public void actionPerformed(ActionEvent e) {
//
//            JFrame jframe = new JFrame();
//
//            String[] possibilities = ((ArrayList<String>)
//                    mainGarden.getListOfPlants().stream().
//                    map(Plant::getName).collect(Collectors.toList())).toArray(new String[0]);
//
//            String nameToRemove = (String) JOptionPane.showInputDialog(jframe,
//                    "What is the name of the plant you'd like to remove?:",
//                    "Groundskeeper:",
//                    JOptionPane.PLAIN_MESSAGE,
//                    null,
//                    possibilities,
//                    null);
//
//            if ((nameToRemove != null) && (nameToRemove.length() > 0)) {
//                gameApp.plantToRemove = nameToRemove;
//                //if (nameToRemove.equals("0")) {
//                    //try {
//                        //mainGarden.removePlant();
//                        //gameApp.plantToRemove = new Plant(gameApp.mainGarden.getListOfPlants()., "");
//                    //} catch (InvalidSeedAlphabetException error) {
//                    //    System.out.println("Invalid seed");
//                    //}
//                //gameApp.plantToRemove = gameApp.mainGarden.getListOfPlants().
//                get(gameApp.mainGarden.getListOfPlants().size() - 1).getName();
//                //} else {
//                    //gameApp.plantToRemove = getName();
//                    //try {
//                    //    mainGarden.removePlant(nameToRemove);
//                    //} catch (NameNotInGardenException error) {
//                    //    JOptionPane.showMessageDialog(jframe,
//                    //            "ERROR: There is not plant of name " + nameToRemove + " in your garden...",
//                    //            "Groundskeeper:",
//                    //            JOptionPane.PLAIN_MESSAGE);
//                    //}
//                //}
//            }
//        }
//    }

//    private class WaterPlant implements ActionListener {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//
//            JFrame jframe = new JFrame();
//
//            String[] possibilities = ((ArrayList<String>)
//                    mainGarden.getListOfPlants().stream().
//                    map(Plant::getName).collect(Collectors.toList())).toArray(new String[0]);
//
//
//            String nameToWater = (String) JOptionPane.showInputDialog(jframe,
//                    "What is the name of the plant you'd like to water?:", "Groundskeeper:",
//                    JOptionPane.PLAIN_MESSAGE, null, possibilities, null);
//
//            if ((nameToWater != null) && (nameToWater.length() > 0)) {
//                String timesToWater = (String) JOptionPane.showInputDialog(jframe,
//                        "How many times would you like to water " + nameToWater + "?", "Groundskeeper:",
//                        JOptionPane.PLAIN_MESSAGE, null, null, null);
//
//                if (Integer.parseInt(timesToWater) >= 0) {
//                    try {
//                        mainGarden.waterPlant(nameToWater, Integer.parseInt(timesToWater));
//                    } catch (NameNotInGardenException error) {
//                        JOptionPane.showMessageDialog(jframe,
//                                "ERROR: There is not plant of name " + nameToWater + " in your garden...",
//                                "Groundskeeper:", JOptionPane.PLAIN_MESSAGE);
//                    }
//                }
//            }
//        }
//    }

//    private class LoadGarden implements ActionListener {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//
//            JFrame jframe = new JFrame();
//
//            try {
//                //mainGarden = jsonReader.read();
//                mainGarden.getListOfPlants().clear();
//                plantsToAdd = jsonReader.read().getListOfPlants();
//                JOptionPane.showMessageDialog(jframe,
//                        "Garden re-cultivated from " + JSON_STORE, "Groundskeeper:",
//                        JOptionPane.PLAIN_MESSAGE);
//            } catch (IOException error) {
//                JOptionPane.showMessageDialog(jframe,
//                        "ERROR: File not found at " + JSON_STORE, "Groundskeeper:",
//                        JOptionPane.PLAIN_MESSAGE);
//            } catch (InvalidSeedAlphabetException error) {
//                JOptionPane.showMessageDialog(jframe,
//                        "ERROR: InvalidSeedAlphabetException", "Groundskeeper:",
//                        JOptionPane.PLAIN_MESSAGE);
//            } catch (NameAlreadyInGardenException error) {
//                JOptionPane.showMessageDialog(jframe,
//                        "ERROR: NameAlreadyInGardenException", "Groundskeeper:",
//                        JOptionPane.PLAIN_MESSAGE);
//            }
//        }
//    }
//
//    private class SaveGarden implements ActionListener {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//
//            JFrame jframe = new JFrame();
//
//            try {
//                jsonWriter.open();
//                jsonWriter.write(mainGarden);
//                jsonWriter.close();
//            } catch (FileNotFoundException error) {
//                JOptionPane.showMessageDialog(jframe,
//                        "ERROR: File not found at " + JSON_STORE, "Groundskeeper:",
//                        JOptionPane.PLAIN_MESSAGE);
//            }
//            JOptionPane.showMessageDialog(jframe,
//                    "Garden checkpoint saved to " + JSON_STORE, "Groundskeeper:",
//                    JOptionPane.PLAIN_MESSAGE);
//        }
//    }
}
