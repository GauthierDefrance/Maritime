package experimental_gui;

import config.GameConfiguration;

import javax.swing.*;
import java.awt.*;

/**
 * Starting point of the program
 * @author Zue Jack-Arthur
 * @version NOPE LOL
 */
public class mainGUI extends JFrame {
    private Container window;

    public mainGUI() {
        super("Maritime");
        init();
    }

    public void init() {
        window = getContentPane();
        window.setLayout(new BorderLayout());
        setSize(GameConfiguration.WINDOW_SIZE);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GUILoader.loadStartMenu(window);
    }

    public static void main(String[] args) {
        new mainGUI();
    }
}
