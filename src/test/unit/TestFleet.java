package test.unit;

import engine.data.graph.GraphPoint;
import engine.data.graph.GraphSegment;
import engine.process.manager.BoatManager;
import engine.process.manager.FleetManager;
import engine.utilities.SearchInGraph;
import org.junit.Before;
import org.junit.Test;

import engine.data.Fleet;
import engine.data.entity.boats.Boat;
import engine.data.entity.boats.Standard;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Unit test that check if a Fleet correctly receive moving order.
 * @author Gauthier Defrance
 * Class TestFleet
 * @version 1.0
 */
public class TestFleet {
    private Fleet fleet;

    /**
     * Method that init a Fleet with 3 boats and a Graph of 2 points
     *
     */
    @Before
    public void initFleet() {
        FleetManager fleetManager = new FleetManager();
        this.fleet = new Fleet("TestFleet");
        GraphPoint graphP = new GraphPoint(new Point(0,0), "A");
        GraphPoint destination = new GraphPoint(new Point(200,0), "B");

        graphP.addSegment(new GraphSegment(destination,1));
        destination.addSegment(new GraphSegment(graphP,1));

        ArrayList<GraphPoint> lst = SearchInGraph.findPath(graphP, destination);
        lst.add(graphP);
        lst.add(destination);

        fleet.add(new Standard("0","red",graphP));
        fleet.add(new Standard("1","red",graphP));
        fleet.add(new Standard("2","red",graphP));

        fleetManager.setNewPath(fleet, lst, true);
        fleetManager.setContinuePathAll(fleet, false);

        fleetManager.pathUpdate(fleet);

        BoatManager boatManager = new BoatManager();

        for(Boat boat: fleet.getArrayListBoat()){
            boatManager.followThePath(boat);
        }
    }

    /**
     * Method that check if each boats has been assigned the correct Point.
     */
    @Test
    public void testMoveFleet() {
        for(Boat boat: fleet.getArrayListBoat()){
            assertNotNull(boat);
            assertFalse(boat.getContinuePath());

            assertEquals("B", boat.getNextGraphPoint().getIdPoint());
            assertEquals(200, boat.getNextGraphPoint().getPoint().getX(), 0.0);
            assertEquals(0, boat.getNextGraphPoint().getPoint().getY(), 0.0);
        }
    }

}
