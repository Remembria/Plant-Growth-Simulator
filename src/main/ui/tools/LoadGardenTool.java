package ui.tools;

import exceptions.InvalidSeedAlphabetException;
import exceptions.NameAlreadyInGardenException;
import model.Garden;
import model.Plant;
import persistence.JsonReader;
import ui.DrawingCanvas;
import ui.GameApp;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

// A class in charge of the button for loading the JSON data to garden format
public class LoadGardenTool extends GameButton implements ActionListener {

   // private GameApp mainGame;
   // private Garden mainGarden;
   // private DrawingCanvas drawing;
    private JsonReader jsonReader;

    public LoadGardenTool(JPanel parent, GameApp gameApp) {
        super("Load Garden", parent, gameApp);
        initializeButton(700, 40, this);
        //this.mainGame = gameApp;
        //this.mainGarden = gameApp.getMainGarden();
        //this.drawing = gameApp.getDrawing();
        jsonReader = new JsonReader(gameApp.JSON_STORE);
    }

    // EFFECTS: Adds the JSON persistence garden file to the mainGarden
    @Override
    public void actionPerformed(ActionEvent e) {
        JFrame jframe = new JFrame();

        try {
            //mainGarden = jsonReader.read();
            mainGarden.getListOfPlants().clear();
            addPlants();

            //mainGame.setPlantsToAdd(jsonReader.read().getListOfPlants());
            JOptionPane.showMessageDialog(jframe,
                    "Garden re-cultivated from " + mainGame.JSON_STORE, "Groundskeeper:",
                    JOptionPane.PLAIN_MESSAGE);
        } catch (IOException error) {
            JOptionPane.showMessageDialog(jframe,
                    "ERROR: File not found at " + mainGame.JSON_STORE, "Groundskeeper:",
                    JOptionPane.PLAIN_MESSAGE);
        } catch (InvalidSeedAlphabetException error) {
            JOptionPane.showMessageDialog(jframe,
                    "ERROR: InvalidSeedAlphabetException", "Groundskeeper:",
                    JOptionPane.PLAIN_MESSAGE);
        } catch (NameAlreadyInGardenException error) {
            JOptionPane.showMessageDialog(jframe,
                    "ERROR: NameAlreadyInGardenException", "Groundskeeper:",
                    JOptionPane.PLAIN_MESSAGE);
        }
    }

    // EFFECTS: Directly adds the plants to the mainGarden and sets the currently drawn plant to the added plant
    private void addPlants() throws IOException, InvalidSeedAlphabetException, NameAlreadyInGardenException {
        for (Plant p : jsonReader.read().getListOfPlants()) {
            mainGame.setPlantsToAdd(p);
            drawing.setPlant(p);
            drawing.getDrawer().setDead(false);
            repaint();
        }
    }

//    // A private ActionListener class to handle loading the garden when the given button is pressed
//    private class LoadGarden implements ActionListener {
//
//        // EFFECTS: Adds the JSON persistence garden file to the mainGarden
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            JFrame jframe = new JFrame();
//
//            try {
//                //mainGarden = jsonReader.read();
//                mainGarden.getListOfPlants().clear();
//                addPlants();
//
//                //mainGame.setPlantsToAdd(jsonReader.read().getListOfPlants());
//                JOptionPane.showMessageDialog(jframe,
//                        "Garden re-cultivated from " + mainGame.JSON_STORE, "Groundskeeper:",
//                        JOptionPane.PLAIN_MESSAGE);
//            } catch (IOException error) {
//                JOptionPane.showMessageDialog(jframe,
//                        "ERROR: File not found at " + mainGame.JSON_STORE, "Groundskeeper:",
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
//
//        // EFFECTS: Directly adds the plants to the mainGarden and sets the currently drawn plant to the added plant
//        private void addPlants() throws IOException, InvalidSeedAlphabetException, NameAlreadyInGardenException {
//            for (Plant p : jsonReader.read().getListOfPlants()) {
//                mainGame.setPlantsToAdd(p);
//                drawing.setPlant(p);
//                drawing.getDrawer().setDead(false);
//                repaint();
//            }
//        }
//    }
}
