package gui.process;

import config.GameConfiguration;
import engine.data.entity.Harbor;
import engine.data.entity.boats.Boat;
import engine.data.Fleet;
import engine.data.faction.Faction;
import engine.data.trading.SeaRoad;
import engine.process.manager.FactionManager;
import gui.PopUp;
import log.LoggerUtility;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * @author Kenan Ammad
 * Classe ImageStock
 * @version 0.2
 */
public class ImageStock {

    private static Logger logger = LoggerUtility.getLogger(ImageStock.class);
    private final BufferedImage[] images;
    private final BufferedImage[][] tbSprite;
    private final BufferedImage[][] tbFramesSprite;
    private static ImageStock instance;

    private ImageStock(){
        images = new BufferedImage[5];
        tbSprite = new BufferedImage[7][6];
        tbFramesSprite = new BufferedImage[6][GameConfiguration.NUMBER_OF_BACK_GROUND_FRAMES];
        try {
            images[0] = ImageIO.read(new File(GameConfiguration.IMG_FILE_PATH+"/fleet.png"));
            images[1] = ImageIO.read(new File(GameConfiguration.IMG_FILE_PATH+"/seaRoad.png"));
            images[2] = ImageIO.read(new File(GameConfiguration.IMG_FILE_PATH+"/chest.png"));

            tbSprite[0][0] = ImageIO.read(new File(GameConfiguration.IMG_FILE_PATH+"/boat/standard.png"));
            tbSprite[0][1] = ImageIO.read(new File(GameConfiguration.IMG_FILE_PATH+"/boat/standard/red.png"));
            tbSprite[0][2] = ImageIO.read(new File(GameConfiguration.IMG_FILE_PATH+"/boat/standard/blue.png"));
            tbSprite[0][3] = ImageIO.read(new File(GameConfiguration.IMG_FILE_PATH+"/boat/standard/purple.png"));
            tbSprite[0][4] = ImageIO.read(new File(GameConfiguration.IMG_FILE_PATH+"/boat/standard/yellow.png"));
            tbSprite[0][5] = ImageIO.read(new File(GameConfiguration.IMG_FILE_PATH+"/boat/standard/green.png"));

            tbSprite[1][0] = ImageIO.read(new File(GameConfiguration.IMG_FILE_PATH+"/boat/fodder.png"));
            tbSprite[1][1] = ImageIO.read(new File(GameConfiguration.IMG_FILE_PATH+"/boat/fodder/red.png"));
            tbSprite[1][2] = ImageIO.read(new File(GameConfiguration.IMG_FILE_PATH+"/boat/fodder/blue.png"));
            tbSprite[1][3] = ImageIO.read(new File(GameConfiguration.IMG_FILE_PATH+"/boat/fodder/purple.png"));
            tbSprite[1][4] = ImageIO.read(new File(GameConfiguration.IMG_FILE_PATH+"/boat/fodder/yellow.png"));
            tbSprite[1][5] = ImageIO.read(new File(GameConfiguration.IMG_FILE_PATH+"/boat/fodder/green.png"));

            tbSprite[2][0] = ImageIO.read(new File(GameConfiguration.IMG_FILE_PATH+"/boat/merchant.png"));
            tbSprite[2][1] = ImageIO.read(new File(GameConfiguration.IMG_FILE_PATH+"/boat/merchant/red.png"));
            tbSprite[2][2] = ImageIO.read(new File(GameConfiguration.IMG_FILE_PATH+"/boat/merchant/blue.png"));
            tbSprite[2][3] = ImageIO.read(new File(GameConfiguration.IMG_FILE_PATH+"/boat/merchant/purple.png"));
            tbSprite[2][4] = ImageIO.read(new File(GameConfiguration.IMG_FILE_PATH+"/boat/merchant/yellow.png"));
            tbSprite[2][5] = ImageIO.read(new File(GameConfiguration.IMG_FILE_PATH+"/boat/merchant/green.png"));

            tbSprite[3][0] = ImageIO.read(new File(GameConfiguration.IMG_FILE_PATH+"/boat/military.png"));
            tbSprite[3][1] = ImageIO.read(new File(GameConfiguration.IMG_FILE_PATH+"/boat/military/red.png"));
            tbSprite[3][2] = ImageIO.read(new File(GameConfiguration.IMG_FILE_PATH+"/boat/military/blue.png"));
            tbSprite[3][3] = ImageIO.read(new File(GameConfiguration.IMG_FILE_PATH+"/boat/military/purple.png"));
            tbSprite[3][4] = ImageIO.read(new File(GameConfiguration.IMG_FILE_PATH+"/boat/military/yellow.png"));
            tbSprite[3][5] = ImageIO.read(new File(GameConfiguration.IMG_FILE_PATH+"/boat/military/green.png"));

            tbSprite[4][0] = ImageIO.read(new File(GameConfiguration.IMG_FILE_PATH+"/harbor/harbor.png"));
            tbSprite[4][1] = ImageIO.read(new File(GameConfiguration.IMG_FILE_PATH+"/harbor/red.png"));
            tbSprite[4][2] = ImageIO.read(new File(GameConfiguration.IMG_FILE_PATH+"/harbor/blue.png"));
            tbSprite[4][3] = ImageIO.read(new File(GameConfiguration.IMG_FILE_PATH+"/harbor/purple.png"));
            tbSprite[4][4] = ImageIO.read(new File(GameConfiguration.IMG_FILE_PATH+"/harbor/yellow.png"));
            tbSprite[4][5] = ImageIO.read(new File(GameConfiguration.IMG_FILE_PATH+"/harbor/green.png"));

            tbSprite[5][0] = ImageIO.read(new File(GameConfiguration.IMG_FILE_PATH+"/boat/destroy/standard.png"));
            tbSprite[5][1] = ImageIO.read(new File(GameConfiguration.IMG_FILE_PATH+"/boat/destroy/fodder.png"));
            tbSprite[5][2] = ImageIO.read(new File(GameConfiguration.IMG_FILE_PATH+"/boat/destroy/merchant.png"));
            tbSprite[5][3] = ImageIO.read(new File(GameConfiguration.IMG_FILE_PATH+"/boat/destroy/military.png"));

            tbSprite[6][0] = ImageIO.read(new File(GameConfiguration.IMG_FILE_PATH+"/faction/faction.png"));
            tbSprite[6][1] = ImageIO.read(new File(GameConfiguration.IMG_FILE_PATH+"/faction/red.png"));
            tbSprite[6][2] = ImageIO.read(new File(GameConfiguration.IMG_FILE_PATH+"/faction/blue.png"));
            tbSprite[6][3] = ImageIO.read(new File(GameConfiguration.IMG_FILE_PATH+"/faction/purple.png"));
            tbSprite[6][4] = ImageIO.read(new File(GameConfiguration.IMG_FILE_PATH+"/faction/yellow.png"));
            tbSprite[6][5] = ImageIO.read(new File(GameConfiguration.IMG_FILE_PATH+"/faction/green.png"));

            for (int i = 0; i < GameConfiguration.NUMBER_OF_BACK_GROUND_FRAMES; i++) {
                tbFramesSprite[0][i] = (ImageIO.read(new File(GameConfiguration.IMG_FILE_PATH+"/background/background-"+i+".png")));
                tbFramesSprite[1][i] = (ImageIO.read(new File(GameConfiguration.IMG_FILE_PATH+"/background/map.png")));
                tbFramesSprite[2][i] = (ImageIO.read(new File(GameConfiguration.IMG_FILE_PATH+"/popup/popup+.png")));
                tbFramesSprite[3][i] = (ImageIO.read(new File(GameConfiguration.IMG_FILE_PATH+"/popup/popup-.png")));
                tbFramesSprite[4][i] = (ImageIO.read(new File(GameConfiguration.IMG_FILE_PATH+"/popup/explosion-"+Math.min(4,i)+".png")));
            }

            logger.info("load image success");
        } catch (Exception e) {
            logger.warn("load image fail : "+e);
        }
    }

