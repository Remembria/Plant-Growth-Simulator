package ui;

import model.Plant;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

// The Class directly responsible for drawing each plant
public class PlantDrawer {

    private Boolean dead;
    private int locX;
    private int locY;
    private double angle;
    private float angleBreadth;
    private float lineLength;
    private int newLocX;
    private int newLocY;
    private List<String> codeList;
    private LinkedList<Integer> stackX;
    private LinkedList<Integer> stackY;
    private LinkedList<Double> stackTheta;
    private String code;

    // EFFECTS: Uses the drawLine function along with the plant LindenString to draw out the plant
    public void drawPlant(Plant plant, Graphics graphics, int startX, int startY) {
        if (!dead) { //!plant.getLindenString().equals("") &&
            setupText(plant, graphics);
            initializeFields(plant, startX, startY, code);
            Graphics2D g2 = (Graphics2D) graphics;
            g2.setStroke(new BasicStroke(1));

            for (String c : codeList) {
                if (c.equals("F")) {
                    forwardsDraw(graphics);
                } else if (c.equals("+")) {
                    //angle -= angleBreadth * (Math.max(0.955555, Math.random()));
                    angle -= angleBreadth + Math.pow(((0.5 * (Math.sin(System.nanoTime() / Math.pow(10, 9))))), 4);
                } else if (c.equals("-")) {
                    //angle += angleBreadth * (Math.max(0.955555, Math.random()));
                    angle += angleBreadth + Math.pow(((0.5 * (Math.sin(System.nanoTime() / Math.pow(10, 9))))), 4);
                } else if (c.equals("[")) {
                    stackAdd(stackX, stackY, stackTheta);
                } else { // ] case
                    drawLeaf(graphics);
                    stackEnd(stackX, stackY, stackTheta);
                }
            }
        } else {
            graphics.drawRect(0,0,800,500);
        }
    }

    // MODIFIES: this
    // EFFECTS: Instantiates all class fields
    private void initializeFields(Plant plant, int startX, int startY, String code) {
        code = plant.getLindenString();
        codeList = new ArrayList<String>(Arrays.asList(code.split("")));
        stackX = new LinkedList<Integer>();
        stackY = new LinkedList<Integer>();
        stackTheta = new LinkedList<Double>();

        locX = startX;
        locY = startY;
        angle = Math.PI / 2.0;
        angleBreadth = plant.getBreadth();
        lineLength = (float) (plant.getStemLength() * 1.1 / Math.pow(codeList.size(), 1.0 / 4));
    }

    // MODIFIES: this
    // EFFECTS: Responsible for changing field values when the stack is drawn from
    private void stackEnd(LinkedList<Integer> stackX, LinkedList<Integer> stackY, LinkedList<Double> stackTheta) {
        locX = stackX.getLast();
        locY = stackY.getLast();
        angle = stackTheta.getLast();
        stackX.removeLast();
        stackY.removeLast();
        stackTheta.removeLast();
    }

    // MODIFIES: this
    // EFFECTS: Responsible for adding field values to the stack
    private void stackAdd(LinkedList<Integer> stackX, LinkedList<Integer> stackY, LinkedList<Double> stackTheta) {
        stackX.add(locX);
        stackY.add(locY);
        stackTheta.add(angle);
    }

    // EFFECTS: Responsible for drawing a straight line in the direction of the curernt branch
    private void forwardsDraw(Graphics graphics) {
        newLocX = (int) (locX - Math.cos(angle) * lineLength);
        newLocY = (int) (locY - Math.sin(angle) * lineLength);
        graphics.drawLine(locX, locY, newLocX, newLocY);
        locX = newLocX;
        locY = newLocY;
    }

    // EFFECTS: Draws all text information about the plant's stats on the top left corner of the JPanel
    private void setupText(Plant plant, Graphics graphics) {
        graphics.drawString("Name: " + plant.getName(), 10, 15);
        graphics.drawString("LindenString: " + plant.getLindenString(), 10, 35);
        graphics.drawString("Progress to grow: " + plant.getProgressToGrow(), 10, 55);
        graphics.drawString("Thirst: " + plant.getThirst(), 10, 75);
        graphics.drawString("Stem Length: " + plant.getStemLength(), 10, 95);
        graphics.drawString("Breadth: " + plant.getBreadth(), 10, 115);
        graphics.drawString("Max Size: " + plant.getMaxSize(), 10, 135);
    }

    public void drawLeaf(Graphics g) {
        g.drawOval(locX - 3, locY - 3, 6, 6);
        //int [] x = {0 + locX, 3 + locX, 5 + locX, 0 + locX, -5 + locX, -3 + locX};
        //int [] y = {0 + locY, 2 + locY, 5 + locY, 15 + locY, 5 + locY, 2 + locY};
        //g.drawPolygon(x, y, 6);
        //g.fillPolygon(x, y, 6);
    }

    public void setDead(Boolean dead) {
        this.dead = dead;
    }

}
