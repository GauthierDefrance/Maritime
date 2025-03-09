package gui.panel;

import gui.process.ListenerBehavior;

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

    public SimpleMenu() {
        super();
    }

    /**
     * An ActionListener allowing to exit the game
     */
    public class ExitListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ListenerBehavior listenerBehavior = ListenerBehavior.create();
            listenerBehavior.exit(SimpleMenu.this);
        }
    }
}

