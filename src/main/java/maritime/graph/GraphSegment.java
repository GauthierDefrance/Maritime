package maritime.graph;

/**
 * @author @Kenan Ammad @Gauthier Defrance
 * Classe GraphSegment
 * @version 0.1
 */
public class GraphSegment {
    private final GraphPoint destination;
    private int cost;

    public GraphSegment(GraphPoint destination, int cost) {
        this.destination = destination;
        initCost(cost);
    }

    public void initCost(int cost) {
        this.cost = cost;
    }

    public GraphPoint getGraphPoint(){return destination;}

    public int getCost(){return cost;}

    public String toString(){return "go to "+destination.getIdPoint()+" and cost "+getCost()+"\n";}
}
