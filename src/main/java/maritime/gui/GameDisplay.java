package maritime.gui;

import maritime.config.GameConfiguration;
import maritime.config.MapBuilder;
import maritime.engine.entity.boats.Boat;
import maritime.engine.entity.Harbor;
import maritime.engine.trading.SeaRoad;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * @author @Kenan Ammad
 * Classe GameDisplay
 * @version 0.4
 */
public class GameDisplay extends JPanel {

    private final MapBuilder map;
    private final PaintEntity paintEntity;
    private final PaintBackGround paintBackGround;
    private final PaintPopUp paintPopUp;

    /**
     * Typical builder generating an GameDisplay
     */
    public GameDisplay(MapBuilder map){
        this.map = map;
        this.paintEntity = new PaintEntity();
        this.paintBackGround = new PaintBackGround();
        this.paintPopUp = new PaintPopUp(map);
    }
    /**
     * It paints everything that has to be painted on graphics2D
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        double scale = Math.min((double)getWidth()/640,(double) getHeight() /360);
        g2d.scale(scale,scale);
        paintBackGround.paint(g2d);
        g2d.scale((double) 1 /GameConfiguration.GAME_SCALE, (double) 1 /GameConfiguration.GAME_SCALE);

//        //test
//        for (Faction faction : map.getLstBotFaction()){
//
//            for(Harbor harbor : faction.getLstHarbor()){
//                paintEntity.paint(harbor,g2d);
//            }
//            for(Boat boat : faction.getLstBoat()){
//                paintEntity.paint(boat,g2d);
//            }
//        }
//        for(Boat boat : map.getPlayer().getVision()){
//            paintEntity.paintPlayer(boat,g2d);
//        }
//        //end test

        for (Harbor harbor : map.getLstHarbor()){
            paintEntity.paint(harbor,g2d);
        }
        for(Boat boat : map.getPlayer().getVision()){
            paintEntity.paint(boat,g2d);
        }
        for(Boat boat : map.getPlayer().getLstBoat()){
            paintEntity.paintPlayer(boat,g2d);
        }
        for (Harbor harbor : map.getPlayer().getLstHarbor()){
            paintEntity.paintPlayer(harbor,g2d);
        }
        for(SeaRoad seaRoad: map.getPlayer().getLstSeaRouts()){
            paintEntity.paint(seaRoad,g2d);
        }
        ArrayList<PopUp> lstPopUp = new ArrayList<>();
        lstPopUp.addAll(map.getLstPopUp());
        for (PopUp popUp : lstPopUp){
            paintPopUp.paint(popUp,g2d);
        }
        g2d.scale(GameConfiguration.GAME_SCALE,GameConfiguration.GAME_SCALE);
    }
}
