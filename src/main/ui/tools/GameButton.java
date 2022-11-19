package ui.tools;

import model.Garden;
import model.Plant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

// The most top-level class to describe game button behaviour
public class GameButton extends JButton {

    public GameButton(String name, JPanel parent) {
        super(name);
        parent.add(this);
    }

    // MODIFIES: this
    // EFFECTS: Sets up the fields of the JButton
    public void initializeButton(int width, int height, ActionListener listener) {
        setBorderPainted(true);
        setFocusPainted(true);
        setContentAreaFilled(true);
        addActionListener(listener);
        setPreferredSize(new Dimension(width, height));
    }

}
