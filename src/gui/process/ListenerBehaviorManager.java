package gui.process;

import static config.GameConfiguration.*;

import config.GameConfiguration;
import engine.battleengine.data.Battle;
import engine.data.Fleet;
import engine.data.faction.Faction;
import engine.data.trading.SeaRoad;
import gui.utilities.GUILoader;
import log.LoggerUtility;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;

/**
 * Store and allow the use of typical EventListener behaviors
 * @author Zue Jack-Arthur
 * @version 0.3
 */
public class ListenerBehaviorManager {
    private static Logger logger = LoggerUtility.getLogger(JComponentFactory.class);

    private ListenerBehaviorManager() { }

    /**
     * Create a new ListenerBehaviorManager Object and allows operation on it
     * @return ListenerBehavior
     */
    public static ListenerBehaviorManager create(){
        return new ListenerBehaviorManager();
    }

    /**
     * Toggle/Actualise a JButton
     * @param button JButton to be toggled
     * @param masterBoolean Global boolean linked with the button
     */
    public Boolean toggle(JButton button, Boolean masterBoolean){
        boolean bool = masterBoolean;
        if (bool) {
            bool = false;
            button.setText("turn On");
        } else {
            bool = true;
            button.setText("turn Off");
        } return bool;
    }

    /**
     * Increment a value associated with a JButton
     * @param limit value that should not be exceeded
     * @param value current value
     * @return updated "value"
     */
    public int increment(int limit, int value){
        int localValue = value;
        if (localValue < limit){
            return ++localValue;
        } return localValue;
    }

    /**
     * Decrement a value associated with a JButton
     * @param limit value that should not be exceeded
     * @param value current value
     * @return updated "value"
     */
    public int decrement(int limit, int value){
        int localValue = value;
        if (localValue > limit){
            return --localValue;
        } return localValue;
    }

    /**
     * try to exit the program and shows a MessageDialog if failed to do so
     * @param panel current JPanel
     */
    public void exit(JPanel panel){
        try {
            System.exit(0);
        } catch ( SecurityException e1 ) {
            JOptionPane.showMessageDialog(panel, "You are not allowed to exit!", "Error", JOptionPane.ERROR_MESSAGE );
        }
    }

    /**
     * Ensure that the action of clicking in a JPanel remain relevant relative to scaling
     * @param panel current Panel
     * @param point Point which is clicked
     * @return a Point indicating the zone which was clicked
     */
    public Point clickLogic(JPanel panel, Point point){
        double scale = Math.min((double)panel.getWidth()/640,(double) panel.getHeight() /360);
        int x = (int) ((point.getX()* GameConfiguration.GAME_SCALE)/scale);
        int y = (int) ((point.getY()*GameConfiguration.GAME_SCALE)/scale);
        return new Point(x, y);
    }

    /**
     * handle the process of returning to former Menu for GUIs
     * @param token identifier of the former GUI
     */
    public void goBack(int token){
        goBack(token,null);
    }

    /**
     * handle the process of returning to former Menu for GUIs
     * @param token identifier of the former GUI
     */
    public void goBack(int token,Object object){
        switch(token){
            case ROOT_PAUSE_FROM_MAIN: {
                GUILoader.loadPauseMenu(ROOT_MAIN_GAME,object);
                break;
            }
            case ROOT_PAUSE_FROM_COMBAT: {
                GUILoader.loadPauseMenu(ROOT_COMBAT,object);
                break;
            }
            case ROOT_PAUSE_FROM_RELATION: {
                GUILoader.loadPauseMenu(ROOT_RELATION_MENU,object);
                break;
            }
            case ROOT_PAUSE_FROM_FLEET: {
                GUILoader.loadPauseMenu(ROOT_FLEET_MENU,object);
                break;
            }

            case ROOT_START_MENU:{
                GUILoader.loadStartMenu();
                break;
            }
            case ROOT_MAIN_GAME: {
                GUILoader.loadMainGame();
                break;
            }
            case ROOT_COMBAT: {
                if(object == null){
                    logger.error("object Battle to init the CombatMenu is null");
                    GUILoader.loadStartMenu();
                }
                else GUILoader.loadCombat((Battle) object);
                break;
            }
            case ROOT_RELATION_MENU:{
                if(object == null){
                    logger.error("object Faction to init the RelationMenu is null");
                    GUILoader.loadStartMenu();
                }
                else GUILoader.loadRelationMenu((Faction) object);
                break;
            }
            case ROOT_FLEET_MENU:{
                if(object == null){
                    logger.error("object to init the FleetMenu is null");
                    GUILoader.loadStartMenu();
                }
                else if(object instanceof SeaRoad)GUILoader.loadFleetMenu((SeaRoad) object);
                else GUILoader.loadFleetMenu((Fleet) object);
                break;
            }
            default: {
                GUILoader.loadStartMenu();
            }
        }
    }
}
