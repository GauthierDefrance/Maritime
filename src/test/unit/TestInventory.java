package test.unit;

import static org.junit.Assert.*;

import engine.data.trading.Inventory;
import engine.data.trading.Resource;
import engine.process.manager.TradeManager;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test that check if the inventory works as intended
 * @author Gauthier Defrance
 * Class TestInventory
 * @version 0.1
 */
public class TestInventory {
    private Inventory inventory;
    private Resource resource;
    private TradeManager manager;

    /**
     * Init of the Tested inventory and his resource.
     */
    @Before
    public void init(){
        manager = TradeManager.getInstance();
        inventory = new Inventory(100);
        resource = new Resource("TestResource",10,1);

    }

    /**
     * Testing of the Inventory Safe Add.
     */
    @Test
    public void testInventorySafeAdd(){
        //We first check that our inventory currently have 0 ressource
        assertEquals(0, inventory.getNbResource(resource));

        //We then add 100 ressources of a kind, making then logically the inventory full.

        manager.safeAdd(inventory, resource,100, null);
        assertEquals(100, inventory.getNbResource(resource));

        //We add 100 more again, logically impossible because max size is define at 100
        //We then check if the size has indeed stayed the same.
        manager.safeAdd(inventory, resource,100, null);
        assertEquals(100, inventory.getNbResource(resource));

        inventory.clearContent();
    }

    /**
     * Testing of the safeSubstract methode with Inventory
     */
    @Test
    public void testInventorySubtract(){
        manager.safeAdd(inventory, resource,100, null);

        //We remove all the ressources in the inventory
        manager.safeSubtract(inventory, resource,100, null);
        assertEquals(0, inventory.getNbResource(resource));

        //We try to remove more than it's logically possible
        manager.safeSubtract(inventory, resource,100, null);
        assertEquals(0, inventory.getNbResource(resource));

        //We try to remove a negative amount, we logically then add ressources
        manager.safeSubtract(inventory, resource,-100, null);
        assertEquals(100, inventory.getNbResource(resource));

        inventory.clearContent();
    }
}
