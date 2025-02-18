package maritime.gui;


import maritime.config.GameConfiguration;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

/**
 * @author @Kenan Ammad
 * Classe PaintBackGround
 * @version 0.1
 */
public class PaintBackGround {

    private final ArrayList<BufferedImage> frames = new ArrayList<>();
    private int currentFrame = 0;

    /**
     * Typical constructor generating an PaintBackGround
     */
    public PaintBackGround(){
        try {
            for (int i = 0; i < GameConfiguration.NUMBER_OF_BACK_GROUND_FRAMES; i++) {
                frames.add(ImageIO.read(new File("src/main/resources/images/background/background-"+i+".png")));
            }
        } catch (Exception e) {
            System.err.println(e+"error can't find image BackGround");
        }
    }

    /**
     * Paints a backGround that needs to be painted on 2D graphics
     */
    public void paint(Graphics2D g2d){
        if (frames.size() > currentFrame){
        g2d.drawImage(frames.get(currentFrame), 0, 0,null);
        currentFrame = (currentFrame + 1) % frames.size();
        }
    }
}