    public static ImageStock getInstance() {
        if (instance == null) {
            instance = new ImageStock();
        } return instance;
    }

    /**
     * Allows you to choose the image to displayed
     */
    public static BufferedImage getImage(Boat boat){
        int i = 0;
        int j = 0;

        switch (boat.getClass().getName()) {
            case "engine.data.entity.boats.Standard" : {
                i=0 ;
                break;
            }
            case "engine.data.entity.boats.Fodder" :{
                i=1;
                break;
            }
            case "engine.data.entity.boats.Merchant" :{
                i=2;
                break;
            }
            case "engine.data.entity.boats.Military" :{
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
            case "purple" :{
                j=3;
                break;
            }
            case "yellow" :{
                j=4;
                break;
            }
            case "green" :{
                j=5;
                break;
            }
            case "black" :{
                j=0;
                break;
            }
            default : {
            }
        }
        return getTbSprite(i,j);
    }

    /**
     * Allows you to choose the image to displayed
     */
    public static BufferedImage getImage(Harbor harbor){
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
            case "purple" :{
                j=3;
                break;
            }
            case "yellow" :{
                j=4;
                break;
            }
            case "green" :{
                j=5;
                break;
            }
            case "black" :{
                j=0;
                break;
            }
            default : {
            }
        }

        return getTbSprite(4,j);
    }

    /**
     * Allows you to choose the image to displayed
     */
    public static BufferedImage getImage(Faction faction){
        int j = 0;
        switch (faction.getColor()) {
            case "red" :{
                j=1;
                break;
            }
            case "blue" :{
                j=2;
                break;
            }
            case "purple" :{
                j=3;
                break;
            }
            case "yellow" :{
                j=4;
                break;
            }
            case "green" :{
                j=5;
                break;
            }
            case "black" :{
                j=0;
                break;
            }
            default : {
            }
        }

        return getTbSprite(6,j);
    }

    /**
     * Allows you to choose the image to displayed
     */
    public static BufferedImage getImage(Fleet fleet){
        return getImages(0);
    }

    /**
     * Allows you to choose the image to displayed
     */
    public static BufferedImage getImage(SeaRoad seaRoad){
        return getImages(1);
    }

