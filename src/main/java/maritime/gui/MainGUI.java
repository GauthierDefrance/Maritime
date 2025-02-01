package maritime.gui;

import javax.swing.*;
import java.awt.*;

public class MainGUI extends JFrame {

    private GameDisplay dashboard;


    public MainGUI(String title) {
        super(title);
        init();
    }

    private void init() {

        Container contentPane = getContentPane();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setSize(640, 360);
        setVisible(true);
    }
}
