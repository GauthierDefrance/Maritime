package engine.data.graph;

import java.io.Serializable;

/**
 * @author Kenan Ammad
 * @author Gauthier Defrance
 * Classe GraphSegment
 * @version 1.0
 */
public class GraphSegment implements Serializable {
    private final GraphPoint destination;
    private int cost;

    public GraphSegment(GraphPoint destination, int cost) {
        this.destination = destination;
        this.cost = cost;
    }

    /**
     * Sets the cost associated with this segment.
     * @param cost {@link Integer} The cost to be set for this segment.
     */
    public void setCost(int cost) {
        this.cost = cost;
    }

    /**
     * Gets the cost associated with this segment.
     * @return {@link Integer} The cost of the segment.
     */
    public int getCost() {
        return cost;
    }

    /**
     * Gets the {@link GraphPoint} destination associated with this segment.
     * @return {@link GraphPoint} The destination graph point.
     */
    public GraphPoint getGraphPoint() {
        return destination;
    }

    /**
     * Generates a string representation of this segment, showing its destination and cost.
     * @return {@link String} A formatted string describing the segment's destination and cost.
     */
    public String toString() {
        return "go to " + destination.getIdPoint() + " and cost " + getCost() + "\n";
    }

}