    /**
     * Allows you to choose the image to displayed
     */
    public static BufferedImage getImage(PopUp popUp){
        switch (popUp.getName()) {
            case "+" :{
                return getTbFramesSprite(2,popUp.getIFrame());
            }
            case "-" :{
                return getTbFramesSprite(3,popUp.getIFrame());
            }
            case "explosion" :{
                return getTbFramesSprite(4,popUp.getIFrame());
            }
            case "03" :{
                return getTbFramesSprite(5,popUp.getIFrame());
            }
            case "04" :{
                return getTbFramesSprite(6,popUp.getIFrame());
            }
            default : {
            }
        }
        return getTbFramesSprite(2,popUp.getIFrame());
    }

    /**
     * Allows you to choose the image to displayed
     */
    public static BufferedImage getImage(PaintBackGround backGround){
        return getTbFramesSprite(0,backGround.getIFrame());
    }

    /**
     * Allows you to choose the image to displayed
     */
    public static BufferedImage getImageMap(PaintBackGround backGround){
        return getTbFramesSprite(1,backGround.getIFrame());
    }

    /**
     * Allows you to choose the image to displayed
     */
    public static BufferedImage getImageDeadBoat(Boat boat){
        switch (boat.getClass().getName()) {
            case "engine.data.entity.boats.Standard" : {
                return  getTbSprite(5,0);
            }
            case "engine.data.entity.boats.Fodder" :{
                return getTbSprite(5,1);
            }
            case "engine.data.entity.boats.Merchant" :{
                return getTbSprite(5,2);
            }
            case "engine.data.entity.boats.Military" :{
                return getTbSprite(5,3);
            }
            default : {
            }
        }
        return getTbFramesSprite(5,0);
    }
    /**
     * Allows you to choose the color to displayed
     */
    public static Color colorChoice(String color){
        if (color.equals("black"))return colorChoice(color, 100);
        else return colorChoice(color, 75);
    }

    /**
     * Allows you to choose the color to displayed
     */
    public static Color colorChoice(String color,int alpha){
        switch (color) {
            case "red" :{
                return new Color(255,0,0,alpha);
            }
            case "blue" :{
                return new Color(0,0,255,alpha);
            }
            case "purple" :{
                return new Color(125, 0, 255, alpha);
            }
            case "yellow" :{
                return new Color(255, 255,0,alpha);
            }
            case "green" :{
                return new Color(0,255,0,alpha);
            }
            case "black" :{
                return new Color(0,0,0, alpha);
            }
            default : {
                return new Color(0,0,0,0);
            }
        }
    }

    public static int getColorInt(String color){
        switch (color) {
            case "red" :{
                return 1;
            }
            case "blue" :{
                return 2;
            }
            case "purple" :{
                return 3;
            }
            case "yellow" :{
                return 4;
            }
            case "green" :{
                return 5;
            }
            case "black" :{
                return 0;
            }
            default : {
            }
            return 0;
        }
    }

    public static Color mixColor(Color color1, Color color2, double ratio) {
        double inverseRatio = 1.0 - ratio;
        int r = (int) (color1.getRed() * inverseRatio + color2.getRed() * ratio);
        int g = (int) (color1.getGreen() * inverseRatio + color2.getGreen() * ratio);
        int b = (int) (color1.getBlue() * inverseRatio + color2.getBlue() * ratio);
        int a = (int) (color1.getAlpha() * inverseRatio + color2.getAlpha() * ratio);
        return new Color(r, g, b, a);
    }

    public static BufferedImage getImages(int a) {
        BufferedImage bufferedImage = getInstance().images[a];
        if (bufferedImage == null) {
            bufferedImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = bufferedImage.createGraphics();
            g2d.setColor(Color.magenta);
            g2d.fillRect(0, 0, 100, 100);
            g2d.setColor(Color.black);
            g2d.fillRect(50, 0, 50, 50);
            g2d.fillRect(0, 50, 50, 50);
            g2d.dispose();
        }
        return bufferedImage;
    }

    public static BufferedImage getTbSprite(int a, int b) {
        BufferedImage bufferedImage = getInstance().tbSprite[a][b];
        if (bufferedImage == null) {
            bufferedImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = bufferedImage.createGraphics();
            g2d.setColor(Color.magenta);
            g2d.fillRect(0, 0, 100, 100);
            g2d.setColor(Color.black);
            g2d.fillRect(50, 0, 50, 50);
            g2d.fillRect(0, 50, 50, 50);
            g2d.dispose();
        }
        return bufferedImage;
    }

    public static BufferedImage getTbFramesSprite(int a ,int b) {
        BufferedImage bufferedImage = getInstance().tbFramesSprite[a][b];
        if (bufferedImage == null) {
            bufferedImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = bufferedImage.createGraphics();
            g2d.setColor(Color.magenta);
            g2d.fillRect(0, 0, 100, 100);
            g2d.setColor(Color.black);
            g2d.fillRect(50, 0, 50, 50);
            g2d.fillRect(0, 50, 50, 50);
            g2d.dispose();
        }
        return bufferedImage;
    }

}
