package maritime.entity;

import maritime.inventory.*;
import java.awt.*;

public abstract class Entity {
    private final String idModel;
    private String name;
    private int visionRadius;
    private int maxHp;
    private int currentHp;
    private Inventory inventory;
    private Point position;

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
    public void transferTo(String Elem, int Quantity) {}//Prends les éléments d'un inventaire et les donne à un autre


    public boolean damaged(int value) {
        if (value < this.currentHp){
            this.currentHp -= value;
            return true;
        }
        else {
            currentHp = 0;
            return false;
        }
    }
}
