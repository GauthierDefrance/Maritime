package maritime.engine.entity;


import maritime.engine.inventory.Inventory;
import java.awt.*;

public abstract class Entity_useless implements EntityInterface {
    private String name;
    private int visionRadius;
    private int maxHp;
    private int currentHp;
    private Inventory inventory;
    private Point position;

    public Entity_useless(String name, int visionRadius, int maxHp, Point position) {
        this.name = name;
        this.visionRadius = visionRadius;
        this.inventory = new Inventory();
        this.maxHp = maxHp;
        this.currentHp = maxHp;
        this.position = position;
    }

    //Getters

    @Override
    public String getName() { return name; }

    @Override
    public Inventory getInventory() { return inventory; }

    @Override
    public int getVisionRadius() { return visionRadius; }

    @Override
    public int getCurrentHp() { return currentHp; }

    @Override
    public int getMaxHp() { return maxHp; }

    @Override
    public Point getPosition() { return position; }


    //Setters

    @Override
    public void setVisionRadius(int visionRadius) { this.visionRadius = visionRadius; }

    public void setName(String name) {this.name = name; }

    /**
     * Setter qui permet de modifier la position actuelle du point.
     * Prend en paramètre un objet de type Point.
     * @param position position actuelle de l'entité
     */
    @Override
    public void setPosition(Point position) {this.position = position; }

    /**
     * Setter qui permet de modifier la position actuelle du point.
     * Prend en paramètre deux Int, respectivement x et y.
     * @param x coordonnée x
     * @param y coordonnée y
     */
    public void setPosition(double x, double y){
        position.setLocation(x,y);
    }

    //Other Methods
    public void addToInventory(String Elem, int Quantity) { this.inventory.add(Elem, Quantity); }

    public void removeFromInventory(String Elem, int Quantity) {
        try {
            this.inventory.subtract(Elem, Quantity);
        }  catch (ArithmeticException e) {
            //Pop-Up d'interaction impossible
        }
    }

    public void transferTo(Entity_useless target, String Elem, int Quantity) {
            this.removeFromInventory(Elem, Quantity);
            target.addToInventory(Elem, Quantity);
    }

//    @Override
//    public boolean reduceHp(int value) {
//        if (value < this.currentHp){
//            this.currentHp -= value;
//            return true;
//        }
//        else {
//            currentHp = 0;
//            return false;
//        }
//    }
}
