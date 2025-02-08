package maritime.gui;

import maritime.config.GameConfiguration;
import maritime.engine.entity.*;
import maritime.engine.entity.boats.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

/**
 * @author @Kenan Ammad
 * Classe PaintEntity
 */
public class PaintEntity {
    private BufferedImage[][] tbSprite;

    public PaintEntity(){
        tbSprite = new BufferedImage[4][4];
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

        } catch (Exception e) {
            System.err.println(e+"error can't find image sprite");
        }
    }

    public void paint(Harbor harbor, Graphics2D g2d){
        System.out.println("lol-test");
    }

    public void paint(Boat boat, Graphics2D g2d){
        BufferedImage sprite = spriteChoice(boat,boat.getColor());
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
    public void paintPlayer(Boat boat, Graphics2D g2d){
        g2d.rotate(boat.getAngle(),(int)(boat.getPosition().getX()),(int)(boat.getPosition().getY()));
        g2d.setColor(new Color(255,255,255, GameConfiguration.Transparency_Halo));
        g2d.fillOval((int)(boat.getPosition().getX())-((int)boat.getVisionRadius()/2),(int)(boat.getPosition().getY())-((int)boat.getVisionRadius()/2), (int) boat.getVisionRadius(), (int) boat.getVisionRadius());
        g2d.setColor(Color.black);
        g2d.rotate(-boat.getAngle(),(int)(boat.getPosition().getX()),(int)(boat.getPosition().getY()));
        paint(boat,g2d);
    }

    private BufferedImage spriteChoice(Boat boat,String color){
        int i = 0;
        int j = 0;
        switch (boat) {
            case Standard standard -> {
                i=0 ;
            }
            case Fodder fodder ->{
                i=1;
            }
            case Merchant merchant ->{
                i=2;
            }
            case Military military ->{
                i=3;
            }
            default -> {
            }
        }
        switch (color) {
            case "red" ->{
                j=1;
            }
            case "blue" ->{
                j=2;
            }
            default -> {
                j=0;
            }
        }
        return tbSprite[i][j];
    }
    private Color colorChoice(String color){
        switch (color) {
            case "red" ->{
                return new Color(255,0,0,75);
            }
            case "blue" ->{
                return new Color(0,0,255,75);
            }
            default -> {
                return new Color(0,0,0,0);
            }
        }
    }

}