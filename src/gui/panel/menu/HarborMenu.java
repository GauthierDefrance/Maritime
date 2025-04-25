package gui.panel.menu;

import config.GameConfiguration;
import engine.MapGame;
import engine.data.entity.Harbor;
import engine.data.entity.boats.*;
import engine.data.trading.Resource;
import engine.process.manager.FactionManager;
import gui.process.JComponentFactory;
import gui.process.PaintEntity;
import gui.utilities.GUILoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static gui.MainGUI.getWindow;

public class HarborMenu extends JPanel {

    private boolean isInBoatMode;
    private String jComboBoxCurrentSelectedBoat;
    private String jComboBoxCurrentSelectedResource;
    private Harbor activeHarbor;
    private Object activeObject;

    private JPanel jPanelCenterCenter;
    private JPanel jPanelNorthResource;
    private JPanel jPanelCenterSouth;
    private JPanel jPanelEastCenter;
    private JPanel jPanelWestGrid;
    private JPanel statsPanel;
    private JPanel gridPanel;
    private JPanel currentCreatePanel;

    private JComboBox<String> jComboBoxHarbor;
    private JComboBox<String> jComboBoxCurrent;

    private JButton goBackButton;
    private JButton activeButton;
    private JButton moveResourceButton;
    private JButton removeCurrent;
    private JButton jComboBoxCurrentButton;
    private JButton boatModeButton;
    private JButton levelUpButton;
    private JButton renameButton;
    private JButton healButton;
    private JButton healAllButton;
    private JButton upgradeHpButton;
    private JButton upgradeDamageSpeedButton;
    private JButton upgradeSpeedButton;
    private JButton upgradeInventorySizeButton;


    private JLabel imageLabel;

    public HarborMenu(Harbor harbor) {
        super();
        this.activeHarbor = harbor;
        init();
    }

