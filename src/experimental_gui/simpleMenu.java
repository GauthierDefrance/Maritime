package experimental_gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * abstract template for game menus
 * @author Zue Jack-Arthur
 * @version 0.1
 * @see JPanel
 */
public abstract class simpleMenu extends JPanel {

    Container window;

    public simpleMenu(Container window) {
        super();
        this.window = window;
    }

    /**
     * An ActionListener allowing to exit the game
     */
    public class ExitListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                System.exit(0);
            } catch ( SecurityException e1 ) {
                JOptionPane.showMessageDialog(simpleMenu.this, "You are not allowed to exit!", "Error", JOptionPane.ERROR_MESSAGE );
            }

        }
    }
}

