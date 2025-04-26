package test;

import config.GameConfiguration;
import engine.MapGame;
import engine.data.entity.Harbor;
import engine.data.trading.Resource;
import gui.process.ImageStock;
import gui.process.JComponentFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Debug extends JFrame{

    public Debug(String title) {
        super(title);
        init();
    }

    private void init() {

        Container contentPane = getContentPane();
        contentPane.setLayout(new FlowLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JButton timeStop = JComponentFactory.menuButton("TimeStop",new TimeStop());
        JButton godMode = JComponentFactory.menuButton("GodMode",new GodMode());
        JButton noSpawnMode = JComponentFactory.menuButton("NoSpawnMode",new NoSpawnMode());
        JButton giveResource = JComponentFactory.menuButton("GiveResource",new GiveResource());

        if(MapGame.getInstance().isTimeStop())timeStop.setText("TimeStop : true");
        else timeStop.setText("timeStop : false");
        if(MapGame.getInstance().isGodMode())godMode.setText("GodMode : true");
        else godMode.setText("GodMode : false");
        if(MapGame.getInstance().isNoSpawnMode())noSpawnMode.setText("NoSpawnMode : true");
        else noSpawnMode.setText("NoSpawnMode : false");

        setIconImage(ImageStock.getImages(0));
        contentPane.add(timeStop);
        contentPane.add(godMode);
        contentPane.add(noSpawnMode);
        contentPane.add(giveResource);
        setAlwaysOnTop(true);
        setFocusable(false);
        setLocationRelativeTo(null);
        setSize(360, 360);
        setVisible(false);
    }
    private class TimeStop implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            MapGame.getInstance().setTimeStop(!MapGame.getInstance().isTimeStop());
            if(MapGame.getInstance().isTimeStop())((JButton)e.getSource()).setText("TimeStop : true");
            else((JButton)e.getSource()).setText("timeStop : false");
        }
    }

    private class GodMode implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            MapGame.getInstance().setGodMode(!MapGame.getInstance().isGodMode());
            if(MapGame.getInstance().isGodMode())((JButton)e.getSource()).setText("GodMode : true");
            else((JButton)e.getSource()).setText("GodMode : false");
        }
    }

    private class NoSpawnMode implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            MapGame.getInstance().setNoSpawnMode(!MapGame.getInstance().isNoSpawnMode());
            if(MapGame.getInstance().isNoSpawnMode())((JButton)e.getSource()).setText("NoSpawnMode : true");
            else((JButton)e.getSource()).setText("NoSpawnMode : false");
        }
    }

    private class GiveResource implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            for (Harbor harbor : MapGame.getInstance().getPlayer().getLstHarbor()){
                for(Resource resource : GameConfiguration.LIST_RESOURCE){
                    harbor.getInventory().add(resource,1000);
                }
            }
            MapGame.getInstance().getPlayer().addAmountCurrency(1000);
        }
    }
}
