package maritime.inventory;
import java.util.HashMap;

public class Inventory {
    private final HashMap<String, Integer> content; //Plus parlant que dictionary

    public Inventory() { content = new HashMap<>(); }

    public HashMap<String, Integer> getInventoryContent(){ return content; }

    public int checkRessourceNumber(String elem){
        return content.getOrDefault(elem, 0);
        /* évite de renvoyer null si l'élément n'est pas présent dans l'inventaire et considère plutôt une présence
         par défaut de nb 0, alternativement gérable par une exception */
    }

    public void add(String elem,int nb){ content.put(elem, checkRessourceNumber(elem) + nb); }

    public boolean subtract(String elem, int nb){
        if (this.checkRessourceNumber(elem) >= nb) {
            this.add(elem, -nb);
            return true;
        }
        else {return false;}
    }
}
