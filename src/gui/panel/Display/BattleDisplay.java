package gui.panel.Display;

import engine.battleengine.data.Battle;
import engine.battleengine.data.Bullet;
import config.GameConfiguration;
import engine.MapGame;
import engine.entity.boats.Boat;
import gui.MainGUI;
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
    private final PaintPopUp paintPopUp;
    private Battle battle;

    /**
     * Typical constructor generating an GameDisplay
     */
    public BattleDisplay(Battle battle){
        this.battle = battle;
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
        paintBackGround.paint(g2d,false);

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


        for(Boat boat : battle.getBoatsInBattleB().getArrayListBoat()){
            paintEntity.paintBattle(boat,g2d);
        }
        for(Boat boat : battle.getBoatsInBattleA().getArrayListBoat()){
            paintEntity.paintBattle(boat,g2d);
        }
        for(Boat boat : battle.getLstBoatsCurrentlyBeingPlaced()){
            paintEntity.paintBattle(boat,g2d);
        }


        for (Bullet bullet : battle.getLstBulletsteamA()){
            g2d.setColor(ImageStock.colorChoice(bullet.getColor()));
            g2d.fillOval((int) (bullet.getPosition().getX()-5), (int) (bullet.getPosition().getY()-5),10,10);
            g2d.setColor(Color.black);
        }

        for (Bullet bullet : battle.getLstBulletsteamB()){
            g2d.setColor(ImageStock.colorChoice(bullet.getColor()));
            g2d.fillOval((int) (bullet.getPosition().getX()-5), (int) (bullet.getPosition().getY()-5),10,10);
            g2d.setColor(Color.black);
        }

        for(Boat boat : battle.getBoatsInBattleB().getArrayListBoat()){
            paintEntity.paintHP(boat,g2d);
        }
        for(Boat boat : battle.getBoatsInBattleA().getArrayListBoat()){
            paintEntity.paintHP(boat,g2d);
        }
        for(Boat boat : battle.getLstBoatsCurrentlyBeingPlaced()){
            paintEntity.paintHP(boat,g2d);
        }

        if(battle.getCurrentBoat() != null && battle.isInPlacingMode()) {
            int x = (int) ((battle.getCurrentBoatPoint().getX()*GameConfiguration.GAME_SCALE)/scale);
            int y = (int) ((battle.getCurrentBoatPoint().getY()*GameConfiguration.GAME_SCALE)/scale);
            Point boatPoint = new Point(x, y);
            paintEntity.paint(battle.getCurrentBoat(), boatPoint, (Graphics2D) g);
        }

        ArrayList<PopUp> lstPopUp = new ArrayList<>();
        lstPopUp.addAll(MapGame.getInstance().getLstPopUp());
        for (PopUp popUp : lstPopUp){
            paintPopUp.paint(popUp,g2d);
        }
        g2d.scale(GameConfiguration.GAME_SCALE,GameConfiguration.GAME_SCALE);
    }
    public PaintEntity getPaintEntity() { return paintEntity; }

    public PaintBackGround getPaintBackGround() { return paintBackGround; }

}
