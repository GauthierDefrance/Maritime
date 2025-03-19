package battleengine.tools;

import engine.entity.boats.Boat;
import java.awt.Point;

public final class AngleCalculator {

    /**
     * Angle PointB to PointA
     * @param origin
     * @param direction
     * @return
     */
    public static double calculateAngle(Point origin, Point direction){
        double x1 = origin.getX();
        double y1 = origin.getY();
        double x2 = direction.getX();
        double y2 = direction.getY();
        //Angle in RADIAN
        return Math.atan2(y2 - y1, x2 - x1);
    }

    public static double calculateDeltaAngle(Boat boat, Point direction){
        double angle = AngleCalculator.calculateAngle(boat.getPosition(), direction);
        double deltaAngle = angle - boat.getAngle();
        return Math.atan2(Math.sin(deltaAngle),Math.cos(deltaAngle));
    }

}
