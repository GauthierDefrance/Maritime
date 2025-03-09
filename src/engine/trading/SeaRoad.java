package engine.trading;

import config.GameConfiguration;
import engine.entity.Harbor;
import engine.entity.boats.Boat;
import engine.entity.boats.Fleet;
import engine.graph.GraphPoint;
import engine.graph.SearchInGraph;

import java.util.ArrayList;

/**
 * @author Kenan Ammad
 * @version 0.1
 */
public class SeaRoad {
    private int timer;
    private ArrayList<GraphPoint> path;
    private Fleet fleet;
    private final Harbor sellerHarbor;
    private final Harbor buyerHarbor;
    private final Resource buyResource;
    private final Resource soldResource;
    private final double ratio;

    public SeaRoad(int timer, Harbor sellerHarbor, Harbor buyerHarbor, Resource buyResource, Resource soldResource, double ratio){
        this.timer = timer;
        this.buyResource = buyResource;
        this.soldResource = soldResource;
        this.ratio = ratio;
        this.path = SearchInGraph.findPath(sellerHarbor.getGraphPosition(), buyerHarbor.getGraphPosition());
        this.fleet = new Fleet();
        this.sellerHarbor = sellerHarbor;
        this.buyerHarbor = buyerHarbor;
    }

    public SeaRoad(int timer, Harbor sellerHarbor, Harbor buyerHarbor, ArrayList<GraphPoint> path, Resource buyResource, Resource soldResource, double ratio){
        this.timer = timer;
        this.path = path;
        this.buyResource = buyResource;
        this.soldResource = soldResource;
        this.ratio = ratio;
        this.fleet = new Fleet();
        this.sellerHarbor = sellerHarbor;
        this.buyerHarbor = buyerHarbor;
    }

    //Getters

    public void setPath(ArrayList<GraphPoint> path) {this.path = path;}

    public ArrayList<GraphPoint> getPath() {return path;}

    public Harbor getSellerHarbor() {return sellerHarbor;}

    public Harbor getBuyerHarbor() {return buyerHarbor;}

    public double getRatio() { return ratio; }

    public Resource getSoldResource() { return soldResource; }

    public Resource getBuyResource() { return buyResource; }

    public Fleet getFleet() {return fleet;}
    
    //Setters

    public void setFleet(Fleet fleet) { this.fleet = fleet; }

    public void removeFleet() { this.fleet = new Fleet(); }

    public void setFleet(Boat boat) {
        Fleet fleet = new Fleet();
        fleet.add(boat);
        this.fleet = fleet;
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
