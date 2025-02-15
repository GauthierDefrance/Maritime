package maritime.battleengine_trash.entity;

import java.awt.*;
import java.awt.geom.Rectangle2D;


/**
 * @author @Gauthier Defrance
 * @version 0.1
 */
public class Hitbox {
    public Rectangle2D rect;
    public int angle;

    public Hitbox(int x, int y, int width, int height, int angle) {
        this.rect = new Rectangle2D.Double(x, y, width, height);
        this.angle = angle;
    }

    public boolean intersects(Hitbox hitbox) {


    }


}
