package gui.process;

import config.GameConfiguration;
import gui.MainGUI;

import javax.swing.*;

/**
 * Store and allow the use of typical EventListener behaviors
 * @author Zue Jack-Arthur
 */
public class ListenerBehaviorManager {

    private ListenerBehaviorManager() { }

    /**
     * Create a new ListenerBehavior Object
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
            button.setText("On");
        } else {
            bool = true;
            button.setText("Off");
        } return bool;
    }

    /**
     * Increment a value associated with a JButton
     * @param limit value that should not be exceeded
     * @param value current value
     * @return updated "value"
     */
    public int increment(int limit, int value){
        int localvalue = value;
        if (localvalue < limit){
            return ++localvalue;
        } return localvalue;
    }

    /**
     * Decrement a value associated with a JButton
     * @param limit value that should not be exceeded
     * @param value current value
     * @return updated "value"
     */
    public int decrement(int limit, int value){
        int localvalue = value;
        if (localvalue > limit){
            return --localvalue;
        } return localvalue;
    }

    /**
     * Exit the program
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
     * Manage the process of returning to former Menu for GUIs
     * @param token
     */
    public void goBack(int token){
        switch(token){
            case GameConfiguration.ROOT_PAUSE_FROM_MAIN: {
                GUILoader.loadPauseMenu(GameConfiguration.ROOT_MAIN_GAME);
                break;
            }
            case GameConfiguration.ROOT_PAUSE_FROM_COMBAT: {
                GUILoader.loadPauseMenu(GameConfiguration.ROOT_COMBAT);
                break;
            }

            case GameConfiguration.ROOT_START_MENU:{
                GUILoader.loadStartMenu();
                break;
            }
            case GameConfiguration.ROOT_MAIN_GAME: {
                MainGUI.getMap().setTimeStop(true);
                GUILoader.loadMainGame(MainGUI.getMap());
                break;
            }
            case GameConfiguration.ROOT_COMBAT: {
                MainGUI.getMap().setTimeStop(true);
                GUILoader.loadCombat();
                break;
            }
            default: {
                GUILoader.loadStartMenu();
            }
        }
    }
}
