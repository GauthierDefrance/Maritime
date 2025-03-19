package gui.panel.Display;

import config.GameConfiguration;
import engine.MapGame;
import engine.entity.Harbor;
import engine.entity.boats.Boat;
import engine.trading.SeaRoad;
import gui.process.PaintBackGround;
import gui.process.PaintEntity;
import gui.process.PaintPopUp;
import gui.PopUp;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * @author Kenan Ammad
 * Classe GameDisplay
 * @version 0.4
 */
public class GameDisplay extends JPanel {

    private final PaintEntity paintEntity;
    private final PaintBackGround paintBackGround;
    private final PaintPopUp paintPopUp;

    /**
     * Typical constructor generating an GameDisplay
     */
    public GameDisplay(){
        this.paintEntity = new PaintEntity();
        this.paintBackGround = new PaintBackGround();
        this.paintPopUp = new PaintPopUp();
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

        for (Harbor harbor : MapGame.getInstance().getLstHarbor()){
            paintEntity.paint(harbor,g2d);
        }
        for(Boat boat : MapGame.getInstance().getPlayer().getVision()){
            paintEntity.paint(boat,g2d);
        }
        for(Boat boat : MapGame.getInstance().getPlayer().getLstBoat()){
            paintEntity.paintPlayer(boat,g2d);
        }
        for (Harbor harbor : MapGame.getInstance().getPlayer().getLstHarbor()){
            paintEntity.paintPlayer(harbor,g2d);
        }
        for(SeaRoad seaRoad: MapGame.getInstance().getPlayer().getLstSeaRouts()){
            paintEntity.paint(seaRoad,g2d);
        }
        ArrayList<PopUp> lstPopUp = new ArrayList<>();
        lstPopUp.addAll(MapGame.getInstance().getLstPopUp());
        for (PopUp popUp : lstPopUp){
            paintPopUp.paint(popUp,g2d);
        }
        g2d.scale(GameConfiguration.GAME_SCALE,GameConfiguration.GAME_SCALE);
    }

    public PaintBackGround getPaintBackGround() { return paintBackGround; }

}