    private void init() {
        this.setLayout(new BorderLayout());

        jPanelWestGrid = JComponentFactory.gridMenuPanel(0, 1,GameConfiguration.BUTTON_SEPARATOR,GameConfiguration.BUTTON_SEPARATOR);
        jPanelNorthResource = JComponentFactory.flowMenuPanel();
        currentCreatePanel = JComponentFactory.borderMenuPanel();
        jPanelCenterCenter = JComponentFactory.borderMenuPanel();
        jPanelCenterSouth = JComponentFactory.gridMenuPanel(2,1);
        statsPanel = JComponentFactory.gridMenuPanel(0, 1);
        jPanelEastCenter = JComponentFactory.borderMenuPanel();
        jComboBoxHarbor = JComponentFactory.comboBox();
        jComboBoxCurrent = JComponentFactory.comboBox();
        imageLabel = JComponentFactory.title("Image Placeholder");
        boatModeButton = JComponentFactory.menuButton("Placeholder",new switchModeListener());
        jComboBoxCurrentButton = JComponentFactory.menuButton("Placeholder",new comboBoxCurrentButtonListener());
        goBackButton = JComponentFactory.menuButton("Go back", new goBackButtonListener());
        renameButton = JComponentFactory.menuButton("Rename", new renameListener());
        healButton = JComponentFactory.menuButton("Heal", new healListener());
        healAllButton = JComponentFactory.menuButton("Heal All", new healAllListener());
        moveResourceButton = JComponentFactory.menuButton("Export Resources", new moveResourceListener());
        removeCurrent = JComponentFactory.menuButton("Destroy",new removeListener());
        levelUpButton = JComponentFactory.menuButton("levelUp", new levelUpListener());

        upgradeHpButton = JComponentFactory.menuButton("upgradeHp", new upgradeHpListener());
        upgradeDamageSpeedButton = JComponentFactory.menuButton("upgradeDamageSpeed", new upgradeDamageSpeedListener());
        upgradeSpeedButton = JComponentFactory.menuButton("upgradeSpeed", new upgradeSpeedListener());
        upgradeInventorySizeButton = JComponentFactory.menuButton("upgradeInventorySize", new upgradeInventorySizeListener());
        isInBoatMode = false;
        jComboBoxCurrentSelectedResource = GameConfiguration.WOOD.getName();
        jComboBoxCurrentSelectedBoat = "Standard";


        jPanelCenterCenter.add(imageLabel,BorderLayout.CENTER);
        jPanelCenterCenter.add(renameButton,BorderLayout.SOUTH);

        jPanelCenterSouth.add(healButton);
        jPanelCenterSouth.add(healAllButton);

        JPanel jPanelCenterEastTmp = JComponentFactory.borderMenuPanel();
        jPanelCenterEastTmp.add(statsPanel,BorderLayout.CENTER);;
        jPanelCenterEastTmp.add(jPanelCenterSouth,BorderLayout.SOUTH);



        currentCreatePanel.add(jComboBoxCurrent,BorderLayout.WEST);
        currentCreatePanel.add(jComboBoxCurrentButton,BorderLayout.CENTER);

        gridPanel = JComponentFactory.gridMenuPanel(0,1);
        JPanel gridPanelTmp = JComponentFactory.voidPanel();
        gridPanelTmp.add(gridPanel);

        JScrollPane jScrollPane = JComponentFactory.ScrollPaneMenuPanel(gridPanelTmp);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        jPanelEastCenter = JComponentFactory.borderMenuPanel();
        jPanelEastCenter.add(jComboBoxHarbor,BorderLayout.SOUTH);
        jPanelEastCenter.add(jScrollPane,BorderLayout.CENTER);


        JPanel jPanelEastButton = JComponentFactory.voidPanel();
        jPanelEastButton.add(goBackButton);



        JPanel jPanelCenter = JComponentFactory.flowMenuPanel();
        jPanelCenter.add(jPanelCenterEastTmp);
        jPanelCenter.add(jPanelCenterCenter);
        jPanelCenter.add(jPanelWestGrid);
        jPanelCenter.add(jPanelEastCenter);

        this.add(jPanelNorthResource,BorderLayout.NORTH);
        this.add(jPanelCenter,BorderLayout.CENTER);
        this.add(jPanelEastButton,BorderLayout.SOUTH);

        this.setBackground(Color.lightGray);
        gridPanelTmp.setBackground(Color.gray);
        jPanelCenterEastTmp.setBackground(Color.gray);
        jPanelEastButton.setBackground(Color.gray);
        gridPanel.setBackground(Color.gray);
        jPanelNorthResource.setBackground(Color.lightGray);
        jPanelCenter.setBackground(Color.lightGray);


        jScrollPane.setBackground(Color.lightGray);
        jPanelWestGrid.setBackground(Color.lightGray);
        jPanelEastCenter.setBackground(Color.lightGray);
        statsPanel.setBackground(Color.lightGray);
        currentCreatePanel.setBackground(Color.lightGray);
        imageLabel.setBackground(Color.white);

        imageLabel.setOpaque(true);

        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        imageLabel.setVerticalTextPosition(SwingConstants.BOTTOM);

        this.addKeyListener(new KeyControls());
        getWindow().addComponentListener(new ComponentControls());
        allUpdate();
    }

    private void sizeUpdate() {
        jPanelWestGrid.setPreferredSize(new Dimension((int) (getWindow().getWidth()*0.25), (int) (getWindow().getHeight()*(0.08*jPanelWestGrid.getComponentCount()))));
        statsPanel.setPreferredSize(new Dimension((int) (getWindow().getWidth()*0.25), (int) (getWindow().getHeight()*0.50)));
        jPanelCenterSouth.setPreferredSize(new Dimension((int) (getWindow().getWidth()*0.25), (int) (getWindow().getHeight()*0.1)));

        jComboBoxCurrent.setPreferredSize(new Dimension((int) (getWindow().getWidth()*0.045), getWindow().getHeight()));
        goBackButton.setPreferredSize(new Dimension((int) (getWindow().getWidth()*0.15), (int) (getWindow().getHeight()*0.08)));
        if(!isInBoatMode)gridPanel.setPreferredSize(new Dimension((int) (getWindow().getWidth()*0.17), (int) (getWindow().getHeight()*(0.08* (activeHarbor.getGenerator().size())))));
        else gridPanel.setPreferredSize(new Dimension((int) (getWindow().getWidth()*0.17), (int) (getWindow().getHeight()*(0.08* (activeHarbor.getHashMapBoat().size())))));
        jPanelEastCenter.setPreferredSize(new Dimension((int) (getWindow().getWidth()*0.2), (int) (getWindow().getHeight()*0.8)));

        getWindow().revalidate();
        getWindow().repaint();
    }

