package test;

import engine.MapGame;
import engine.process.EngineBuilder;
import gui.MainGUI;
import gui.panel.ChoicePathMenu;
import gui.utilities.GUILoader;
import gui.utilities.JComponentBuilder;

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

        JButton timeStop = JComponentBuilder.menuButton("timeStop",new TimeStop());
        JButton combatMenu = JComponentBuilder.menuButton("CombatMenu",new CombatMenu());
        JButton relationMenu = JComponentBuilder.menuButton("RelationMenu", new RelationMenu());
        JButton choicePathMenu = JComponentBuilder.menuButton("ChoicePathMenu", new ChoiceMenu());
        contentPane.add(timeStop);
        contentPane.add(combatMenu);
        contentPane.add(relationMenu);
        contentPane.add(choicePathMenu);
        setFocusable(false);
        setLocationRelativeTo(null);
        setSize(360, 360);
        setVisible(false);
    }
    private class TimeStop implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            MapGame.getInstance().setTimeStop(!MapGame.getInstance().isTimeStop());
        }
    }

    private class ChoiceMenu implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (MainGUI.getWindow().getComponent(0).getClass().getName()) {
                case "gui.panel.OptionsMenu" :
                case "gui.panel.PauseMenu" : {
                    EngineBuilder.mapInit(0);
                    TestMove.addBaotTest();
                    MainGUI.getWindow().removeAll();
                    MainGUI.getWindow().add(new ChoicePathMenu(1, MapGame.getInstance().getPlayer()));
                    MainGUI.getWindow().revalidate();
                    MainGUI.getWindow().repaint();
                    break;
                }
                default : {
                }
            }
        }
    }

    private class RelationMenu implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (MainGUI.getWindow().getComponent(0).getClass().getName()) {
                case "gui.panel.OptionsMenu" :
                case "gui.panel.PauseMenu" : {
                    GUILoader.loadRelationMenu(MapGame.getInstance().getLstBotFaction().get(0));
                    break;
                }
                default : {
                }
            }
        }
    }
    
    private class CombatMenu implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            switch (MainGUI.getWindow().getComponent(0).getClass().getName()) {
                case "gui.panel.OptionsMenu" :
                case "gui.panel.PauseMenu" : {
                    GUILoader.loadCombat(MainGUI.getBattle());
                    break;
                }
                default : {
                }
            }
        }
    }
}
