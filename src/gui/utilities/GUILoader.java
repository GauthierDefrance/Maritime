package gui.utilities;

import engine.MapGame;
import engine.battleengine.data.Battle;
import engine.data.Fleet;
import engine.data.entity.Harbor;
import engine.data.faction.Faction;
import engine.data.trading.SeaRoad;
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

    public static void loadHarborMenu(Harbor harbor){
        actualise(new HarborMenu(harbor));
    }

    public static void loadFleetMenu(Fleet fleet){
        actualise(new FleetMenu(fleet));
    }

    public static void loadFleetMenu(SeaRoad seaRoad){
        actualise(new FleetMenu(seaRoad));
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

    public static void loadTradeMenu(Harbor sellerHarbor, Harbor targetHarbor){
        actualise(new TradeMenu(sellerHarbor,targetHarbor));
    }

    public static void loadChoicePathMenu(Faction faction){
        actualise(new ChoicePathMenu(faction));
    }

    public static void loadChoicePathMenu(SeaRoad seaRoad){
        actualise(new ChoicePathMenu(seaRoad));
    }

    public static void loadChoicePathMenu(Fleet fleet){
        actualise(new ChoicePathMenu(fleet));
    }
}
