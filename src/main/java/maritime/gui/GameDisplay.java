package maritime.gui;

import maritime.engine.entity.Boat;
import maritime.engine.entity.Harbor;
import maritime.engine.faction.Faction;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameDisplay extends JPanel {

    private final ArrayList<Faction> lstFaction ;
    private final PaintEntity paintEntity = new PaintEntity();
    private final PaintBackGround paintBackGround = new PaintBackGround();

    public GameDisplay(ArrayList<Faction> lstFaction){
        this.lstFaction = lstFaction;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        double scale = Math.min((double)getWidth()/640,(double) getHeight() /360);
        g2d.scale(scale,scale);
        paintBackGround.paint(g2d);
        for (Faction faction : lstFaction){

            for(Harbor harbor : faction.getLstHarbor()){
                paintEntity.paint(harbor,g2d);
            }

            for(Boat boat : faction.getLstBoat()){
                paintEntity.paint(boat,g2d);
            }


        }
    }
}
