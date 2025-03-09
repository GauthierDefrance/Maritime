package gui.process;

import config.MapBuilder;
import gui.MainGUI;
import gui.panel.*;

import java.awt.*;

/**
 * Handle changes between GUIs
 * @author Zue Jack-Arthur
 * @version 0.1
 */
public class GUILoader {

    public static void actualise(SimpleMenu menu){
        Container window = MainGUI.getWindow();
        window.removeAll();
        window.add(menu);
        window.revalidate();
        window.repaint();
        menu.setFocusable(true);
        menu.requestFocusInWindow();
    }
    public static void loadMainGameMenu(MapBuilder map){
        actualise(new MainGameMenu(map));
    }

    public static void loadPauseMenu(int token, MapBuilder map){
        actualise(new PauseMenu(token, map));
    }

    public static void loadStartMenu(){
        actualise(new StartMenu());
    }

    public static void loadOptionsMenu(int token, MapBuilder map){
        actualise(new OptionsMenu(token, map));
    }

    public static void loadChargeGameMenu(int token, MapBuilder map){
        actualise(new ChargeGameMenu(token, map));
    }
}
