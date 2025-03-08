package gui.process;

import config.GameConfiguration;
import config.MapBuilder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * @author @Kenan Ammad
 * @version 0.2
 */
public class PaintPopUp {
    private BufferedImage[][] tbSprite;
    private MapBuilder map;

    /**
     * Typical constructor generating an PaintPopUp
     */
    public PaintPopUp(MapBuilder map){
        this.map=map;
        tbSprite = new BufferedImage[5][GameConfiguration.NUMBER_OF_BACK_GROUND_FRAMES];
        try {
            for (int i = 0; i < GameConfiguration.NUMBER_OF_BACK_GROUND_FRAMES; i++) {
                tbSprite[0][i] = (ImageIO.read(new File(GameConfiguration.START_FILE_PATH+"/popup/popup+.png")));
                tbSprite[1][i] = (ImageIO.read(new File(GameConfiguration.START_FILE_PATH+"/popup/popup-.png")));
            }
        } catch (Exception e) {
            System.err.println(e+"error can't find image PopUp");
        }

    }

    /**
     * It paints a popUp that needs to be painted on 2D graphics
     */
    public void paint(PopUp popUp, Graphics2D g2d) {

        if (popUp.getIFrame() < GameConfiguration.NUMBER_OF_BACK_GROUND_FRAMES) {
            BufferedImage[] sprite = spriteChoice(popUp.getName());
            if (sprite[popUp.getIFrame()] != null) {
                g2d.drawImage(sprite[popUp.getIFrame()], (int) (popUp.getPosition().getX()) - (sprite[popUp.getIFrame()].getWidth() / 2), ((int) (popUp.getPosition().getY()) - (sprite[popUp.getIFrame()].getHeight() / 2))-(popUp.getIFrame()*2), null);
            } else {
                g2d.setColor(Color.MAGENTA);
                g2d.fillOval((int) (popUp.getPosition().getX()) - 10, (int) (popUp.getPosition().getY()) - 10, 20, 20);
                g2d.setColor(Color.black);
            }
            popUp.addIFrame(1);
        }
        else {map.removePopUp(popUp);}
    }

    /**
     * Allows you to choose the sprite to displayed
     */
    private BufferedImage[] spriteChoice(String name){
        switch (name) {
            case "+" :{
                return tbSprite[0];
            }
            case "-" :{
                return tbSprite[1];
            }
            case "02" :{
                return tbSprite[2];
            }
            case "03" :{
                return tbSprite[3];
            }
            case "04" :{
                return tbSprite[4];
            }
            default : {
                return tbSprite[0];
            }
        }
    }

}
