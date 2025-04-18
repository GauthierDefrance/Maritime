package test;

import config.GameConfiguration;
import engine.MapGame;
import engine.data.entity.boats.Boat;
import engine.data.entity.boats.Standard;
import engine.process.creational.EngineBuilder;
import gui.MainGUI;
import gui.utilities.GUILoader;
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

        JButton timeStop = JComponentFactory.menuButton("timeStop",new TimeStop());
        JButton combatMenu = JComponentFactory.menuButton("CombatMenu",new CombatMenu());
        JButton relationMenu = JComponentFactory.menuButton("RelationMenu", new RelationMenu());
        JButton choicePathMenu0 = JComponentFactory.menuButton("ChoicePathMenu0", new ChoiceMenu0());
        JButton choicePathMenu1 = JComponentFactory.menuButton("HarborMenu", new ChoiceMenu1());
        contentPane.add(timeStop);
        contentPane.add(combatMenu);
        contentPane.add(relationMenu);
        contentPane.add(choicePathMenu0);
        contentPane.add(choicePathMenu1);
        setAlwaysOnTop(true);
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

    private class ChoiceMenu0 implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (MainGUI.getWindow().getComponent(0).getClass().getName()) {
                case "gui.panel.menu.OptionsMenu" :
                case "gui.panel.menu.PauseMenu" : {
                    EngineBuilder.mapInit(0);
                    TestMove.addBoatTest();
                    GUILoader.loadChoicePathMenu(MapGame.getInstance().getPlayer().getLstHarbor().get(0),MapGame.getInstance().getLstHarbor().get(MapGame.getInstance().getLstHarbor().size()-1), GameConfiguration.ROOT_MAIN_GAME);
                    break;
                }
                default : {
                }
            }
        }
    }

    private class ChoiceMenu1 implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (MainGUI.getWindow().getComponent(0).getClass().getName()) {
                case "gui.panel.menu.OptionsMenu" :
                case "gui.panel.menu.PauseMenu" : {
                    EngineBuilder.mapInit(0);
                    TestMove.addBoatTest();
                    GUILoader.loadHarborMenu(MapGame.getInstance().getPlayer().getLstHarbor().get(0));
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
                case "gui.panel.menu.OptionsMenu" :
                case "gui.panel.menu.PauseMenu" : {
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
                case "gui.panel.menu.OptionsMenu" :
                case "gui.panel.menu.PauseMenu" : {
                    GUILoader.loadCombat(EngineBuilder.createBattle(MapGame.getInstance().getPlayer(),MapGame.getInstance().getLstBotFaction().get(0),MapGame.getInstance().getPlayer().getLstFleet().get(0), MapGame.getInstance().getLstBotFaction().get(0).getLstFleet().get(0)));
                    break;
                }
                default : {
                }
            }
        }
    }
}
