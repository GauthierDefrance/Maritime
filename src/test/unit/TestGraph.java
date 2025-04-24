package test.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import engine.data.graph.GraphPoint;
import engine.data.graph.GraphSegment;
import java.awt.*;
import java.util.ArrayList;

import engine.utilities.SearchInGraph;

/**
 * Unit test that check if the graph is working as intended
 * @author Gauthier Defrance
 * Class TestGraph
 * @version 0.1
 */
public class TestGraph {
    private GraphPoint Origin;
    private GraphPoint Destination;

    /**
     * Method that construct a graph of 3 points : A<-(1)->B<-(1)->C<-(4)->A
     */
    @Before
    public void initGraph(){
        GraphPoint A = new GraphPoint(new Point(),"A");
        GraphPoint B = new GraphPoint(new Point(),"B");
        GraphPoint C = new GraphPoint(new Point(),"C");

        GraphSegment AB = new GraphSegment(B,1);
        GraphSegment BA = new GraphSegment(A,1);

        GraphSegment CB = new GraphSegment(B,1);
        GraphSegment BC = new GraphSegment(C,1);

        GraphSegment CA = new GraphSegment(A,4);
        GraphSegment AC = new GraphSegment(C,4);

        A.addSegment(AB);
        A.addSegment(AC);
        B.addSegment(BA);
        B.addSegment(BC);
        C.addSegment(CB);
        C.addSegment(CA);

        this.Origin = A;
        this.Destination = C;
    }


    /**
     * Method that check if A and C are considered has neighbor.
     */
    @Test
    public void testGraphNeighbor(){
        assertEquals(this.Origin.getSegmentHashMap().get("C").getGraphPoint(), this.Destination);
        assertEquals(this.Destination.getSegmentHashMap().get("A").getGraphPoint(), this.Origin);
    }


    /**
     * Unit test that checks if the first point is the Origin and if the final point of the graph is indeed the Destination.
     * We also check the size of the graph in the init case, we should always have a List of size = 3.
     */
    @Test
    public void testGraphPath(){
        ArrayList<GraphPoint> PList = SearchInGraph.findPath(this.Origin, this.Destination);

        assertNotNull(PList);
        assertEquals(this.Destination, PList.get(PList.size()-1));
        assertEquals(this.Origin, PList.get(0));
        assertEquals(3, PList.size());
    }






}
