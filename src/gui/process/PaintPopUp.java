package gui.process;

import config.GameConfiguration;
import engine.Map;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Kenan Ammad
 * Classe PaintPopUp
 * @version 0.4
 */
public class PaintPopUp {

    /**
     * Typical constructor generating an PaintPopUp
     */
    public PaintPopUp(){}

    /**
     * It paints a popUp that needs to be painted on 2D graphics
     */
    public void paint(PopUp popUp, Graphics2D g2d) {

        if (popUp.getIFrame() < GameConfiguration.NUMBER_OF_BACK_GROUND_FRAMES) {
            BufferedImage sprite = ImageStock.getImage(popUp);
            if (sprite != null) {
                g2d.drawImage(sprite, (int) (popUp.getPosition().getX()) - (sprite.getWidth() / 2), ((int) (popUp.getPosition().getY()) - (sprite.getHeight() / 2))-(popUp.getIFrame()*2), null);
            } else {
                g2d.setColor(Color.MAGENTA);
                g2d.fillOval((int) (popUp.getPosition().getX()) - 10, (int) (popUp.getPosition().getY()) - 10, 20, 20);
                g2d.setColor(Color.black);
            }
            popUp.addIFrame(1);
        }
        else {Map.getInstance().removePopUp(popUp);}
    }
}
