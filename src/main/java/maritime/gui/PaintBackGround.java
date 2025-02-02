package maritime.gui;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import static maritime.config.GameConfiguration.NUMBER_OF_BACK_GROUND_FRAMES;

public class PaintBackGround {

    private final ArrayList<BufferedImage> frames = new ArrayList<>();
    private int currentFrame = 0;

    public PaintBackGround(){
        try {
            for (int i = 0; i < NUMBER_OF_BACK_GROUND_FRAMES; i++) {
                frames.add(ImageIO.read(new File("D:\\github\\Maritime2\\src\\main\\resources\\pif" + i + ".png")));
            }
        } catch (Exception e) {
            System.err.println("rip");
        }
    }

    public void paint(Graphics2D g2d){

        g2d.drawImage(frames.get(currentFrame), 0, 0,null);
        currentFrame = (currentFrame + 1) % frames.size();
    }
}