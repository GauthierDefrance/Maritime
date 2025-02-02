package maritime.gui;

import maritime.engine.entity.Boat;
import maritime.engine.entity.Entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class PaintEntity {
    private BufferedImage frame;

    public PaintEntity(){
        try {
                frame = (ImageIO.read(Objects.requireNonNull(getClass().getResource("pixil-frame-2.png"))));
        } catch (Exception e) {
            System.err.println("rip");
        }
    }

    public void paint(Entity entity, Graphics2D g2d){
        System.out.println("lol-test");
    }

    public void paint(Boat boat, Graphics2D g2d){
        g2d.drawImage(frame,(int)(boat.getPosition().getX())-(frame.getWidth() / 2) ,(int)(boat.getPosition().getY())-(frame.getHeight() / 2) , null);

    }
}