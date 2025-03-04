package experimental_gui;

import java.awt.*;

/**
 * Handle changes between GUIs
 * @author Zue Jack-Arthur
 * @version 0.1
 */
public class GUILoader {

    public static void actualise(Container window, SimpleMenu menu){
        window.removeAll();
        window.add(menu);
        window.revalidate();
        window.repaint();
        menu.setFocusable(true);
        menu.requestFocusInWindow();
    }

    public static void loadPauseMenu(int token,Container window){
        actualise(window, new PauseMenu(token, window));
    }

    public static void loadStartMenu(Container window){
        actualise(window, new StartMenuTest(window));
    }

    public static void loadOptionsMenu(int token, Container window){
        actualise(window, new OptionsMenu(token, window));
    }

    public static void loadChargeGameMenu(int token, Container window){
        actualise(window, new ChargeGameMenu(token, window));
    }
}
