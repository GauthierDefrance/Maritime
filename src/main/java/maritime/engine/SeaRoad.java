package maritime.engine;

import maritime.engine.entity.Harbor;
import maritime.engine.entity.boats.Boat;
import maritime.engine.entity.boats.Fleet;
import maritime.engine.graph.GraphPoint;
import maritime.engine.graph.SearchInGraph;
import maritime.engine.trading.Resource;

import java.util.ArrayList;

/**
 * @author @Kenan Ammad
 * @version 0.1
 */
public class SeaRoad {
    private int time;
    private ArrayList<GraphPoint> path;
    private Fleet fleet;
    private final Harbor startSeaRout;
    private final Harbor endSeaRout;
    private final Resource buy;
    private final Resource sold;
    private final double ratio;

    public SeaRoad(int time, Harbor startSeaRout, Harbor endSeaRout, Resource buy, Resource sold, double ratio){
        this.time = time;
        this.buy = buy;
        this.sold = sold;
        this.ratio = ratio;
        this.path = SearchInGraph.findPath(startSeaRout.getGraphPosition(),endSeaRout.getGraphPosition());
        this.fleet = null;
        this.startSeaRout = startSeaRout;
        this.endSeaRout = endSeaRout;
    }

    public SeaRoad(int time, Harbor startSeaRout, Harbor endSeaRout, ArrayList<GraphPoint> path, Resource buy, Resource sold, double ratio){
        this.time = time;
        this.path = path;
        this.buy = buy;
        this.sold = sold;
        this.ratio = ratio;
        this.fleet = null;
        this.startSeaRout = startSeaRout;
        this.endSeaRout = endSeaRout;
    }

    public void setTime0() {this.time = 0;}
    public void subtractTime(int nb) {this.time -= nb;}
    public void addTime(int nb) {this.time += nb;}
    public boolean available(){return time > 0;}


    public ArrayList<GraphPoint> getPath() {return path;}

    public void setPath(ArrayList<GraphPoint> path) {this.path = path;}

    public Fleet getFleet() {return fleet;}

    public void setFleet(Fleet fleet) {
        this.fleet = fleet;
    }
    public void setFleet(Boat boat) {
        Fleet fleet = new Fleet();
        fleet.add(boat);
        this.fleet = fleet;
    }

    public Harbor getStartSeaRout() {return startSeaRout;}

    public Harbor getEndSeaRout() {return endSeaRout;}

    public double getRatio() {
        return ratio;
    }

    public Resource getSold() {
        return sold;
    }

    public Resource getBuy() {
        return buy;
    }
}
