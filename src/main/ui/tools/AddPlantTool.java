package ui.tools;

import exceptions.InvalidSeedAlphabetException;
import model.Garden;
import model.Plant;
import ui.DrawingCanvas;
import ui.GameApp;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// A tool that can be used to add plants to the garden
public class AddPlantTool extends GameButton {

    private GameApp mainGame;
    private Garden mainGarden;
    private DrawingCanvas drawing;

    public AddPlantTool(JPanel parent, GameApp gameApp) {
        super("Add Plant", parent);
        initializeButton(700, 40, new AddPlant());
        this.mainGame = gameApp;
        this.mainGarden = gameApp.getMainGarden();
        this.drawing = gameApp.getDrawing();
    }

    // A private ActionListener class to handle adding a plant when the given button is pressed
    private class AddPlant implements ActionListener {

        // EFFECTS: Adds a plant to the mainGame
        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame jframe = new JFrame();
            String plantName = (String) JOptionPane.showInputDialog(jframe,
                    "What is the name of the plant you'd like to add?:", "Groundskeeper:",
                    JOptionPane.PLAIN_MESSAGE, null, null, null);
            if ((plantName != null) && (plantName.length() > 0)) {
                String seed = (String) JOptionPane.showInputDialog(jframe,
                        "What seed would you like to use?:", "Groundskeeper:",
                        JOptionPane.PLAIN_MESSAGE, null, null, null);
                if ((seed != null) && (seed.length() > 0)) {
                    addPlant(plantName, seed, jframe);

                }
            }
        }

        // EFFECTS: Directly responsible for the adding of the plant to the mainGame, handles all exceptions
        private void addPlant(String plantName, String seed, JFrame jframe) {
            try {
                Plant plantToAdd = new Plant(plantName, seed);
                mainGame.setPlantsToAdd(plantToAdd);
                if (!mainGarden.nameInGarden(plantToAdd.getName())) {
                    drawing.setPlant(plantToAdd);
                    drawing.getDrawer().setDead(false);
                    repaint();
                }
            } catch (InvalidSeedAlphabetException error) {
                JOptionPane.showMessageDialog(jframe,
                        "ERROR: Sorry, but this is not a valid seed. "  + "A valid seed contains only "
                                + "elements of: \n" + "{F, +, -, [, ]} \n" + "Additionally, each start brace "
                                + "[ has its own end brace ]", "Groundskeeper:", JOptionPane.PLAIN_MESSAGE);
            }
        }
    }
}
