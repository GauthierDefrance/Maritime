package maritime.battleengine_trash.collision;

import java.awt.*;

public class Rectangle {
    private Point tl;
    private Point br;
    public Rectangle(Point topLeft, int width, int height) {
        this.tl = topLeft;
        this.br = new Point(topLeft.x+width, topLeft.y+height);
    }
    public Point getTopLeft() {
        return tl;
    }
    public Point getBottomRight() {
        return br;
    }

    public boolean contains(Point point) {
        return point.x > tl.getX() && point.x < br.getX() &&
               point.y > tl.getY() && point.y < br.getY();
    }
}
