package battleengine_trash.gui;

import battleengine_trash.engine.Battle;
import engine.entity.boats.Boat;
import engine.entity.boats.Fleet;
import engine.process.BoatManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class DashBoardFightPanel extends JPanel {

    private Battle battle;

    public DashBoardFightPanel() {
        super(new BorderLayout());
        this.battle = battle;
        addMouseListener(new MouseListener());
        addMouseMotionListener(new MouseListener());
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
            ArrayList<Boat> tmp = BoatManager.boatCollisionToPoint(e.getPoint(),battle.getBattleMap().getTeamA().getArrayListBoat());
            for(Boat boat : tmp){
            }
        }

        public void RightClick(MouseEvent e) {

        }

        @Override
        public void mouseMoved(MouseEvent e) {

        }

        @Override
        public void mouseDragged(MouseEvent e) {
        }
    }


}
