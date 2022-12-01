package ui;

import exceptions.*;
import model.Event;
import model.EventLog;
import model.Garden;
import model.Plant;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.tools.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

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

    // MODIFIES: this
    // EFFECTS: Initializes every button / tool the Game App uses
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

        add(drawing, BorderLayout.NORTH);
        validate();
        pack();
        add(jpanel, BorderLayout.SOUTH);
        setLayout(null);
        setVisible(true);
        setWindowListener();
    }

    // MODIFIES: this
    // EFFECTS: Adds a window listener that prints out the eventlog when the application is closed
    private void setWindowListener() {
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                Iterator<Event> iterator = EventLog.getInstance().iterator();
                while (iterator.hasNext()) {
                    System.out.println(iterator.next().getDescription());
                }
                System.exit(0);
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: Processes user input and presents the tui
    private void runGame() {
        long startTime = System.nanoTime();
        while (true) {
            double elapsedTime = (System.nanoTime() - startTime) / Math.pow(10, 5);
            startTime = System.nanoTime();
            repaintTimer -= elapsedTime;
            if (repaintTimer <= 0) {
                repaintTimer = 50000;
                ArrayList<String> diedOfThirst = new ArrayList<String>();
                ArrayList<String> diedOfWater = new ArrayList<String>();
                for (int i = 0; i < mainGarden.getListOfPlants().size(); i++) {
                    Plant p = mainGarden.getListOfPlants().get(i);
                    p.grow(elapsedTime, (float) 10);
                    if (p.getThirst() >= 10) {
                        diedOfThirst.add(p.getName());
                    } else if (p.getThirst() < -1) {
                        diedOfWater.add(p.getName());
                    }
                }
                update(diedOfThirst, diedOfWater);
                repaint();
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: Responsible for adding, removing, and killing plants
    private void update(ArrayList<String> diedOfThirst, ArrayList<String> diedOfWater) {
        addPlants();
        removePlants();
        killPlants(diedOfThirst, "a lack of water");
        killPlants(diedOfWater, "over-watering");
    }

    // MODIFIES: this
    // EFFECTS: Removes the first plant from the garden written in  the list of plants to remove
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


    // MODIFIES: this
    // EFFECTS: Adds the first plant to the garden from the list of plants to add
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

    // MODIFIES: this
    // EFFECTS: Directly responsible for removing plants
    private void killPlants(ArrayList<String> plants, String cause) {
        for (String p : plants) {
            try {
                if (drawing.getPlant().getName().equals(p)) {
                    drawing.getDrawer().setDead(true);
                    repaint();
                }
                mainGarden.removePlant(p, cause);
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

}
