package maritime.test;

import maritime.config.GameInitFactory;
import maritime.gui.GameDisplay;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TestDebug extends JFrame{

    private GameInitFactory map;


    public TestDebug(String title, GameInitFactory map) {
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
