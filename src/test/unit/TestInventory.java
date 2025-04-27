package test.unit;

import static org.junit.Assert.*;

import engine.data.entity.boats.Standard;
import engine.data.graph.GraphPoint;
import engine.data.trading.Inventory;
import engine.data.trading.Resource;
import engine.process.manager.TradeManager;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

/**
 * Unit test that check if the inventory works as intended
 * @author Gauthier Defrance
 * Class TestInventory
 * @version 1.0
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
    public void testInventorySafeSubtract(){
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


    /**
     * Method that test if total free space and total value are calculated as intended
     */
    @Test
    public void testInventoryCount(){
        //We add 90 ressources of type : "TestResource" with a value of 10.
        //So, on an inventory of 100 places, we have logically 10 places lefts
        //And the total value is of 90*10 = 900
        manager.safeAdd(inventory, resource,90, null);

        assertEquals(10, manager.totalFreeSpace(inventory));
        assertEquals(900, manager.totalValue(inventory));

        inventory.clearContent();
    }

    /**
     * Methods that check if the transferMaxAll methods works as intended
     */
    @Test
    public void testInventoryTransfer(){
        //We create the origin boat, that will have an inventory with a certain quantity of ressources
        Standard originBoat = new Standard("origin", "red", new GraphPoint(new Point(), "A"));
        originBoat.getInventory().setCapacity(100);
        manager.safeAdd(originBoat.getInventory(), resource,100, originBoat);

        //We then create the destination boat to which we will transfer the ressources
        Standard destinationBoat = new Standard("destination", "red", new GraphPoint(new Point(), "B"));
        destinationBoat.getInventory().setCapacity(50);

        //We check the FreeSpace in the boats
        assertEquals(0, manager.totalFreeSpace(originBoat.getInventory()));
        assertEquals(50, manager.totalFreeSpace(destinationBoat.getInventory()));

        //We transfer the resources from the origin to the destination (50 max will be transfer on 100)
        manager.transferMaxAll(originBoat.getInventory(), destinationBoat.getInventory() ,originBoat, destinationBoat);
        assertEquals(0, manager.totalFreeSpace(destinationBoat.getInventory()));
        assertEquals(50, manager.totalFreeSpace(originBoat.getInventory()));

        //We then transfer it backs
        manager.transferMaxAll(destinationBoat.getInventory() , originBoat.getInventory(),  destinationBoat, originBoat);
        assertEquals(50, manager.totalFreeSpace(destinationBoat.getInventory()));
        assertEquals(0, manager.totalFreeSpace(originBoat.getInventory()));
    }


}
