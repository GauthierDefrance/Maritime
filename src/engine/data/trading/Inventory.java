package engine.data.trading;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Class relative to the notion of Inventory : Managed Hashmap with (potentially) limited capacity
 * @version 0.2
 * @author Zue Jack-Arthur
 */
public class Inventory implements Serializable {

    private HashMap<Resource, Integer> content;
    private int capacity;

    /**
     * Typical constructor generating an Inventory
     * @param capacity maximum capacity
     */
    public Inventory(int capacity) {
        this.content = new HashMap<>();
        this.capacity = capacity;
    }

    /**
     * Constructor generating an Inventory with unlimited capacity
     */
    public Inventory() {
       this.content = new HashMap<>();
       this.capacity = Integer.MAX_VALUE;
    }

    //Getters

    /**
     * Allows fetching of the content of the Inventory
     * @return complete content of the Inventory
     */
    public HashMap<Resource, Integer> getContent(){
        return this.content;
    }

    /**
     *Allows fetching of the number of elements of a targeted Resources in the Inventory
     * @param elem name of the Resource element
     * @return number of contained elements of the mentioned Resource or 0 if Resource isn't present
     */
    public int getNbResource(Resource elem){
        return this.content.getOrDefault(elem, 0);
    }

    /**
     * Allows fetching of the total capacity of the inventory
     * @return capacity of the inventory
     */
    public int getCapacity() {
        return this.capacity;
    }

    //Setters

    /**
     * Allows to assign an arbitrary number of a designated Resource element to the Inventory
     * @param elem name of the Resource element
     * @param nbResource number of elements that must be in the inventory
     */
    public void setNbResource(Resource elem, int nbResource) {
        this.content.put(elem, nbResource);
    }

    /**
     * Allows for a modification of the capacity of the Inventory
     * @param capacity new maximum capacity of the Inventory
     */
    public void setCapacity( int capacity ) {
        this.capacity = capacity;
    }

    //Basic resource management behavior

    /**
     * Allows for a complete clear of the Inventory
     */
    public void clearContent() {
        this.content.clear();
    }

    /**
     * Default addition behavior
     * @param elem name of the Resource element
     * @param nb number of elements that must be added
     */
    public void add(Resource elem, int nb){
        this.content.put(elem, getNbResource(elem) + nb);
    }

    /**
     * Default subtraction behavior
     * @param elem name of the Resource element
     * @param nb number of elements that must be subtracted
     */
    public void subtract(Resource elem, int nb){
        this.content.put(elem, getNbResource(elem) - nb);
    }

    public String toString() {
        StringBuilder textResource = new StringBuilder();
        for (Resource resource : content.keySet()) {
            if (content.get(resource) != 0) {
                textResource.append(resource.getName());
                textResource.append(" ");
                textResource.append(content.get(resource));
                textResource.append(" | ");
            }
        }
        if(textResource.length()>2)textResource.delete(textResource.length()-2,textResource.length());
        return String.valueOf(textResource);
    }
    
}