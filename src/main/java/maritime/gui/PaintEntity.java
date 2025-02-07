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
    private BufferedImage frameBoatFodder;
    private BufferedImage frameBoatStandard;
    private BufferedImage frameBoatMerchant;
    private BufferedImage frameBoatMilitary;

    public PaintEntity(){
        try {
            frameBoatFodder = (ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/boat/fodder.png"))));
            frameBoatStandard = (ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/boat/standard.png"))));
            frameBoatMerchant = (ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/boat/merchant.png"))));
            frameBoatMilitary = (ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/boat/military.png"))));
        } catch (Exception e) {
            System.err.println("rip");
        }
    }

    public void paint(Harbor harbor, Graphics2D g2d){
        System.out.println("lol-test");
    }

    public void paint(Boat boat, Graphics2D g2d){
        g2d.rotate(boat.getAngle(),(int)(boat.getPosition().getX()),(int)(boat.getPosition().getY()));

        g2d.setColor(new Color(255,255,255, GameConfiguration.test));
        g2d.fillOval((int)(boat.getPosition().getX())-((int)boat.getVisionRadius()/2),(int)(boat.getPosition().getY())-((int)boat.getVisionRadius()/2), (int) boat.getVisionRadius(), (int) boat.getVisionRadius());
        g2d.setColor(Color.black);
        switch (boat) {
            case Fodder fodder -> {
                g2d.drawImage(frameBoatFodder, (int) (boat.getPosition().getX()) - (frameBoatFodder.getWidth() / 2), (int) (boat.getPosition().getY()) - (frameBoatFodder.getHeight() / 2), null);
            }
            case Standard standard ->{
                    g2d.drawImage(frameBoatStandard, (int) (boat.getPosition().getX()) - (frameBoatStandard.getWidth() / 2), (int) (boat.getPosition().getY()) - (frameBoatStandard.getHeight() / 2), null);
            }
            case Merchant merchant ->{
                    g2d.drawImage(frameBoatMerchant, (int) (boat.getPosition().getX()) - (frameBoatMerchant.getWidth() / 2), (int) (boat.getPosition().getY()) - (frameBoatMerchant.getHeight() / 2), null);
            }
            case Military military ->{
                    g2d.drawImage(frameBoatMilitary, (int) (boat.getPosition().getX()) - (frameBoatMilitary.getWidth() / 2), (int) (boat.getPosition().getY()) - (frameBoatMilitary.getHeight() / 2), null);
            }
            default -> {
                g2d.setColor(Color.MAGENTA);
                g2d.fillOval((int)(boat.getPosition().getX())-10,(int)(boat.getPosition().getY())-10, 20, 20);
                g2d.setColor(Color.black);
            }
        }

        g2d.rotate(-boat.getAngle(),(int)(boat.getPosition().getX()),(int)(boat.getPosition().getY()));

    }
}