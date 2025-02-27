package experimental_gui;

import java.awt.*;

/**
 * Handle changes between GUIs
 * @author Zue Jack-Arthur
 * @version 0.1
 */
public class GUILoader {

    public static void actualise(Container window, simpleMenu menu){
        window.removeAll();
        window.add(menu);
        window.revalidate();
        window.repaint();
    }

    public static void loadPauseMenu(Container window){
        actualise(window, new pauseMenu(window));
    }

    public static void loadStartMenu(Container window){
        actualise(window, new startMenu(window));
    }

    public static void loadOptionsMenu(int token, Container window){
        actualise(window, new optionsMenu(token, window));
    }
}
