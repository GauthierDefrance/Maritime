package maritime.engine.trading;

import java.util.HashMap;

/**
 * @version 0.2
 * @author Zue Jack-Arthur
 */
public class Inventory {
    private final HashMap<Ressource, Integer> content; //La HashMap reste pertinente dans une optique de gestion
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

    public int getCapacity() { return this.capacity; }

    //Setters

    public void setCapacity( int capacity ) { this.capacity = capacity; }

//la HashMap devrait etre final ? et aussi mauvaise comparaison effectuée c'est pas la taille de la HashMap mais la Somme des quantités de la HashMap Qu'il faut calculer
//
//    public void setInventoryContent( HashMap<Ressource, Integer> content ) {
//        if (content.size() < capacity) this.content = content;
//        else throw new IllegalArgumentException("Too many items");
//    }

    //Logic Methods (must be moved to a future InventoryManager class)

    public int checkRessourceNumber(Ressource elem){
        return this.content.getOrDefault(elem, 0);
        /* évite de renvoyer null si l'élément n'est pas présent dans l'inventaire et considère plutôt une présence
         par défaut de nb 0 */
    }

//    public int totalUsedSpace(){
//        int total = 0;
//        for (Ressource elem : this.content.keySet()) total += this.content.get(elem);
//        return total;
//    }

//    public void safeAdd(Ressource elem, int nb){
//        if ((totalUsedSpace() + nb) < capacity) this.add(elem, nb);
//    }

    public void add(Ressource elem,int nb){ this.content.put(elem, checkRessourceNumber(elem) + nb); }

    public void subtract(Ressource elem, int nb){
        if (this.checkRessourceNumber(elem) >= nb) {
            this.add(elem, -nb);
        } else {
            throw new ArithmeticException( "Not enough elements in inventory" );
            /* Une exception personnalisée à prévoir ? Capable de générer un pop-up, le comportement importe peu ici */
        }
    }
}