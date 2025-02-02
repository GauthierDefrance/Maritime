package maritime.engine.inventory;
import maritime.engine.entity.Entity;

import java.util.HashMap;

public class Inventory {
    private HashMap<String, Integer> content;

    public Inventory() { content = new HashMap<>(); }

    public HashMap<String, Integer> getInventoryContent(){ return this.content; }

    public int checkRessourceNumber(String elem){
        return this.content.getOrDefault(elem, 0);
        /* évite de renvoyer null si l'élément n'est pas présent dans l'inventaire et considère plutôt une présence
         par défaut de nb 0 */
    }

    public void add(String elem,int nb){ this.content.put(elem, checkRessourceNumber(elem) + nb); }

    public void subtract(String elem, int nb){
        if (this.checkRessourceNumber(elem) >= nb) {
            this.add(elem, -nb);
        } else {
            throw new ArithmeticException( "Not enough elements in inventory" );
            /* Une exception personnalisée à prévoir ? Capable de générer un pop-up, le comportement importe peu ici */
        }
    }
}