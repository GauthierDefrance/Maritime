package maritime.entity;

import maritime.inventory.*;
import java.awt.*;

public abstract class Entity {
    private String name;
    private int visionRadius;
    private Inventory inventory;
    private int maxHp;
    private int currentHp;
    private Point position;

    private String idModel;

    public Entity(String name, int visionRadius, int maxHp, Point position, String idModel) {
        this.name = name;
        this.visionRadius = visionRadius;
        this.inventory = new Inventory();
        this.maxHp = maxHp;
        this.currentHp = maxHp;
        this.position = position;
        this.idModel = idModel;
    }
    //Getters
    public String getName() { return name; }
    public Inventory getInventory() { return inventory; }
    public int getVisionRadius() { return visionRadius; }

    public int getCurrentHp() { return currentHp; }
    public int getMaxHp() { return maxHp; }
    public Point getPosition() { return position; }

    public String getIDModel() { return idModel; }

    //Setters
    public void setVisionRadius(int visionRadius) { this.visionRadius = visionRadius; }

    /* Dans le cas où l'on souhaite pouvoir modifier les noms, par rapport lors d'une amélioration */
    public void setName(String name) {this.name = name; }

    /**
     * Setter qui permet de modifier la position actuel du point.
     * Prend en paramètre un objet de type Point.
     * @param position
     */
    public void setPosition(Point position) {this.position = position; }

    /**
     * Setter qui permet de modifier la position actuel du point.
     * Prend en paramètre deux Int, respectivement x et y.
     * @param x
     * @param y
     */
    public void setPosition(int x, int y){
        position.setLocation(x,y);
    }

    //Other Methods
    public void addToInventory(String Elem, int Quantity) {}
    public void removeFromInventory(String Elem, int Quantity) {}

    public void damaged(int value) {
        if (value < this.currentHp) this.currentHp -= value;
        else { currentHp = 0; down(); }
    }
    public void down(){}
}
