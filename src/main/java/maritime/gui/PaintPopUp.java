package maritime.gui;

import maritime.config.GameConfiguration;
import maritime.config.GameInitFactory;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PaintPopUp {
    private BufferedImage[][] tbSprite;
    private GameInitFactory map;

    public PaintPopUp(GameInitFactory map){
        this.map=map;

    }


    public void paint(PopUp popUp, Graphics2D g2d) {

        if (popUp.getIFrame() < GameConfiguration.NUMBER_OF_BACK_GROUND_FRAMES) {
            BufferedImage[] sprite = spriteChoice(popUp.getName());
            if (sprite[popUp.getIFrame()] != null) {
                g2d.drawImage(sprite[popUp.getIFrame()], (int) (popUp.getPosition().getX()) - (sprite[popUp.getIFrame()].getWidth() / 2), (int) (popUp.getPosition().getY()) - (sprite[popUp.getIFrame()].getHeight() / 2), null);
            } else {
                g2d.setColor(Color.MAGENTA);
                g2d.fillOval((int) (popUp.getPosition().getX()) - 10, (int) (popUp.getPosition().getY()) - 10, 20, 20);
                g2d.setColor(Color.black);
            }
            popUp.addIFrame(1);
        }
        else {map.removePopUp(popUp);}
    }

    private BufferedImage[] spriteChoice(String name){
        switch (name) {
            case "00" ->{
                return tbSprite[0];
            }
            case "01" ->{
                return tbSprite[1];
            }
            case "02" ->{
                return tbSprite[2];
            }
            case "03" ->{
                return tbSprite[3];
            }
            case "04" ->{
                return tbSprite[4];
            }
            default -> {
                return tbSprite[0];
            }
        }
    }

}
