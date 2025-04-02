package engine.data.graph;

import java.io.Serializable;

/**
 * @author Kenan Ammad
 * @author Gauthier Defrance
 * Classe GraphSegment
 * @version 0.2
 */
public class GraphSegment implements Serializable {
    private final GraphPoint destination;
    private int cost;

    public GraphSegment(GraphPoint destination, int cost) {
        this.destination = destination;
        this.cost = cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public GraphPoint getGraphPoint(){return destination;}

    public int getCost(){return cost;}

    public String toString(){return "go to "+destination.getIdPoint()+" and cost "+getCost()+"\n";}
}
