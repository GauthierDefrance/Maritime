package test;

import config.MapBuilder;
import gui.process.JComponentBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Debug extends JFrame{

    private MapBuilder map;


    public Debug(String title, MapBuilder map) {
        super(title);
        this.map = map;
        init();
    }

    private void init() {

        Container contentPane = getContentPane();
        contentPane.setLayout(new FlowLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JButton timeStop = JComponentBuilder.menuButton("timeStop",new TimeStop());
        contentPane.add(timeStop);

        setLocationRelativeTo(null);
        setSize(360, 360);
        setVisible(false);
    }
    private class TimeStop implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            map.setTimeStop(!map.isTimeStop());
        }
    }
}
