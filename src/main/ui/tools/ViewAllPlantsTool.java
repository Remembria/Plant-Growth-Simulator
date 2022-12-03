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

// A tool that controls the button for viewing each plant in the garden
public class ViewAllPlantsTool extends GameButton implements ActionListener {

    //private GameApp mainGame;
    //private Garden mainGarden;
    //private DrawingCanvas drawing;

    // initializes the view all plants tool
    public ViewAllPlantsTool(JPanel parent, GameApp gameApp) {
        super("View All Plants", parent, gameApp);
        initializeButton(700, 40, this);
        //this.mainGame = gameApp;
        //this.mainGarden = gameApp.getMainGarden();
        //this.drawing = gameApp.getDrawing();
    }

    // EFFECTS: Shows a selected plant on the screen
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

//    // A private ActionListener class to handle removing a plant from the garden when the given button is pressed
//    private class ViewAllPlants implements ActionListener {
//
//        // EFFECTS: Shows a selected plant on the screen
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            JFrame jframe = new JFrame();
//
//            String[] possibilities = ((ArrayList<String>)
//                    mainGarden.getListOfPlants().stream().map(Plant::getName).collect(Collectors.toList())).toArray(
//                            new String[0]);
//
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
}
