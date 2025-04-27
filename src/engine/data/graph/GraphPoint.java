package engine.data.graph;

import java.awt.*;
import java.io.Serializable;
import java.util.HashMap;


/**
 * This class is for the representation of the points of a Graph.
 * It allows to know to which point is a point connected and at what cost.
 * Each GraphPoint has his owns id.
 * @author Kenan Ammad
 * @author Gauthier Defrance
 * @see GraphPoint
 * @version 1.0
 */
public class GraphPoint implements Serializable {
    private final Point point;
    private final String idPoint;
    private HashMap<String,GraphSegment> SegmentHashMap;


    public GraphPoint(Point point, String name) {
        this.point = point;
        this.idPoint = name;
        initSegmentsHashMap();
    }

    /**
     * Initializes the HashMap for storing graph segments.
     * The map uses the point's ID as the key, and the associated {@link GraphSegment} as the value.
     */
    public void initSegmentsHashMap() {
        SegmentHashMap = new HashMap<String, GraphSegment>();
    }

    /**
     * Adds a {@link GraphSegment} to the segment map using its point's ID as the key.
     * @param segment The {@link GraphSegment} to be added to the map.
     */
    public void addSegment(GraphSegment segment) {
        SegmentHashMap.put(segment.getGraphPoint().getIdPoint(), segment);
    }

    /**
     * Gets the HashMap containing the graph segments.
     * @return {@link HashMap} A map of segment IDs to their respective {@link GraphSegment}.
     */
    public HashMap<String, GraphSegment> getSegmentHashMap() {
        return SegmentHashMap;
    }

    /**
     * Gets the ID of the point associated with this segment.
     * @return {@link String} The unique ID of the point.
     */
    public String getIdPoint() {
        return idPoint;
    }

    /**
     * Gets the X coordinate of the point.
     * @return {@link Integer} The X coordinate of the point.
     */
    public int getX() {
        return (int) point.getX();
    }

    /**
     * Gets the Y coordinate of the point.
     * @return {@link Integer} The Y coordinate of the point.
     */
    public int getY() {
        return (int) point.getY();
    }

    /**
     * Gets the {@link Point} representing the location of this graph point.
     * @return {@link Point} The {@link Point} object of this segment.
     */
    public Point getPoint() {
        return point;
    }

    /**
     * Generates a string representation of this point and its connected segments.
     * @return {@link String} The string describing the point and its connections.
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(idPoint + " est connecté à :\n");
        for (GraphSegment p : SegmentHashMap.values()) {
            sb.append("->" + p.toString());
        }
        return sb.toString();
    }

}
