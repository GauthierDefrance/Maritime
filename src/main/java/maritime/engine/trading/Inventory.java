package maritime.engine.trading;

import java.util.HashMap;

/**
 * @version 0.2
 * @author Zue Jack-Arthur
 */
public class Inventory {
    private HashMap<Ressource, Integer> content;
    private int capacity;

    public Inventory(int capacity) {
        this.content = new HashMap<>();
        this.capacity = capacity;
    }

    public Inventory() {
       this.content = new HashMap<>();
       this.capacity = Integer.MAX_VALUE;
    }

    //Getters

    public HashMap<Ressource, Integer> getContent(){ return this.content; }

    public int getNbRessource(Ressource elem){ return this.content.getOrDefault(elem, 0); }

    public int getCapacity() { return this.capacity; }

    //Setters

    public void setCapacity( int capacity ) { this.capacity = capacity; }

//
//    public void setContent( HashMap<Ressource, Integer> content ) {
//    if (content.totalUsedSpace() <= capacity) this.content = content;
//        else throw new IllegalArgumentException("Too many items");
//    }

    //Other

    public void add(Ressource elem,int nb){ this.content.put(elem, getNbRessource(elem) + nb); }

    public void subtract(Ressource elem, int nb){ this.content.put(elem, getNbRessource(elem) - nb); }
    
}