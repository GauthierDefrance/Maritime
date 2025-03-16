package test;

import engine.Map;
import gui.MainGUI;
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
        JButton RelationMenu = JComponentBuilder.menuButton("RelationMenu", new RelationMenu());
        contentPane.add(timeStop);
        contentPane.add(combatMenu);
        contentPane.add(RelationMenu);
        setFocusable(false);
        setLocationRelativeTo(null);
        setSize(360, 360);
        setVisible(false);
    }
    private class TimeStop implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Map.getInstance().setTimeStop(!Map.getInstance().isTimeStop());
        }
    }

    private class RelationMenu implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (MainGUI.getWindow().getComponent(0).getClass().getName()) {
                case "gui.panel.OptionsMenu" :
                case "gui.panel.PauseMenu" : {
                    GUILoader.loadRelationMenu();
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
