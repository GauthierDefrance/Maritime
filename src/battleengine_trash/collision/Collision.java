package battleengine_trash.collision;

import config.*;

import java.awt.*;


/**
 * @author @Gauthier Defrance
 * Classe Collision
 * @version 0.1
 * */
public final class Collision {

    /**
     * Méthode permettant de savoir si deux bateaux sont en collisions.
     * Elle peut être utilisé pour savoir si un boulet de cannon a touché un bateau.
     * @param p1 Le point de votre bateau
     * @param p2 Le point de l'autre bateau
     * @return boolean true si il y a collision false sinon
     */
    public static boolean CollisionCircle(Point p1, Point p2) {
        return p1.distance(p2)< maritime.config.GameConfiguration.HITBOX_BOAT;
    }

    /**
     * Méthode permettant de savoir si un bateau se trouve dans un Rectangle représentant la zone d'apparition.
     * @param rect Un objet de type rectangle non incliné
     * @param p Un objet point qui représente votre bateau et indique si il se trouve dans la zone du rectangle
     * @return boolean true si le point se trouve dans le rectangle.
     */
    public static boolean CollisionRectangle(Rectangle rect, Point p) {
        return rect.contains(p);
    }

}
