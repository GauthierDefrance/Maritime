package gui.panel.menu;

import config.GameConfiguration;
import engine.MapGame;
import engine.data.Fleet;
import engine.data.entity.boats.Boat;
import engine.process.manager.FactionManager;
import gui.process.JComponentFactory;
import gui.utilities.GUILoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static gui.MainGUI.getWindow;

public class FleetMenu extends JPanel {

    private boolean isInSeaRoadMode;
    private Fleet activeFleet;

    private JPanel jPanelCenter1;
    private JPanel jPanelCenter2;
    private JPanel jPanelCenter3;
    private JPanel jPanelGrid;
    private JPanel gridPanel1;
    private JPanel gridPanel2;
    private JPanel gridPanel3;

    private JButton goBackButton;
    private JButton activeButton1;
    private JButton activeButton2;
    private JButton activeButton3;

    public FleetMenu(Fleet activeFleet) {
        super();
        this.activeFleet = activeFleet;
        init();
    }


    public void init() {
        this.setLayout(new BorderLayout());

        jPanelCenter1 = JComponentFactory.borderMenuPanel();
        jPanelCenter2 = JComponentFactory.borderMenuPanel();
        jPanelCenter3 = JComponentFactory.borderMenuPanel();
        jPanelGrid = JComponentFactory.gridMenuPanel(0, 1, GameConfiguration.BUTTON_SEPARATOR,GameConfiguration.BUTTON_SEPARATOR);
        goBackButton = JComponentFactory.menuButton("Go back", new goBackButtonListener());
        isInSeaRoadMode = false;

        gridPanel1 = JComponentFactory.gridMenuPanel(0,1);
        JPanel gridPanelTmp1 = JComponentFactory.voidPanel();
        gridPanelTmp1.add(gridPanel1);
        JScrollPane jScrollPane1 = JComponentFactory.ScrollPaneMenuPanel(gridPanelTmp1);
        jScrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        gridPanel2 = JComponentFactory.gridMenuPanel(0,1);
        JPanel gridPanelTmp2 = JComponentFactory.voidPanel();
        gridPanelTmp2.add(gridPanel2);
        JScrollPane jScrollPane2 = JComponentFactory.ScrollPaneMenuPanel(gridPanelTmp2);
        jScrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        gridPanel3 = JComponentFactory.gridMenuPanel(0,1);
        JPanel gridPanelTmp3 = JComponentFactory.voidPanel();
        gridPanelTmp3.add(gridPanel3);
        JScrollPane jScrollPane3 = JComponentFactory.ScrollPaneMenuPanel(gridPanelTmp3);
        jScrollPane3.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);


        jPanelCenter1.add(jScrollPane1,BorderLayout.CENTER);
        jPanelCenter2.add(jScrollPane2,BorderLayout.CENTER);
        jPanelCenter3.add(jScrollPane3,BorderLayout.CENTER);
        jPanelCenter2.add(jPanelGrid,BorderLayout.SOUTH);

        jPanelGrid.add(JComponentFactory.menuButton(""));
        jPanelGrid.add(JComponentFactory.menuButton(""));
        jPanelGrid.add(JComponentFactory.menuButton(""));

        JPanel jPanelCenter = JComponentFactory.flowMenuPanel();
        jPanelCenter.add(jPanelCenter1);
        jPanelCenter.add(jPanelCenter2);
        jPanelCenter.add(jPanelCenter3);

