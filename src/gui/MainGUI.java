package gui;

import config.GameConfiguration;
import config.GameOptions;
import gui.process.ImageStock;
import gui.utilities.GUILoader;
import saveSystem.process.OptSaveManager;
import test.Debug;

import javax.swing.*;
import java.awt.*;

/**
 * Starting point of the program
 * @author Zue Jack-Arthur
 * @author Kenan Ammad
 * @version 0.2
 */
public class MainGUI extends JFrame {

    private static Container window;
    private static Debug debug;

    public MainGUI() {
        super("Maritime");
        init();
    }

    public void init() {
        debug = new Debug("Debug");
        window = getContentPane();
        window.setLayout(new BorderLayout());
        GameOptions.setInstance(OptSaveManager.create().loadParamFile());
        setIconImage(ImageStock.getImages(0));
        setSize(GameConfiguration.WINDOW_SIZE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GUILoader.loadStartMenu();
        setVisible(true);
    }

    public static Debug getDebug() {
        return debug;
    }

    public static Container getWindow() {
        return window;
    }

    public static void main(String[] args) {
        new MainGUI();
    }

}
