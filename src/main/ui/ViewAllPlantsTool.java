package ui;

import exceptions.NameNotInGardenException;
import model.Garden;
import model.Plant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.stream.Collectors;

// A tool that controls the button for viewing each plant in the garden
public class ViewAllPlantsTool extends GameButton {

    private GameApp mainGame;
    private Garden mainGarden;
    private DrawingCanvas drawing;

    public ViewAllPlantsTool(JPanel parent, GameApp gameApp) {
        super("View All Plants", parent);
        initializeButton(700, 40, new ViewAllPlants());
        this.mainGame = gameApp;
        this.mainGarden = gameApp.getMainGarden();
        this.drawing = gameApp.getDrawing();
    }

    private class ViewAllPlants implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame jframe = new JFrame();

            String[] possibilities = ((ArrayList<String>)
                    mainGarden.getListOfPlants().stream().map(Plant::getName).collect(Collectors.toList())).toArray(
                            new String[0]);

            String plantToShow = (String) JOptionPane.showInputDialog(jframe,
                    "What is the name of the plant you'd like to view?:", "Groundskeeper:",
                    JOptionPane.PLAIN_MESSAGE, null, possibilities, null);

            if (mainGarden.nameInGarden(plantToShow)) {
                try {
                    drawing.setPlant(mainGarden.getListOfPlants().get(mainGarden.indexOfNameInGarden(plantToShow)));
                    drawing.getDrawer().setDead(false);
                    repaint();
                } catch (NameNotInGardenException error) {
                    System.out.println("NameNotInGardenException unexpected");
                }
            }
        }
    }
}
