package maritime.gui;

import maritime.config.GameConfiguration;
import maritime.config.MapConfig;
import maritime.engine.entity.Boat;
import maritime.engine.entity.Harbor;
import maritime.engine.faction.Faction;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author @Kenan Ammad
 * Classe GameDisplay
 */
public class GameDisplay extends JPanel {

    private final MapConfig map;
    private final PaintEntity paintEntity = new PaintEntity();
    private final PaintBackGround paintBackGround = new PaintBackGround();

    public GameDisplay(MapConfig map){
        this.map = map;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        double scale = Math.min((double)getWidth()/640,(double) getHeight() /360);
        g2d.scale(scale,scale);
        paintBackGround.paint(g2d);
        g2d.scale((double) 1 /GameConfiguration.GAME_SCALE, (double) 1 /GameConfiguration.GAME_SCALE);

        for (Faction faction : map.getLstBotFaction()){

            for(Harbor harbor : faction.getLstHarbor()){
                paintEntity.paint(harbor,g2d);
            }

            for(Boat boat : faction.getLstBoat()){
                paintEntity.paint(boat,g2d);
            }
        }

        for (Harbor harbor : map.getLstHarbor()){
            paintEntity.paint(harbor,g2d);
        }
        for(Boat boat : map.getPlayer().getVision()){
            paintEntity.paint(boat,g2d);
        }
        for(Boat boat : map.getPlayer().getLstBoat()){
            paintEntity.paint(boat,g2d);
        }

        g2d.scale(GameConfiguration.GAME_SCALE,GameConfiguration.GAME_SCALE);

    }
}
