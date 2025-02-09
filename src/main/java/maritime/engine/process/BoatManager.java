package maritime.engine.process;

import maritime.config.GameInitFactory;
import maritime.engine.entity.boats.Boat;
import maritime.engine.graph.GraphPoint;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author @Kenan Ammad
 * Classe BoatManager
 */

public class BoatManager {

    private final GameInitFactory map;

    public BoatManager(GameInitFactory map) {
        this.map = map;
    }

    public void followThePath(Boat boat){
        if (!boat.getPath().isEmpty()){
            approachingToPoint(boat,boat.getPath().get(boat.getIPath()));
        }
    }

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

    public void weAreOnPoint(Boat boat){
        if (boat.getPath().size()>boat.getIPath()+1){boat.addIPath(1);}
        else {
            boat.setIPath(0);
            if (boat.getContinuePath()){Collections.reverse(boat.getPath());}
            else boat.getPath().clear();
        }
    }

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

}
