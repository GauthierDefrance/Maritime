package engine.process;

import config.GameConfiguration;
import config.MapBuilder;
import engine.entity.Harbor;
import engine.entity.boats.Boat;
import engine.entity.boats.Fleet;
import engine.faction.Faction;
import engine.graph.GraphPoint;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Kenan Ammad
 * @author Zue Jack-Arthur
 * Classe BoatManager
 * @version 0.3
 */
public class BoatManager {

    private final MapBuilder map;

    /**
     * Typical constructor generating an BoatManager
     */
    public BoatManager(MapBuilder map) {
        this.map = map;
    }

    /**
     * If a given boat has a path to follow, make sure it does
     * @param boat targeted boat
     */
    public void followThePath(Boat boat){
        if (!boat.getPath().isEmpty()){
            approachingToPoint(boat,boat.getPath().get(boat.getIPath()));
        }
    }

    /**
     * Take a boat and make it follow the point
     * @param boat targeted boat
     */
    public void approachingToPoint(Boat boat, GraphPoint point){
        double x1 = point.getX();
        double y1 = point.getY();
        double x2 = boat.getPosition().getX();
        double y2 = boat.getPosition().getY();
        double distance = boat.getPosition().distance(point.getPoint());

        if (distance < boat.getSpeed()) {
            moveTo(x1,y1,boat);
            weAreOnPoint(boat);
        }//distance < speed, on se déplace sur le point visé
        else {
            boat.setAngle(Math.atan2(y1 - y2, x1 - x2)); //calcul avec ArcTan la position cible
            moveTo(Math.round(x2 + Math.cos(boat.getAngle()) * boat.getSpeed()), Math.round(y2 + Math.sin(boat.getAngle()) * boat.getSpeed()), boat);// Sinon, on se déplace en direction de notre point grâce aux formules de trigonometrie
        }
    }

    /**
     *Update a boat path if we reached the end of the previous one, simply reverses it if continuePath is true otherwise makes it empty
     * @param boat targeted boat
     */
    private void weAreOnPoint(Boat boat){
        boat.setOldGraphPoint(boat.getNextGraphPoint());
        if (boat.getPath().size()>boat.getIPath()+1){
            boat.addIPath(1);
            boat.setNextGraphPoint(boat.getPath().get(boat.getIPath()));
        }
        else {
            boat.setIPath(0);
            if (boat.getContinuePath())Collections.reverse(boat.getPath());
            else boat.getPath().clear();
        }
    }

    /**
     * Take a boat and Change its position
     * @param x coordinate x
     * @param y coordinate y
     * @param boat targeted boat
     */
    public void moveTo(double x,double y,Boat boat){
        boat.setPosition(x,y);
    }

    public boolean reduceHp(int value,Boat boat) {
        if (value < boat.getCurrentHp()){
            boat.addCurrentHp(-value);
            return true;
        }
        else {
            boat.setCurrentHp(0);
            return false;
        }
    }

    /**
     * Check if a boat reached a targeted harbor
     * @param boat concerned boat
     * @param harbor targeted harbor
     * @return result of the check
     */
    public boolean HarborReached (Boat boat, Harbor harbor){
        return boat.getPosition().equals(harbor.getPreciseGraphPosition());
    }

    /**
     * Check if a boat1 reached a targeted boat2
     * @param boat1 concerned boat
     * @param boat2 targeted boat
     * @return result of the check
     */
    public static boolean boatCollisionToBoat (Boat boat1, Boat boat2){
        return GameConfiguration.HITBOX_BOAT - 5 >= boat1.getPosition().distance(boat2.getPosition());
    }

    /**
     * Check if a boat1 reached a targeted in lstBoat
     * @param boat1 concerned boat
     * @param lstBoat lst of targeted boat
     * @return result of the check
     */
    public static boolean boatCollisionToBoat (Boat boat1, ArrayList<Boat> lstBoat){
        for (Boat boat2 : lstBoat){
            if (GameConfiguration.HITBOX_BOAT - 5 >= boat1.getPosition().distance(boat2.getPosition())){
                return true;
            }
        }
        return false;
    }

    /**
     * Check if a boat1 reached a targeted point
     * @param boat concerned boat
     * @param point targeted point
     * @return result of the check
     */
    public static boolean boatCollisionToPoint (Boat boat, Point point){
        return GameConfiguration.HITBOX_BOAT - 5 >= boat.getPosition().distance(point);
    }

    /**
     * Check if a boat1 reached a point targeted in lstPoint
     * @param boat concerned boat
     * @param lstPoint lst of targeted point
     * @return result of the check
     */
    public static boolean boatCollisionToPoint (Boat boat, ArrayList<Point> lstPoint){
        for (Point point : lstPoint){
            if (GameConfiguration.HITBOX_BOAT - 5 >= boat.getPosition().distance(point)){
                return true;
            }
        }
        return false;
    }

    /**
     * Check if a boat reached a targeted boat in map
     * @param boat concerned boat
     * @return result of the check
     */
    public boolean boatCollisionToMap (Boat boat){
        for (Faction faction : map.getLstFaction()){
            for (Boat boat2 : faction.getLstBoat()){
                if (GameConfiguration.HITBOX_BOAT - 5 >= boat.getPosition().distance(boat2.getPosition())){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Check the collision beetween a Fleet of boats and a point.
     * @param p
     * @param fleet
     * @return
     */
    public static Fleet collideGroup(Point p, Fleet fleet){
        Fleet result = new Fleet();
        for(Boat boat : fleet.getArrayListBoat()){
            if(collide(p, boat)){
                result.add(boat);
            }
        }
        return result;
    }

    /**
     * Check the collision beetween a boat and a point.
     * @param p
     * @param boat
     * @return
     */
    public static Boolean collide(Point p, Boat boat){
        if (boat.getPosition().distance(p)< GameConfiguration.HITBOX_BOAT/2 ) {
            return true;
        } return false;
    }

}
