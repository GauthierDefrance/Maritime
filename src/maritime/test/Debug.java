package maritime.test;

import maritime.config.MapBuilder;

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
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JButton timeStop = new JButton("timeStop");
        timeStop.addActionListener(new TimeStop());
        contentPane.add(timeStop);

        setSize(640, 360);
        setVisible(true);
    }
    private class TimeStop implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            map.setTimeStop(!map.isTimeStop());
        }
    }
}
