package ui;

import model.Plant;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        try {
            new GameApp();
        } catch (FileNotFoundException e) {
            System.out.println("Seeds misplaced, unable to garden (~file not found)");
        }
    }
}