        this.add(jPanelCenter,BorderLayout.CENTER);
        this.addKeyListener(new KeyControls());
        getWindow().addComponentListener(new ComponentControls());
        elementInPanelUpdate();
        sizeUpdate();
    }

    private void sizeUpdate() {
        gridPanel1.setPreferredSize(new Dimension((int) (getWindow().getWidth()*0.17), (int) (getWindow().getHeight()*(0.08* (gridPanel1.getComponentCount())))));
        gridPanel2.setPreferredSize(new Dimension((int) (getWindow().getWidth()*0.17), (int) (getWindow().getHeight()*(0.08* (gridPanel2.getComponentCount())))));
        gridPanel3.setPreferredSize(new Dimension((int) (getWindow().getWidth()*0.17), (int) (getWindow().getHeight()*(0.08* (gridPanel3.getComponentCount())))));
        jPanelCenter1.setPreferredSize(new Dimension((int) (getWindow().getWidth()*0.2), (int) (getWindow().getHeight()*0.8)));
        jPanelCenter2.setPreferredSize(new Dimension((int) (getWindow().getWidth()*0.2), (int) (getWindow().getHeight()*0.8)));
        jPanelCenter3.setPreferredSize(new Dimension((int) (getWindow().getWidth()*0.2), (int) (getWindow().getHeight()*0.8)));

        jPanelGrid.setPreferredSize(new Dimension((int) (getWindow().getWidth()*0.25), (int) (getWindow().getHeight()*(0.08*jPanelGrid.getComponentCount()))));
        getWindow().revalidate();
        getWindow().repaint();
    }

    private void elementInPanelUpdate() {
        gridPanel1.removeAll();
        gridPanel2.removeAll();
        gridPanel3.removeAll();
        JButton tmp;
        if(isInSeaRoadMode){

        }
        else {
            for (Boat boat : MapGame.getInstance().getPlayer().getLstBoat()){
                if(!FactionManager.getInstance().doIHaveFleet(boat)){
                    tmp = JComponentFactory.menuButton(boat,new addBoatToFleetListener(boat));
                    gridPanel1.add(tmp);
                }
            }
            for (Boat boat : activeFleet.getArrayListBoat()){
                tmp = JComponentFactory.menuButton(boat,new removeBoatToFleetListener(boat));
                gridPanel2.add(tmp);
            }
            for (Fleet fleet : MapGame.getInstance().getPlayer().getLstFleet()){
                tmp = JComponentFactory.menuButton(fleet,new setFleetListener(fleet));
                gridPanel3.add(tmp);
                if(activeFleet.equals(fleet)){
                    changeCurrentJButton(tmp,activeButton3);
                    activeButton3 = tmp;
                }
            }
        }

    }

    private void changeCurrentJButton(JButton jButton,JButton activeButton){
        if(activeButton!=null)activeButton.setBackground(Color.darkGray);
        if(jButton!=null)jButton.setBackground(new Color(125, 130, 200));
    }

    public class addBoatToFleetListener implements ActionListener {
        private Boat boat;

        public addBoatToFleetListener(Boat boat) {
            this.boat = boat;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            activeFleet.add(boat);
            elementInPanelUpdate();
            sizeUpdate();
        }
    }

    public class removeBoatToFleetListener implements ActionListener {
        private Boat boat;

        public removeBoatToFleetListener(Boat boat) {
            this.boat = boat;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            activeFleet.remove(boat);
            elementInPanelUpdate();
            sizeUpdate();
        }
    }

    public class setFleetListener implements ActionListener {
        private Fleet fleet;

        public setFleetListener(Fleet fleet) {
            this.fleet = fleet;
        }


        @Override
        public void actionPerformed(ActionEvent e) {
            activeFleet = fleet;
            elementInPanelUpdate();
            sizeUpdate();
        }
    }

    private class ComponentControls implements ComponentListener {

        @Override
        public void componentResized(ComponentEvent e) {
            sizeUpdate();
        }

        @Override
        public void componentMoved(ComponentEvent e) {

        }

        @Override
        public void componentShown(ComponentEvent e) {

        }

        @Override
        public void componentHidden(ComponentEvent e) {

        }
    }

    public class goBackButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            GUILoader.loadMainGame();
        }
    }

    private class KeyControls implements KeyListener {

        @Override
        public void keyPressed(KeyEvent event) {
            if(event.getKeyCode() == KeyEvent.VK_ESCAPE){
                GUILoader.loadMainGame();
            }
        }

        @Override
        public void keyTyped(KeyEvent e) { }

        @Override
        public void keyReleased(KeyEvent e) { }
    }
}
