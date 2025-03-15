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

    private JButton goBackButton;
    private ArrayList<JButton> fileButtons;

    private JPanel saveLine1;
    private JPanel saveLine2;
    private JPanel menu;

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
            return "Pas de sauvegarde";
        }
        return save.getName();
    }

    public void init(){
        this.setLayout(new BorderLayout());
        this.addKeyListener(new KeyControls());
        for (int i = 0; i < 6; i++) {
            fileButtons.add(JComponentBuilder.menuButton(textSetter(GameSaveManager.getInstance().fetchSaveFile(i)), new SaveListener(i)));
        }

        saveLine1 = JComponentBuilder.gridMenuPanel(1,3,BUTTON_SEPARATOR, BUTTON_SEPARATOR, fileButtons.get(0), fileButtons.get(1), fileButtons.get(2));
        saveLine2 = JComponentBuilder.gridMenuPanel(1,3,BUTTON_SEPARATOR, BUTTON_SEPARATOR, fileButtons.get(3), fileButtons.get(4), fileButtons.get(5));

        goBackButton = JComponentBuilder.menuButton("Cancel", new goBackButtonListener());
        menu = JComponentBuilder.gridMenuPanel(3,1,BUTTON_SEPARATOR, BUTTON_SEPARATOR, saveLine1, saveLine2, goBackButton );

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
            GameSave save = GameSaveManager.getInstance().fetchSaveFile(fileID);
            if (state == 1){
                String msg;
                if (save == null) {
                    msg = GameSaveManager.getInstance().saveNewGame(fileID);
                } else {
                    msg = GameSaveManager.getInstance().overwriteSaveFile(save);
                } JOptionPane.showMessageDialog(SaveFileMenu.this, msg, "", JOptionPane.INFORMATION_MESSAGE);
                fileButtons.get(fileID).setText("GameSave0"+fileID+".ser");
            } else if (state == 0) {
                if (save != null) {
                    GameSaveManager.getInstance().loadGame(save);
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
