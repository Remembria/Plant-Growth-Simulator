package ui;

import exceptions.InvalidSeedAlphabetException;
import model.Plant;

import javax.swing.*;
import java.awt.*;

// Responsible for creating the Jpanel drawing canvas where the plants are drawn
public class DrawingCanvas extends JPanel {

    private Plant plant;
    private PlantDrawer drawer = new PlantDrawer();

    public DrawingCanvas() {
        super();
        setBorder(BorderFactory.createLineBorder(Color.black));
        setPreferredSize(new Dimension(600,600));
        try {
            plant = new Plant("", "");
            drawer.setDead(true);
        } catch (InvalidSeedAlphabetException e) {
            System.out.println("Invalid seed alphabet exception given");
        }
        setBackground(Color.white);
    }

    // MODIFIES: this
    // EFFECTS: Sets the preferred size of the Jpanel
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(800,500);
    }

    // EFFECTS: Responsible for painting the plant by modifying the given graphics
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(0, 0, 0));
        drawer.drawPlant(plant, g, getWidth() / 2, getHeight());

    }

    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    public Plant getPlant() {
        return this.plant;
    }

    public PlantDrawer getDrawer() {
        return this.drawer;
    }

}
