package battleengine_trash.gui;

import battleengine_trash.engine.SpawnZone;
import battleengine_trash.process.FightManager;
import battleengine_trash.process.PlacingManager;
import config.GameConfiguration;
import engine.entity.boats.Boat;
import gui.PaintEntity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Classe qui gère l'affichage du DashBoard lors de la phase de placements de bateaux.
 * Cette classe est nécessaire car il y a des comportements particuliers entre phase combat et phase placements.
 * @author Gauthier Defrance
 * @version 0.1
 */
public class DashBoardPlacingPanel extends JPanel {

    private FightManager fightManager;
    private PlacingManager placingManager;
    private PaintEntity paintEntity;

    public DashBoardPlacingPanel(FightManager fightManager, PlacingManager placingManager) {
        super(new BorderLayout());
        this.fightManager = fightManager;
        this.placingManager = placingManager;
        paintEntity = new PaintEntity();
        addMouseListener(new MouseListener());
        addMouseMotionListener(new MouseListener());
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Dessin zone de spawn
        SpawnZone tmp = placingManager.getSpawnzone();
        g2d.setColor(new Color(0, 0, 255, GameConfiguration.SPAWN_ZONE_OPACITY));
        Point tmpP = tmp.getTopLeft();
        g2d.fillRect((int) tmpP.getX(), (int) tmpP.getY(), tmp.getWidth(), tmp.getHeight());

        tmp = placingManager.getSpawnzoneEnnemy();
        g2d.setColor(new Color(255, 0, 0, GameConfiguration.SPAWN_ZONE_OPACITY));
        tmpP = tmp.getTopLeft();
        g2d.fillRect((int) tmpP.getX(), (int) tmpP.getY(), tmp.getWidth(), tmp.getHeight());
        // ------------

        //Dessin bateaux allié déjà présent
        if ( fightManager.getA() != null ) {
            for (Boat boat : fightManager.getA().getArrayListBoat()) {
                paintEntity.paint(boat, g2d);
            }
        }
        //---------

        //Dessin bateaux ennemis déjà présent
        if (fightManager.getB() != null) {
            for (Boat boat : fightManager.getB().getArrayListBoat()) {
                paintEntity.paint(boat, g2d);
            }
        }
        //---------

        // Dessin bateau sélectionné
        if (placingManager.getCurrentBoat() != null) {
            System.out.println(placingManager.getCurrentBoat().getPosition());
            paintEntity.paint(placingManager.getCurrentBoat(), g2d);
        }
        // -----------

        //Dessin bateaux en cours de placement allié
        if ( placingManager.getBoatCurrentlyBeingPlaced() != null ) {
            for (Boat boat :  placingManager.getBoatCurrentlyBeingPlaced().getArrayListBoat()) {
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
            if (placingManager.isPlacable()) {
                Boat tmp = placingManager.getCurrentBoat();
                placingManager.placeBoat();
                placingManager.getSpawnzone().addPlacedBoat(tmp);
            }
        }

        public void RightClick(MouseEvent e) {

        }

        @Override
        public void mouseMoved(MouseEvent e) {
            //System.out.println("mouvement détecté");
            if (placingManager.getCurrentBoat() != null) {
                placingManager.getCurrentBoat().setPosition(e.getX(), e.getY());
                repaint();
                //System.out.println(placingManager.getCurrentBoat()+" a été déplacé");
            }
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            Boat tmp = placingManager.getCurrentBoat();
            if (tmp != null) {
                double x1 = e.getX();
                double y1 = e.getY();
                double x2 = tmp.getPosition().getX();
                double y2 = tmp.getPosition().getY();
                double distance = tmp.getPosition().distance(new Point(e.getX(), e.getY()));
                tmp.setAngle(Math.atan2(y1 - y2, x1 - x2));
                repaint();
            }
        }
    }





}
