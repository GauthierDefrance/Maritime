package gui;

import config.GameConfiguration;
import gui.process.GUILoader;
import javax.swing.*;
import java.awt.*;

/**
 * Starting point of the program
 * @author Zue Jack-Arthur
 * @version 0.1
 */
public class MainGUI extends JFrame {
    private Container window;

    public MainGUI() {
        super("Maritime");
        init();
    }

    public void init() {
        window = getContentPane();
        window.setLayout(new BorderLayout());
        setSize(GameConfiguration.WINDOW_SIZE);
//        setExtendedState(JFrame.MAXIMIZED_BOTH);
//        setUndecorated(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GUILoader.loadStartMenu(window);
//        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args) {
        new MainGUI();
    }
}
