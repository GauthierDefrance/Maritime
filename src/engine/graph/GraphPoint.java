package engine.graph;

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
 * @version 0.2
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

    public void initSegmentsHashMap() {
        SegmentHashMap = new HashMap<String,GraphSegment>();
    }

    public void addSegment(GraphSegment segment) {SegmentHashMap.put(segment.getGraphPoint().getIdPoint(), segment);}

    public HashMap<String,GraphSegment> getSegmentHashMap() {return SegmentHashMap;}

    public String getIdPoint() {return idPoint;}

    public int getX(){return (int) point.getX();}

    public int getY(){return (int) point.getY();}

    public Point getPoint(){return point;}

    public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append(idPoint +" est connecté à :\n");
        for (GraphSegment p : SegmentHashMap.values()) {
            sb.append("->"+p.toString());
        }
        return sb.toString();
    }

}
