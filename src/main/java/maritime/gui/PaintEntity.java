package maritime.gui;

import maritime.engine.entity.Boat;
import maritime.engine.entity.Entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Objects;

public class PaintEntity {
    private BufferedImage frameBoat;

    public PaintEntity(){
        try {
                frameBoat = (ImageIO.read(new File("D:\\github\\Maritime2\\src\\main\\resources\\pixil-frame-2.png")));
        } catch (Exception e) {
            System.err.println("rip");
        }
    }

    public void paint(Entity entity, Graphics2D g2d){
        System.out.println("lol-test");
    }

    public void paint(Boat boat, Graphics2D g2d){
        g2d.rotate(boat.getAngle(),(int)(boat.getPosition().getX()),(int)(boat.getPosition().getY()));
        g2d.drawImage(frameBoat,(int)(boat.getPosition().getX())-(frameBoat.getWidth() / 2) ,(int)(boat.getPosition().getY())-(frameBoat.getHeight() / 2) , null);
//        g2d.rotate(0);

    }
}