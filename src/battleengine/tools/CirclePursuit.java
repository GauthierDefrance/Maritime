package battleengine.tools;

import battleengine.entity.Battle;
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
    public static Point getPointToFollow(Boat hunter, Boat prey , Battle battle){
        if(battle.getHunterPreyPointHashMap().get(hunter)!=null &&GameConfiguration.DEFAULT_SHOOT_DISTANCE * hunter.getVisionRadius()/2 < battle.getHunterPreyPointHashMap().get(hunter).distance(hunter.getPosition()))System.out.println("2");
        if(battle.getHunterPreyPointHashMap().get(hunter)!=null &&GameConfiguration.HITBOX_BOAT > battle.getHunterPreyPointHashMap().get(hunter).distance(hunter.getPosition()))System.out.println("3");


        if(battle.getHunterPreyPointHashMap().get(hunter)==null ||
                GameConfiguration.DEFAULT_SHOOT_DISTANCE * hunter.getVisionRadius()/2 < battle.getHunterPreyPointHashMap().get(hunter).distance(hunter.getPosition()) ||
                GameConfiguration.HITBOX_BOAT > battle.getHunterPreyPointHashMap().get(hunter).distance(hunter.getPosition())){
        int SHOOT_DISTANCE = (int) (GameConfiguration.DEFAULT_SHOOT_DISTANCE * hunter.getVisionRadius()/4);
        ArrayList<Point> PointToTest = new ArrayList<>();
        PointToTest.add(getBoatPointBehind(prey, SHOOT_DISTANCE));
        PointToTest.add(getBoatPointFront(prey, SHOOT_DISTANCE));
        PointToTest.add(getBoatPointRight(prey, SHOOT_DISTANCE));
        PointToTest.add(getBoatPointLeft(prey, SHOOT_DISTANCE));
        double minDistance = Double.MAX_VALUE;
        ArrayList<Point> validPoints = new ArrayList<>();
        for (Point p : PointToTest) {
            double distance = hunter.getPosition().distance(p);
            if (distance < minDistance) {
                minDistance = distance;
                validPoints.clear();
                validPoints.add(p);
            }
            else if (distance == minDistance){
                validPoints.add(p);
            }
        }
        if (!validPoints.isEmpty()) {
            if (validPoints.size() == 1) {
                return validPoints.get(0);
            }
            else {
                double championAngle = Double.MAX_VALUE;
                Point champion = null;
                for (Point p : validPoints) {
                    double tmpAngle = Math.abs(hunter.getAngle() - AngleCalculator.calculateAngle(hunter, p));
                    if (tmpAngle < championAngle && !battle.getHunterPreyPointHashMap().get(hunter).equals(p)) {
                        championAngle = tmpAngle;
                        champion = p;
                    }
                }
                battle.getHunterPreyPointHashMap().put(hunter,champion);
                return champion;
            }
        }
        }
        else return battle.getHunterPreyPointHashMap().get(hunter);
        return null;
    }

    public static Point getBoatPointFront(Boat boat,int SHOOT_DISTANCE){return getBoatPoint(boat,SHOOT_DISTANCE,0);}
    public static Point getBoatPointRight(Boat boat,int SHOOT_DISTANCE){return getBoatPoint(boat,SHOOT_DISTANCE,-Math.PI/2);}
    public static Point getBoatPointLeft(Boat boat,int SHOOT_DISTANCE){return getBoatPoint(boat,SHOOT_DISTANCE,Math.PI/2);}
    public static Point getBoatPointBehind(Boat boat,int SHOOT_DISTANCE){return getBoatPoint(boat,SHOOT_DISTANCE,Math.PI);}

//    public static Point getBoatPointFront(Boat boat,int SHOOT_DISTANCE){return getBoatPoint(boat,SHOOT_DISTANCE,0);}
//    public static Point getBoatPointRight(Boat boat,int SHOOT_DISTANCE){return getBoatPoint(boat,SHOOT_DISTANCE,-90);}
//    public static Point getBoatPointLeft(Boat boat,int SHOOT_DISTANCE){return getBoatPoint(boat,SHOOT_DISTANCE,90);}
//    public static Point getBoatPointBehind(Boat boat,int SHOOT_DISTANCE){return getBoatPoint(boat,SHOOT_DISTANCE,180);}

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
