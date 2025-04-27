package gui;

import java.awt.*;
import java.io.Serializable;

/**
 * @author Kenan Ammad
 * @version 1.0
 */
public class PopUp implements Serializable {
    private String name;
    private int iFrame;
    private int maxFrame;
    private Point position;

    /**
     * Typical constructor generating a PopUp
     * @param name identifying name of the PopUP
     * @param position expected location of the PopUp
     */
    public PopUp(String name,Point position,int maxFrame) {
        this.name=name;
        this.maxFrame = maxFrame;
        this.iFrame = 0;
        this.position = position;
    }

    public int getIFrame() {
        return iFrame;
    }

    public void setIFrame(int IFrame) {
        this.iFrame = IFrame;
    }

    public void addIFrame(int IFrame) {
        this.iFrame += IFrame;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public int getMaxFrame() {
        return maxFrame;
    }
}
