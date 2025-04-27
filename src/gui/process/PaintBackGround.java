package gui.process;

import gui.utilities.ImageStock;

import java.awt.*;

/**
 * @author Kenan Ammad
 * Classe PaintBackGround
 * @version 0.2
 */
public class PaintBackGround {
    private int iFrame;

    /**
     * Typical constructor generating an PaintBackGround
     */
    public PaintBackGround(){
        this.iFrame = 0;
    }

    /**
     * Paints a backGround that needs to be painted on 2D graphics
     */
    public void paint(Graphics2D g2d,boolean map){
        g2d.drawImage(ImageStock.getImage(this), 0, 0,null);
        if (map) g2d.drawImage(ImageStock.getImageMap(this), 0, 0,null);
    }

    public int getIFrame() {
        return iFrame;
    }

    public void setIFrame(int iFrame) {
        this.iFrame = iFrame;
    }

}