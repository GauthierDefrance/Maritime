package gui.utilities;

import engine.data.MapGame;
import engine.battleengine.data.Battle;
import engine.data.Fleet;
import engine.data.entity.Harbor;
import engine.data.faction.Faction;
import engine.data.trading.SeaRoad;
import gui.MainGUI;
import gui.panel.menu.*;
import music.MusicManager;

import javax.swing.JPanel;
import java.awt.*;

/**
 * Handle changes between GUIs
 * @author Zue Jack-Arthur
 * @version 0.4
 */
public class GUILoader {

    private static void actualise(JPanel menu,int i){
        MusicManager.getInstance().pauseAllMusicPlayersLoop(i);
        Container window = MainGUI.getWindow();
        window.removeAll();
        window.add(menu);
        window.revalidate();
        window.repaint();
        menu.setFocusable(true);
        menu.requestFocusInWindow();
    }

    public static void loadMainGame(){
        actualise(new MainGameMenu(),2);
        if(!MusicManager.getInstance().getMusicPlayer(2).isPlaying())MusicManager.getInstance().playerMusic(2);
    }

    public static void loadHarborMenu(Harbor harbor){
        actualise(new HarborMenu(harbor),3);
        if(!MusicManager.getInstance().getMusicPlayer(3).isPlaying())MusicManager.getInstance().playerMusic(3);
    }

    public static void loadFleetMenu(Fleet fleet){
        actualise(new FleetMenu(fleet),3);
        if(!MusicManager.getInstance().getMusicPlayer(3).isPlaying())MusicManager.getInstance().playerMusic(3);
    }

    public static void loadFleetMenu(SeaRoad seaRoad){
        actualise(new FleetMenu(seaRoad),3);
        if(!MusicManager.getInstance().getMusicPlayer(3).isPlaying())MusicManager.getInstance().playerMusic(3);
    }

    public static void loadPauseMenu(int token,Object object){
        actualise(new PauseMenu(token,object),1);
        if(!MusicManager.getInstance().getMusicPlayer(1).isPlaying())MusicManager.getInstance().playerMusic(1);
    }

    public static void loadStartMenu(){
        actualise(new StartMenu(),0);
        if(!MusicManager.getInstance().getMusicPlayer(0).isPlaying())MusicManager.getInstance().playerMusic(0);
    }

    public static void loadOptionsMenu(int token,Object object){
        actualise(new OptionsMenu(token,object),1);
        if(!MusicManager.getInstance().getMusicPlayer(1).isPlaying())MusicManager.getInstance().playerMusic(1);
    }

    public static void loadChargeGameMenu(int token, Object object){
        actualise(new SaveFileMenu(token, 0,object),1);
        if(!MusicManager.getInstance().getMusicPlayer(1).isPlaying())MusicManager.getInstance().playerMusic(1);
    }

    public static void loadSaveGameMenu(int token, Object object){
        actualise(new SaveFileMenu(token, 1,object),1);
        if(!MusicManager.getInstance().getMusicPlayer(1).isPlaying())MusicManager.getInstance().playerMusic(1);
    }

    public static void loadRelationMenu(Faction faction){
        actualise(new RelationMenu(faction),3);
        if(!MusicManager.getInstance().getMusicPlayer(3).isPlaying())MusicManager.getInstance().playerMusic(3);
    }

    public static void loadCombat(Battle battle){
        MapGame.getInstance().setTimeStop(true);
        actualise(new CombatMenu(battle),4);
        MusicManager.getInstance().resumeMusicPlayer(4);
    }

    public static void loadTradeMenu(Harbor sellerHarbor, Harbor targetHarbor){
        actualise(new TradeMenu(sellerHarbor,targetHarbor),3);
        if(!MusicManager.getInstance().getMusicPlayer(3).isPlaying())MusicManager.getInstance().playerMusic(3);
    }

    public static void loadChoicePathMenu(Faction faction){
        actualise(new ChoicePathMenu(faction),3);
        if(!MusicManager.getInstance().getMusicPlayer(3).isPlaying())MusicManager.getInstance().playerMusic(3);
    }

    public static void loadChoicePathMenu(SeaRoad seaRoad){
        actualise(new ChoicePathMenu(seaRoad),3);
        if(!MusicManager.getInstance().getMusicPlayer(3).isPlaying())MusicManager.getInstance().playerMusic(3);
    }

    public static void loadChoicePathMenu(Fleet fleet){
        actualise(new ChoicePathMenu(fleet),3);
        if(!MusicManager.getInstance().getMusicPlayer(3).isPlaying())MusicManager.getInstance().playerMusic(3);
    }
}
