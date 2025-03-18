package gui.process;

import static config.GameConfiguration.*;

import engine.MapGame;
import gui.MainGUI;
import gui.utilities.GUILoader;

import javax.swing.*;

/**
 * Store and allow the use of typical EventListener behaviors
 * @author Zue Jack-Arthur
 * @version 0.3
 */
public class ListenerBehaviorManager {

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
     * handle the process of returning to former Menu for GUIs
     * @param token identifier of the former GUI
     */
    public void goBack(int token){
        switch(token){
            case ROOT_PAUSE_FROM_MAIN: {
                GUILoader.loadPauseMenu(ROOT_MAIN_GAME);
                break;
            }
            case ROOT_PAUSE_FROM_COMBAT: {
                GUILoader.loadPauseMenu(ROOT_COMBAT);
                break;
            }

            case ROOT_START_MENU:{
                GUILoader.loadStartMenu();
                break;
            }
            case ROOT_MAIN_GAME: {
                MapGame.getInstance().setTimeStop(false);
                GUILoader.loadMainGame();
                break;
            }
            case ROOT_COMBAT: {
                MapGame.getInstance().setTimeStop(false);
                GUILoader.loadCombat(MainGUI.getBattle());
                break;
            }
            default: {
                GUILoader.loadStartMenu();
            }
        }
    }
}
