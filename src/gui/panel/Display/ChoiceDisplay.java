package gui.panel.Display;

import config.GameConfiguration;
import engine.MapGame;
import engine.entity.Harbor;
import engine.graph.GraphPoint;
import engine.graph.GraphSegment;
import gui.PopUp;
import gui.process.ImageStock;
import gui.process.PaintBackGround;
import gui.process.PaintEntity;
import gui.process.PaintPopUp;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

/**
 * @author Kenan Ammad
 * Classe GameDisplay
 * @version 0.4
 */
public class ChoiceDisplay extends JPanel {

    private final int state;
    private final PaintEntity paintEntity;
    private final PaintBackGround paintBackGround;
    private final PaintPopUp paintPopUp;
    private Harbor harbor1;
    private Harbor harbor2;
    private ArrayList<GraphPoint> path;

    /**
     * Typical constructor generating an GameDisplay
     */
    public ChoiceDisplay(int state){
        this.path = new ArrayList<>();
        this.state = state;
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
        paintBackGround.paint(g2d,true);
        g2d.scale((double) 1 /GameConfiguration.GAME_SCALE, (double) 1 /GameConfiguration.GAME_SCALE);

        if (state == 0){
            g2d.setColor(new Color(4, 4, 62,75));
            for(Map.Entry<String, GraphPoint> entry : MapGame.getInstance().getMapGraphPoint().entrySet()){
                g2d.fillOval(entry.getValue().getX()-15, entry.getValue().getY()-15,30,30);
            }
            g2d.setColor(new Color(10, 35, 160));
            if(path.get(path.size()-1).equals(harbor2.getGraphPosition())){
                g2d.setColor(new Color(10, 255, 51));
            }
            g2d.setStroke(new BasicStroke(6));
            for(int i = 0; i < path.size();i++){
                g2d.fillOval((path.get(i).getX()-15), (path.get(i).getY()-15),30,30);
                if(i>0){
                    g2d.drawLine(path.get(i).getX(),path.get(i).getY(),path.get(i-1).getX(),path.get(i-1).getY());
                }
            }

            if(!path.isEmpty()&&!path.get(path.size()-1).equals(harbor2.getGraphPosition())){
                g2d.setColor(new Color(10, 160, 70));
                for(Map.Entry<String, GraphSegment> entry : path.get(path.size()-1).getSegmentHashMap().entrySet()){
                    if(!path.contains(entry.getValue().getGraphPoint())){
                        g2d.fillOval(entry.getValue().getGraphPoint().getX()-20, entry.getValue().getGraphPoint().getY()-20,40,40);
                    }
                }
            }
            g2d.setColor(Color.black);
        }
        if (harbor2 != null){
            g2d.setColor(ImageStock.colorChoice(harbor2.getColor()));
            g2d.fillOval((int)(harbor2.getPosition().getX())-((int)GameConfiguration.HITBOX_BOAT/2),(int)(harbor2.getPosition().getY()), (int) GameConfiguration.HITBOX_BOAT, (int) GameConfiguration.HITBOX_BOAT);
            g2d.setColor(new Color(10, 255, 51));
            g2d.fillOval((harbor2.getGraphPosition().getX()-15), (harbor2.getGraphPosition().getY()-15),30,30);
            g2d.setColor(Color.black);
        }
        if (harbor1 != null){
            g2d.setColor(ImageStock.colorChoice(harbor1.getColor()));
            g2d.fillOval((int)(harbor1.getPosition().getX())-((int)GameConfiguration.HITBOX_BOAT/2),(int)(harbor1.getPosition().getY()), (int) GameConfiguration.HITBOX_BOAT, (int) GameConfiguration.HITBOX_BOAT);
            g2d.setColor(Color.black);
        }

        for (Harbor harbor : MapGame.getInstance().getLstHarbor()){
            paintEntity.paint(harbor,g2d);
        }

        ArrayList<PopUp> lstPopUp = new ArrayList<>();
        lstPopUp.addAll(MapGame.getInstance().getLstPopUp());
        for (PopUp popUp : lstPopUp){
            paintPopUp.paint(popUp,g2d);
        }
        g2d.scale(GameConfiguration.GAME_SCALE,GameConfiguration.GAME_SCALE);
    }

    public PaintBackGround getPaintBackGround() { return paintBackGround; }

    public void setHarbor2(Harbor harbor2) {
        this.harbor2 = harbor2;
    }

    public void setHarbor1(Harbor harbor1) {
        this.harbor1 = harbor1;
    }

    public void setPath(ArrayList<GraphPoint> path) {
        this.path = path;
    }
}
