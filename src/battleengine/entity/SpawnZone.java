package battleengine.entity;

import config.GameConfiguration;
import engine.entity.boats.Boat;
import engine.entity.boats.Fleet;

import java.awt.*;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;

/**
 * Classe servant à la représentation de zones de spawn
 * sert également au placement correct des bateaux
 * @author Gauthier Defrance
 * @version 0.3
 */
public class SpawnZone extends Dimension2D {
    private String color;
    private Point2D positiontl;
    private Point2D positionbr;
    private int width;
    private int height;
    private Fleet placedList;

    public SpawnZone(Point topLeft, int width, int height, String color, Fleet BoatsCurrentlyBeingPlaced) {
        super();
        this.setSize(width, height);
        this.positiontl = topLeft;
        this.positionbr = new Point(topLeft.x+width, topLeft.y+height);
        this.color = color;
        this.placedList = BoatsCurrentlyBeingPlaced;
    }

    public boolean isPlaceable(Boat boat) {
        if (!contains(boat.getPosition())) {
            return false;
        }
        for (Boat placedBoat : placedList.getArrayListBoat()) {
            if (boat.getPosition().distance(placedBoat.getPosition()) < GameConfiguration.HITBOX_BOAT + 10) {
                return false;
            }
        }
        return true;
    }

    public Fleet getPlacedList() {
        return placedList;
    }

    public boolean contains(Point point) {
        return point.x > positiontl.getX() && point.x < positionbr.getX() &&
                point.y > positiontl.getY() && point.y < positionbr.getY();
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double getHeight() {
        return height;
    }

    @Override
    public void setSize(double width, double height) {
        this.width = (int) width;
        this.height = (int) height;
    }

}
