package ui;

import model.Garden;
import model.Plant;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// A tool that can be used to remove a plant from the garden
public class RemovePlantTool extends GameButton {

    private GameApp mainGame;
    private Garden mainGarden;
    private DrawingCanvas drawing;

    public RemovePlantTool(JPanel parent, GameApp gameApp) {
        super("Remove Plant", parent);
        initializeButton(700, 40, new RemovePlant());
        this.mainGame = gameApp;
        this.mainGarden = gameApp.getMainGarden();
        this.drawing = gameApp.getDrawing();
    }

    private class RemovePlant implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame jframe = new JFrame();

            //String[] possibilities = ((ArrayList<String>)
            //        mainGarden.getListOfPlants().stream().map(Plant::getName).
            //        collect(Collectors.toList())).toArray(new String[0]);
            Stream<String> possF = mainGarden.getListOfPlants().stream().map(Plant::getName);

            String[] possibilities = possF.collect(Collectors.toList()).toArray(new String[0]);

            String nameToRemove = (String) JOptionPane.showInputDialog(jframe,
                    "What is the name of the plant you'd like to remove?:",
                    "Groundskeeper:",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    possibilities,
                    null);

            if ((nameToRemove != null) && (nameToRemove.length() > 0)) {
                mainGame.setPlantsToRemove(nameToRemove);
            }
        }
    }
}
