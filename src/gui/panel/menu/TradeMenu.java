package gui.panel.menu;
import config.GameConfiguration;
import engine.MapGame;
import engine.data.entity.Harbor;
import engine.data.faction.Faction;
import engine.data.trading.Currency;
import engine.data.trading.Resource;
import engine.data.trading.SeaRoad;
import engine.process.manager.FactionManager;
import engine.process.manager.TradeManager;
import gui.process.JComponentFactory;
import gui.utilities.GUILoader;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

import static gui.MainGUI.getWindow;

public class TradeMenu extends JPanel {
    private boolean isMoveResource;

    private Harbor sellerHarbor;
    private Harbor targetHarbor;

    private Resource sellerResource;
    private Resource targetResource;

    private Integer sellerResourceQuantity;
    private Integer targetResourceQuantity;

    private JTextField Quantity1;
    private JTextField Quantity2;

    private JButton currentSellerResource;
    private JButton currentTargetResource;
    private JButton seaRoadTry;
    private JButton goBackButton;

    private JPanel jCenterCenterPanel;
    private JPanel jCenterWestPanel;
    private JPanel jSouthPanel;
    private JPanel gridPanel1;
    private JPanel gridPanel2;

    public TradeMenu(Harbor sellerHarbor, Harbor targetHarbor) {
        super();
        this.sellerHarbor = sellerHarbor;
        this.targetHarbor = targetHarbor;
        init();
    }
    private void init() {
        this.setLayout(new BorderLayout());
        sellerResourceQuantity = 1;
        targetResourceQuantity = 1;
        isMoveResource = MapGame.getInstance().getPlayer().getLstHarbor().contains(targetHarbor);

        JPanel jvoidPanel;

        jCenterCenterPanel = JComponentFactory.gridMenuPanel(2,1);
        jCenterWestPanel = JComponentFactory.gridMenuPanel(1,2);
        JPanel jCenterWestPanel1 = JComponentFactory.borderMenuPanel();
        JPanel jCenterWestPanel2 = JComponentFactory.borderMenuPanel();
        JPanel jCenterPanel = JComponentFactory.borderMenuPanel();

        Quantity1 = JComponentFactory.textField("1",new textKeyControls(true));
        gridPanel1 = JComponentFactory.gridMenuPanel(0,1);
        jvoidPanel = JComponentFactory.voidPanel();
        jvoidPanel.add(gridPanel1);
        jvoidPanel.setBackground(Color.gray);
        JScrollPane jScrollPane1 = JComponentFactory.ScrollPaneMenuPanel(jvoidPanel);
        jScrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jCenterWestPanel1.add(jScrollPane1,BorderLayout.CENTER);
        jCenterWestPanel1.add(Quantity1,BorderLayout.SOUTH);

        JLabel jLabelTmp = JComponentFactory.menuLabel("you");
        if(isMoveResource)jLabelTmp.setText(sellerHarbor.getName());
        jLabelTmp.setHorizontalAlignment(SwingConstants.CENTER);
        jLabelTmp.setFont(new Font( "Noto Sans Display", Font.BOLD, 30));
        jLabelTmp.setOpaque(true);
        jLabelTmp.setBackground(Color.lightGray);
        jCenterWestPanel1.add(jLabelTmp,BorderLayout.NORTH);

        Quantity2 = JComponentFactory.textField("1",new textKeyControls(false));
        gridPanel2 = JComponentFactory.gridMenuPanel(0,1);
        jvoidPanel = JComponentFactory.voidPanel();
        jvoidPanel.add(gridPanel2);
        jvoidPanel.setBackground(Color.gray);
        JScrollPane jScrollPane2 = JComponentFactory.ScrollPaneMenuPanel(jvoidPanel);
        jScrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jCenterWestPanel2.add(jScrollPane2,BorderLayout.CENTER);
        jCenterWestPanel2.add(Quantity2,BorderLayout.SOUTH);

        jLabelTmp = JComponentFactory.menuLabel("target");
        if(isMoveResource)jLabelTmp.setText(targetHarbor.getName());
        jLabelTmp.setHorizontalAlignment(SwingConstants.CENTER);
        jLabelTmp.setFont(new Font( "Noto Sans Display", Font.BOLD, 30));
        jLabelTmp.setOpaque(true);
        jLabelTmp.setBackground(Color.lightGray);
        jCenterWestPanel2.add(jLabelTmp,BorderLayout.NORTH);

        jCenterWestPanel.add(jCenterWestPanel1);
        jCenterWestPanel.add(jCenterWestPanel2);

        goBackButton = JComponentFactory.menuButton("Go back", new  GoBackListener());
        seaRoadTry = JComponentFactory.menuButton("Try",new TryListener());
        if(isMoveResource)seaRoadTry.setText("Create");
        jSouthPanel = JComponentFactory.flowMenuPanel(goBackButton,seaRoadTry);

        jvoidPanel = JComponentFactory.voidPanel();
        jvoidPanel.add(jCenterCenterPanel);
        jvoidPanel.setBackground(Color.gray);
        jCenterPanel.add(jvoidPanel,BorderLayout.CENTER);

        jvoidPanel = JComponentFactory.voidPanel();
        jvoidPanel.add(jCenterWestPanel);
        jvoidPanel.setBackground(Color.gray);
        jCenterPanel.add(jvoidPanel,BorderLayout.WEST);

        this.add(jCenterPanel,BorderLayout.CENTER);
        this.add(jSouthPanel,BorderLayout.SOUTH);

        jCenterPanel.setBackground(Color.gray);
        jCenterCenterPanel.setBackground(Color.gray);
        jCenterWestPanel.setBackground(Color.gray);
        gridPanel1.setBackground(Color.gray);
        gridPanel2.setBackground(Color.gray);


        jSouthPanel.setBackground(Color.lightGray);



        this.addKeyListener(new KeyControls());
        getWindow().addComponentListener(new ComponentControls());
        seaRoadTryUpdate();
        elementInPanelUpdate();
        sizeUpdate();
    }

