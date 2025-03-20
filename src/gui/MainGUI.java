package gui;

import battleengine.entity.Battle;
import config.GameConfiguration;
import config.GameOptions;
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
    private static Battle battle;

    public MainGUI() {
        super("Maritime");
        init();
    }

    public void init() {
        debug = new Debug("Debug");
        window = getContentPane();
        window.setLayout(new BorderLayout());
        GameOptions.setInstance(OptSaveManager.create().loadParamFile());
        setSize(GameConfiguration.WINDOW_SIZE);  /*setExtendedState(JFrame.MAXIMIZED_BOTH); setUndecorated(true);*/
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GUILoader.loadStartMenu(); //setResizable(false);
        setVisible(true);
    }
    public static Battle getBattle() {
        return battle;
    }

    public static Debug getDebug() {
        return debug;
    }

    public static Container getWindow() {
        return window;
    }

    public static void setBattle(Battle battle) {
        MainGUI.battle = battle;
    }

    public static void main(String[] args) {
        new MainGUI();
    }

}
