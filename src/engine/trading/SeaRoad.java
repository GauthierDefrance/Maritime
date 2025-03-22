package engine.trading;

import config.GameConfiguration;
import engine.entity.Harbor;
import engine.entity.boats.Boat;
import engine.entity.boats.Fleet;
import engine.graph.GraphPoint;
import engine.utilities.SearchInGraph;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Kenan Ammad
 * @version 0.2
 */
public class SeaRoad implements Serializable {
    private String name;
    private int timer;
    private ArrayList<GraphPoint> path;
    private Fleet fleet;
    private final Harbor sellerHarbor;
    private final Harbor targetHarbor;
    private TradeObject interlocutorObject;
    private TradeObject proposerObject;
    private final double ratio;

    public SeaRoad(int timer, Harbor sellerHarbor, Harbor targetHarbor, Resource interlocutorObject, Resource proposerObject, double ratio, String name){
        this.timer = timer;
        this.interlocutorObject = interlocutorObject;
        this.proposerObject = proposerObject;
        this.ratio = ratio;
        this.path = SearchInGraph.findPath(sellerHarbor.getGraphPosition(), targetHarbor.getGraphPosition());
        this.fleet = new Fleet();
        this.sellerHarbor = sellerHarbor;
        this.targetHarbor = targetHarbor;
        this.name = name;
    }

    public SeaRoad(int timer, Harbor sellerHarbor, Harbor targetHarbor, ArrayList<GraphPoint> path, Resource interlocutorObject, Resource proposerObject, double ratio, String name){
        this.timer = timer;
        this.path = path;
        this.interlocutorObject = interlocutorObject;
        this.proposerObject = proposerObject;
        this.ratio = ratio;
        this.fleet = new Fleet();
        this.sellerHarbor = sellerHarbor;
        this.targetHarbor = targetHarbor;
        this.name = name;
    }

    public SeaRoad(TradeOffer offer, double ratio, String name){
        this.name = name;
        this.sellerHarbor = offer.getStartingHarbor();
        this.targetHarbor = offer.getTargetedHarbor();
        for (TradeObject object : offer.getSelection().keySet())
            this.proposerObject = object;
        for (TradeObject object : offer.getDemand().keySet())
            this.interlocutorObject = object;
        this.timer = Integer.MAX_VALUE;
        this.ratio = ratio;
        this.path = SearchInGraph.findPath(sellerHarbor.getGraphPosition(), targetHarbor.getGraphPosition());
        this.fleet = offer.getConcernedFleet();
    }

    //Getters

    public void setPath(ArrayList<GraphPoint> path) {this.path = path;}

    public ArrayList<GraphPoint> getPath() {return path;}

    public Harbor getSellerHarbor() {return sellerHarbor;}

    public Harbor getTargetHarbor() {return targetHarbor;}

    public double getRatio() { return ratio; }

    public TradeObject getProposedObject() { return proposerObject; }

    public TradeObject getInterlocutorObject() { return interlocutorObject; }

    public Fleet getFleet() {return fleet;}

    public String getName() {
        return name;
    }
    
    //Setters

    public void setFleet(Fleet fleet) { this.fleet = fleet; }

    public void removeFleet() { this.fleet = new Fleet(); }

    public void setFleet(Boat boat) {
        Fleet fleet = new Fleet();
        fleet.add(boat);
        this.fleet = fleet;
    }

    public void setName(String name) {
        this.name = name;
    }

    //Basic Time management behavior

    public void abandonTask() {this.timer = 0;}
    
    public void subtractTime(int nb) {this.timer -= nb;}
    
    public void addTime(int nb) {this.timer += nb;}
    
    public boolean available(){return timer > 0;}

    public String getStringTimer(){
        int time = (int) (this.timer*(((double) GameConfiguration.GAME_SPEED) /1000));
        return time/60+":"+time%60;
    }

}
