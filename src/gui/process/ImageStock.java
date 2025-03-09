package gui.process;

import config.GameConfiguration;
import engine.entity.Harbor;
import engine.entity.boats.Boat;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageStock {

    private final BufferedImage[][] tbSprite;
    private final BufferedImage[][] tbFramesSprite;
    private static ImageStock instance;

    private ImageStock(){
        tbSprite = new BufferedImage[5][4];
        tbFramesSprite = new BufferedImage[6][GameConfiguration.NUMBER_OF_BACK_GROUND_FRAMES];
        try {
            tbSprite[0][0] = ImageIO.read(new File(GameConfiguration.START_FILE_PATH+"/boat/standard.png"));
            tbSprite[0][1] = ImageIO.read(new File(GameConfiguration.START_FILE_PATH+"/boat/standard/red.png"));
            tbSprite[0][2] = ImageIO.read(new File(GameConfiguration.START_FILE_PATH+"/boat/standard/blue.png"));

            tbSprite[1][0] = ImageIO.read(new File(GameConfiguration.START_FILE_PATH+"/boat/fodder.png"));
            tbSprite[1][1] = ImageIO.read(new File(GameConfiguration.START_FILE_PATH+"/boat/fodder/red.png"));
            tbSprite[1][2] = ImageIO.read(new File(GameConfiguration.START_FILE_PATH+"/boat/fodder/blue.png"));

            tbSprite[2][0] = ImageIO.read(new File(GameConfiguration.START_FILE_PATH+"/boat/merchant.png"));
            tbSprite[2][1] = ImageIO.read(new File(GameConfiguration.START_FILE_PATH+"/boat/merchant/red.png"));
            tbSprite[2][2] = ImageIO.read(new File(GameConfiguration.START_FILE_PATH+"/boat/merchant/blue.png"));

            tbSprite[3][0] = ImageIO.read(new File(GameConfiguration.START_FILE_PATH+"/boat/military.png"));
            tbSprite[3][1] = ImageIO.read(new File(GameConfiguration.START_FILE_PATH+"/boat/military/red.png"));
            tbSprite[3][2] = ImageIO.read(new File(GameConfiguration.START_FILE_PATH+"/boat/military/blue.png"));

            tbSprite[4][0] = ImageIO.read(new File(GameConfiguration.START_FILE_PATH+"/harbor/harbor.png"));
            tbSprite[4][1] = ImageIO.read(new File(GameConfiguration.START_FILE_PATH+"/harbor/red.png"));
            tbSprite[4][2] = ImageIO.read(new File(GameConfiguration.START_FILE_PATH+"/harbor/blue.png"));

            for (int i = 0; i < GameConfiguration.NUMBER_OF_BACK_GROUND_FRAMES; i++) {
                tbFramesSprite[0][i] = (ImageIO.read(new File(GameConfiguration.START_FILE_PATH+"/background/background-"+i+".png")));
                tbFramesSprite[1][i] = (ImageIO.read(new File(GameConfiguration.START_FILE_PATH+"/popup/popup+.png")));
                tbFramesSprite[2][i] = (ImageIO.read(new File(GameConfiguration.START_FILE_PATH+"/popup/popup-.png")));
            }

        } catch (Exception e) {
            System.err.println(e+"error can't find image sprite");
        }
    }

    /**
     * Allows you to choose the image to displayed
     */
    public static BufferedImage getImage(Boat boat){
        if(instance == null)instance = new ImageStock();
        int i = 0;
        int j = 0;

        switch (boat.getClass().getName()) {
            case "engine.entity.boats.Standard" : {
                i=0 ;
                break;
            }
            case "engine.entity.boats.Fodder" :{
                i=1;
                break;
            }
            case "engine.entity.boats.Merchant" :{
                i=2;
                break;
            }
            case "engine.entity.boats.Military" :{
                i=3;
                break;
            }
            default : {
            }
        }
        switch (boat.getColor()) {
            case "red" :{
                j=1;
                break;
            }
            case "blue" :{
                j=2;
                break;
            }
            default : {
            }
        }
        return instance.getTbSprite(i,j);
    }

    /**
     * Allows you to choose the image to displayed
     */
    public static BufferedImage getImage(Harbor harbor){
        if(instance == null)instance = new ImageStock();
        int j = 0;
        switch (harbor.getColor()) {
            case "red" :{
                j=1;
                break;
            }
            case "blue" :{
                j=2;
                break;
            }
            default : {
            }
        }

        return instance.getTbSprite(4,j);
    }

    /**
     * Allows you to choose the image to displayed
     */
    public static BufferedImage getImage(PopUp popUp){
        if(instance == null)instance = new ImageStock();
        switch (popUp.getName()) {
            case "+" :{
                return instance.getTbFramesSprite(1,popUp.getIFrame());
            }
            case "-" :{
                return instance.getTbFramesSprite(2,popUp.getIFrame());
            }
            case "02" :{
                return instance.getTbFramesSprite(3,popUp.getIFrame());
            }
            case "03" :{
                return instance.getTbFramesSprite(4,popUp.getIFrame());
            }
            case "04" :{
                return instance.getTbFramesSprite(5,popUp.getIFrame());
            }
            default : {
            }
        }
        return instance.getTbFramesSprite(1,popUp.getIFrame());
    }

    /**
     * Allows you to choose the image to displayed
     */
    public static BufferedImage getImage(PaintBackGround backGround){
        if(instance == null)instance = new ImageStock();
        return instance.getTbFramesSprite(0,backGround.getIFrame());
    }

    /**
     * Allows you to choose the color to displayed
     */
    public static Color colorChoice(String color){
        switch (color) {
            case "red" :{
                return new Color(255,0,0,75);
            }
            case "blue" :{
                return new Color(0,0,255,75);
            }
            default : {
                return new Color(0,0,0,0);
            }
        }
    }

    private BufferedImage getTbSprite(int a ,int b) {
        return tbSprite[a][b];
    }

    private BufferedImage getTbFramesSprite(int a ,int b) {
        return tbFramesSprite[a][b];
    }

}
