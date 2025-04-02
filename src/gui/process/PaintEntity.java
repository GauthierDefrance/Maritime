package gui.process;

import config.GameConfiguration;
import engine.MapGame;
import engine.data.entity.Harbor;
import engine.data.entity.boats.Boat;
import engine.data.faction.Faction;
import engine.data.trading.SeaRoad;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * @author Kenan Ammad
 * Classe PaintEntity
 * @version 0.5
 */
public class PaintEntity {
    /**
     * Typical constructor generating an PaintEntity
     */
    public PaintEntity(){}

    /**
     * It paints a harbor that needs to be painted on 2D graphics
     */
    public void paint(Harbor harbor, Graphics2D g2d){
        BufferedImage sprite = ImageStock.getImage(harbor);
        paintSprite(harbor.getPosition(),sprite,g2d,0);
    }

    /**
     * It paints a boat that needs to be painted on 2D graphics
     */
    public void paint(Boat boat, Graphics2D g2d){
        BufferedImage sprite = ImageStock.getImage(boat);
        paintHITBOX(boat.getPosition(),ImageStock.colorChoice(boat.getColor()),g2d);
        paintSprite(boat.getPosition(),sprite,g2d,boat.getAngle());
    }

    /**
     * It paints a boat that needs to be painted on 2D graphics
     */
    public void paint(Boat boat, Point point, Graphics2D g2d){
        BufferedImage sprite = ImageStock.getImage(boat);
        paintHITBOX(point,ImageStock.colorChoice(boat.getColor()),g2d);
        paintSprite(point,sprite,g2d,boat.getAngle());
    }

    /**
     * It paints a timer seaRoad that needs to be painted on 2D graphics
     */
    public void paint(SeaRoad seaRoad, Graphics2D g2d){
        BufferedImage sprite = ImageStock.getImage(seaRoad.getTargetHarbor());
        g2d.setColor(Color.black);
        g2d.setFont(new Font("time20",Font.PLAIN, (int) (7.5*GameConfiguration.GAME_SCALE)));
        if(sprite!=null)g2d.drawString(seaRoad.getStringTimer(),(int) (seaRoad.getTargetHarbor().getPosition().getX())- (g2d.getFontMetrics().stringWidth(seaRoad.getStringTimer())/2), (int) (seaRoad.getTargetHarbor().getPosition().getY())-(sprite.getHeight() / 2)-5);
    }

    /**
     * It paints a player boat that needs to be painted on 2D graphics
     */
    public void paintPlayer(Boat boat, Graphics2D g2d){
        g2d.setColor(new Color(255,255,255, GameConfiguration.TRANSPARENCY_HALO));
        g2d.fillOval((int)(boat.getPosition().getX())-((int)boat.getVisionRadius()/2),(int)(boat.getPosition().getY())-((int)boat.getVisionRadius()/2), (int) boat.getVisionRadius(), (int) boat.getVisionRadius());
        g2d.setColor(Color.black);
        paint(boat,g2d);
    }

    /**
     * It paints a player harbor that needs to be painted on 2D graphics
     */
    public void paintPlayer(Harbor harbor, Graphics2D g2d){
        g2d.setColor(new Color(255,255,255, GameConfiguration.TRANSPARENCY_HALO));
        g2d.fillOval((int)(harbor.getPosition().getX())-((int)harbor.getVisionRadius()/2),(int)(harbor.getPosition().getY())-((int)harbor.getVisionRadius()/2), (int) harbor.getVisionRadius(), (int) harbor.getVisionRadius());
        g2d.setColor(Color.black);
        paint(harbor,g2d);
    }

    public void paintChase(Boat hunter,Boat prey, Graphics2D g2d){
        g2d.setColor(ImageStock.colorChoice(hunter.getColor()));
        float[] floats = {10,10};
        g2d.setStroke(new BasicStroke(5,BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER,1,floats,0));
        g2d.drawLine((int) hunter.getPosition().getX(), (int) hunter.getPosition().getY(), (int) prey.getPosition().getX(), (int) prey.getPosition().getY());
        g2d.setColor(Color.black);
    }

