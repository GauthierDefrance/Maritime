package gui.panel;

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
public abstract class SimpleMenu extends JPanel {

     private Container window;

    public SimpleMenu(Container window) {
        super();
        this.window = window;
    }

    //Getter

    public Container getWindow() {
        return window;
    }

    //Setter

    public void setWindow(Container window) {
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
                JOptionPane.showMessageDialog(SimpleMenu.this, "You are not allowed to exit!", "Error", JOptionPane.ERROR_MESSAGE );
            }
        }
    }
}

