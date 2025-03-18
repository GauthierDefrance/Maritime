package gui.panel;

import config.GameConfiguration;
import engine.MapGame;
import engine.entity.Harbor;
import engine.faction.Faction;
import engine.faction.Player;
import engine.process.FactionManager;
import gui.PopUp;
import gui.utilities.GUILoader;
import gui.utilities.JComponentBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static gui.MainGUI.getWindow;

/**
 * Simple test start menu for the game, serves as the entrypoint of the program
 * @author Kenan Ammad
 * @version 0.2
 */
public class ChoicePathMenu extends JPanel implements Runnable {

    private final int state;
    private final Faction faction;
    private Harbor harborPlayer;
    private Harbor harborBot;

    private JPanel dashboardJPanel;
    private JPanel jPanelATH;
    private JPanel jSouthATHPanel;

    private JButton confirm;
    private JButton cancel;

    private FactionManager factionManager;
    private ChoiceDisplay dashboard;
    private boolean ThreadStop;

    /**
     * Typical constructor to make the startMenu appear
     */
    public ChoicePathMenu(int state, Faction faction) {
        super();
        this.state = state;
        this.faction = faction;
        init();
    }
    public void init() {
        this.setLayout(new BorderLayout());
        dashboardJPanel = JComponentBuilder.borderMenuPanel();
        jPanelATH = JComponentBuilder.borderMenuPanel();
        jSouthATHPanel = JComponentBuilder.flowMenuPanel();
        confirm = JComponentBuilder.menuButton("Confirm",new confirmListener());
        cancel = JComponentBuilder.menuButton("Cancel",new cancelListener());

        factionManager = new FactionManager();
        dashboard = new ChoiceDisplay(state);

        //Window arrangement
        JLayeredPane jLayeredPane = new JLayeredPane();
        jPanelATH.setOpaque(false);
        dashboardJPanel.add(dashboard,BorderLayout.CENTER);
        jSouthATHPanel.add(cancel);
        jSouthATHPanel.add(confirm);
        jPanelATH.add(jSouthATHPanel,BorderLayout.SOUTH);

        jLayeredPane.add(dashboardJPanel,JLayeredPane.DEFAULT_LAYER);
        jLayeredPane.add(jPanelATH,JLayeredPane.PALETTE_LAYER);

        dashboard.setBackground(GameConfiguration.WATER_BACKGROUND_COLOR);

        this.add(jLayeredPane);
        this.addMouseListener(new MouseListener());
        this.addKeyListener(new KeyControls());
        getWindow().addComponentListener(new ComponentControls());
        sizeUpdate();
        ThreadStop = false;
        Thread gameThread = new Thread(this);
        gameThread.start();
    }

    public void sizeUpdate() {
        dashboardJPanel.setBounds(getWindow().getBounds());
        jPanelATH.setBounds(getWindow().getBounds());

        jPanelATH.setBounds(getWindow().getBounds());
        jSouthATHPanel.setPreferredSize(new Dimension(getWindow().getWidth(),(int) (getWindow().getHeight()*0.15)));
        confirm.setPreferredSize(new Dimension((int) Math.max(50,getWindow().getWidth()*0.2), (int) Math.max(26,getWindow().getHeight()*0.08)));
        cancel.setPreferredSize(new Dimension((int) Math.max(50,getWindow().getWidth()*0.2), (int) Math.max(26,getWindow().getHeight()*0.08)));
        getWindow().revalidate();
        getWindow().repaint();
    }

    public class confirmListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(state == 0) {
                //WIP
            }
            else if(state == 1){
                if (harborBot != null && harborPlayer != null){
                    //WIP
                }
                else JOptionPane.showMessageDialog(ChoicePathMenu.this,"you need to Choice");
            }
        }
    }

    public class cancelListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(state == 0) {
                //WIP
            }
            else if(state == 1){
                harborBot = null;
                harborPlayer = null;
                dashboard.setHarborBot(null);
                dashboard.setHarborPlayer(null);

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

    private class MouseListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            double scale = Math.min((double)getWidth()/640,(double) getHeight() /360);
            int x = (int) ((e.getPoint().getX()*GameConfiguration.GAME_SCALE)/scale);
            int y = (int) ((e.getPoint().getY()*GameConfiguration.GAME_SCALE)/scale);
            Point point = new Point(x, y);
            if(state == 0) {
                //WIP
            }
            else if(state == 1) {
                Harbor harbor = factionManager.getHarborManager().pointCollisionToMapHarbor(point);
                if (harbor != null) {
                    if(e.getButton() == MouseEvent.BUTTON1) {
                        if (MapGame.getInstance().getPlayer().getLstHarbor().contains(harbor) && harborPlayer == null) {
                            harborPlayer = harbor;
                            dashboard.setHarborPlayer(harborPlayer);
                        } else if (faction.getLstHarbor().contains(harbor) && harborBot == null) {
                            harborBot = harbor;
                            dashboard.setHarborBot(harborBot);
                        }
                    }
                    else if (e.getButton() == MouseEvent.BUTTON3){
                        if (MapGame.getInstance().getPlayer().getLstHarbor().contains(harbor) && MapGame.getInstance().getPlayer().getLstHarbor().contains(harborPlayer)) {
                            harborPlayer = null;
                            dashboard.setHarborPlayer(null);
                        } else if (faction.getLstHarbor().contains(harbor) && faction.getLstHarbor().contains(harborBot)) {
                            harborBot = null;
                            dashboard.setHarborBot(null);
                        }
                    }
                }
            }
        }
    }

    private class KeyControls implements KeyListener {

        @Override
        public void keyPressed(KeyEvent event) {
            if(event.getKeyCode() == KeyEvent.VK_ESCAPE){
                ThreadStop = true;
                GUILoader.loadPauseMenu(GameConfiguration.ROOT_COMBAT);
            }
        }

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

    @Override
    public void run() {
        while (!ThreadStop) {
            try {
                Thread.sleep(GameConfiguration.GAME_SPEED);

            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            dashboard.repaint();
            dashboard.getPaintBackGround().setIFrame((dashboard.getPaintBackGround().getIFrame() + 1) % GameConfiguration.NUMBER_OF_BACK_GROUND_FRAMES);
            for (PopUp popUp : MapGame.getInstance().getLstPopUp()) {
                popUp.addIFrame(1);
            }
        }
    }
}