    private void elementInPanelUpdate() {
        Object object = activeObject;
        gridPanel.removeAll();
        activeObject = object;
        JButton tmp;
        if(isInBoatMode){
            for (Boat boat : activeHarbor.getHashMapBoat().keySet()){
                if(activeHarbor.getHashMapBoat().get(boat)){
                   tmp = JComponentFactory.menuButton(boat,new buttonObjectListener(boat));
                   gridPanel.add(tmp);
                   if(activeObject!=null && activeObject.equals(boat)) changeCurrentJButton(tmp);
                }
            }
        }
        else {
            for (Resource resource : activeHarbor.getGenerator().keySet()){
                tmp = JComponentFactory.menuButton(resource.getName()+" Δ"+activeHarbor.getGenerator().get(resource)[1],new buttonObjectListener(resource));
                tmp.setBackground(Color.darkGray);
                tmp.setForeground(Color.white);
                tmp.setBorderPainted(false);
                gridPanel.add(tmp);
                if(activeObject!=null && activeObject.equals(resource)) changeCurrentJButton(tmp);
            }
        }
    }

    private void allUpdate(){
        int nb = 0;
        jPanelWestGrid.removeAll();
        statsPanel.removeAll();
        jPanelNorthResource.removeAll();
        currentCreatePanel.removeAll();
        imageLabel.setText("¯\\_(ツ)_/¯");
        imageLabel.setIcon(null);
        healButton.setText("Heal ...");
        healAllButton.setText("Heal All ...");
        renameButton.setText("Rename...");
        upgradeHpButton.setText("Upgrade Hp...");
        upgradeDamageSpeedButton.setText("Upgrade dps...");
        upgradeSpeedButton.setText("Upgrade Speed...");
        upgradeInventorySizeButton.setText("Upgrade Inventory Size...");
        levelUpButton.setText("Level X -> X");
        jComboBoxCurrentButton.setText("Build...");
        jComboBoxCurrentButton.setEnabled(false);
        levelUpButton.setEnabled(false);
        upgradeHpButton.setEnabled(false);
        upgradeDamageSpeedButton.setEnabled(false);
        upgradeSpeedButton.setEnabled(false);
        upgradeInventorySizeButton.setEnabled(false);
        renameButton.setEnabled(false);
        healButton.setEnabled(false);
        healAllButton.setEnabled(false);
        removeCurrent.setEnabled(activeObject != null);

        JLabel northResourceLabel = JComponentFactory.menuLabel(" "+MapGame.getInstance().getPlayer().getCurrency().getName()+" : "+MapGame.getInstance().getPlayer().getAmountCurrency()+"   "+"Resource : "+activeHarbor.getInventory().toString());
        northResourceLabel.setFont(new Font( "Noto Sans Display", Font.BOLD, 30));
        northResourceLabel.setOpaque(true);
        northResourceLabel.setBackground(Color.lightGray);
        jPanelNorthResource.add(northResourceLabel);

        jPanelEastCenter.remove(jComboBoxHarbor);
        jComboBoxHarbor = JComponentFactory.comboBox();
        for(Harbor harbor: MapGame.getInstance().getPlayer().getLstHarbor())jComboBoxHarbor.addItem(harbor.getName());
        jComboBoxHarbor.setSelectedItem(activeHarbor.getName());
        jComboBoxHarbor.addActionListener(new comboBoxHarborListener());
        jPanelEastCenter.add(jComboBoxHarbor,BorderLayout.SOUTH);

        jComboBoxCurrent = JComponentFactory.comboBox();
        currentCreatePanel.add(jComboBoxCurrent,BorderLayout.WEST);
        currentCreatePanel.add(jComboBoxCurrentButton,BorderLayout.CENTER);

        if(isInBoatMode){
            if(activeObject == null && !activeHarbor.getHashMapBoat().isEmpty()) {
                activeObject = activeHarbor.getHashMapBoat().keySet().iterator().next();
            }
            if(jComboBoxCurrentSelectedBoat!=null) {
                jComboBoxCurrentButton.setText("Build " + jComboBoxCurrentSelectedBoat+ " :"+FactionManager.getInstance().getHarborManager().canIAddBoatString(activeHarbor,jComboBoxCurrentSelectedBoat));
                if (FactionManager.getInstance().getHarborManager().canIAddBoat(activeHarbor, jComboBoxCurrentSelectedBoat))jComboBoxCurrentButton.setEnabled(true);
            }

            jComboBoxCurrent.setModel(new DefaultComboBoxModel<>(new String[]{"Standard","Fodder","Merchant","Military"}));
            if(jComboBoxCurrentSelectedBoat!=null)jComboBoxCurrent.setSelectedItem(jComboBoxCurrentSelectedBoat);

            for (Boat boat : activeHarbor.getHashMapBoat().keySet()) nb += boat.getMaxHp()-boat.getCurrentHp();
            healAllButton.setText("heal All Boat : "+nb+" "+GameConfiguration.WOOD.getName());
            if(FactionManager.getInstance().getHarborManager().canIHealAllBoat(activeHarbor))healAllButton.setEnabled(true);


            if(activeObject != null && activeObject instanceof Boat){
                Boat boat = ((Boat) activeObject);
                String boatClass = "Boat";
                if(boat instanceof Standard)boatClass = "Standard";
                else if(boat instanceof Fodder)boatClass = "Fodder";
                else if(boat instanceof Merchant)boatClass = "Merchant";
                else if(boat instanceof Military)boatClass = "Military";


                healButton.setText("heal Boat "+boat.getName()+" : "+(boat.getMaxHp()-boat.getCurrentHp())*GameConfiguration.COST_HEAL_BOAT+" "+GameConfiguration.WOOD.getName());
                if(FactionManager.getInstance().getHarborManager().canIHealBoat(activeHarbor,boat))healButton.setEnabled(true);

                levelUpButton.setText("LevelUp "+boat.getLevel()+" -> "+(boat.getLevel()+1)+" : "+boat.getLevel()*GameConfiguration.COST_LEVEL_UP_BOAT+" "+MapGame.getInstance().getPlayer().getCurrency().getName());
                if(FactionManager.getInstance().getHarborManager().canILevelUpBoat(activeHarbor, (Boat) activeObject))levelUpButton.setEnabled(true);


                upgradeHpButton.setText("Upgrade Hp "+boat.getMaxHp()+" -> "+boat.nextUpgradeHp()+" : 100 "+GameConfiguration.WOOD.getName()+" | 100 "+GameConfiguration.METAL.getName());
                if(FactionManager.getInstance().getHarborManager().canIUpgradeHp(activeHarbor, (Boat) activeObject))upgradeHpButton.setEnabled(true);

                upgradeDamageSpeedButton.setText("Upgrade dps "+boat.getDamageSpeed()+" -> "+boat.nextUpgradeDamageSpeed()+" : 100 "+GameConfiguration.METAL.getName()+" | 200 "+GameConfiguration.CLOTH.getName());
                if(FactionManager.getInstance().getHarborManager().canIUpgradeDamageSpeed(activeHarbor, (Boat) activeObject))upgradeDamageSpeedButton.setEnabled(true);

                upgradeSpeedButton.setText("Upgrade Speed "+boat.getSpeed()+" -> "+boat.nextUpgradeSpeed()+" : 300 "+GameConfiguration.CLOTH.getName());
                if(FactionManager.getInstance().getHarborManager().canIUpgradeSpeed(activeHarbor, (Boat) activeObject))upgradeSpeedButton.setEnabled(true);

                upgradeInventorySizeButton.setText("Upgrade Inventory Size "+boat.getInventory().getCapacity()+" -> "+boat.nextUpgradeInventorySize()+" : 100 "+GameConfiguration.WOOD.getName());
                if(FactionManager.getInstance().getHarborManager().canIUpgradeInventorySize(activeHarbor, (Boat) activeObject))upgradeInventorySizeButton.setEnabled(true);

                JPanel nameDisplay = JComponentFactory.flowMenuPanel(JComponentFactory.menuLabel("Name : " +boat.getName()));
                JPanel levelDisplay = JComponentFactory.flowMenuPanel(JComponentFactory.menuLabel("Level : " +boat.getLevel()));
                JPanel pointDisplay = JComponentFactory.flowMenuPanel(JComponentFactory.menuLabel("Upgrade Point : "+boat.getUpgradePoint()+"/"+(boat.getLevel()*GameConfiguration.UPGRADE_POINT_DEFAULT)));
                JPanel boatDisplay = JComponentFactory.flowMenuPanel(JComponentFactory.menuLabel("Boat Type : "+boatClass));
                JPanel healthDisplay = JComponentFactory.flowMenuPanel(JComponentFactory.menuLabel("Boat Health : "+boat.getCurrentHp()+"/"+boat.getMaxHp()));
                JPanel DamageSpeedDisplay = JComponentFactory.flowMenuPanel(JComponentFactory.menuLabel("Boat dps : "+boat.getDamageSpeed()));
                JPanel SpeedDisplay = JComponentFactory.flowMenuPanel(JComponentFactory.menuLabel("Boat Speed : "+boat.getSpeed()));
                JPanel InventorySizeDisplay = JComponentFactory.flowMenuPanel(JComponentFactory.menuLabel("Boat Inventory Size : "+boat.getInventory().getCapacity()));
                statsPanel.add(nameDisplay);
                statsPanel.add(levelDisplay);
                statsPanel.add(pointDisplay);
                statsPanel.add(boatDisplay);
                statsPanel.add(healthDisplay);
                statsPanel.add(DamageSpeedDisplay);
                statsPanel.add(SpeedDisplay);
                statsPanel.add(InventorySizeDisplay);

                renameButton.setText("Rename");
                renameButton.setEnabled(true);

                imageLabel.setText(boat.getName());
                if(activeHarbor.getHashMapBoat().get(boat)){
                    Point tmp = new Point(boat.getPosition());
                    boat.setPosition(600*GameConfiguration.GAME_SCALE,330*GameConfiguration.GAME_SCALE);
                    imageLabel.setIcon(new ImageIcon(PaintEntity.paintImage(boat,4,400)));
                    boat.setPosition(tmp);
                }
                else imageLabel.setIcon(new ImageIcon(PaintEntity.paintImage(boat,4,400)));
            }
            boatModeButton.setText("Harbor Menu");
            jPanelWestGrid.add(boatModeButton);
            jPanelWestGrid.add(levelUpButton);
            jPanelWestGrid.add(upgradeHpButton);
            jPanelWestGrid.add(upgradeDamageSpeedButton);
            jPanelWestGrid.add(upgradeSpeedButton);
            jPanelWestGrid.add(upgradeInventorySizeButton);
            jPanelWestGrid.add(currentCreatePanel);
            jPanelWestGrid.add(removeCurrent);
        }
        else {
            if(jComboBoxCurrentSelectedResource!=null) {
                Resource resource = GameConfiguration.WOOD;
                for (Resource resourceTnp : GameConfiguration.LIST_RESOURCE)if(resourceTnp.getName().equals(jComboBoxCurrentSelectedResource)) resource = resourceTnp;
                jComboBoxCurrentButton.setText("Build " + jComboBoxCurrentSelectedResource +" Generator"+ " : "+resource.getValue()*GameConfiguration.COST_GENERATOR / resource.getProductionRate()+" "+MapGame.getInstance().getPlayer().getCurrency().getName());
                if (FactionManager.getInstance().getHarborManager().canIAddGenerator(activeHarbor, resource))jComboBoxCurrentButton.setEnabled(true);
            }


            jComboBoxCurrent.setModel(new DefaultComboBoxModel<>(new String[]{GameConfiguration.WOOD.getName(),GameConfiguration.CLOTH.getName(),GameConfiguration.METAL.getName(),GameConfiguration.SUGAR.getName(),GameConfiguration.CACAO.getName(),GameConfiguration.PEARL.getName()}));
            if(jComboBoxCurrentSelectedResource!=null)jComboBoxCurrent.setSelectedItem(jComboBoxCurrentSelectedResource);

            for (Harbor harbor : MapGame.getInstance().getPlayer().getLstHarbor()) nb += harbor.getMaxHp()-harbor.getCurrentHp();
            healAllButton.setText("heal All Harbor : "+nb+" "+MapGame.getInstance().getPlayer().getCurrency().getName());
            if(FactionManager.getInstance().getHarborManager().canIHealAllHarbor())healAllButton.setEnabled(true);

            healButton.setText("heal Harbor "+activeHarbor.getName()+" : "+(activeHarbor.getMaxHp()-activeHarbor.getCurrentHp())*GameConfiguration.COST_HEAL_HARBOR+" "+MapGame.getInstance().getPlayer().getCurrency().getName());
            if(FactionManager.getInstance().getHarborManager().canIHealHarbor(activeHarbor))healButton.setEnabled(true);

            levelUpButton.setText("LevelUp "+activeHarbor.getLevel()+" -> "+(activeHarbor.getLevel()+1)+" : "+activeHarbor.getLevel()*GameConfiguration.COST_LEVEL_UP_HARBOR+" "+MapGame.getInstance().getPlayer().getCurrency().getName());
            if(FactionManager.getInstance().getHarborManager().canILevelUpHarbor(activeHarbor))levelUpButton.setEnabled(true);

            renameButton.setText("Rename");
            renameButton.setEnabled(true);

            nb = 0;
            for (Boat boat : activeHarbor.getHashMapBoat().keySet())if(activeHarbor.getHashMapBoat().get(boat))nb+=1;
            JPanel nameDisplay = JComponentFactory.flowMenuPanel(JComponentFactory.menuLabel("Name : " +activeHarbor.getName()));
            JPanel levelDisplay = JComponentFactory.flowMenuPanel(JComponentFactory.menuLabel("Level : " +activeHarbor.getLevel()));
            JPanel boatDisplay = JComponentFactory.flowMenuPanel(JComponentFactory.menuLabel("Boat Contained : "+activeHarbor.getHashMapBoat().size()+"("+nb+")"));
            JPanel healthDisplay = JComponentFactory.flowMenuPanel(JComponentFactory.menuLabel("Harbor Health : "+activeHarbor.getCurrentHp()+"/"+activeHarbor.getMaxHp()));
            nb = 0;
            for (Resource resource : activeHarbor.getGenerator().keySet())nb+=activeHarbor.getGenerator().get(resource)[1];
            JPanel generatorDisplay = JComponentFactory.flowMenuPanel(JComponentFactory.menuLabel("Harbor Generator : "+nb+"/"+activeHarbor.getLevel()));
            JPanel resourceDisplay = JComponentFactory.flowMenuPanel(JComponentFactory.menuLabel("Harbor Resource : "+activeHarbor.getInventory().toString()));
            statsPanel.add(nameDisplay);
            statsPanel.add(levelDisplay);
            statsPanel.add(boatDisplay);
            statsPanel.add(healthDisplay);
            statsPanel.add(generatorDisplay);
            statsPanel.add(resourceDisplay);

            imageLabel.setText(activeHarbor.getName());
            imageLabel.setIcon(new ImageIcon(PaintEntity.paintImage(activeHarbor,3,310)));
            boatModeButton.setText("Boat Menu");
            jPanelWestGrid.add(boatModeButton);
            jPanelWestGrid.add(moveResourceButton);
            jPanelWestGrid.add(levelUpButton);
            jPanelWestGrid.add(currentCreatePanel);
            jPanelWestGrid.add(removeCurrent);
        }
        jComboBoxCurrent.addActionListener(new comboBoxCurrentListener());
        elementInPanelUpdate();
        sizeUpdate();
    }

