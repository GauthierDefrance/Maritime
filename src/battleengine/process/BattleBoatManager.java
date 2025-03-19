package battleengine.process;

import battleengine.entity.Battle;
import battleengine.tools.AngleCalculator;
import config.GameConfiguration;
import engine.entity.boats.Boat;
import engine.entity.boats.Fleet;
import engine.graph.GraphPoint;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class the manage the movement and operations of the {@link Boat}
 * @author Gauthier Defrance
 * @version 0.3
 */
public class BattleBoatManager {

    private final Battle battle;

    /**
     * Constructor of the BattleBoatManager.
     * @param battle {@link Battle} the data class
     */
    public BattleBoatManager(Battle battle) {
        this.battle = battle;
    }


    /**
     * Method that "tick" the BattleBoatManager,
     * moving the boats and actualizing the angle.
     */
    public void tick(){
        actualizeFleet(this.battle.getBoatsInBattleA());
//      actualizeFleet(this.battle.getBoatsInBattleB());
    }


    /**
     * Method that actualize the angle of all the {@link Boat} of a {@link Fleet}.
     * If it was given a position to move to, it will turn in direction of the point.
     * Else if it has a prey, it will follow the prey.
     * @param fleet A {@link Fleet} object
     */
    public void actualizeFleet(Fleet fleet){
        HashMap<Boat,Boat> HunterPreyHashMap = this.battle.getHunterPreyHashMap();
        Boat prey;
        for(Boat hunter : fleet.getArrayListBoat()){
            if(hunter.getNextGraphPoint()!=null){
                if(2*hunter.getSpeed()>hunter.getPosition().distance(hunter.getNextGraphPoint().getPoint())){
                    hunter.setNextGraphPoint(null);
                }
                else{
                    //Si un point précis a été sélectionné par le joueur
                    turnBoat(hunter, hunter.getNextGraphPoint().getPoint());
                }
            }
            else {
                //Sinon, on essaye de traquer une proie si elle existe
                prey = HunterPreyHashMap.get(hunter);
                if(prey!=null){
                    turnBoat(hunter, getPointToFollow(hunter, prey));
                }
            }
        }
    }

    /**
     * Method that turn a boat with his turn speed in direction of a given point.
     * @param boat A {@link Boat} object
     * @param point A {@link Point} object
     */
    private void turnBoat(Boat boat, Point point){
        double angle = AngleCalculator.calculateAngle(boat.getPosition(), point);
        double deltaAngle = angle - boat.getAngle();
        deltaAngle = (deltaAngle + Math.PI) % (2 * Math.PI) - Math.PI;

        if (Math.abs(deltaAngle) < GameConfiguration.BOAT_ROTATION_SPEED) {
            boat.setAngle(angle);
        } else if (deltaAngle > 0) {
            boat.setAngle(boat.getAngle() + GameConfiguration.BOAT_ROTATION_SPEED);
        } else {
            boat.setAngle(boat.getAngle() - GameConfiguration.BOAT_ROTATION_SPEED);
        }
        int x = (int) ((Math.cos(boat.getAngle())*boat.getSpeed()) + boat.getPosition().getX());
        int y = (int) ((Math.sin(boat.getAngle())*boat.getSpeed()) + boat.getPosition().getY());
        boat.setPosition(x,y);
    }

    public static void setBoatDirection(Boat boat, Point point){
        boat.setNextGraphPoint(new GraphPoint(point,null));
    }

    private Point getPointToFollow(Boat hunter, Boat prey){
        Point champion = null;
        int SHOOT_DISTANCE = (int) (GameConfiguration.DEFAULT_SHOOT_DISTANCE * hunter.getVisionRadius()/3);
        ArrayList<Point> PointToTest = new ArrayList<>();
        PointToTest.add(getBoatPointBehind(prey, SHOOT_DISTANCE));
        PointToTest.add(getBoatPointFront(prey, SHOOT_DISTANCE));
        PointToTest.add(getBoatPointRight(prey, SHOOT_DISTANCE));
        PointToTest.add(getBoatPointLeft(prey, SHOOT_DISTANCE));
        if(battle.getHunterPreyPointHashMap().get(hunter)!=null)PointToTest.remove(battle.getHunterPreyPointHashMap().get(hunter));
        double minDistance = Double.MAX_VALUE;
        ArrayList<Point> validPoints = new ArrayList<>();
        for (Point p : PointToTest) {
            double distance = hunter.getPosition().distance(p);
            if (distance < minDistance -GameConfiguration.HITBOX_BOAT) {
                minDistance = distance;
                validPoints.clear();
                validPoints.add(p);
            }
            else if(distance < minDistance +GameConfiguration.HITBOX_BOAT){
                if(distance < minDistance)minDistance = distance;
                validPoints.add(p);
            }
        }
        if (!validPoints.isEmpty()) {
            double championAngle = Double.MAX_VALUE;
            for (Point p : validPoints) {
                double tmpAngle = Math.abs((hunter.getAngle()%Math.PI) - AngleCalculator.calculateAngle(hunter, p));
                tmpAngle = (tmpAngle + Math.PI) % (2 * Math.PI) - Math.PI;
                if (tmpAngle < championAngle ) {
                    championAngle = tmpAngle;
                    champion = p;
                }
            }
        }
        for (Point p : PointToTest) {
            if(GameConfiguration.HITBOX_BOAT > p.distance(hunter.getPosition())){
                battle.getHunterPreyPointHashMap().put(hunter,p);
            }
        }
        return champion;
    }

    public static Point getBoatPointFront(Boat boat,int SHOOT_DISTANCE){return getBoatPoint(boat,SHOOT_DISTANCE,0);}
    public static Point getBoatPointRight(Boat boat,int SHOOT_DISTANCE){return getBoatPoint(boat,SHOOT_DISTANCE,-Math.PI/2);}
    public static Point getBoatPointLeft(Boat boat,int SHOOT_DISTANCE){return getBoatPoint(boat,SHOOT_DISTANCE,Math.PI/2);}
    public static Point getBoatPointBehind(Boat boat,int SHOOT_DISTANCE){return getBoatPoint(boat,SHOOT_DISTANCE,Math.PI);}

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
