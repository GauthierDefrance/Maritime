package battleengine.tools;

import config.GameConfiguration;
import engine.entity.boats.Boat;

import java.awt.*;
import java.util.ArrayList;

public final class CirclePursuit {

    /**
     *
     * @param hunter
     * @param prey
     * @return
     */
    public static Point getPointToFollow(Boat hunter, Boat prey){
        double angle = AngleCalculator.calculateAngle(hunter, prey);
        int SHOOT_DISTANCE = (int) (GameConfiguration.DEFAULT_SHOOT_DISTANCE * hunter.getVisionRadius()/2);
        ArrayList<Point> PointToTest = new ArrayList<>();
        PointToTest.add(getBoatPointBehind(prey, SHOOT_DISTANCE));
        PointToTest.add(getBoatPointFront(prey, SHOOT_DISTANCE));
        PointToTest.add(getBoatPointRight(prey, SHOOT_DISTANCE));
        PointToTest.add(getBoatPointLeft(prey, SHOOT_DISTANCE));

        Point closestPoint = null;
        double minDistance = Double.MAX_VALUE;

        for (Point p : PointToTest) {
            double distance = hunter.getPosition().distance(p);
            if (distance < minDistance) {
                minDistance = distance;
                closestPoint = p;
            }
        }
        if (closestPoint != null) {
            ArrayList<Point> validPoints = new ArrayList<>();
            validPoints.add(closestPoint);
            for (Point p : PointToTest) {
                if (!p.equals(closestPoint) && hunter.getPosition().distance(p) == minDistance) {
                    validPoints.add(p);
                }
            }

            if (validPoints.size() == 1) {
                return validPoints.get(0);
            } else if (validPoints.size() > 1) {
                double championAngle = Double.MAX_VALUE;
                Point champion = null;
                for (Point p : validPoints) {
                    double tmpAngle = Math.abs(hunter.getAngle() - AngleCalculator.calculateAngle(hunter, p));
                    if (tmpAngle < championAngle) {
                        championAngle = tmpAngle;
                        champion = p;
                    }
                }
                return champion;
            }
        }

        return null;
    }

    public static Point getBoatPointFront(Boat boat,int SHOOT_DISTANCE){return getBoatPoint(boat,SHOOT_DISTANCE,0);}
    public static Point getBoatPointRight(Boat boat,int SHOOT_DISTANCE){return getBoatPoint(boat,SHOOT_DISTANCE,-90);}
    public static Point getBoatPointLeft(Boat boat,int SHOOT_DISTANCE){return getBoatPoint(boat,SHOOT_DISTANCE,90);}
    public static Point getBoatPointBehind(Boat boat,int SHOOT_DISTANCE){return getBoatPoint(boat,SHOOT_DISTANCE,180);}

    /**
     *
     * @param boat
     * @param SHOOT_DISTANCE
     * @param BONUS_ANGLE
     * @return
     */
    public static Point getBoatPoint(Boat boat,int SHOOT_DISTANCE, double BONUS_ANGLE){
        Point tmpPoint = boat.getPosition();
        int x= (int) Math.round( Math.cos(boat.getAngle() + BONUS_ANGLE )*SHOOT_DISTANCE + tmpPoint.getX());
        int y= (int) Math.round( Math.sin(boat.getAngle() + BONUS_ANGLE )*SHOOT_DISTANCE + tmpPoint.getY());
        return new Point(x, y);
    }



}
