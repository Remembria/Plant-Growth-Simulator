package ui;

import model.Garden;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

// A class for saving the garden to JSON format
public class SaveGardenTool extends GameButton {

    private GameApp mainGame;
    private Garden mainGarden;
    private DrawingCanvas drawing;
    private JsonWriter jsonWriter;

    public SaveGardenTool(JPanel parent, GameApp gameApp) {
        super("Save Garden", parent);
        initializeButton(700, 40, new SaveGarden());
        this.mainGame = gameApp;
        this.mainGarden = gameApp.getMainGarden();
        this.drawing = gameApp.getDrawing();
        this.jsonWriter = new JsonWriter(mainGame.JSON_STORE);
    }

    private class SaveGarden implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame jframe = new JFrame();

            try {
                jsonWriter.open();
                jsonWriter.write(mainGarden);
                jsonWriter.close();
            } catch (FileNotFoundException error) {
                JOptionPane.showMessageDialog(jframe,
                        "ERROR: File not found at " + mainGame.JSON_STORE, "Groundskeeper:",
                        JOptionPane.PLAIN_MESSAGE);
            }
            JOptionPane.showMessageDialog(jframe,
                    "Garden checkpoint saved to " + mainGame.JSON_STORE, "Groundskeeper:",
                    JOptionPane.PLAIN_MESSAGE);
        }
    }
}
