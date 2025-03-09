package gui.process;

import config.GameConfiguration;

import javax.swing.*;

public class ListenerBehavior {

    private ListenerBehavior() { }

    /**
     * Create a new ListenerBehavior Object
     * @return ListenerBehavior
     */
    public static ListenerBehavior create(){
        return new ListenerBehavior();
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
/*
    public void goBack(int rootToken){
        switch(rootToken){
            case GameConfiguration.ROOT_STARTMENU:
                GUILoader.loadStartMenu();
                break;
            case GameConfiguration.ROOT_MAINGAME:
                GUILoader.loadMainGameMenu();
                break;
            default:
                GUILoader.loadPauseMenu(rootToken);
        }
    }
 */
}
