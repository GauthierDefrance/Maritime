package engine.utilities;

import config.GameConfiguration;
import engine.MapGame;
import engine.entity.Harbor;
import engine.entity.boats.Boat;
import engine.faction.Faction;
import engine.graph.GraphPoint;
import engine.graph.GraphSegment;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;


/**
 * @author Kenan Ammad
 * Classe SearchInGraph
 * @version 0.3
 */
public final class SearchInGraph {

    /**
     * Take one points one boat and return the shortest path as a list of GraphPoint
     */
    public static ArrayList<GraphPoint> findPath(Boat boat, GraphPoint pointEnd) {
        GraphPoint pointStart = new GraphPoint(boat.getPosition(),"temp");
        pointStart.addSegment(new GraphSegment(boat.getOldGraphPoint(), (int) pointStart.getPoint().distance(boat.getOldGraphPoint().getPoint())));
        pointStart.addSegment(new GraphSegment(boat.getNextGraphPoint(), (int) pointStart.getPoint().distance(boat.getNextGraphPoint().getPoint())));
        return findPath(pointStart, pointEnd);
    }

    /**
     * Take two points and return the shortest path as a list of GraphPoint
     */
    public static ArrayList<GraphPoint> findPath(GraphPoint pointStart, GraphPoint pointEnd) {
        if (pointStart.equals(pointEnd)){ArrayList<GraphPoint> Result = new ArrayList<>();Result.add(pointStart);return Result;}
        ArrayList<ArrayList<GraphSegment>> lstPath = new ArrayList<>(deletePath(getPointToPath(pointStart),pointStart));;
        ArrayList<ArrayList<GraphSegment>> lstPathResult = new ArrayList<>();
        while (!lstPath.isEmpty()) {
            ArrayList<ArrayList<GraphSegment>> lstPathTemp = new ArrayList<>();
            for (ArrayList<GraphSegment> lstSegment : lstPath) {
                if(lstSegment.get(lstSegment.size()-1).getGraphPoint().equals(pointEnd)){lstPathResult.add(lstSegment);}
                else {lstPathTemp.addAll(getLstSegmentUpdate(lstSegment));}
            }
            lstPath = new ArrayList<>(deletePath(lstPathTemp,pointStart));
            lstPathTemp.clear();
        }
        ArrayList<ArrayList<GraphSegment>> result = deletePath(lstPathResult,pointStart);
        if(!result.isEmpty()){
            return getLstSegmentToPoint(result.get(0),pointStart);
        }
        else {
        return new ArrayList<>();
        }
    }

    private static ArrayList<ArrayList<GraphSegment>> getPointToPath(GraphPoint point) {
        ArrayList<ArrayList<GraphSegment>> lstPath = new ArrayList<>();
        for (String key : point.getSegmentHashMap().keySet()) {
            ArrayList<GraphSegment> lstSegment = new ArrayList<>();
            lstSegment.add(point.getSegmentHashMap().get(key));
            lstPath.add(lstSegment);
        }
        return lstPath;
    }

    private static ArrayList<GraphPoint> getLstSegmentToPoint(ArrayList<GraphSegment> lstSegment,GraphPoint pointStart) {
        ArrayList<GraphPoint> lstPoint = new ArrayList<>();
        lstPoint.add(pointStart);
        for(GraphSegment segment : lstSegment){lstPoint.add(segment.getGraphPoint());}
        return lstPoint;
    }

    private static ArrayList<ArrayList<GraphSegment>> getLstSegmentUpdate(ArrayList<GraphSegment> lstSegment) {
        ArrayList<ArrayList<GraphSegment>> lstPath = new ArrayList<>();
            GraphPoint point = lstSegment.get(lstSegment.size()-1).getGraphPoint();
            for (String key : point.getSegmentHashMap().keySet()) {
                ArrayList<GraphSegment> newLstSegment = new ArrayList<>(lstSegment);
                newLstSegment.add(point.getSegmentHashMap().get(key));
                lstPath.add(newLstSegment);
            }
        return lstPath;
    }

    private static ArrayList<ArrayList<GraphSegment>> deletePath(ArrayList<ArrayList<GraphSegment>> lstPath, GraphPoint pointStart) {
        ArrayList<ArrayList<GraphSegment>> lstPathResult = new ArrayList<>(lstPath);
        for (ArrayList<GraphSegment> iLstSegment : lstPath){
            for (ArrayList<GraphSegment> jLstSegment : lstPath){
                if ((lstPathResult.contains(iLstSegment)) && (HaveTwoSamePoint(iLstSegment, iLstSegment.get(iLstSegment.size()-1).getGraphPoint()) || haveSamePoint(iLstSegment, pointStart))){
                    lstPathResult.remove(iLstSegment);
                }
                else if ((lstPathResult.contains(jLstSegment)) && (HaveTwoSamePoint(jLstSegment, iLstSegment.get(iLstSegment.size()-1).getGraphPoint()) || haveSamePoint(jLstSegment, pointStart))){
                    lstPathResult.remove(jLstSegment);
                }
                else if(lstPathResult.contains(iLstSegment) && lstPathResult.contains(jLstSegment) && doYouFight(jLstSegment,iLstSegment.get(iLstSegment.size()-1)) &&!iLstSegment.equals(jLstSegment)){

                    if(lstPathResult.contains(iLstSegment) && lstPathResult.contains(jLstSegment) && amountCost(iLstSegment, iLstSegment.get(iLstSegment.size()-1).getGraphPoint()) >= amountCost(jLstSegment, iLstSegment.get(iLstSegment.size()-1).getGraphPoint())){
                    lstPathResult.remove(iLstSegment);
                    }
                }
            }
        }
        return lstPathResult ;
    }

    private static boolean doYouFight(ArrayList<GraphSegment> lstSegment, GraphSegment segment){
        boolean flag = false;
        for(GraphSegment graphsegment : lstSegment){
            if (!graphsegment.getGraphPoint().equals(segment.getGraphPoint())) flag=true;
            if (flag && graphsegment.getGraphPoint().equals(segment.getGraphPoint()))return true;
        }
        return false;
    }

    private static boolean haveSamePoint(ArrayList<GraphSegment> lstSegment, GraphPoint point){
        for (GraphSegment segment : lstSegment) {
            if (segment.getGraphPoint().equals(point)) return true;
        }
        return false;
    }

    private static boolean HaveTwoSamePoint(ArrayList<GraphSegment> lstSegment,GraphPoint point){
        int acc = 0;
        for (GraphSegment segment : lstSegment) {
            if (segment.getGraphPoint().equals(point)) acc++;
        }
        return acc > 1;
    }

    private static int amountCost(ArrayList<GraphSegment> lstSegment,GraphPoint point) {
        int amount = 0;
        for(GraphSegment segment : lstSegment){amount += segment.getCost();
            if (segment.getGraphPoint().equals(point)) return amount;
        }
        return -1;
    }

    /**
     * Check if a GraphPoint reached a targeted point in map
     * @param point concerned
     * @return Harbor
     */
    public static GraphPoint pointCollisionToMapGraphPoint(Point point){
        GraphPoint graphPoint = null;
        for (Map.Entry<String, GraphPoint> entry : MapGame.getInstance().getMapGraphPoint().entrySet()){
            if (GameConfiguration.HITBOX_BOAT - 5 >= point.distance(entry.getValue().getPoint())){
                if(graphPoint != null){
                    if(point.distance(entry.getValue().getPoint()) < point.distance(graphPoint.getPoint())){
                        graphPoint = entry.getValue();
                    }
                }
                else graphPoint = entry.getValue();
            }
        }
        return graphPoint;
    }

}
