package ui;

import exceptions.InvalidSeedAlphabetException;
import model.Plant;

import javax.swing.*;
import java.awt.*;

public class DrawingCanvas extends JPanel {

    Plant plant;

    PlantDrawer drawer = new PlantDrawer();

    public DrawingCanvas() {
        super();
        setBorder(BorderFactory.createLineBorder(Color.black));
        //setPreferredSize(new Dimension(600,600));
        try {
            plant = new Plant("Jeremy", "F[+F[+F]F][-F[-F]F]F[+F][-F]");
        } catch (InvalidSeedAlphabetException e) {
            System.out.println("Invalid seed alphabet exception given");
        }
        setBackground(Color.white);

    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(800,500);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(0, 0, 0));
        //g.drawString("Hello",40,40);
        //g.drawLine(0, 0, 300, 300);
        drawer.drawPlant(plant, g, getWidth() / 2, getHeight());

    }

}