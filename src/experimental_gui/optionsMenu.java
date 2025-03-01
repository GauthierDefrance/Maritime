package experimental_gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * options menu for the game
 * @see JPanel
 * @author Zue Jack-Arthur
 * @version 0.3
 */
public class optionsMenu extends simpleMenu {

    private int token;

    private JButton goBackButton;
    private JPanel goBackPanel;

    private JPanel optionDisplayer;
    private JPanel suboptionDisplayer;

    private JPanel soundPanel;
    private JLabel soundLevel;
    private JButton plusButton;
    private JButton minusButton;

    private JPanel mutePanel;
    private JLabel muteLabel;
    private JButton muteButton;

    private JPanel debugPanel;
    private JLabel debugLabel;
    private JButton debugButton;


    public optionsMenu(int token, Container window) {
        super(window);
        this.token = token;
        init();
    }

    public void init() {

        this.setLayout(new FlowLayout(FlowLayout.CENTER));

        goBackButton = JComponentBuilder.menuButton("Go back", new goBackButtonListener());
        goBackPanel = JComponentBuilder.flowMenuPanel(goBackButton);

        soundLevel = JComponentBuilder.menuLabel("Sound Level");

        plusButton = JComponentBuilder.menuButton("+", new plusButtonListener());

        minusButton = JComponentBuilder.menuButton("-", new minusButtonListener());

        soundPanel = JComponentBuilder.flowMenuPanel(plusButton, minusButton);

        muteLabel = JComponentBuilder.menuLabel("Mute");
        muteButton = JComponentBuilder.menuButton("Off", new muteButtonListener());

        debugLabel = JComponentBuilder.menuLabel("Debug Menu");
        debugButton = JComponentBuilder.menuButton("Off", new debugMenuListener());

        optionDisplayer = JComponentBuilder.borderMenuPanel();
        suboptionDisplayer = JComponentBuilder.gridMenuPanel(3,2,soundLevel, soundPanel, muteLabel, muteButton, debugLabel, debugButton);
        optionDisplayer.add(goBackPanel, BorderLayout.NORTH);
        optionDisplayer.add(suboptionDisplayer, BorderLayout.CENTER);

        this.add(optionDisplayer);
    }

    public class goBackButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (token == 0) GUILoader.loadStartMenu(window);
            else GUILoader.loadPauseMenu(0,window);
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
            if (muteButton.getText().equals("Off")) {
                muteButton.setText("On");
            } else muteButton.setText("Off");
        }
    }

    public class debugMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //WiP
        }
    }
}