    public void paintChaseMap(Graphics2D g2d){
        for(Faction faction : MapGame.getInstance().getLstFaction()){
            for (Boat hunter : faction.getLstBoat()){
                if(MapGame.getInstance().getHunterPreyHashMap().containsKey(hunter)){
                    paintChase(hunter,MapGame.getInstance().getHunterPreyHashMap().get(hunter),g2d);
                }
            }
        }
    }

    /**
     * It paints a player boat that needs to be painted in Battle on 2D graphics
     */
    public void paintBattle(Boat boat, Graphics2D g2d){
        g2d.setColor(new Color(64, 64, 64, 15));
        g2d.fillArc((int)(boat.getPosition().getX())-((int)boat.getVisionRadius()*GameConfiguration.DEFAULT_SHOOT_DISTANCE/2),(int)(boat.getPosition().getY())-((int)boat.getVisionRadius()*GameConfiguration.DEFAULT_SHOOT_DISTANCE/2), (int) boat.getVisionRadius()*GameConfiguration.DEFAULT_SHOOT_DISTANCE, (int) boat.getVisionRadius()*GameConfiguration.DEFAULT_SHOOT_DISTANCE, (int) (Math.toDegrees(-boat.getAngle())+45), 90);
        g2d.fillArc((int)(boat.getPosition().getX())-((int)boat.getVisionRadius()*GameConfiguration.DEFAULT_SHOOT_DISTANCE/2),(int)(boat.getPosition().getY())-((int)boat.getVisionRadius()*GameConfiguration.DEFAULT_SHOOT_DISTANCE/2), (int) boat.getVisionRadius()*GameConfiguration.DEFAULT_SHOOT_DISTANCE, (int) boat.getVisionRadius()*GameConfiguration.DEFAULT_SHOOT_DISTANCE, (int) (Math.toDegrees(-boat.getAngle())-45), -90);
        g2d.setColor(Color.black);
        paint(boat,g2d);
    }
    public void paintBattleDeadBoat(Boat boat, Graphics2D g2d){
        BufferedImage sprite = ImageStock.getImageDeadBoat(boat);
        paintSprite(boat.getPosition(),sprite,g2d,0);
    }

    public void paintHP(Boat boat, Graphics2D g2d){
        g2d.setColor(Color.DARK_GRAY);
        g2d.fillRect((int) (boat.getPosition().getX()- (double) 50 /2), (int) (boat.getPosition().getY()-((double) 10)+GameConfiguration.HITBOX_BOAT/2),50,10);
        if (boat.getCurrentHp()/(double)boat.getMaxHp()>0.75)g2d.setColor(Color.GREEN);
        else if (boat.getCurrentHp()/(double)boat.getMaxHp()>0.50)g2d.setColor(Color.YELLOW);
        else if (boat.getCurrentHp()/(double)boat.getMaxHp()>0.25)g2d.setColor(Color.ORANGE);
        else g2d.setColor(Color.RED);
        int width = (int) ((50*boat.getCurrentHp())/(double)boat.getMaxHp());
        g2d.fillRect((int) (boat.getPosition().getX()- (double) 50 /2), (int) (boat.getPosition().getY()-((double) 10)+GameConfiguration.HITBOX_BOAT/2),width,10);
        g2d.setColor(Color.black);
    }

    public void paintHITBOX(Point point,  Color color, Graphics2D g2d){
        g2d.setColor(color);
        g2d.fillOval((int)(point.getX())-((int)GameConfiguration.HITBOX_BOAT/2),(int)(point.getY())-((int)GameConfiguration.HITBOX_BOAT/2), (int) GameConfiguration.HITBOX_BOAT, (int) GameConfiguration.HITBOX_BOAT);
        g2d.setColor(Color.black);
    }

    public void paintSprite(Point point,  BufferedImage sprite, Graphics2D g2d,double angle){
        if(sprite!=null) {
            AffineTransform transform = new AffineTransform();
            transform.rotate(angle, point.getX(), point.getY());
            transform.translate((point.getX()) - (sprite.getWidth() / 2), (point.getY()) - (sprite.getHeight() / 2));
            g2d.drawImage(sprite, transform, null);
        }
        else {
            g2d.setColor(Color.MAGENTA);
            g2d.fillOval((int)(point.getX())-10,(int)(point.getY())-10, 20, 20);
            g2d.setColor(Color.black);
        }
    }

}