package gui;

import config.GameConfiguration;
import config.MapBuilder;
import gui.process.GUILoader;
import javax.swing.*;
import java.awt.*;

/**
 * Starting point of the program
 * @author Zue Jack-Arthur
 * @author Kenan Ammad
 * @version 0.1
 */
public class MainGUI extends JFrame {

    private static Container window;
    private static MapBuilder map;

    public MainGUI() {
        super("Maritime");
        init();
    }

    public void init() {
        window = getContentPane();
        window.setLayout(new BorderLayout());
        setSize(GameConfiguration.WINDOW_SIZE); /* setExtendedState(JFrame.MAXIMIZED_BOTH); setUndecorated(true);*/
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GUILoader.loadStartMenu(); //setResizable(false);
        setVisible(true);
    }

    public static Container getWindow() {
        return window;
    }

    public static MapBuilder getMap() {
        return map;
    }

    public static void setMap(MapBuilder map) {
        MainGUI.map = map;
    }

    public static void main(String[] args) {
        new MainGUI();
    }
}
