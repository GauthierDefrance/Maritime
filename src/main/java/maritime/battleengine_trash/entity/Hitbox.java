package maritime.battleengine_trash.entity;

import java.awt.*;

public class Hitbox {
    public int width;
    public int height;
    public Point cornerTopLeft;
    public Hitbox(int w, int h, Point cornerTopLeft) {
        width = w;
        height = h;
    }

    /**
     * Méthode vérifiant si une Hitbox contient un objet point.
     * @param p
     * @return Boolean
     */
    public boolean contains(Point p) {
        double xo =  p.getX();
        double yo =  p.getY();

        double x = cornerTopLeft.getX();
        double y = cornerTopLeft.getY();

        return x< xo && xo < x+width &&
                y< yo && yo < y+height;
    }

    /**
     * Méthode vérifiant si une Hitbox est en collision avec une autre hitbox.
     * @param other
     * @return Boolean
     */
    public boolean collide(Hitbox other) {
        double x1 = cornerTopLeft.getX();
        double y1 = cornerTopLeft.getY();
        double x2 = other.cornerTopLeft.getX();
        double y2 = other.cornerTopLeft.getY();

        return (x1 + width > x2 && x2 + other.width > x1 &&
                y1 + height > y2 && y2 + other.height > y1);
    }

    /**
     * Méthode vérifiant si une Hitbox et une autre sont égals.
     * @param other
     * @return Boolean
     */
    public boolean equals(Hitbox other) {
        return width == other.width && height == other.height && cornerTopLeft.equals(other.cornerTopLeft);
    }

}
