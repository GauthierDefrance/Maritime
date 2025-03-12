package battleengine.entity;

import config.GameConfiguration;
import engine.entity.boats.Boat;
import engine.entity.boats.Fleet;

import java.awt.*;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;

/**
 * Classe servant à la représentation de zones de spawn
 * sert également au placement correct des bateaux
 * @author Gauthier Defrance
 * @version 0.3
 */
public class SpawnZone extends Dimension2D {
    private String color;
    private Point2D positiontl;
    private Point2D positionbr;
    private int width;
    private int height;
    private Fleet placedList;

    public SpawnZone(Point topLeft, int width, int height, String color, Fleet BoatsCurrentlyBeingPlaced) {
        super();
        this.setSize(width, height);
        this.positiontl = topLeft;
        this.positionbr = new Point(topLeft.x+width, topLeft.y+height);
        this.color = color;
        this.placedList = BoatsCurrentlyBeingPlaced;
    }

    /**
     * @param boat
     * @return bool Indique si on peut placer un bateau à sa position actuelle.
     */
    public boolean isPlaceable(Boat boat) {
        //On vérifie si le bateau se trouve dans la zone de spawn lors du placement
        if (!this.contains(boat.getPosition())) {
            return false;
        }
        //On vérifie que le bateau n'est pas trop proche d'un autre bateau
        for (Boat placedBoat : this.placedList.getArrayListBoat()) {
            if (boat.getPosition().distance(placedBoat.getPosition())< GameConfiguration.HITBOX_BOAT ) {
                return false;
            }
        }
        return true;
    }

    /**
     * Retourne la liste des flottes placées.
     * @return La liste des flottes placées (type {@link Fleet}).
     */
    public Fleet getPlacedList() {
        return placedList;
    }

    /**
     * Vérifie si un point donné est contenu dans la zone définie par les positions
     * du coin supérieur gauche (positiontl) et du coin inférieur droit (positionbr).
     *
     * @param point Le point à vérifier, représenté par un objet {@link Point}.
     * @return {@code true} si le point est dans la zone, {@code false} sinon.
     */
    public boolean contains(Point point) {
        return point.x > positiontl.getX() && point.x < positionbr.getX() &&
                point.y > positiontl.getY() && point.y < positionbr.getY();
    }

    /**
     * Retourne la largeur de la zone.
     *
     * @return La largeur de la zone, en unités de type {@code double}.
     */
    @Override
    public double getWidth() {
        return width;
    }

    /**
     * Retourne la hauteur de la zone.
     *
     * @return La hauteur de la zone, en unités de type {@code double}.
     */
    @Override
    public double getHeight() {
        return height;
    }

    /**
     * Définit la taille de la zone en spécifiant la largeur et la hauteur.
     *
     * @param width La largeur de la zone, exprimée en {@code double}. La valeur
     *              est convertie en entier pour être affectée à {@code width}.
     * @param height La hauteur de la zone, exprimée en {@code double}. La valeur
     *               est convertie en entier pour être affectée à {@code height}.
     */
    @Override
    public void setSize(double width, double height) {
        this.width = (int) width;
        this.height = (int) height;
    }

}
