package ui;

import exceptions.NameNotInGardenException;
import model.Garden;
import model.Plant;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// A class for the button in charge of watering the garden's plants
public class WaterPlantTool extends GameButton {

    private GameApp mainGame;
    private Garden mainGarden;
    private DrawingCanvas drawing;

    public WaterPlantTool(JPanel parent, GameApp gameApp) {
        super("Water Plant", parent);
        initializeButton(700, 40, new WaterPlant());
        this.mainGame = gameApp;
        this.mainGarden = gameApp.getMainGarden();
        this.drawing = gameApp.getDrawing();
    }

    private class WaterPlant implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame jframe = new JFrame();

            Stream<String> possF = mainGarden.getListOfPlants().stream().map(Plant::getName);

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
    }
}
