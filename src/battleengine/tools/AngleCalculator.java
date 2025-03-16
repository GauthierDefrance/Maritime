package battleengine.tools;

import engine.entity.boats.Boat;
import java.awt.Point;

public final class AngleCalculator {


    /**
     * Take the boat Origin and rotate it in the point of the point Direction
     * @param origin
     * @param direction
     */
    public static void rotateBoatCalculatedAngle(Boat origin, Point direction){
        origin.setAngle(calculateAngle(origin, direction));
    }

    /**
     * Angle PointB to PointA
     * @param direction
     * @param origin
     * @return
     */
    public static double calculateAngle(Point origin, Point direction){
        double x1 = direction.getX();
        double y1 = direction.getY();
        double x2 = origin.getX();
        double y2 = origin.getY();
        //Angle in RADIAN
        return Math.atan2(y1 - y2, x1 - x2);
    }

    public static double calculateAngle(Boat origin, Point direction) {
        return calculateAngle(origin.getPosition(), direction);
    }

    public static double calculateAngle(Point origin, Boat direction) {
        return calculateAngle(origin, direction.getPosition());
    }

    public static double calculateAngle(Boat origin, Boat direction) {
        return calculateAngle(origin.getPosition(), direction.getPosition());
    }

}
