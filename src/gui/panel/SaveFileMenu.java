package gui.panel;


import gui.utilities.JComponentBuilder;
import gui.process.ListenerBehaviorManager;
import saveSystem.GameSave;
import saveSystem.process.GameSaveManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import static config.GameConfiguration.BUTTON_SEPARATOR;

public class SaveFileMenu extends JPanel {

    private final int state; //decide if we're in a loading (0) or saving (1) state

    private final int token;

    private final GameSaveManager manager = GameSaveManager.create();

    private final ArrayList<JButton> fileButtons;

    public SaveFileMenu(int token, int state) {
        super();
        this.token = token;
        this.state = state;
        this.fileButtons = new ArrayList<>();
        init();
    }

    //Utilities

    private String textSetter(GameSave save) {
        if (save == null) {
            return "No save found";
        }
        return save.getName().split(".ser")[0];
    }

    public void init(){
        this.setLayout(new BorderLayout());
        this.addKeyListener(new KeyControls());
        for (int i = 0; i < 6; i++) {
            fileButtons.add(JComponentBuilder.menuButton(textSetter(manager.fetchSaveFile(i)), new SaveListener(i)));
        }

        JPanel saveLine1 = JComponentBuilder.gridMenuPanel(1, 3, BUTTON_SEPARATOR, BUTTON_SEPARATOR, fileButtons.get(0), fileButtons.get(1), fileButtons.get(2));
        JPanel saveLine2 = JComponentBuilder.gridMenuPanel(1, 3, BUTTON_SEPARATOR, BUTTON_SEPARATOR, fileButtons.get(3), fileButtons.get(4), fileButtons.get(5));

        JButton goBackButton = JComponentBuilder.menuButton("Cancel", new goBackButtonListener());
        JPanel menu = JComponentBuilder.gridMenuPanel(3, 1, BUTTON_SEPARATOR, BUTTON_SEPARATOR, saveLine1, saveLine2, goBackButton);

        this.add(menu, BorderLayout.CENTER);
        for (String position : new String[]{BorderLayout.NORTH, BorderLayout.SOUTH, BorderLayout.EAST, BorderLayout.WEST}) {
            this.add(JComponentBuilder.voidPanel(), position);
        }
    }

    public class SaveListener implements ActionListener {
        private final int fileID;
        public SaveListener(int fileID) {
            this.fileID = fileID;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            GameSave save = manager.fetchSaveFile(fileID);
            if (state == 1) {
                String msg;
                msg = manager.saveNewGame(fileID);
                JOptionPane.showMessageDialog(SaveFileMenu.this, msg, "", JOptionPane.INFORMATION_MESSAGE);
                fileButtons.get(fileID).setText("GameSave0" + fileID);
            } else if (state == 0) {
                if (save != null) {
                    manager.loadGame(save);
                }
            }
        }
    }

    public class goBackButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ListenerBehaviorManager ls = ListenerBehaviorManager.create();
            ls.goBack(token);
        }
    }

    private class KeyControls implements KeyListener {

        @Override
        public void keyPressed(KeyEvent event) {
            if(event.getKeyCode() == KeyEvent.VK_ESCAPE){
                ListenerBehaviorManager ls = ListenerBehaviorManager.create();
                ls.goBack(token);
            }
        }

        @Override
        public void keyTyped(KeyEvent e) { }

        @Override
        public void keyReleased(KeyEvent e) { }
    }
}
