package test.unit;

import org.junit.runners.Suite;
import org.junit.runner.RunWith;


/**
 * This is the global test suite of all unit tests. It includes six test cases :
 * {@link TestFleet}, {@link TestGraph}, {@link TestImageStock}, {@link TestInventory},
 * {@link TestJComponentFactory} and {@link TestSave}.
 * @author Gauthier Defrance
 * Class TestSuite
 * @version 1.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({TestFleet.class, TestGraph.class, TestImageStock.class, TestInventory.class, TestJComponentFactory.class, TestSave.class})
public class TestSuite {

}
