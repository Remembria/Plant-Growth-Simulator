package ui.tools;

import model.Garden;
import model.Plant;
import ui.DrawingCanvas;
import ui.GameApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

// The most top-level class to describe game button behaviour
public class GameButton extends JButton {

    protected GameApp mainGame;
    protected Garden mainGarden;
    protected DrawingCanvas drawing;

    // EFFECT: Initializes the game button superclass with its parent gameApp
    public GameButton(String name, JPanel parent, GameApp gameApp) {
        super(name);
        parent.add(this);
        this.mainGame = gameApp;
        this.mainGarden = gameApp.getMainGarden();
        this.drawing = gameApp.getDrawing();
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
