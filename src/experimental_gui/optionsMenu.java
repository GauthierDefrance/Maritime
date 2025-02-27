package experimental_gui;

import config.GameConfiguration;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * options menu for the game
 * @see JPanel
 * @author Zue Jack-Arthur
 * @version 0.1
 */
public class optionsMenu extends simpleMenu {

    private int token;
    private JButton goBackButton;

    private Label soundLevel;
    private JButton plusButton;
    private JButton minusButton;

    private Label muteLabel;
    private JButton muteButton;

    private Label debugLabel;
    private JButton debugButton;


    public optionsMenu(int token, Container window) {
        super(window);
        this.token = token;
        init();
    }

    public void init() {

        goBackButton = new JButton("Go Back");
        goBackButton.setFont(GameConfiguration.FONT);
        goBackButton.addActionListener(new goBackButtonListener());

        soundLevel = new Label("Sound Level");
        soundLevel.setFont(GameConfiguration.FONT);


        plusButton = new JButton("+");
        plusButton.setFont(GameConfiguration.FONT);
        minusButton = new JButton("-");
        minusButton.setFont(GameConfiguration.FONT);

        muteLabel = new Label("Mute");
        muteLabel.setFont(GameConfiguration.FONT);
        muteButton = new JButton("Mute");
        muteButton.setFont(GameConfiguration.FONT);
        muteButton.addActionListener(new muteButtonListener());


        debugLabel = new Label("Debug");
        debugLabel.setFont(GameConfiguration.FONT);
        debugButton = new JButton("Debug");
        debugButton.setFont(GameConfiguration.FONT);

        this.add(goBackButton);
        this.add(soundLevel);
        this.add(plusButton);
        this.add(minusButton);
        this.add(muteLabel);
        this.add(muteButton);
        this.add(debugLabel);
        this.add(debugButton);
    }

    public class goBackButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (token == 0) GUILoader.loadStartMenu(window);
            else GUILoader.loadPauseMenu(window);
        }
    }

    public class minusButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //WiP
        }
    }

    public class plusButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //WiP
        }
    }

    public class muteButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (muteButton.getText().equals("Mute")) {
                muteButton.setText("Unmute");
            } else muteButton.setText("Mute");
        }
    }

    public class debugMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //WiP
        }
    }
}
