package gui.process;

import config.GameConfiguration;
import engine.entity.Harbor;
import engine.entity.boats.Boat;
import engine.trading.SeaRoad;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Kenan Ammad
 * Classe PaintEntity
 * @version 0.4
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
        if(sprite!=null){g2d.drawImage(sprite, (int) (harbor.getPosition().getX()) - (sprite.getWidth() / 2), (int) (harbor.getPosition().getY()) - (sprite.getHeight() / 2), null);}
        else {
            g2d.setColor(Color.MAGENTA);
            g2d.fillOval((int)(harbor.getPosition().getX())-10,(int)(harbor.getPosition().getY())-10, 20, 20);
            g2d.setColor(Color.black);
        }
    }

    /**
     * It paints a boat that needs to be painted on 2D graphics
     */
    public void paint(Boat boat, Graphics2D g2d){
        BufferedImage sprite = ImageStock.getImage(boat);
        g2d.rotate(boat.getAngle(),(int)(boat.getPosition().getX()),(int)(boat.getPosition().getY()));
        g2d.setColor(ImageStock.colorChoice(boat.getColor()));
        g2d.fillOval((int)(boat.getPosition().getX())-((int)GameConfiguration.HITBOX_BOAT/2),(int)(boat.getPosition().getY())-((int)GameConfiguration.HITBOX_BOAT/2), (int) GameConfiguration.HITBOX_BOAT, (int) GameConfiguration.HITBOX_BOAT);
        g2d.setColor(Color.black);

        if(sprite!=null){g2d.drawImage(sprite, (int) (boat.getPosition().getX()) - (sprite.getWidth() / 2), (int) (boat.getPosition().getY()) - (sprite.getHeight() / 2), null);}
        else {
            g2d.setColor(Color.MAGENTA);
            g2d.fillOval((int)(boat.getPosition().getX())-10,(int)(boat.getPosition().getY())-10, 20, 20);
            g2d.setColor(Color.black);
        }
        g2d.rotate(-boat.getAngle(),(int)(boat.getPosition().getX()),(int)(boat.getPosition().getY()));
    }

    /**
     * It paints a boat that needs to be painted on 2D graphics
     */
    public void paint(Boat boat, Point point, Graphics2D g2d){
        BufferedImage sprite = ImageStock.getImage(boat);
        g2d.rotate(boat.getAngle(),(int)(point.getX()),(int)(point.getY()));
        g2d.setColor(ImageStock.colorChoice(boat.getColor()));
        g2d.fillOval((int)(point.getX())-((int)GameConfiguration.HITBOX_BOAT/2),(int)(point.getY())-((int)GameConfiguration.HITBOX_BOAT/2), (int) GameConfiguration.HITBOX_BOAT, (int) GameConfiguration.HITBOX_BOAT);
        g2d.setColor(Color.black);

        if(sprite!=null){g2d.drawImage(sprite, (int) (point.getX()) - (sprite.getWidth() / 2), (int) (point.getY()) - (sprite.getHeight() / 2), null);}
        else {
            g2d.setColor(Color.MAGENTA);
            g2d.fillOval((int)(point.getX())-10,(int)(point.getY())-10, 20, 20);
            g2d.setColor(Color.black);
        }
        g2d.rotate(-boat.getAngle(),(int)(point.getX()),(int)(point.getY()));
    }

    /**
     * It paints a timer seaRoad that needs to be painted on 2D graphics
     */
    public void paint(SeaRoad seaRoad, Graphics2D g2d){
        BufferedImage sprite = ImageStock.getImage(seaRoad.getTargetHarbor());
        g2d.setColor(Color.darkGray);
        g2d.setFont(new Font("time20",Font.PLAIN,20));
        if(sprite!=null)g2d.drawString(seaRoad.getStringTimer(),(int) (seaRoad.getTargetHarbor().getPosition().getX())- (g2d.getFontMetrics().stringWidth(seaRoad.getStringTimer())/2), (int) (seaRoad.getTargetHarbor().getPosition().getY())-(sprite.getHeight() / 2)-5);
        g2d.setColor(Color.black);
    }

    /**
     * It paints a player boat that needs to be painted on 2D graphics
     */
    public void paintPlayer(Boat boat, Graphics2D g2d){
        g2d.rotate(boat.getAngle(),(int)(boat.getPosition().getX()),(int)(boat.getPosition().getY()));
        g2d.setColor(new Color(255,255,255, GameConfiguration.Transparency_Halo));
        g2d.fillOval((int)(boat.getPosition().getX())-((int)boat.getVisionRadius()/2),(int)(boat.getPosition().getY())-((int)boat.getVisionRadius()/2), (int) boat.getVisionRadius(), (int) boat.getVisionRadius());
        g2d.setColor(Color.black);
        g2d.rotate(-boat.getAngle(),(int)(boat.getPosition().getX()),(int)(boat.getPosition().getY()));
        paint(boat,g2d);
    }

    /**
     * It paints a player harbor that needs to be painted on 2D graphics
     */
    public void paintPlayer(Harbor harbor, Graphics2D g2d){
        g2d.setColor(new Color(255,255,255, GameConfiguration.Transparency_Halo));
        g2d.fillOval((int)(harbor.getPosition().getX())-((int)harbor.getVisionRadius()/2),(int)(harbor.getPosition().getY())-((int)harbor.getVisionRadius()/2), (int) harbor.getVisionRadius(), (int) harbor.getVisionRadius());
        g2d.setColor(Color.black);
        paint(harbor,g2d);
    }

    /**
     * It paints a player boat that needs to be painted in Battle on 2D graphics
     */
    public void paintBattle(Boat boat, Graphics2D g2d){
        g2d.rotate(boat.getAngle(),(int)(boat.getPosition().getX()),(int)(boat.getPosition().getY()));
        g2d.setColor(new Color(64, 64, 64, GameConfiguration.Transparency_Halo));
        g2d.setStroke(new BasicStroke(5*GameConfiguration.GAME_SCALE));
        g2d.fillArc((int)(boat.getPosition().getX())-((int)boat.getVisionRadius()*GameConfiguration.DEFAULT_SHOOT_DISTANCE/2),(int)(boat.getPosition().getY())-((int)boat.getVisionRadius()*GameConfiguration.DEFAULT_SHOOT_DISTANCE/2), (int) boat.getVisionRadius()*GameConfiguration.DEFAULT_SHOOT_DISTANCE, (int) boat.getVisionRadius()*GameConfiguration.DEFAULT_SHOOT_DISTANCE, 45, 90);
        g2d.drawArc((int)(boat.getPosition().getX())-((int)boat.getVisionRadius()*GameConfiguration.DEFAULT_SHOOT_DISTANCE/2),(int)(boat.getPosition().getY())-((int)boat.getVisionRadius()*GameConfiguration.DEFAULT_SHOOT_DISTANCE/2), (int) boat.getVisionRadius()*GameConfiguration.DEFAULT_SHOOT_DISTANCE, (int) boat.getVisionRadius()*GameConfiguration.DEFAULT_SHOOT_DISTANCE, -45, -90);
        g2d.setColor(Color.black);
        g2d.rotate(-boat.getAngle(),(int)(boat.getPosition().getX()),(int)(boat.getPosition().getY()));
        paint(boat,g2d);
    }

}