package test;

import config.Map;
import gui.MainGUI;
import gui.process.GUILoader;
import gui.process.JComponentBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Debug extends JFrame{

    private Map map;


    public Debug(String title, Map map) {
        super(title);
        this.map = map;
        init();
    }

    private void init() {

        Container contentPane = getContentPane();
        contentPane.setLayout(new FlowLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JButton timeStop = JComponentBuilder.menuButton("timeStop",new TimeStop());
        JButton combatMenu = JComponentBuilder.menuButton("CombatMenu",new CombatMenu());
        contentPane.add(timeStop);
        contentPane.add(combatMenu);
        setFocusable(false);
        setLocationRelativeTo(null);
        setSize(360, 360);
        setVisible(false);
    }
    private class TimeStop implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            map.setTimeStop(!map.isTimeStop());
        }
    }
    private class CombatMenu implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            switch (MainGUI.getWindow().getComponent(0).getClass().getName()) {
                case "gui.panel.OptionsMenu" :
                case "gui.panel.PauseMenu" : {
                    GUILoader.loadCombat(MainGUI.getMap(),MainGUI.getBattle());
                    break;
                }
                default : {
                }
            }
        }
    }
}
