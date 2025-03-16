package gui.process;


import config.GameConfiguration;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

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
    public void paint(Graphics2D g2d){
        g2d.drawImage(ImageStock.getImage(this), 0, 0,null);
    }

    public int getIFrame() {
        return iFrame;
    }

    public void setIFrame(int iFrame) {
        this.iFrame = iFrame;
    }

}