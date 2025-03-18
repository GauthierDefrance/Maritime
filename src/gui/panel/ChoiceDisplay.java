package gui.panel;

import config.GameConfiguration;
import engine.MapGame;
import engine.entity.Harbor;
import gui.PopUp;
import gui.process.ImageStock;
import gui.process.PaintBackGround;
import gui.process.PaintEntity;
import gui.process.PaintPopUp;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * @author Kenan Ammad
 * Classe GameDisplay
 * @version 0.4
 */
public class ChoiceDisplay extends JPanel {

    private final int state;
    private final PaintEntity paintEntity;
    private final PaintBackGround paintBackGround;
    private final PaintPopUp paintPopUp;
    private Harbor harborPlayer;
    private Harbor harborBot;

    /**
     * Typical constructor generating an GameDisplay
     */
    public ChoiceDisplay(int state){
        this.state = state;
        this.paintEntity = new PaintEntity();
        this.paintBackGround = new PaintBackGround();
        this.paintPopUp = new PaintPopUp();
    }

    /**
     * It paints everything that has to be painted on graphics2D
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        double scale = Math.min((double)getWidth()/640,(double) getHeight() /360);
        g2d.scale(scale,scale);
        paintBackGround.paint(g2d);
        g2d.scale((double) 1 /GameConfiguration.GAME_SCALE, (double) 1 /GameConfiguration.GAME_SCALE);

        if (state == 1){
            if (harborBot != null){
                g2d.setColor(ImageStock.colorChoice(harborBot.getColor()));
                g2d.fillOval((int)(harborBot.getPosition().getX())-((int)GameConfiguration.HITBOX_BOAT/2),(int)(harborBot.getPosition().getY()), (int) GameConfiguration.HITBOX_BOAT, (int) GameConfiguration.HITBOX_BOAT);
                g2d.setColor(Color.black);
            }
            if (harborPlayer != null){
                g2d.setColor(ImageStock.colorChoice(harborPlayer.getColor()));
                g2d.fillOval((int)(harborPlayer.getPosition().getX())-((int)GameConfiguration.HITBOX_BOAT/2),(int)(harborPlayer.getPosition().getY()), (int) GameConfiguration.HITBOX_BOAT, (int) GameConfiguration.HITBOX_BOAT);
                g2d.setColor(Color.black);
            }
        }

        for (Harbor harbor : MapGame.getInstance().getLstHarbor()){
            paintEntity.paint(harbor,g2d);
        }
        if (state == 0){}

        ArrayList<PopUp> lstPopUp = new ArrayList<>();
        lstPopUp.addAll(MapGame.getInstance().getLstPopUp());
        for (PopUp popUp : lstPopUp){
            paintPopUp.paint(popUp,g2d);
        }
        g2d.scale(GameConfiguration.GAME_SCALE,GameConfiguration.GAME_SCALE);
    }

    public PaintBackGround getPaintBackGround() { return paintBackGround; }

    public void setHarborBot(Harbor harborBot) {
        this.harborBot = harborBot;
    }

    public void setHarborPlayer(Harbor harborPlayer) {
        this.harborPlayer = harborPlayer;
    }
}
