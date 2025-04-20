package gui.panel.display;

import engine.battleengine.data.Battle;
import engine.battleengine.data.Bullet;
import config.GameConfiguration;
import engine.MapGame;
import engine.data.entity.boats.Boat;
import gui.PopUp;
import gui.process.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * @author Kenan Ammad
 * Classe BattleDisplay
 * @version 0.1
 */
public class BattleDisplay extends JPanel {

    private final PaintEntity paintEntity;
    private final PaintBackGround paintBackGround;
    private Battle battle;

    /**
     * Typical constructor generating an GameDisplay
     */
    public BattleDisplay(Battle battle){
        this.battle = battle;
        this.paintEntity = new PaintEntity();
        this.paintBackGround = new PaintBackGround();
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
        paintBackGround.paint(g2d,false);

        if(MapGame.getInstance().isTimeStop()){
            g2d.setColor(new Color(64, 64, 64, 100));
            g2d.fillRect(0,0, 640, 360);
            g2d.setColor(Color.black);
        }

        g2d.setColor(ImageStock.colorChoice(MapGame.getInstance().getPlayer().getColor()));
        g2d.fillRect(0,0, (int) (GameConfiguration.SPAWN_ZONE.getWidth()/GameConfiguration.GAME_SCALE), (int) (GameConfiguration.SPAWN_ZONE.getHeight()/GameConfiguration.GAME_SCALE));
        g2d.setColor(Color.black);

        g2d.scale((double) 1 /GameConfiguration.GAME_SCALE, (double) 1 /GameConfiguration.GAME_SCALE);

        for(Boat boat : battle.getDeadBoatsA().getArrayListBoat()){
            paintEntity.paintBattleDeadBoat(boat,g2d);
        }
        for(Boat boat : battle.getDeadBoatsB().getArrayListBoat()){
            paintEntity.paintBattleDeadBoat(boat,g2d);
        }

        ArrayList<Boat> tmplstBoatA = new ArrayList<>();
        tmplstBoatA.addAll(battle.getBoatsInBattleA().getArrayListBoat());
        ArrayList<Boat> tmplstBoatB = new ArrayList<>();
        tmplstBoatB.addAll(battle.getBoatsInBattleB().getArrayListBoat());

        for(Boat boat : battle.getLstBoatsCurrentlyBeingPlaced()){
            if(boat.getNextGraphPoint() != null){
                paintEntity.paintChase(boat, boat.getNextGraphPoint().getPoint(), g2d);
                g2d.setColor(ImageStock.colorChoice(boat.getColor()));
                g2d.fillOval((int) (boat.getNextGraphPoint().getPoint().getX()-8), (int) (boat.getNextGraphPoint().getPoint().getY()-8),16,16);
                g2d.setColor(Color.black);
            }
        }
        for(Boat boat : tmplstBoatA){
            if(boat.getNextGraphPoint() != null){
                paintEntity.paintChase(boat, boat.getNextGraphPoint().getPoint(), g2d);
                g2d.setColor(ImageStock.colorChoice(boat.getColor()));
                g2d.fillOval((int) (boat.getNextGraphPoint().getPoint().getX()-8), (int) (boat.getNextGraphPoint().getPoint().getY()-8),16,16);
                g2d.setColor(Color.black);
            }
        }

        for(Boat boat : tmplstBoatB){
            paintEntity.paintBattle(boat,g2d);
        }
        for(Boat boat : tmplstBoatA){
            paintEntity.paintBattle(boat,g2d);
        }
        for(Boat boat : battle.getLstBoatsCurrentlyBeingPlaced()){
            paintEntity.paintBattle(boat,g2d);
        }

        ArrayList<Bullet> tmplstBullet = new ArrayList<>();
        tmplstBullet.addAll(battle.getLstBulletsteamA());
        for (Bullet bullet : tmplstBullet){
            paintEntity.paint(bullet,g2d);
        }

        tmplstBullet = new ArrayList<>();
        tmplstBullet.addAll(battle.getLstBulletsteamB());
        for (Bullet bullet : tmplstBullet){
            paintEntity.paint(bullet,g2d);
        }

        for(Boat boat : tmplstBoatB){
            paintEntity.paintHP(boat,g2d);
        }
        for(Boat boat : tmplstBoatA){
            paintEntity.paintHP(boat,g2d);
        }
        for(Boat boat : battle.getLstBoatsCurrentlyBeingPlaced()){
            paintEntity.paintHP(boat,g2d);
        }

        if(battle.getCurrentBoat() != null && battle.isInPlacingMode()) {
            int x = (int) ((battle.getCurrentBoatPoint().getX()*GameConfiguration.GAME_SCALE)/scale);
            int y = (int) ((battle.getCurrentBoatPoint().getY()*GameConfiguration.GAME_SCALE)/scale);
            Point boatPoint = new Point(x, y);
            paintEntity.paint(battle.getCurrentBoat(), boatPoint, g2d);
        }
        if(battle.getCurrentBoat2() != null && battle.getCurrentBoatPoint2() != null) {
            int x = (int) ((battle.getCurrentBoatPoint2().getX()*GameConfiguration.GAME_SCALE)/scale);
            int y = (int) ((battle.getCurrentBoatPoint2().getY()*GameConfiguration.GAME_SCALE)/scale);
            float[] floats = {10,10};
            g2d.setStroke(new BasicStroke(5,BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER,1,floats,0));
            g2d.drawLine((int) battle.getCurrentBoat2().getPosition().getX(), (int) battle.getCurrentBoat2().getPosition().getY(), x, y);
            g2d.fillOval(x-8, y-8,16,16);
        }

        ArrayList<PopUp> lstPopUp = new ArrayList<>();
        lstPopUp.addAll(MapGame.getInstance().getLstPopUp());
        for (PopUp popUp : lstPopUp){
            paintEntity.paint(popUp,g2d);
        }
        g2d.scale(GameConfiguration.GAME_SCALE,GameConfiguration.GAME_SCALE);
    }
    public PaintEntity getPaintEntity() { return paintEntity; }

    public PaintBackGround getPaintBackGround() { return paintBackGround; }

}
