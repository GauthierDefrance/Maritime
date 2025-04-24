package test.unit;

import static org.junit.Assert.*;

import engine.data.entity.Harbor;
import engine.data.entity.boats.Standard;
import engine.data.graph.GraphPoint;
import gui.PopUp;
import gui.process.ImageStock;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Unit test that check if some of the buffered Image from the ImageStock works.
 * @author Gauthier Defrance
 * Class TestImageStock
 * @version 0.1
 */
public class TestImageStock {
    private ImageStock imageStock;


    /**
     * Init the ImageStock instance.
     */
    @Before
    public void init() {
        imageStock =  ImageStock.getInstance();
    }

    /**
     * Unit that check if a bufferedImage is correctly loaded from a Standard Boat
     */
    @Test
    public void testImageStockStandard() {
        Standard standard = new Standard("A","red",new GraphPoint(new Point(),"A"));
        BufferedImage image = imageStock.getImage(standard);
        assertNotNull(image);
        assertTrue(image instanceof BufferedImage);
    }

    /**
     * Unit that check if a bufferedImage is correctly loaded from an Harbor
     */
    @Test
    public void testImageStockHarbor() {
        Harbor harbor = new Harbor("A","red", new Point(), new GraphPoint(new Point(),"A"));
        BufferedImage image = imageStock.getImage(harbor);
        assertNotNull(image);
        assertTrue(image instanceof BufferedImage);
    }

    /**
     * Unit that check if a bufferedImage is correctly loaded from a PopUp explosion
     */
    @Test
    public void testImageStockPopUpExplosion() {
        PopUp explosion = new PopUp("explosion",new Point(),5);
        BufferedImage image = imageStock.getImage(explosion);
        assertNotNull(image);
        assertTrue(image instanceof BufferedImage);
    }

}
