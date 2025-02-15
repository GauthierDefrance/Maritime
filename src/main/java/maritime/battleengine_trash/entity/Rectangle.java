package maritime.battleengine_trash.entity;

import java.awt.*;

//SAT THEOREM
public class Rectangle {
    private Point center;
    private int Half_Height;
    private int Half_Width;
    private double angle;

    public Rectangle(Point center, int Half_Length, int Half_Width, double angle) {
        this.center = center;
        this.Half_Height = Half_Height;
        this.Half_Width = Half_Width;
        this.angle = angle;
    }

    public Point getCenter() {return center;}
    public int getHalf_Height() {return Half_Height;}
    public int getHalf_Width() {return Half_Width;}
    public double getAngle() {return angle;}


    private Point rotatePoint(int x, int y) {
        // Calcul de la nouvelle position après rotation autour du centre
        double cosA = Math.cos(this.angle);
        double sinA = Math.sin(this.angle);

        // Rotation autour du centre (cx, cy)
        int xRotated = (int) (center.x + (x - center.x) * cosA - (y - center.y) * sinA);
        int yRotated = (int) (center.y + (x - center.x) * sinA + (y - center.y) * cosA);

        return new Point(xRotated, yRotated);
    }


    public Point getTopLeft() {
        int x = center.x - Half_Width;
        int y = center.y - Half_Height;
        return rotatePoint(x, y);
    }

    public Point getTopRight() {
        int x = center.x + Half_Width;
        int y = center.y - Half_Height;
        return rotatePoint(x, y);
    }

    public Point getBottomLeft() {
        int x = center.x - Half_Width;
        int y = center.y + Half_Height;
        return rotatePoint(x, y);
    }

    public Point getBottomRight() {
        int x = center.x + Half_Width;
        int y = center.y + Half_Height;
        return rotatePoint(x, y);
    }


    public boolean intersects(Rectangle r) {
        return true;
    }


}
