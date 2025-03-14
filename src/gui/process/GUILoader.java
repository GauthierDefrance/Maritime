package gui.process;

import battleengine.entity.Battle;
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

    public static void loadMainGame(MapBuilder map){
        actualise(new MainGameMenu(map));
    }

    public static void loadPauseMenu(int token){
        actualise(new PauseMenu(token));
    }

    public static void loadStartMenu(){
        actualise(new StartMenu());
    }

    public static void loadOptionsMenu(int token){
        actualise(new OptionsMenu(token));
    }

    public static void loadChargeGameMenu(int token){
        actualise(new SaveFileMenu(token, 0));
    }

    public static void loadSaveGameMenu(int token){
        actualise(new SaveFileMenu(token, 1));
    }

    public static void loadCombat(MapBuilder map,Battle battle){
        actualise(new CombatMenu(map, battle));
    }
}
