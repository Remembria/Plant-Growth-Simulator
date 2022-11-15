package ui;

import model.Plant;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class PlantDrawer {

    public PlantDrawer() {}

    public void drawPlant(Plant plant, Graphics graphics, int startX, int startY) {
        String code = plant.getLindenString();
        List<String> codeList = new ArrayList<String>(Arrays.asList(code.split("")));
        System.out.println(codeList);
        LinkedList<Integer> stackX = new LinkedList<Integer>();
        LinkedList<Integer> stackY = new LinkedList<Integer>();
        LinkedList<Double> stackTheta = new LinkedList<Double>();

        int locX = startX;
        int locY = startY;
        double angle = Math.PI / 2.0;
        int lineLength = 20;

        int newLocX;
        int newLocY;

        Graphics2D g2 = (Graphics2D) graphics;
        g2.setStroke(new BasicStroke(3));

        for (String c : codeList) {
            if (c.equals("F")) {
                System.out.println("F");
                newLocX = (int) (locX - (Math.cos(angle) * lineLength));
                newLocY = (int) (locY - (Math.sin(angle) * lineLength));
                graphics.drawLine(locX, locY, newLocX, newLocY);
                locX = newLocX;
                locY = newLocY;
            } else if (c.equals("+")) {
                angle -= Math.PI / 10.0;
            } else if (c.equals("-")) {
                angle += Math.PI / 10.0;
            } else if (c.equals("[")) {
                stackX.add(locX);
                stackY.add(locY);
                stackTheta.add(angle);
            } else { // ] case
                locX = stackX.getLast();
                locY = stackY.getLast();
                angle = stackTheta.getLast();
                stackX.removeLast();
                stackY.removeLast();
                stackTheta.removeLast();
            }
            System.out.println("---");
            System.out.println(locX);
            System.out.println(locY);
            System.out.println(stackX);
            System.out.println(stackY);
            System.out.println("---");
        }

    }

}
