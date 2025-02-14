package maritime.engine.trading;

import maritime.engine.entity.Harbor;
import maritime.engine.entity.boats.Boat;
import maritime.engine.entity.boats.Fleet;
import maritime.engine.graph.GraphPoint;
import maritime.engine.graph.SearchInGraph;

import java.util.ArrayList;

/**
 * @author @Kenan Ammad
 * @version 0.1
 */
public class SeaRoad {
    private int clock;
    private ArrayList<GraphPoint> path;
    private Fleet convoy;
    private final Harbor sellerHarbor;
    private final Harbor buyerHarbor;
    private final Resource sellingResource;
    private final Resource sold;
    private final double ratio;

    public SeaRoad(int clock, Harbor sellerHarbor, Harbor buyerHarbor, Resource sellingResource, Resource sold, double ratio){
        this.clock = clock;
        this.sellingResource = sellingResource;
        this.sold = sold;
        this.ratio = ratio;
        this.path = SearchInGraph.findPath(sellerHarbor.getGraphPosition(), buyerHarbor.getGraphPosition());
        this.convoy = null;
        this.sellerHarbor = sellerHarbor;
        this.buyerHarbor = buyerHarbor;
    }

    public SeaRoad(int clock, Harbor sellerHarbor, Harbor buyerHarbor, ArrayList<GraphPoint> path, Resource sellingResource, Resource sold, double ratio){
        this.clock = clock;
        this.path = path;
        this.sellingResource = sellingResource;
        this.sold = sold;
        this.ratio = ratio;
        this.convoy = null;
        this.sellerHarbor = sellerHarbor;
        this.buyerHarbor = buyerHarbor;
    }

    //Getters

    public void setPath(ArrayList<GraphPoint> path) {this.path = path;}

    public ArrayList<GraphPoint> getPath() {return path;}

    public Harbor getSellerHarbor() {return sellerHarbor;}

    public Harbor getBuyerHarbor() {return buyerHarbor;}

    public double getRatio() { return ratio; }

    public Resource getSold() { return sold; }

    public Resource getSellingResource() { return sellingResource; }

    public Fleet getConvoy() {return convoy;}
    
    //Setters

    public void setConvoy(Fleet convoy) { this.convoy = convoy; }

    public void setConvoy(Boat boat) {
        Fleet fleet = new Fleet();
        fleet.add(boat);
        this.convoy = fleet;
    }

    //Basic Time management behavior
    
    public void abandonTask() {this.clock = 0;}
    
    public void subtractTime(int nb) {this.clock -= nb;}
    
    public void addTime(int nb) {this.clock += nb;}
    
    public boolean available(){return clock > 0;}
}
