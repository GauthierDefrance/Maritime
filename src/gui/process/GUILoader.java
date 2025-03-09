package gui.process;

import config.MapBuilder;
import gui.panel.*;

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
    public static void loadMainGameMenu(Container window, MapBuilder map){
        actualise(window, new MainGameMenu(window,map));
    }

    public static void loadPauseMenu(int token,Container window, MapBuilder map){
        actualise(window, new PauseMenu(token, window, map));
    }

    public static void loadStartMenu(Container window){
        actualise(window, new StartMenu(window));
    }

    public static void loadOptionsMenu(int token, Container window, MapBuilder map){
        actualise(window, new OptionsMenu(token, window, map));
    }

    public static void loadChargeGameMenu(int token, Container window, MapBuilder map){
        actualise(window, new ChargeGameMenu(token, window, map));
    }
}