    private void sizeUpdate() {
        jCenterCenterPanel.setPreferredSize(new Dimension((int) (getWindow().getWidth()*0.3),(int) (getWindow().getWidth()*0.4)));
        jCenterWestPanel.setPreferredSize(new Dimension((int) (getWindow().getWidth()*0.26), (int) (getWindow().getHeight()*0.7)));
        jSouthPanel.setPreferredSize(new Dimension(getWindow().getWidth(), (int) (getWindow().getHeight()*0.15)));

        gridPanel1.setPreferredSize(new Dimension((int) (getWindow().getWidth()*0.119), (int) (getWindow().getHeight()*(0.08* (sellerHarbor.getInventory().getContent().size()+1)))));
        gridPanel2.setPreferredSize(new Dimension((int) (getWindow().getWidth()*0.119), (int) (getWindow().getHeight()*(0.08* (targetHarbor.getInventory().getContent().size()+1)))));

        Quantity1.setPreferredSize(new Dimension(getWindow().getWidth(), (int) (getWindow().getHeight()*0.03)));
        Quantity2.setPreferredSize(new Dimension(getWindow().getWidth(), (int) (getWindow().getHeight()*0.03)));

        goBackButton.setPreferredSize(new Dimension((int) (getWindow().getWidth()*0.3), (int) (getWindow().getHeight()*0.15)));
        seaRoadTry.setPreferredSize(new Dimension((int) (getWindow().getWidth()*0.3), (int) (getWindow().getHeight()*0.15)));

        getWindow().revalidate();
        getWindow().repaint();
    }

    private void elementInPanelUpdate() {
        gridPanel1.removeAll();
        gridPanel2.removeAll();
        JButton tmp;
        Faction faction;

        if(isMoveResource) {
            faction = MapGame.getInstance().getPlayer();
            tmp = JComponentFactory.menuButton("Δ",new ResourceListener(true,faction.getCurrency()));
            tmp.setBackground(Color.lightGray);
            gridPanel1.add(tmp);
        }
        else {
            faction = MapGame.getInstance().getPlayer();
            tmp = JComponentFactory.menuButton(faction.getCurrency().getName() + " Δ" + faction.getAmountCurrency() + " $" + faction.getCurrency().getValue(), new ResourceListener(true, faction.getCurrency()));
            tmp.setBackground(Color.lightGray);
            gridPanel1.add(tmp);
        }
        for (Resource resource : sellerHarbor.getInventory().getContent().keySet()){
            tmp = JComponentFactory.menuButton(resource.getName()+" Δ"+sellerHarbor.getInventory().getNbResource(resource)+" $"+resource.getValue(),new ResourceListener(true,resource));
            tmp.setBackground(Color.lightGray);
            gridPanel1.add(tmp);
        }

        if(isMoveResource) {
            faction = FactionManager.getInstance().getMyFaction(targetHarbor.getColor());
            tmp = JComponentFactory.menuButton("Δ",new ResourceListener(false,faction.getCurrency()));
            tmp.setBackground(Color.lightGray);
            gridPanel2.add(tmp);
        }
        else {
            faction = FactionManager.getInstance().getMyFaction(targetHarbor.getColor());
            tmp = JComponentFactory.menuButton(faction.getCurrency().getName() + " Δ" + faction.getAmountCurrency() + " $" + faction.getCurrency().getValue(), new ResourceListener(false, faction.getCurrency()));
            tmp.setBackground(Color.lightGray);
            gridPanel2.add(tmp);
        }
        for (Resource resource : targetHarbor.getInventory().getContent().keySet()){
            tmp = JComponentFactory.menuButton(resource.getName()+" Δ"+targetHarbor.getInventory().getNbResource(resource)+" $"+resource.getValue(),new ResourceListener(false,resource));
            tmp.setBackground(Color.lightGray);
            gridPanel2.add(tmp);
        }
    }

