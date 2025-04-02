package gui.utilities;

import engine.MapGame;
import engine.battleengine.data.Battle;
import engine.data.entity.Harbor;
import engine.data.faction.Faction;
import engine.data.trading.TradeOffer;
import gui.MainGUI;
import gui.panel.menu.*;

import javax.swing.JPanel;
import java.awt.*;

/**
 * Handle changes between GUIs
 * @author Zue Jack-Arthur
 * @version 0.4
 */
public class GUILoader {

    private static void actualise(JPanel menu){
        Container window = MainGUI.getWindow();
        window.removeAll();
        window.add(menu);
        window.revalidate();
        window.repaint();
        menu.setFocusable(true);
        menu.requestFocusInWindow();
    }

    public static void loadMainGame(){
        actualise(new MainGameMenu());
    }

    public static void loadPauseMenu(int token,Object object){
        actualise(new PauseMenu(token,object));
    }

    public static void loadStartMenu(){
        actualise(new StartMenu());
    }

    public static void loadOptionsMenu(int token,Object object){
        actualise(new OptionsMenu(token,object));
    }

    public static void loadChargeGameMenu(int token, Object object){
        actualise(new SaveFileMenu(token, 0,object));
    }

    public static void loadSaveGameMenu(int token, Object object){
        actualise(new SaveFileMenu(token, 1,object));
    }

    public static void loadRelationMenu(Faction faction){
        actualise(new RelationMenu(faction));
    }

    public static void loadCombat(Battle battle){
        MapGame.getInstance().setTimeStop(true);
        actualise(new CombatMenu(battle));
    }

    public static void loadFleetManagingMenu(TradeOffer offer){
        actualise(new FleetBuildingMenu(offer));
    }

    public static void loadTradeMenu(TradeOffer offer){
        actualise(new TradeMenu(offer));
    }

    public static void loadFleetManagingMenu(){
        actualise(new FleetBuildingMenu());
    }

    public static void loadChoicePathMenu(Faction faction, int token){
        actualise(new ChoicePathMenu(faction,token));
    }

    public static void loadChoicePathMenu(Harbor harbor1,Harbor harbor2 , int token){
        actualise(new ChoicePathMenu(harbor1,harbor2,token));
    }
}
