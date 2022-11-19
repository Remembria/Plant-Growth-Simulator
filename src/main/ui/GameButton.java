package ui;

import model.Garden;
import model.Plant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GameButton extends JButton {

    public GameButton(String name, JPanel parent) {
        super(name);
        parent.add(this);
    }

    public void initializeButton(int width, int height, ActionListener listener) {
        setBorderPainted(true);
        setFocusPainted(true);
        setContentAreaFilled(true);
        addActionListener(listener);
        setPreferredSize(new Dimension(width, height));
    }

}