    private void seaRoadTryUpdate(){
        SeaRoad offer = null;
        if(sellerResource != null && targetResource != null && sellerResourceQuantity != null && targetResourceQuantity != null){
            seaRoadTry.setEnabled(true);
            offer = new SeaRoad("",sellerHarbor,targetHarbor,sellerResource,targetResource,sellerResourceQuantity,targetResourceQuantity, GameConfiguration.SEAROAD_TIME+GameConfiguration.SEAROAD_TIME*(FactionManager.getInstance().getMyFaction(targetHarbor.getColor()).getRelationship(MapGame.getInstance().getPlayer()))/200);
        }
        else seaRoadTry.setEnabled(false);

        String ResourceSeller = "?";
        String ResourceTarget = "?";
        String sellerQuantity = "?";
        String targetQuantity = "?";
        if(sellerResource != null)ResourceSeller = sellerResource.getName();
        if(targetResource != null)ResourceTarget = targetResource.getName();
        if(sellerResourceQuantity != null)sellerQuantity = "Δ"+ sellerResourceQuantity;
        if(targetResourceQuantity != null)targetQuantity = "Δ"+ targetResourceQuantity;

        if(sellerResourceQuantity != null && sellerResource != null)sellerQuantity = "Δ"+sellerResourceQuantity+"($"+sellerResource.getValue()*sellerResourceQuantity+")";
        if(targetResourceQuantity != null && targetResource != null)targetQuantity = "Δ"+targetResourceQuantity+"($"+targetResource.getValue()*targetResourceQuantity+")";

        JPanel jCenterCenterPanel1 = JComponentFactory.gridMenuPanel(1,3);
        JPanel jCenterCenterPanel11 = JComponentFactory.gridMenuPanel(2,1);
        JPanel jCenterCenterPanel12 = JComponentFactory.gridMenuPanel(2,1);

        jCenterCenterPanel1.setBackground(Color.lightGray);
        jCenterCenterPanel11.setBackground(Color.lightGray);
        jCenterCenterPanel12.setBackground(Color.lightGray);

        JLabel tmp;

        tmp = JComponentFactory.menuLabel(ResourceSeller);
        tmp.setHorizontalAlignment(SwingConstants.CENTER);
        tmp.setFont(new Font( "Noto Sans Display", Font.BOLD, 30));
        jCenterCenterPanel11.add(tmp);

        tmp = JComponentFactory.menuLabel(sellerQuantity);
        tmp.setHorizontalAlignment(SwingConstants.CENTER);
        tmp.setFont(new Font( "Noto Sans Display", Font.BOLD, 30));
        jCenterCenterPanel11.add(tmp);

        tmp = JComponentFactory.menuLabel(ResourceTarget);
        tmp.setHorizontalAlignment(SwingConstants.CENTER);
        tmp.setFont(new Font( "Noto Sans Display", Font.BOLD, 30));
        jCenterCenterPanel12.add(tmp);

        tmp = JComponentFactory.menuLabel(targetQuantity);
        tmp.setHorizontalAlignment(SwingConstants.CENTER);
        tmp.setFont(new Font( "Noto Sans Display", Font.BOLD, 30));
        jCenterCenterPanel12.add(tmp);

        tmp = JComponentFactory.title("⇌");
        tmp.setFont(new Font( "Noto Sans Display", Font.BOLD, 100));
        tmp.setHorizontalAlignment(SwingConstants.CENTER);

        jCenterCenterPanel1.add(jCenterCenterPanel11);
        jCenterCenterPanel1.add(tmp);
        jCenterCenterPanel1.add(jCenterCenterPanel12);

        JLabel jCenterCenterPanel2 = JComponentFactory.menuLabel(((int)TradeManager.getInstance().calculateSuccessChance(offer))+"%");
        jCenterCenterPanel2.setFont(new Font( "Noto Sans Display", Font.BOLD, 40));
        jCenterCenterPanel2.setBackground(Color.lightGray);
        jCenterCenterPanel2.setOpaque(true);
        jCenterCenterPanel2.setBorder(new EmptyBorder((int) (getWindow().getHeight()*0.01),(int) (getWindow().getHeight()*0.015), (int) (getWindow().getHeight()*0.01),(int) (getWindow().getHeight()*0.015)));
        JPanel jVoidPanel = JComponentFactory.voidPanel();
        if(!isMoveResource)jVoidPanel.add(jCenterCenterPanel2);
        jVoidPanel.setBackground(Color.gray);
        jVoidPanel.setBorder(new EmptyBorder((int) (getWindow().getHeight()*0.005),0, 0,0));

        jCenterCenterPanel.removeAll();
        jCenterCenterPanel.add(jCenterCenterPanel1);
        jCenterCenterPanel.add(jVoidPanel);
        sizeUpdate();
    }

