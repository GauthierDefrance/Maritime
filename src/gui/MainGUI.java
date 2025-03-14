package gui;

import config.GameConfiguration;
import config.GameOptions;
import config.MapBuilder;
import engine.entity.boats.*;
import engine.graph.SearchInGraph;
import engine.process.FactionManager;
import engine.trading.Resource;
import engine.trading.SeaRoad;
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
    private static MapBuilder map;
    private static Debug debug;

    public MainGUI() {
        super("Maritime");
        init();
    }

    public void init() {
        map = new MapBuilder();
        debug = new Debug("Debug",map);

        GameOptions.setInstance(OptSaveManager.create().loadParamFile());
        window = getContentPane();
        window.setLayout(new BorderLayout());
        setSize(GameConfiguration.WINDOW_SIZE); /* setExtendedState(JFrame.MAXIMIZED_BOTH); setUndecorated(true);*/
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        TestMove.addBaotTest(map);
        GUILoader.loadStartMenu(); //setResizable(false);
        setVisible(true);
    }

    public static Debug getDebug() {
        return debug;
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
