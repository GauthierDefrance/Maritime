package maritime.graph;

import java.util.HashMap;

public class Graph_useless {

    private HashMap<String,GraphPoint> graph;

    public Graph_useless(GraphPoint[] graphPoints) {
        initHashMapGraph(graphPoints);
    }

    public GraphPoint getPoint(String idPoint) throws IllegalArgumentException {
        if (graph.containsKey(idPoint)) {
            return graph.get(idPoint);
        } else {
            throw new IllegalArgumentException("Erreur lors de la lecture du graph");
        }
    }

    private void addGraphPoint(String idPoint, GraphPoint point) {
        graph.put(point.getIdPoint(), point);
    }

    private void initHashMapGraph(GraphPoint[] graphPoints) {
        graph = new HashMap<String,GraphPoint>();
        for (GraphPoint graphPoint : graphPoints) {
            addGraphPoint(graphPoint.getIdPoint(), graphPoint);
        }
    }

    public GraphPoint[] getPath(GraphPoint startPoint, GraphPoint endPoint) throws PathNotFoundException{




        return new GraphPoint[] { startPoint, endPoint };
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (GraphPoint graphPoint : graph.values()) {
            sb.append(graphPoint.toString());
        }
        return sb.toString();
    }

}