    private void ChangeCurrentJButton(boolean isSeller,JButton jButton2){
        if(isSeller){
            if(currentSellerResource!=null)currentSellerResource.setBackground(Color.lightGray);
            currentSellerResource = jButton2;
        }
        else {
            if(currentTargetResource!=null)currentTargetResource.setBackground(Color.lightGray);
            currentTargetResource = jButton2;
        }
        if(jButton2 != null)jButton2.setBackground(new Color(125, 130, 200));
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

    private class ResourceListener implements ActionListener {
        private boolean isSeller;
        private Resource resource;

        private ResourceListener(boolean isSeller, Resource resource) {
            this.isSeller = isSeller;
            this.resource = resource;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int nb;
            if(isSeller) {
                sellerResource = resource;
                ChangeCurrentJButton(true,(JButton) e.getSource());
                if(!Quantity1.getText().isEmpty()) {
                    nb = Integer.parseInt(Quantity1.getText());
                    if (sellerResource != null && sellerResource instanceof Currency && nb > MapGame.getInstance().getPlayer().getAmountCurrency()) {
                        nb = MapGame.getInstance().getPlayer().getAmountCurrency();
                        Quantity1.setText(String.valueOf(nb));
                        sellerResourceQuantity = nb;
                    } else if (sellerResource != null && !(sellerResource instanceof Currency) && nb > sellerHarbor.getInventory().getNbResource(sellerResource)) {
                        nb = sellerHarbor.getInventory().getNbResource(sellerResource);
                        Quantity1.setText(String.valueOf(nb));
                        sellerResourceQuantity = nb;
                    }
                }
            }
            else {
                targetResource = resource;
                ChangeCurrentJButton(false,(JButton) e.getSource());
                if(!Quantity2.getText().isEmpty()) {
                    nb = Integer.parseInt(Quantity2.getText());
                    if (targetResource != null && targetResource instanceof Currency && nb > FactionManager.getInstance().getMyFaction(targetHarbor.getColor()).getAmountCurrency()) {
                        nb = FactionManager.getInstance().getMyFaction(targetHarbor.getColor()).getAmountCurrency();
                        Quantity2.setText(String.valueOf(nb));
                        targetResourceQuantity = nb;
                    } else if (targetResource != null && !(targetResource instanceof Currency) && nb > targetHarbor.getInventory().getNbResource(targetResource)) {
                        nb = targetHarbor.getInventory().getNbResource(targetResource);
                        Quantity2.setText(String.valueOf(nb));
                        targetResourceQuantity = nb;
                    }
                }
            }
            seaRoadTryUpdate();
        }
    }

    private class textKeyControls implements KeyListener {
        private boolean isSeller;

        private textKeyControls(boolean isSeller) {
            this.isSeller = isSeller;
        }

        @Override
        public void keyPressed(KeyEvent event) {
            if(event.getKeyCode() == KeyEvent.VK_ESCAPE){
                GUILoader.loadRelationMenu(FactionManager.getInstance().getMyFaction(targetHarbor.getColor()));
            }
        }

        @Override
        public void keyTyped(KeyEvent event) {
            if (!Character.isDigit(event.getKeyChar()) && event.getKeyCode() != KeyEvent.VK_ESCAPE) {
                event.consume();
            }
        }

        @Override
        public void keyReleased(KeyEvent event) {
            JTextField textField = (JTextField) event.getSource();
            int nb;
            if(textField.getText().isEmpty() || textField.getText().length() > 8 || Integer.parseInt(textField.getText())<=0){
                textField.setText("1");
                nb = 1;
            }
            else nb = Integer.parseInt(textField.getText());
            if(isSeller){

                if(sellerResource!=null && sellerResource instanceof Currency && isMoveResource){
                    nb = 1;
                    textField.setText(String.valueOf(nb));
                }
                else if(sellerResource!=null && sellerResource instanceof Currency && nb>MapGame.getInstance().getPlayer().getAmountCurrency()){
                    nb = MapGame.getInstance().getPlayer().getAmountCurrency();
                    textField.setText(String.valueOf(nb));
                }
                else if(sellerResource!=null && !(sellerResource instanceof Currency) && nb > sellerHarbor.getInventory().getNbResource(sellerResource)){
                    nb = sellerHarbor.getInventory().getNbResource(sellerResource);
                    textField.setText(String.valueOf(nb));
                }
                sellerResourceQuantity = nb;
            }
            else {

                if(targetResource!=null && targetResource instanceof Currency && isMoveResource){
                    nb = 1;
                    textField.setText(String.valueOf(nb));
                }
                else if(targetResource!=null && targetResource instanceof Currency && nb>FactionManager.getInstance().getMyFaction(targetHarbor.getColor()).getAmountCurrency()){
                    nb = FactionManager.getInstance().getMyFaction(targetHarbor.getColor()).getAmountCurrency();
                    textField.setText(String.valueOf(nb));
                }
                else if(targetResource!=null && !(targetResource instanceof Currency) && nb > targetHarbor.getInventory().getNbResource(targetResource)){
                    nb = targetHarbor.getInventory().getNbResource(targetResource);
                    textField.setText(String.valueOf(nb));
                }
                targetResourceQuantity = nb;
            }
            seaRoadTryUpdate();
        }
    }

    private class TryListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            SeaRoad seaRoad = new SeaRoad("",sellerHarbor,targetHarbor,sellerResource,targetResource,sellerResourceQuantity,targetResourceQuantity, GameConfiguration.SEAROAD_TIME+GameConfiguration.SEAROAD_TIME*(FactionManager.getInstance().getMyFaction(targetHarbor.getColor()).getRelationship(MapGame.getInstance().getPlayer()))/200);
            if(isMoveResource)seaRoad.setTime(GameConfiguration.SEAROAD_TIME*3);
            if(TradeManager.getInstance().conclude(seaRoad)||isMoveResource){
                String name = JOptionPane.showInputDialog(TradeMenu.this,"      Success\nname the sea-Road");
                if(name!=null)seaRoad.setName(name);
                MapGame.getInstance().getPlayer().addSeaRoad(seaRoad);
                GUILoader.loadFleetMenu(seaRoad);
            }
            else {
                JOptionPane.showMessageDialog(TradeMenu.this,"     Fail\nrelationship -10");
                if(FactionManager.getInstance().getMyFaction(targetHarbor.getColor()).getRelationship(MapGame.getInstance().getPlayer()) <= GameConfiguration.WAR_THRESHOLD){
                    JOptionPane.showMessageDialog(TradeMenu.this,"GG it's War time");
                    GUILoader.loadRelationMenu(FactionManager.getInstance().getMyFaction(targetHarbor.getColor()));
                }
                else seaRoadTryUpdate();
            }
        }
    }

    private class GoBackListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(isMoveResource)GUILoader.loadHarborMenu(sellerHarbor);
            else GUILoader.loadRelationMenu(FactionManager.getInstance().getMyFaction(targetHarbor.getColor()));
        }
    }

    private class KeyControls implements KeyListener {

        @Override
        public void keyPressed(KeyEvent event) {
            if(event.getKeyCode() == KeyEvent.VK_ESCAPE){
                if(isMoveResource)GUILoader.loadHarborMenu(sellerHarbor);
                else GUILoader.loadRelationMenu(FactionManager.getInstance().getMyFaction(targetHarbor.getColor()));
            }
        }

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {
        }
    }

}
