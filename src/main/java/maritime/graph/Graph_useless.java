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

    private void initHashMapGraph(GraphPoint[] graphTab) {
        graph = new HashMap<String,GraphPoint>();
        for (GraphPoint point : graphTab) {
            addGraphPoint(point.getIdPoint(), point);
        }
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (GraphPoint graphPoint : graph.values()) {
            sb.append(graphPoint.toString());
        }
        return sb.toString();
    }

}


