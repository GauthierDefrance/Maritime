package maritime.gui;

import maritime.config.GameConfiguration;
import maritime.engine.entity.*;
import maritime.engine.entity.boats.*;
import maritime.engine.trading.SeaRoad;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

/**
 * @author @Kenan Ammad
 * Classe PaintEntity
 * @version 0.3
 */
public class PaintEntity {
    private BufferedImage[][] tbSprite;

    /**
     * Typical constructor generating an PaintEntity
     */
    public PaintEntity(){
        tbSprite = new BufferedImage[5][4];
        try {
            tbSprite[0][0] = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/boat/standard.png")));
            tbSprite[0][1] = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/boat/standard/red.png")));
            tbSprite[0][2] = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/boat/standard/blue.png")));

            tbSprite[1][0] = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/boat/fodder.png")));
            tbSprite[1][1] = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/boat/fodder/red.png")));
            tbSprite[1][2] = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/boat/fodder/blue.png")));

            tbSprite[2][0] = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/boat/merchant.png")));
            tbSprite[2][1] = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/boat/merchant/red.png")));
            tbSprite[2][2] = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/boat/merchant/blue.png")));

            tbSprite[3][0] = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/boat/military.png")));
            tbSprite[3][1] = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/boat/military/red.png")));
            tbSprite[3][2] = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/boat/military/blue.png")));

            tbSprite[4][0] = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/harbor/harbor.png")));
            tbSprite[4][1] = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/harbor/red.png")));
            tbSprite[4][2] = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/harbor/blue.png")));

        } catch (Exception e) {
            System.err.println(e+"error can't find image sprite");
        }
    }

    /**
     * It paints a harbor that needs to be painted on 2D graphics
     */
    public void paint(Harbor harbor, Graphics2D g2d){
        BufferedImage sprite = spriteChoice(harbor.getClass(),harbor.getColor());
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
        BufferedImage sprite = spriteChoice(boat.getClass(),boat.getColor());
        g2d.rotate(boat.getAngle(),(int)(boat.getPosition().getX()),(int)(boat.getPosition().getY()));
        g2d.setColor(colorChoice(boat.getColor()));
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
     * It paints a timer seaRoad that needs to be painted on 2D graphics
     */
    public void paint(SeaRoad seaRoad, Graphics2D g2d){
        BufferedImage sprite = spriteChoice(seaRoad.getBuyerHarbor().getClass(),seaRoad.getBuyerHarbor().getColor());
        g2d.setColor(Color.darkGray);
        g2d.setFont(new Font("time20",Font.PLAIN,20));
        if(sprite!=null)g2d.drawString(seaRoad.getStringTimer(),(int) (seaRoad.getBuyerHarbor().getPosition().getX())- (g2d.getFontMetrics().stringWidth(seaRoad.getStringTimer())/2), (int) (seaRoad.getBuyerHarbor().getPosition().getY())-(sprite.getHeight() / 2)-5);
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
     * Allows you to choose the sprite to displayed
     */
    private BufferedImage spriteChoice(Class<?> classType, String color){
        int i = 0;
        int j = 0;

        switch (classType.getName()) {
            case "maritime.engine.entity.boats.Standard" : {
                i=0 ;
                break;
            }
            case "maritime.engine.entity.boats.Fodder" :{
                i=1;
                break;
            }
            case "maritime.engine.entity.boats.Merchant" :{
                i=2;
                break;
            }
            case "maritime.engine.entity.boats.Military" :{
                i=3;
                break;
            }
            case "maritime.engine.entity.Harbor" :{
                i=4;
                break;
            }
            default : {
            }
        }
        switch (color) {
            case "red" :{
                j=1;
                break;
            }
            case "blue" :{
                j=2;
                break;
            }
            default : {
                j=0;
                break;
            }
        }
        return tbSprite[i][j];
    }

    /**
     * Allows you to choose the color to displayed
     */
    private Color colorChoice(String color){
        switch (color) {
            case "red" :{
                return new Color(255,0,0,75);
            }
            case "blue" :{
                return new Color(0,0,255,75);
            }
            default : {
                return new Color(0,0,0,0);
            }
        }
    }

}