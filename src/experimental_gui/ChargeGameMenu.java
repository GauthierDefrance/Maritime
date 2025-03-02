package experimental_gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChargeGameMenu extends SimpleMenu {

    private int token;

    private JButton goBackButton;
    private JButton loadFile1;
    private JButton loadFile2;
    private JButton loadFile3;

    public ChargeGameMenu(int token, Container window) {
        super(window);
        this.token = token;
        init();
    }
    public void init(){
    }

    public class goBackButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (token == 0) GUILoader.loadStartMenu(getWindow());
            else GUILoader.loadPauseMenu(1,getWindow());
        }
    }
}
