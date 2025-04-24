package test.unit;

import gui.process.JComponentFactory;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;


/**
 * Unit test that check if the initialisation of JComponent works
 * @author Gauthier Defrance
 * Class TestJComponentFactory
 * @version 0.1
 */
public class TestJComponentFactory {

    /**
     * Method that test the initialisation of some JComponent
     */
    @Test
    public void test() {
        assertNotNull(JComponentFactory.title("test"));
        assertNotNull(JComponentFactory.menuButton("Test"));
        assertNotNull(JComponentFactory.SelectionZone());
        assertNotNull(JComponentFactory.gridMenuPanel(5,5));
        assertNotNull(JComponentFactory.voidPanel());
        assertNotNull(JComponentFactory.voidPopupMenu());
    }

}
