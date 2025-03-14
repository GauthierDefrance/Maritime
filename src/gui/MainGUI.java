package gui;

import battleengine.entity.Battle;
import config.GameConfiguration;
import config.GameOptions;
import config.Map;
import gui.process.GUILoader;
import saveSystem.process.OptSaveManager;
import test.Debug;
import test.TestMove;

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
    private static Map map;
    private static Debug debug;
    private static Battle battle;

    public MainGUI() {
        super("Maritime");
        init();
    }

    public void init() {
        map = Map.getInstance();
        debug = new Debug("Debug",map);
        window = getContentPane();
        window.setLayout(new BorderLayout());

        TestMove.addBaotTest(map);

        GameOptions.setInstance(OptSaveManager.create().loadParamFile());
        setSize(GameConfiguration.WINDOW_SIZE); /* setExtendedState(JFrame.MAXIMIZED_BOTH); setUndecorated(true);*/
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

    public static Map getMap() {
        return map;
    }

    public static void setMap(Map map) {
        MainGUI.map = map;
    }

    public static void setBattle(Battle battle) {
        MainGUI.battle = battle;
    }

    public static void main(String[] args) {
        new MainGUI();
    }

}
