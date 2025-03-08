package battleengine_trash.gui;

import battleengine_trash.engine.Battle;
import battleengine_trash.engine.SpawnZone;
import battleengine_trash.process.FightManager;
import config.GameConfiguration;
import engine.entity.boats.Boat;
import gui.process.PaintEntity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Classe qui gère l'affichage du DashBoard lors de la phase de placements de bateaux.
 * Cette classe est nécessaire car il y a des comportements particuliers entre phase combat et phase placements.
 * @author Gauthier Defrance
 * @version 0.2
 */
public class DashBoardPlacingPanel extends JPanel {

    private Battle battle;

    public DashBoardPlacingPanel(Battle battle) {
        super(new BorderLayout());
        this.battle = battle;
        addMouseListener(new MouseListener());
        addMouseMotionListener(new MouseListener());
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        PaintEntity paintEntity = this.battle.getPaintEntity();
        FightManager fightManager = this.battle.getFightManager();

        // Dessin zone de spawn
        SpawnZone tmp = this.battle.getPlacing().getSpawnzone();
        g2d.setColor(new Color(0, 0, 255, GameConfiguration.SPAWN_ZONE_OPACITY));
        Point tmpP = tmp.getTopLeft();
        g2d.fillRect((int) tmpP.getX(), (int) tmpP.getY(), tmp.getWidth(), tmp.getHeight());

        tmp = this.battle.getPlacing().getSpawnzoneEnnemy();
        g2d.setColor(new Color(255, 0, 0, GameConfiguration.SPAWN_ZONE_OPACITY));
        tmpP = tmp.getTopLeft();
        g2d.fillRect((int) tmpP.getX(), (int) tmpP.getY(), tmp.getWidth(), tmp.getHeight());
        // ------------

        //Dessin bateaux allié déjà présent
        if ( this.battle.getBattleMap().getTeamA() != null ) {
            for (Boat boat : this.battle.getBattleMap().getTeamA().getArrayListBoat()) {
                paintEntity.paint(boat, g2d);
            }
        }
        //---------

        //Dessin bateaux ennemis déjà présent
        if (this.battle.getBattleMap().getTeamB() != null) {
            for (Boat boat : this.battle.getBattleMap().getTeamB().getArrayListBoat()) {
                paintEntity.paint(boat, g2d);
            }
        }
        //---------

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        // Dessin bateau sélectionné
        if (this.battle.getPlacing().getCurrentBoat() != null) {
            paintEntity.paint(this.battle.getPlacing().getCurrentBoat(), g2d);
        }
        // -----------
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        //Dessin bateaux en cours de placement allié
        if ( this.battle.getPlacing().getBoatCurrentlyBeingPlaced() != null ) {
            for (Boat boat :  this.battle.getPlacing().getBoatCurrentlyBeingPlaced().getArrayListBoat()) {
                paintEntity.paint(boat, g2d);
            }
        }
        //---------

    }

    private class MouseListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                LeftClick(e);
            } else if (e.getButton() == MouseEvent.BUTTON3) {
                RightClick(e);
            }
        }

        public void LeftClick(MouseEvent e) {
            if (battle.getPlacingManager().isPlacable()) {
                Boat tmp = battle.getPlacing().getCurrentBoat();
                battle.getPlacingManager().placeBoat();
                battle.getPlacing().getSpawnzone().addPlacedBoat(tmp);
            }
        }

        public void RightClick(MouseEvent e) {

        }

        @Override
        public void mouseMoved(MouseEvent e) {
            if (battle.getPlacing().getCurrentBoat() != null) {
                battle.getPlacing().getCurrentBoat().setPosition(e.getX(), e.getY());

            }
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            Boat tmp = battle.getPlacing().getCurrentBoat();
            if (tmp != null) {
                double x1 = e.getX();
                double y1 = e.getY();
                double x2 = tmp.getPosition().getX();
                double y2 = tmp.getPosition().getY();
                double distance = tmp.getPosition().distance(new Point(e.getX(), e.getY()));
                tmp.setAngle(Math.atan2(y1 - y2, x1 - x2));
            }
        }
    }





}
