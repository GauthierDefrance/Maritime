package maritime.engine.process;

import maritime.config.MapBuilder;
import maritime.engine.entity.Harbor;
import maritime.engine.entity.boats.Boat;
import maritime.engine.graph.GraphPoint;

import java.util.Collections;

/**
 * @author @Kenan Ammad
 * Classe BoatManager
 * @version 0.2
 */
public class BoatManager {

    private final MapBuilder map;

    /**
     * Typical builder generating an BoatManager
     */
    public BoatManager(MapBuilder map) {
        this.map = map;
    }

    /**
     * Take a boat and make it follow its path, don't do anything if the path is empty
     */
    public void followThePath(Boat boat){
        if (!boat.getPath().isEmpty()){
            approachingToPoint(boat,boat.getPath().get(boat.getIPath()));
        }
    }
    /**
     * Take a boat and make it follow the point
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
     * Take a boat and update its Path if we are at the end of the path returns it revers the path if continuePath is true otherwise makes it empty
     */
    private void weAreOnPoint(Boat boat){
        boat.setGraphPoint1(boat.getGraphPoint2());
        if (boat.getPath().size()>boat.getIPath()+1){
            boat.addIPath(1);
            boat.setGraphPoint2(boat.getPath().get(boat.getIPath()));
        }
        else {
            boat.setIPath(0);
            if (boat.getContinuePath())Collections.reverse(boat.getPath());
            else boat.getPath().clear();
        }
    }

    /**
     * Take a boat and Change its position
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

}
