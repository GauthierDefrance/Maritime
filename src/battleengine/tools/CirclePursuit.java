package battleengine.tools;

import engine.entity.boats.Boat;

import java.awt.*;

public final class CirclePursuit {

    /**
     *
     * @param hunter
     * @param prey
     * @return
     */
    public static Point getPointToFollow(Boat hunter, Boat prey){

        // Streamliner ceci ??
        double x1 = hunter.getPosition().getX();
        double y1 = hunter.getPosition().getY();
        double x2 = prey.getPosition().getX();
        double y2 = prey.getPosition().getY();
        double angle = Math.atan2(y1 - y2, x1 - x2);
        // -----

        int SHOOT_DISTANCE = config.GameConfiguration.DEFAULT_SHOOT_DISTANCE;

        if (0 <= angle && angle < Math.PI/2) {
            return getBoatPointLeft(prey,SHOOT_DISTANCE);
        }
        else if (Math.PI/2 <= angle && angle < Math.PI) {
            return getBoatPointBehind(prey,SHOOT_DISTANCE);
        }

        else if (-Math.PI/2 <= angle && angle < 0 ) {
            return getBoatPointFront(prey,SHOOT_DISTANCE);
        }
        else {
            return getBoatPointRight(prey,SHOOT_DISTANCE);
        }
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
