package gui.panel.menu;

import config.GameConfiguration;
import engine.MapGame;
import engine.data.Fleet;
import engine.data.entity.boats.Boat;
import engine.data.trading.SeaRoad;
import engine.process.creational.EngineBuilder;
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
    private SeaRoad activeSeaRoad;

    private JPanel jPanelNorth;
    private JPanel jPanelCenter1;
    private JPanel jPanelCenter2;
    private JPanel jPanelCenter3;
    private JPanel jPanelGrid;
    private JPanel gridPanel1;
    private JPanel gridPanel2;
    private JPanel gridPanel3;

    private JButton goBackButton;
    private JButton SeaRoadButton;
    private JButton activeButton1;
    private JButton activeButton3;

    public FleetMenu(Fleet activeFleet) {
        super();
        this.activeFleet = activeFleet;
        isInSeaRoadMode = false;
        init();
    }

    public FleetMenu(SeaRoad activeSeaRoad) {
        super();
        this.activeSeaRoad = activeSeaRoad;
        isInSeaRoadMode = true;
        init();
    }


    public void init() {
        this.setLayout(new BorderLayout());

        jPanelCenter1 = JComponentFactory.borderMenuPanel();
        jPanelCenter2 = JComponentFactory.borderMenuPanel();
        jPanelCenter3 = JComponentFactory.borderMenuPanel();
        jPanelGrid = JComponentFactory.gridMenuPanel(0, 1, GameConfiguration.BUTTON_SEPARATOR,GameConfiguration.BUTTON_SEPARATOR);
        goBackButton = JComponentFactory.menuButton("Go back", new goBackButtonListener());
        SeaRoadButton = JComponentFactory.menuButton("Placeholder",new switchModeListener());
        jPanelNorth = JComponentFactory.voidPanel();

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

        JPanel jPanelCenter = JComponentFactory.flowMenuPanel();
        jPanelCenter.add(jPanelCenter1);
        jPanelCenter.add(jPanelCenter2);
        jPanelCenter.add(jPanelCenter3);

        JPanel jPanelEastButton = JComponentFactory.voidPanel();
        jPanelEastButton.add(goBackButton);

        this.add(jPanelNorth,BorderLayout.NORTH);
        this.add(jPanelCenter,BorderLayout.CENTER);
        this.add(jPanelEastButton,BorderLayout.SOUTH);

        this.setBackground(Color.lightGray);
        jPanelCenter.setBackground(Color.lightGray);
        jPanelCenter1.setBackground(Color.lightGray);
        jPanelCenter2.setBackground(Color.lightGray);
        jPanelCenter3.setBackground(Color.lightGray);
        jPanelGrid.setBackground(Color.lightGray);


        gridPanel1.setBackground(Color.gray);
        gridPanel2.setBackground(Color.gray);
        gridPanel3.setBackground(Color.gray);
        gridPanelTmp1.setBackground(Color.gray);
        gridPanelTmp2.setBackground(Color.gray);
        gridPanelTmp3.setBackground(Color.gray);
        jPanelEastButton.setBackground(Color.gray);
        jPanelNorth.setBackground(Color.gray);


        this.addKeyListener(new KeyControls());
        getWindow().addComponentListener(new ComponentControls());
        allUpdate();
    }

    private void sizeUpdate() {
        gridPanel1.setPreferredSize(new Dimension((int) (getWindow().getWidth()*0.17), (int) (getWindow().getHeight()*(0.09* (gridPanel1.getComponentCount())))));
        gridPanel2.setPreferredSize(new Dimension((int) (getWindow().getWidth()*0.17), (int) (getWindow().getHeight()*(0.09* (gridPanel2.getComponentCount())))));
        gridPanel3.setPreferredSize(new Dimension((int) (getWindow().getWidth()*0.17), (int) (getWindow().getHeight()*(0.09* (gridPanel3.getComponentCount())))));
        jPanelCenter1.setPreferredSize(new Dimension((int) (getWindow().getWidth()*0.2), (int) (getWindow().getHeight()*0.8)));
        jPanelCenter2.setPreferredSize(new Dimension((int) (getWindow().getWidth()*0.2), (int) (getWindow().getHeight()*0.8)));
        jPanelCenter3.setPreferredSize(new Dimension((int) (getWindow().getWidth()*0.2), (int) (getWindow().getHeight()*0.8)));
        jPanelGrid.setPreferredSize(new Dimension((int) (getWindow().getWidth()*0.25), (int) (getWindow().getHeight()*(0.08*jPanelGrid.getComponentCount()))));
        jPanelNorth.setPreferredSize(new Dimension((getWindow().getWidth()), (int) (getWindow().getHeight()*0.04)));

        goBackButton.setPreferredSize(new Dimension((int) (getWindow().getWidth()*0.15), (int) (getWindow().getHeight()*0.08)));
        getWindow().revalidate();
        getWindow().repaint();
    }

    private void elementInPanelUpdate() {
        gridPanel1.removeAll();
        gridPanel2.removeAll();
        gridPanel3.removeAll();
        JButton tmp;
        if(isInSeaRoadMode){
            for (Fleet fleet : MapGame.getInstance().getPlayer().getLstFleet()){
                tmp = JComponentFactory.menuButton(fleet,new setFleetListener(fleet));
                gridPanel1.add(tmp);
                if(activeFleet!=null && activeFleet.equals(fleet)){
                    changeCurrentJButton(tmp,activeButton1);
                    activeButton1 = tmp;

                }
                if(activeSeaRoad!=null && activeSeaRoad.getFleet().equals(fleet)){
                    tmp.setBackground(new Color(28, 85, 31));
                }
                if(activeFleet!=null && activeFleet.equals(fleet) && activeSeaRoad!=null && activeSeaRoad.getFleet().equals(fleet)){
                    tmp.setBackground(new Color(10, 160, 70));
                }
            }
            if(activeFleet!=null) {
                for (Boat boat : activeFleet.getArrayListBoat()) {
                    tmp = JComponentFactory.menuButton(boat);
                    tmp.setEnabled(false);
                    gridPanel2.add(tmp);
                }
            }
            for (SeaRoad seaRoad : MapGame.getInstance().getPlayer().getLstSeaRouts()){
                tmp = JComponentFactory.menuButton(seaRoad,new setSeaRoadListener(seaRoad));
                gridPanel3.add(tmp);
                if(activeSeaRoad!=null && activeSeaRoad.equals(seaRoad)){
                    changeCurrentJButton(tmp,activeButton3);
                    activeButton3 = tmp;
                }
            }
        }
        else {
            for (Boat boat : MapGame.getInstance().getPlayer().getLstBoat()){
                if(!FactionManager.getInstance().doIHaveFleet(boat)){
                    tmp = JComponentFactory.menuButton(boat,new addBoatToFleetListener(boat));
                    gridPanel1.add(tmp);
                }
            }
            if(activeFleet!=null) {
                for (Boat boat : activeFleet.getArrayListBoat()) {
                    tmp = JComponentFactory.menuButton(boat, new removeBoatToFleetListener(boat));
                    gridPanel2.add(tmp);
                }
            }
            for (Fleet fleet : MapGame.getInstance().getPlayer().getLstFleet()){
                tmp = JComponentFactory.menuButton(fleet,new setFleetListener(fleet));
                gridPanel3.add(tmp);
                if(activeFleet!=null && activeFleet.equals(fleet)){
                    changeCurrentJButton(tmp,activeButton3);
                    activeButton3 = tmp;
                }
            }
        }

    }

    private void allUpdate(){
        jPanelGrid.removeAll();

        JButton removeCurrent = JComponentFactory.menuButton("Destroy",new removeListener());
        removeCurrent.setEnabled(false);
        if(isInSeaRoadMode){
            if(activeSeaRoad!=null)removeCurrent.setEnabled(true);
            JButton combineCurrent = JComponentFactory.menuButton("Combine",new combineCurrentListener());
            combineCurrent.setEnabled(false);
            if(activeSeaRoad != null && activeFleet != null && !activeSeaRoad.getFleet().equals(activeFleet))combineCurrent.setEnabled(true);
            SeaRoadButton.setText("Fleet");
            jPanelGrid.add(removeCurrent);
            jPanelGrid.add(combineCurrent);
            JButton pathButton = JComponentFactory.menuButton("path",new  setPathListener(activeSeaRoad));
            jPanelGrid.add(pathButton);
            pathButton.setEnabled(false);
            if(activeSeaRoad != null){
                pathButton.setEnabled(true);
            }
        }
        else {
            if(activeFleet!=null)removeCurrent.setEnabled(true);
            SeaRoadButton.setText("Sea-Road");
            JButton NewFleet = JComponentFactory.menuButton("New Fleet",new newFleetListener());
            jPanelGrid.add(removeCurrent);
            jPanelGrid.add(NewFleet);
            JButton pathButton = JComponentFactory.menuButton("path",new  setPathListener(activeFleet));
            jPanelGrid.add(pathButton);
            pathButton.setEnabled(false);
            if(activeFleet != null ){
                if(FactionManager.getInstance().getMySeaRoad(activeFleet) != null)pathButton.setText("path ( Is Combine with Sea-Road )");
                else pathButton.setEnabled(true);
            }
        }
        jPanelGrid.add(SeaRoadButton);

        elementInPanelUpdate();
        sizeUpdate();
    }

    private void changeCurrentJButton(JButton jButton,JButton activeButton){
        if(activeButton!=null)activeButton.setBackground(Color.darkGray);
        if(jButton!=null)jButton.setBackground(new Color(125, 130, 200));
    }

    public class combineCurrentListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(activeSeaRoad != null && activeFleet != null && !activeSeaRoad.getFleet().equals(activeFleet)){
                FactionManager.getInstance().getSeaRoadManager().setNewFleet(FactionManager.getInstance().getMySeaRoad(activeFleet), new Fleet());
                FactionManager.getInstance().getSeaRoadManager().setNewFleet(activeSeaRoad,activeFleet);
            }
            allUpdate();
        }
    }

    public class addBoatToFleetListener implements ActionListener {
        private Boat boat;

        public addBoatToFleetListener(Boat boat) {
            this.boat = boat;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(activeFleet!=null)activeFleet.add(boat);
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
            if(activeFleet!=null)activeFleet.remove(boat);
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
            allUpdate();
        }
    }

    public class setSeaRoadListener implements ActionListener {
        private SeaRoad seaRoad;

        public setSeaRoadListener(SeaRoad seaRoad) {
            this.seaRoad = seaRoad;
        }


        @Override
        public void actionPerformed(ActionEvent e) {
            activeSeaRoad = seaRoad;
            allUpdate();
        }
    }

    public class newFleetListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String nameForNewFleet = JOptionPane.showInputDialog(FleetMenu.this,"Name for New Fleet");
            if(nameForNewFleet == null || nameForNewFleet.isEmpty())nameForNewFleet = "Fleet"+(MapGame.getInstance().getPlayer().getLstFleet().size()+1);
            MapGame.getInstance().getPlayer().addFleet(EngineBuilder.Fleet(nameForNewFleet));
            allUpdate();
        }
    }

    public class removeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(isInSeaRoadMode){
                if(JOptionPane.showConfirmDialog(FleetMenu.this,"Destroy SeaRoad "+activeSeaRoad.getName()+" ?\n   -10 Relation penalty","confirmation Destroy",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION){
                    MapGame.getInstance().getPlayer().getLstSeaRouts().remove(activeSeaRoad);
                    FactionManager.getInstance().getFleetManager().removePath(activeSeaRoad.getFleet());
                    FactionManager.getInstance().getHarborManager().addFleetInHarbor(activeSeaRoad.getSellerHarbor(),activeSeaRoad.getFleet());
                    FactionManager.getInstance().modifyRelationship(MapGame.getInstance().getPlayer(),FactionManager.getInstance().getMyFaction(activeSeaRoad.getTargetHarbor().getColor()),-10);
                    activeSeaRoad = null;
                    allUpdate();
                }
            }
            else {
                if(JOptionPane.showConfirmDialog(FleetMenu.this,"Destroy Fleet "+activeFleet.getName()+" ?\n   Boats will not be destroyed ","confirmation Destroy",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION){
                    FactionManager.getInstance().getFleetManager().removePath(activeFleet);
                    MapGame.getInstance().getPlayer().removeFleet(activeFleet);
                    activeFleet = null;
                    allUpdate();
                }
            }
        }
    }

    public class setPathListener implements ActionListener {
        private Fleet fleet;
        private SeaRoad seaRoad;

        public setPathListener(SeaRoad seaRoad) {
            this.seaRoad = seaRoad;
            this.fleet = null;
        }
        public setPathListener(Fleet fleet) {
            this.fleet = fleet;
            this.seaRoad = null;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(fleet != null){
                GUILoader.loadChoicePathMenu(fleet);
            }
            else if(seaRoad != null){
                GUILoader.loadChoicePathMenu(seaRoad);
            }
        }
    }

    public class switchModeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            isInSeaRoadMode = !isInSeaRoadMode;
            activeButton1 = null;
            activeButton3 = null;
            allUpdate();
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
