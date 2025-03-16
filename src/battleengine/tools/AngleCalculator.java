package battleengine.tools;

import engine.entity.boats.Boat;
import java.awt.Point;

public final class AngleCalculator {

    public static double calculateAngle(Point pointA, Point pointB){
        double x1 = pointA.getX();
        double y1 = pointA.getY();
        double x2 = pointB.getX();
        double y2 = pointB.getY();
        //Angle in RADIAN
        return Math.atan2(y1 - y2, x1 - x2);
    }

    public static double calculateAngle(Boat pointA, Point pointB) {
        return calculateAngle(pointA.getPosition(), pointB);
    }

    public static double calculateAngle(Point pointA, Boat pointB) {
        return calculateAngle(pointA, pointB.getPosition());
    }

    public static double calculateAngle(Boat pointA, Boat pointB) {
        return calculateAngle(pointA.getPosition(), pointB.getPosition());
    }

}
