package engine.data.trading;

import config.GameConfiguration;
import engine.data.entity.Harbor;
import engine.data.entity.boats.Boat;
import engine.data.Fleet;
import engine.data.faction.Faction;
import engine.data.graph.GraphPoint;
import engine.utilities.SearchInGraph;

import java.io.Serializable;
import java.util.AbstractMap;
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
    private AbstractMap.SimpleEntry<Resource, Integer> selection;
    private AbstractMap.SimpleEntry<Resource, Integer> demand;
    private final double ratio;

    public SeaRoad(String name,Harbor sellerHarbor,Harbor targetHarbor, Resource selectionResource,Resource demandResource,int selectionQuantity,int demandQuantity,int timer){
        this.name = name;
        this.sellerHarbor = sellerHarbor;
        this.targetHarbor = targetHarbor;
        this.fleet = new Fleet();
        this.selection = new AbstractMap.SimpleEntry<>(selectionResource,selectionQuantity);
        this.demand = new AbstractMap.SimpleEntry<>(demandResource,demandQuantity);
        this.timer = timer;
        this.ratio = (double) demand.getValue()/selection.getValue();
        this.path = SearchInGraph.findPath(sellerHarbor.getGraphPosition(), targetHarbor.getGraphPosition());
    }

    //Getters

    public void setPath(ArrayList<GraphPoint> path) {
        this.path = path;
    }

    public ArrayList<GraphPoint> getPath() {
        return path;
    }

    public Harbor getSellerHarbor() {
        return sellerHarbor;
    }

    public Harbor getTargetHarbor() {
        return targetHarbor;
    }

    public double getRatio() {
        return ratio;
    }

    public Fleet getFleet() {return fleet;}

    public String getName() {
        return name;
    }

    public int getTimer(){
        return timer;
    }

    
    //Setters

    public void setFleet(Fleet fleet) {
        this.fleet = fleet;
    }

    public void removeFleet() { this.fleet = new Fleet(); }

    public void setTime(int nb) {
        this.timer = nb;
    }

    public void setName(String name) {
        this.name = name;
    }

    //Basic Time management behavior

    public void abandonTask() {
        this.selection.setValue(-1);
        this.demand.setValue(-1);
    }
    
    public void subtractTime(int nb) {
        this.timer -= nb;
    }
    
    public void addTime(int nb) {
        this.timer += nb;
    }
    
    public boolean isActive(){
        return timer > 0 && (selection.getValue()>0 || demand.getValue()>0);
    }

    public String getStringTimer(){
        int time = (int) (this.timer*(((double) GameConfiguration.GAME_SPEED) /1000));
        return time/60+":"+time%60;
    }

    public AbstractMap.SimpleEntry<Resource, Integer> getDemand() {
        return demand;
    }

    public void setDemand(AbstractMap.SimpleEntry<Resource, Integer> demand) {
        this.demand = demand;
    }

    public AbstractMap.SimpleEntry<Resource, Integer> getSelection() {
        return selection;
    }

    public void setSelection(AbstractMap.SimpleEntry<Resource, Integer> selection) {
        this.selection = selection;
    }
}
