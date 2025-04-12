package gui.panel.menu;


import gui.process.JComponentFactory;
import gui.process.ListenerBehaviorManager;
import saveSystem.GameSave;
import saveSystem.process.GameSaveManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import static config.GameConfiguration.BUTTON_SEPARATOR;
import static gui.MainGUI.getWindow;

public class SaveFileMenu extends JPanel {

    private final int state; //decide if we're in a loading (0) or saving (1) state

    private final int token;
    private final Object objectToken;
    private final GameSaveManager manager = GameSaveManager.create();
    private final ArrayList<JButton> fileButtons;
    private JPanel menu;
    private JPanel jPanelCenter;
    private JPanel goBack;

    public SaveFileMenu(int token, int state, Object objectToken) {
        super();
        this.token = token;
        this.state = state;
        this.objectToken = objectToken;
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
            fileButtons.add(JComponentFactory.menuButton(textSetter(manager.fetchSaveFile(i)), new SaveListener(i)));
        }
        String t = "";
        if(state == 1)t = "Save";
        if(state == 0)t = "Load";


        JLabel title = JComponentFactory.title(t);
        JPanel titleDisplay = JComponentFactory.flowMenuPanel(title);

        JPanel saveLine1 = JComponentFactory.gridMenuPanel(1, 3, 10+BUTTON_SEPARATOR, BUTTON_SEPARATOR, fileButtons.get(0), fileButtons.get(1), fileButtons.get(2));
        JPanel saveLine2 = JComponentFactory.gridMenuPanel(1, 3, 10+BUTTON_SEPARATOR, BUTTON_SEPARATOR, fileButtons.get(3), fileButtons.get(4), fileButtons.get(5));

        JButton goBackButton = JComponentFactory.menuButton("Go back", new goBackButtonListener());
        goBack = JComponentFactory.borderMenuPanel();
        goBack.add(goBackButton,BorderLayout.CENTER);

        menu = JComponentFactory.gridMenuPanel(3, 1, BUTTON_SEPARATOR, BUTTON_SEPARATOR, saveLine1, saveLine2,goBack);
        jPanelCenter = JComponentFactory.voidPanel();
        jPanelCenter.add(menu);

        this.add(jPanelCenter, BorderLayout.CENTER);
        this.add(titleDisplay, BorderLayout.NORTH);
//        for (String position : new String[]{BorderLayout.NORTH, BorderLayout.SOUTH, BorderLayout.EAST, BorderLayout.WEST}) {
//            this.add(JComponentFactory.voidPanel(), position);
//        }

        jPanelCenter.setBackground(Color.gray);
        goBack.setBackground(Color.lightGray);
        menu.setBackground(Color.lightGray);
        saveLine1.setBackground(Color.lightGray);
        saveLine2.setBackground(Color.lightGray);
        titleDisplay.setBackground(Color.lightGray);


        getWindow().addComponentListener(new ComponentControls());
        sizeUpdate();
    }

    private void sizeUpdate() {
        menu.setPreferredSize(new Dimension((int) (getWindow().getWidth()*0.5),(int) (getWindow().getHeight()*0.4)));
        menu.setBorder(new EmptyBorder((int) (getWindow().getHeight()*0.03),(int) (getWindow().getHeight()*0.015), (int) (getWindow().getHeight()*0.02),(int) (getWindow().getHeight()*0.015)));
        jPanelCenter.setBorder(new EmptyBorder((int) (getWindow().getHeight()*0.02),0, (int) (getWindow().getHeight()*0.01),0));

        goBack.setBorder(new EmptyBorder((int) (getWindow().getHeight()*0.015),(int) (getWindow().getHeight()*0.05), 0,(int) (getWindow().getHeight()*0.05)));
        getWindow().revalidate();
        getWindow().repaint();
    }

    private class ComponentControls implements ComponentListener {

        @Override
        public void componentResized(ComponentEvent e) {
            sizeUpdate();
        }

        @Override
        public void componentMoved(ComponentEvent e) {

        }

        @Override
        public void componentShown(ComponentEvent e) {

        }

        @Override
        public void componentHidden(ComponentEvent e) {

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
            ls.goBack(token,objectToken);
        }
    }

    private class KeyControls implements KeyListener {

        @Override
        public void keyPressed(KeyEvent event) {
            if(event.getKeyCode() == KeyEvent.VK_ESCAPE){
                ListenerBehaviorManager ls = ListenerBehaviorManager.create();
                ls.goBack(token,objectToken);
            }
        }

        @Override
        public void keyTyped(KeyEvent e) { }

        @Override
        public void keyReleased(KeyEvent e) { }
    }
}
