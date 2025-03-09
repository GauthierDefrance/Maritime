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

    public static void loadPauseMenu(){
        actualise(new PauseMenu());
    }

    public static void loadStartMenu(){
        actualise(new StartMenu());
    }

    public static void loadOptionsMenu(){
        actualise(new OptionsMenu());
    }

    public static void loadChargeGameMenu(MapBuilder map){
        actualise(new SaveFileMenu(map, 0));
    }

    public static void loadSaveGameMenu(MapBuilder map){
        actualise(new SaveFileMenu(map, 1));
    }
}