    private void changeCurrentJButton(JButton jButton){
        if(activeButton!=null)activeButton.setBackground(Color.darkGray);
        activeButton = jButton;
        if(jButton!=null)jButton.setBackground(new Color(125, 130, 200));
    }

    private class buttonObjectListener implements ActionListener {
        private final Object object;

        public buttonObjectListener(Object object) {
            this.object = object;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            activeObject = object;
            changeCurrentJButton((JButton) e.getSource());
            allUpdate();
        }
    }

    private class healListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (isInBoatMode){
                FactionManager.getInstance().getHarborManager().healBoat(activeHarbor, (Boat) activeObject);
            }
            else {
                FactionManager.getInstance().getHarborManager().healHarbor(activeHarbor);
            }
            allUpdate();
        }
    }

    private class healAllListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (isInBoatMode){
                FactionManager.getInstance().getHarborManager().healAllBoat(activeHarbor);
            }
            else {
                FactionManager.getInstance().getHarborManager().healAllHarbor();
            }
            allUpdate();
        }
    }

    private class levelUpListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (isInBoatMode){
                FactionManager.getInstance().getHarborManager().levelUpBoat(activeHarbor, (Boat) activeObject);
            }
            else {
                FactionManager.getInstance().getHarborManager().levelUpHarbor(activeHarbor);
            }
            allUpdate();
        }
    }

    private class upgradeHpListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            FactionManager.getInstance().getHarborManager().upgradeHp(activeHarbor, (Boat) activeObject);
            allUpdate();
        }
    }

    private class upgradeDamageSpeedListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            FactionManager.getInstance().getHarborManager().upgradeDamageSpeed(activeHarbor, (Boat) activeObject);
            allUpdate();
        }
    }

    private class upgradeSpeedListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            FactionManager.getInstance().getHarborManager().upgradeSpeed(activeHarbor, (Boat) activeObject);
            allUpdate();
        }
    }

    private class upgradeInventorySizeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            FactionManager.getInstance().getHarborManager().upgradeInventorySize(activeHarbor, (Boat) activeObject);
            allUpdate();
        }
    }

    private class renameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            boolean flag = true;
            String name;
            if (isInBoatMode){
                name = JOptionPane.showInputDialog(HarborMenu.this,"new name for "+((Boat)activeObject).getName());
                if(name!=null && !name.isEmpty() && name.length() <= 20){
                    for (Boat boat : MapGame.getInstance().getPlayer().getLstBoat()){
                        if(boat.getName().equals(name)){
                            flag = false;
                            JOptionPane.showMessageDialog(HarborMenu.this,"     Invalid entry\nname already assigned");
                            break;
                        }
                    }
                }
                else flag = false;
                if (flag){
                    ((Boat)activeObject).setName(name);
                    allUpdate();
                }
            }
            else {
                name = JOptionPane.showInputDialog(HarborMenu.this,"new name for "+activeHarbor.getName());
                if(name!=null && !name.isEmpty() && name.length() <= 20){
                    for (Harbor harbor : MapGame.getInstance().getPlayer().getLstHarbor()){
                        if(harbor.getName().equals(name)){
                            flag = false;
                            JOptionPane.showMessageDialog(HarborMenu.this,"     Invalid entry\nname already assigned");
                            break;
                        }
                    }
                }
                else flag = false;
                if (flag){
                    activeHarbor.setName(name);
                    allUpdate();
                }
            }
        }
    }

    private class comboBoxHarborListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selected = (String) jComboBoxHarbor.getSelectedItem();
            for (Harbor harbor : MapGame.getInstance().getPlayer().getLstHarbor()){
                if(harbor.getName().equals(selected))activeHarbor = harbor;
            }
            activeObject = null;
            allUpdate();
        }
    }

    private class comboBoxCurrentListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(isInBoatMode){
                jComboBoxCurrentSelectedBoat = (String) jComboBoxCurrent.getSelectedItem();
            }
            else {
                jComboBoxCurrentSelectedResource = (String) jComboBoxCurrent.getSelectedItem();
            }
            allUpdate();
        }
    }

    private class comboBoxCurrentButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(isInBoatMode){
                FactionManager.getInstance().getHarborManager().addBoat(activeHarbor, jComboBoxCurrentSelectedBoat, JOptionPane.showInputDialog(HarborMenu.this,"Name for "+jComboBoxCurrentSelectedBoat));
            }
            else {
                for (Resource resource : GameConfiguration.LIST_RESOURCE){
                    if(resource.getName().equals(jComboBoxCurrentSelectedResource)){
                        FactionManager.getInstance().getHarborManager().addGenerator(activeHarbor, resource);
                        break;
                    }
                }
            }
            allUpdate();
        }
    }

    private class switchModeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            isInBoatMode = !isInBoatMode;
            activeObject = null;
            allUpdate();
        }
    }

    private class moveResourceListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            GUILoader.loadChoicePathMenu(MapGame.getInstance().getPlayer());
        }
    }

    private class removeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(isInBoatMode && activeObject != null && activeObject instanceof Boat) {
                if (activeObject instanceof Standard && JOptionPane.showConfirmDialog(HarborMenu.this, "Destroy " + ((Boat) activeObject).getName() +" ?\n   Recover : 150 "+GameConfiguration.WOOD.getName()+" | 50 "+GameConfiguration.CLOTH.getName(), "confirmation Destroy", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION) {
                    MapGame.getInstance().getPlayer().removeBoat(((Boat) activeObject));
                    activeHarbor.getInventory().add(GameConfiguration.WOOD,300/2);
                    activeHarbor.getInventory().add(GameConfiguration.CLOTH,100/2);
                    activeObject = null;
                    allUpdate();
                }
                else if (activeObject instanceof Fodder && JOptionPane.showConfirmDialog(HarborMenu.this, "Destroy " + ((Boat) activeObject).getName() +" ?\n   Recover : 50 "+GameConfiguration.WOOD.getName()+" | 10 "+GameConfiguration.CLOTH.getName(), "confirmation Destroy", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION) {
                    MapGame.getInstance().getPlayer().removeBoat(((Boat) activeObject));
                    activeHarbor.getInventory().add(GameConfiguration.WOOD,100/2);
                    activeHarbor.getInventory().add(GameConfiguration.CLOTH,20/2);
                    activeObject = null;
                    allUpdate();
                }
                else if (activeObject instanceof Merchant && JOptionPane.showConfirmDialog(HarborMenu.this, "Destroy " + ((Boat) activeObject).getName() +" ?\n   Recover : 350 "+GameConfiguration.WOOD.getName()+" | 100 "+GameConfiguration.CLOTH.getName()+" | 25 "+GameConfiguration.METAL.getName(), "confirmation Destroy", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION) {
                    MapGame.getInstance().getPlayer().removeBoat(((Boat) activeObject));
                    activeHarbor.getInventory().add(GameConfiguration.WOOD,700/2);
                    activeHarbor.getInventory().add(GameConfiguration.CLOTH,200/2);
                    activeHarbor.getInventory().subtract(GameConfiguration.METAL,50/2);
                    activeObject = null;
                    allUpdate();
                }
                else if (activeObject instanceof Military && JOptionPane.showConfirmDialog(HarborMenu.this, "Destroy " + ((Boat) activeObject).getName() + " ?\n   Recover : 200 "+GameConfiguration.WOOD.getName()+" | 250 "+GameConfiguration.CLOTH.getName()+" | 100 "+GameConfiguration.METAL.getName(), "confirmation Destroy", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION) {
                    MapGame.getInstance().getPlayer().removeBoat(((Boat) activeObject));
                    activeHarbor.getInventory().add(GameConfiguration.WOOD,400/2);
                    activeHarbor.getInventory().add(GameConfiguration.CLOTH,500/2);
                    activeHarbor.getInventory().subtract(GameConfiguration.METAL,200/2);
                    activeObject = null;
                    allUpdate();
                }
            }
            else if(activeObject != null && activeObject instanceof Resource && JOptionPane.showConfirmDialog(HarborMenu.this,"Destroy one "+((Resource)activeObject).getName()+" ?\n   Recover : "+(((Resource)activeObject).getValue()*GameConfiguration.COST_GENERATOR / ((Resource)activeObject).getProductionRate())/2+" "+MapGame.getInstance().getPlayer().getCurrency().getName(),"confirmation Destroy",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION){
                FactionManager.getInstance().getHarborManager().removeGenerator(activeHarbor,((Resource)activeObject));
                MapGame.getInstance().getPlayer().addAmountCurrency((((Resource)activeObject).getValue()*GameConfiguration.COST_GENERATOR / ((Resource)activeObject).getProductionRate())/2);
                activeObject = null;
                allUpdate();
            }
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

    private class goBackButtonListener implements ActionListener {
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
