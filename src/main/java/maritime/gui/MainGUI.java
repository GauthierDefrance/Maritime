package maritime.gui;

import maritime.config.GameConfiguration;

import javax.swing.*;
import java.awt.*;

/**
 * @author @Kenan Ammad
 * Classe MainGUI
 * @version 0.0
 */
public class MainGUI extends JFrame implements Runnable {

    private GameDisplay dashboard;


    public MainGUI(String title) {
        super(title);
        init();
    }

    private void init() {

        Container contentPane = getContentPane();
        contentPane.add(dashboard);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setSize(640, 360);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(GameConfiguration.GAME_SPEED);

            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
            dashboard.repaint();
        }
    }

}
