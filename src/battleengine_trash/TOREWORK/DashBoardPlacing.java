package battleengine_trash.TOREWORK;

import battleengine_trash.engine.SpawnZone;
import config.GameConfiguration;
import engine.entity.boats.Boat;
import engine.entity.boats.Fleet;
import gui.PaintEntity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Classe servant à créer une map ou sont affiché les objets boat appartenant à deux teams passé en paramètre.
 */
public class DashBoardPlacing extends JPanel {
    private Fleet A;
    private Fleet B;
    private PaintEntity painter;
    private Boat currentBoat;
    private boolean isLocked;
    private SpawnZone zoneA, zoneB;
    private BattlePlacingPanel battleplacingpanel;

    public DashBoardPlacing(PaintEntity painter, Fleet A, Fleet B, SpawnZone zoneA, SpawnZone zoneB, BattlePlacingPanel battleplacingpanel) {
        this.battleplacingpanel = battleplacingpanel;
        this.zoneA = zoneA;
        this.zoneB = zoneB;
        this.painter = painter;
        this.A = A;
        this.B = B;
        addMouseListener(new MouseListener());
        addMouseMotionListener(new MouseListener());
    }

    public void setCurrentBoat(Boat currentBoat) {
        this.currentBoat = currentBoat;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(new Color(0, 0, 255, GameConfiguration.SPAWN_ZONE_OPACITY));
        Point tmp = zoneA.getTopLeft();
        g2d.fillRect((int) tmp.getX(), (int) tmp.getY(), zoneA.getWidth(), zoneA.getHeight());

        g2d.setColor(new Color(255, 0, 0, GameConfiguration.SPAWN_ZONE_OPACITY));
        tmp = zoneB.getTopLeft();
        g2d.fillRect((int) tmp.getX(), (int) tmp.getY(), zoneB.getWidth(), zoneB.getHeight());


        if (currentBoat != null) {
            painter.paint(currentBoat, g2d);
        }

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        if (A != null) {
            for (Boat boat : A.getArrayListBoat()) {
                painter.paint(boat, g2d);
            }
        }
        if (B != null) {
            for (Boat boat : B.getArrayListBoat()) {
                painter.paint(boat, g2d);
            }
        }
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
            if (currentBoat != null) {
                if (zoneA.isPlaceable(currentBoat)) {
                    currentBoat.setPosition(e.getX(), e.getY());
                    A.add(currentBoat);
                    System.out.println(battleplacingpanel.getButtonHashMap());

                    if (battleplacingpanel.getButtonHashMap().containsKey(currentBoat)) {
                        battleplacingpanel.getButtonPanel().remove(battleplacingpanel.getButtonHashMap().get(currentBoat));
                    }
                }
                //battleplacingpanel.repaint(); // les repaint doivent pas être là
                battleplacingpanel.getScrollPane().repaint();
                currentBoat = null;
                repaint();  // les repaint doivent pas être là
            }

        }


        public void RightClick(MouseEvent e) {

        }


        @Override
        public void mouseMoved(MouseEvent e) {
            if (currentBoat != null) {
                currentBoat.setPosition(e.getX(), e.getY());
                repaint(); // les repaint doivent pas être là
            }
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (currentBoat != null) {
                double x1 = e.getX();
                double y1 = e.getY();
                double x2 = currentBoat.getPosition().getX();
                double y2 = currentBoat.getPosition().getY();
                double distance = currentBoat.getPosition().distance(new Point(e.getX(), e.getY()));
                currentBoat.setAngle(Math.atan2(y1 - y2, x1 - x2));
                repaint(); // les repaint doivent pas être là
            }
        }


    }
}



