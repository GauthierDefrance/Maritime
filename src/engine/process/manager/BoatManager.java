package engine.process.manager;

import config.GameConfiguration;
import engine.MapGame;
import engine.data.entity.Harbor;
import engine.data.entity.boats.Boat;
import engine.data.faction.Faction;
import engine.data.graph.GraphPoint;

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


    /**
     * Typical constructor generating an BoatManager
     */
    public BoatManager() {}

    /**
     * If a given boat has a path to follow, make sure it does
     * @param boat targeted boat
     */
    public void followThePath(Boat boat){
        if (!boat.getPath().isEmpty()){
            Harbor harbor = FactionManager.getInstance().getMyHarbor(boat);
            if(harbor != null && harbor.getHashMapBoat().get(boat)){
                FactionManager.getInstance().getHarborManager().removeBoatInHarbor(harbor,boat);
            }
            else approachingToPoint(boat,boat.getPath().get(boat.getIPath()));
        }
    }

    /**
     * Take a boat and make it follow the point
     * @param boat targeted boat
     */
    public void approachingToPoint(Boat boat, GraphPoint point){
        int x1 = point.getX();
        int y1 = point.getY();
        double x2 = boat.getPosition().getX();
        double y2 = boat.getPosition().getY();
        double distance = boat.getPosition().distance(point.getPoint());

        if (distance < boat.getSpeed()) {
            moveTo(x1,y1,boat);
            weAreOnPoint(boat);
        }
        else {
            boat.setAngle(Math.atan2(y1 - y2, x1 - x2));
            moveTo((int) Math.round(x2 + Math.cos(boat.getAngle()) * boat.getSpeed()), (int) Math.round(y2 + Math.sin(boat.getAngle()) * boat.getSpeed()), boat);
        }
    }

    /**
     * Update a boat path if we reached the end of the previous one, simply reverses it if continuePath is true otherwise makes it empty
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
            if (boat.getContinuePath()) Collections.reverse(boat.getPath());
            else boat.clearPath();
        }
    }

    /**
     * Take a boat and Change its position
     * @param x coordinate x
     * @param y coordinate y
     * @param boat targeted boat
     */
    public void moveTo(int x,int y, Boat boat){
        boat.setPosition(x,y);
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
     * Check if a lstBoat reached a point
     * @param point concerned point
     * @param lstBoat lst of boat
     * @return lstBoat lst of the Collision true boat
     */
    public static Boat boatCollisionToPoint (Point point, ArrayList<Boat> lstBoat){
        for (Boat boat : lstBoat){
            if (GameConfiguration.HITBOX_BOAT/2 - 5 >= boat.getPosition().distance(point)){
                return boat;
            }
        }
        return null;
    }

    /**
     * Check if a boat reached a targeted boat in map
     * @param point concerned
     * @return Boat
     */
    public Boat pointCollisionToMapBoat (Point point){
        Boat boat1 = null;
        for (Faction faction : MapGame.getInstance().getLstFaction()){
            for (Boat boat2 : faction.getLstBoat()){
                if (GameConfiguration.HITBOX_BOAT/2 >= point.distance(boat2.getPosition())){
                    if(boat1 != null){
                        if(point.distance(boat2.getPosition())<point.distance(boat1.getPosition())){
                            boat1 = boat2;
                        }
                    }
                    else boat1 = boat2;
                }
            }
        }
        return boat1;
    }

}
