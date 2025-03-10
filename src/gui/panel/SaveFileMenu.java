package gui.panel;

import config.MapBuilder;
import gui.process.JComponentBuilder;
import gui.process.ListenerBehavior;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import static config.GameConfiguration.BUTTON_SEPARATOR;

public class SaveFileMenu extends SimpleMenu {

    private int state;
    private int token;

    private JButton goBackButton;
    private JButton file1;
    private JButton file2;
    private JButton file3;
    private JButton file4;
    private JButton file5;
    private JButton file6;

    private JPanel savefile;
    private JPanel savefile2;
    private JPanel menu;

    public SaveFileMenu(int token, int state) {
        super();
        this.token = token;
        this.state = state;
        init();
    }

    public void init(){
        this.setLayout(new BorderLayout());
        this.addKeyListener(new KeyControls());

        file1 = JComponentBuilder.menuButton("");
        file2 = JComponentBuilder.menuButton("");
        file3 = JComponentBuilder.menuButton("");
        file4 = JComponentBuilder.menuButton("");
        file5 = JComponentBuilder.menuButton("");
        file6 = JComponentBuilder.menuButton("");
        savefile = JComponentBuilder.gridMenuPanel(1,3,BUTTON_SEPARATOR, BUTTON_SEPARATOR, file1, file2, file3);
        savefile2 = JComponentBuilder.gridMenuPanel(1,3,BUTTON_SEPARATOR, BUTTON_SEPARATOR, file4, file5, file6);

        goBackButton = JComponentBuilder.menuButton("Cancel", new goBackButtonListener());
        menu = JComponentBuilder.gridMenuPanel(3,1,BUTTON_SEPARATOR, BUTTON_SEPARATOR,savefile,savefile2, goBackButton );

        this.add(JComponentBuilder.voidPanel(), BorderLayout.NORTH);
        this.add(menu, BorderLayout.CENTER);
        this.add(JComponentBuilder.voidPanel(), BorderLayout.SOUTH);
        this.add(JComponentBuilder.voidPanel(), BorderLayout.EAST);
        this.add(JComponentBuilder.voidPanel(), BorderLayout.WEST);
    }

    public class goBackButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ListenerBehavior ls = ListenerBehavior.create();
            ls.goBack(token);
        }
    }

    private class KeyControls implements KeyListener {

        @Override
        public void keyPressed(KeyEvent event) {
            if(event.getKeyCode() == KeyEvent.VK_ESCAPE){
                ListenerBehavior ls = ListenerBehavior.create();
                ls.goBack(token);
            }
        }

        @Override
        public void keyTyped(KeyEvent e) { }

        @Override
        public void keyReleased(KeyEvent e) { }
    }
}
