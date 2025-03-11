package battleengine_trash.engine;

import java.awt.*;

/**
 * @author @Gauthier Defrance
 * Classe Rectangle
 * @version 0.2
 * */
public class Rectangle {

    private Point tl;
    private Point br;
    private int width;
    private int height;
    private Dimension size;

    /**
     * Classe permettant la représentation d'un rectangle à deux dimensions.
     * Le point en haut à gauche sert de référence.
     * On passe en paramètre sa largeur et hauteur en pixel.
     * @param topLeft
     * @param width
     * @param height
     */
    public Rectangle(Point topLeft, int width, int height) {
        this.tl = topLeft;
        this.br = new Point(topLeft.x+width, topLeft.y+height);
        this.width = width;
        this.height = height;
    }

    /**
     * Renvoie le point haut gauche du Rectangle.
     * @return Point
     */
    public Point getTopLeft() {
        return tl;
    }

    /**
     * Renvoie le point bas droit du Rectangle
     * @return Point
     */
    public Point getBottomRight() {return br;}

    /**
     * Méthode renvoyant un booléan, vrai si le point donné se trouve dans le rectangle.
     * @param point
     * @return boolean
     */
    public boolean contains(Point point) {
        return point.x > tl.getX() && point.x < br.getX() &&
               point.y > tl.getY() && point.y < br.getY();
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
}
