package ui.tools;

import exceptions.NameNotInGardenException;
import model.Garden;
import model.Plant;
import ui.DrawingCanvas;
import ui.GameApp;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// A class for the button in charge of watering the garden's plants
public class WaterPlantTool extends GameButton implements ActionListener {

    //private GameApp mainGame;
    //private Garden mainGarden;
    //private DrawingCanvas drawing;
    private ArrayList<Plant> thirstyPlants;

    public WaterPlantTool(JPanel parent, GameApp gameApp) {
        super("Water Thirsty Plants", parent, gameApp);
        initializeButton(700, 40, this);
        this.mainGame = gameApp;
        this.mainGarden = gameApp.getMainGarden();
        this.drawing = gameApp.getDrawing();
        thirstyPlants = new ArrayList<Plant>();
    }

    // EFFECTS: Waters the selected plant a given number of times
    @Override
    public void actionPerformed(ActionEvent e) {
        JFrame jframe = new JFrame();
        ArrayList<Plant> thirstyPlants = initThirstyPlants();
        Stream<String> possF = thirstyPlants.stream().map(Plant::getName);
        String[] possibilities = possF.collect(Collectors.toList()).toArray(new String[0]);

        String nameToWater = (String) JOptionPane.showInputDialog(jframe,
                "What is the name of the plant you'd like to water?:", "Groundskeeper:",
                JOptionPane.PLAIN_MESSAGE, null, possibilities, null);

        if ((nameToWater != null) && (nameToWater.length() > 0)) {
            String timesToWater = (String) JOptionPane.showInputDialog(jframe,
                    "How many times would you like to water " + nameToWater + "?", "Groundskeeper:",
                    JOptionPane.PLAIN_MESSAGE, null, null, null);

            if (Integer.parseInt(timesToWater) >= 0) {
                try {
                    mainGarden.waterPlant(nameToWater, Integer.parseInt(timesToWater));
                } catch (NameNotInGardenException error) {
                    JOptionPane.showMessageDialog(jframe,
                            "ERROR: There is not plant of name " + nameToWater + " in your garden...",
                            "Groundskeeper:", JOptionPane.PLAIN_MESSAGE);
                }
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: Creates a list of the thirsty plants in the mainGarden
    private ArrayList<Plant> initThirstyPlants() {
        thirstyPlants.clear();
        ArrayList<Plant> thirstyPlants = new ArrayList<Plant>();
        for (Plant p : mainGarden.getListOfPlants()) {
            if (p.getThirst() >= 1) {
                thirstyPlants.add(p);
            }
        }
        return thirstyPlants;
    }

//    // A private ActionListener class to handle watering a plant when the given button is pressed
//    private class WaterPlant implements ActionListener {
//
//        // EFFECTS: Waters the selected plant a given number of times
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            JFrame jframe = new JFrame();
//            ArrayList<Plant> thirstyPlants = initThirstyPlants();
//            Stream<String> possF = thirstyPlants.stream().map(Plant::getName);
//            String[] possibilities = possF.collect(Collectors.toList()).toArray(new String[0]);
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
//
//        // MODIFIES: this
//        // EFFECTS: Creates a list of the thirsty plants in the mainGarden
//        private ArrayList<Plant> initThirstyPlants() {
//            thirstyPlants.clear();
//            ArrayList<Plant> thirstyPlants = new ArrayList<Plant>();
//            for (Plant p : mainGarden.getListOfPlants()) {
//                if (p.getThirst() >= 1) {
//                    thirstyPlants.add(p);
//                }
//            }
//            return thirstyPlants;
//        }
//
//
//    }
}
