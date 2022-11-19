package ui;

import exceptions.InvalidSeedAlphabetException;
import exceptions.NameAlreadyInGardenException;
import model.Garden;
import model.Plant;
import persistence.JsonReader;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

// A class in charge of the button for loading the JSON data to garden format
public class LoadGardenTool extends GameButton {

    private GameApp mainGame;
    private Garden mainGarden;
    private DrawingCanvas drawing;
    private JsonReader jsonReader;

    public LoadGardenTool(JPanel parent, GameApp gameApp) {
        super("Load Garden", parent);
        initializeButton(700, 40, new LoadGarden());
        this.mainGame = gameApp;
        this.mainGarden = gameApp.getMainGarden();
        this.drawing = gameApp.getDrawing();
        jsonReader = new JsonReader(mainGame.JSON_STORE);
    }

    private class LoadGarden implements ActionListener {
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

        private void addPlants() throws IOException, InvalidSeedAlphabetException, NameAlreadyInGardenException {
            for (Plant p : jsonReader.read().getListOfPlants()) {
                mainGame.setPlantsToAdd(p);
                drawing.setPlant(p);
                drawing.getDrawer().setDead(false);
                repaint();
            }
        }
    }
}
