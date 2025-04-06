package gui.process;

import config.GameConfiguration;
import engine.data.entity.Harbor;
import engine.data.entity.boats.Boat;
import engine.data.Fleet;
import engine.data.trading.SeaRoad;
import log.LoggerUtility;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.util.EventListener;

/**
 * Class containing methods to make (JComponents)
 * @author Zue Jack-Arthur
 * @author Kenan Ammad
 * @see Component
 * @version 0.6
 */
public class JComponentFactory {
    private static Logger logger = LoggerUtility.getLogger(JComponentFactory.class);

    /**
     * Write log in a logger
     * @param name  text
     */
    private static void loggerWrite(String name) {
        logger.info("creation : "+name);
    }

    /**
     * Write log in a logger
     * @param name  text
     * @param param  text
     */
    private static void loggerWrite(String name,String param) {
        logger.info(name+" got "+param);
    }

    /**
     * Build a JButton accommodating game-menu Convention
     * @param text JButton text
     * @return Built JButton
     */
    public static JButton menuButton(String text) {
        loggerWrite("menuButton name "+text);
        JButton newButton = new JButton(text);
        newButton.setFocusable(false);
        newButton.setFont(GameConfiguration.FONT);
        return newButton;
    }

    /**
     * Build a JButton accommodating game-menu Convention with its given action implemented
     * @param text JButton text
     * @param action Button Action
     * @return Built JButton
     */
    public static JButton menuButton(String text, ActionListener action) {
        loggerWrite("menuButton name "+text,"ActionListener assigned name "+action.getClass().getName());
        JButton newButton = menuButton(text);
        newButton.addActionListener(action);
        return newButton;
    }

    /**
     * Build a JButton for an Entity
     * @param o the Object attached to JButton
     * @return Built JButton
     */
    public static JButton menuButton(Object o) {
        JButton newButton = menuButton(o.getClass().getName());
        loggerWrite("menuButton name "+o.getClass().getName()," Object assigned name : "+o.getClass().getName());
        newButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        newButton.setHorizontalTextPosition(SwingConstants.CENTER);
        newButton.setBackground(Color.DARK_GRAY);
        newButton.setForeground(Color.WHITE);
        newButton.setFocusPainted(false);
        newButton.setBorderPainted(false);
        if (o instanceof Harbor) {
            Harbor harbor = (Harbor) o;
            newButton.setText(harbor.getName());
            newButton.setIcon(new ImageIcon(ImageStock.getImage(harbor)));
        } else if (o instanceof Boat) {
            Boat boat = (Boat) o;
            newButton.setText(boat.getName());
            newButton.setIcon(new ImageIcon(ImageStock.getImage(boat)));
        } else if (o instanceof Fleet) {
            Fleet fleet = (Fleet) o;
            newButton.setText(fleet.getName());
            newButton.setIcon(new ImageIcon(ImageStock.getImage(fleet)));
        } else if (o instanceof SeaRoad) {
            SeaRoad seaRoad = (SeaRoad) o;
            newButton.setText(seaRoad.getName());
            newButton.setIcon(new ImageIcon(ImageStock.getImage(seaRoad)));
        } else {
            newButton.setText(o.getClass().getName());
        }
        return newButton;
    }

    /**
     * Build a JButton for an Entity with its given Listener implemented
     * @param o the Object attached to JButton
     * @param listener Listener
     * @return Built JButton
     */
    public static JButton menuButton(Object o, EventListener listener) {
        JButton newButton = menuButton(o);
        String name = getEntityName(o);

        if (listener instanceof ActionListener) {
            newButton.addActionListener((ActionListener) listener);
            loggerWrite("menuButton name : " + name, "--> ActionListener assigned name " + listener.getClass().getName());
        } else if (listener instanceof MouseListener) {
            newButton.addMouseListener((MouseListener) listener);
            loggerWrite("menuButton name : " + name, "--> MouseListener assigned name " + listener.getClass().getName());
        } else {
            loggerWrite("menuButton name : " + name, "--> Unsupported listener type: " + listener.getClass().getName());
        }

        return newButton;
    }

    /**
     * Check the name of the associated Object
     * @param o object to be analyzed
     * @return name of the Object
     */
    private static String getEntityName(Object o) {
        if (o instanceof Harbor) {
            return ((Harbor) o).getName();
        } else if (o instanceof Boat) {
            return ((Boat) o).getName();
        } else if (o instanceof Fleet) {
            return ((Fleet) o).getName();
        } else if (o instanceof SeaRoad) {
            return ((SeaRoad) o).getName();
        } else {
            return o.getClass().getName();
        }
    }


    public static JButton ImageButton(ImageIcon image) {
        JButton newButton = new JButton(image);
        newButton.setFocusable(false);
        loggerWrite("ImageButton Created");

        return newButton;
    }

    public static JButton ImageButton(ImageIcon image, ActionListener action) {
        JButton newButton = ImageButton(image);
        newButton.addActionListener(action);
        loggerWrite("ImageButton ActionListener assigned > "+image.getClass().getName());
        return newButton;
    }

    /**
     * Build a JLabel accommodating game-menu Convention
     * @param text JLabel text
     * @return Built JLabel
     */
    public static JLabel menuLabel(String text) {
        loggerWrite("menuLabel name "+text);
        JLabel newLabel = new JLabel(text);
        newLabel.setFocusable(false);
        newLabel.setFont(GameConfiguration.FONT);
        return newLabel;
    }

    /**
     * Build a "credits" JLabel accommodating game-menu Convention
     * @param text JLabel text
     * @return Built JLabel
     */
    public static JLabel credits(String text) {
        loggerWrite("creditsLabel name "+text);
        JLabel newLabel = new JLabel(text);
        newLabel.setFocusable(false);
        newLabel.setFont(GameConfiguration.CREDITS_FONT);
        return newLabel;
    }

    /**
     * Build a "title" JLabel accommodating game-menu Convention
     * @param text JLabel text
     * @return Built JLabel
     */
    public static JLabel title(String text) {
        loggerWrite("titleLabel  name "+text);
        JLabel newLabel = new JLabel(text);
        newLabel.setFocusable(false);
        newLabel.setFont(GameConfiguration.TITLE_FONT);
        return newLabel;
    }

    public static JLabel ImageLabel(ImageIcon image) {
        JLabel newLabel = new JLabel(image);
        newLabel.setFocusable(false);
        return newLabel;
    }

    /**
     * Build an Empty JPanel to occupy void space
     * @return built JPanel
     */
    public static JPanel voidPanel() {
        loggerWrite("JPanel");
        return new JPanel();
    }

    /**
     * Build an Empty JPanel to occupy void space
     * @return built JPanel
     */
    public static JPopupMenu voidPopupMenu() {
        loggerWrite("PopupMenu");
        JPopupMenu jPopupMenu = new JPopupMenu();
        jPopupMenu.setFocusable(false);
        return jPopupMenu;
    }

    /**
     * Build a JScrollPane accommodating game-menu Convention
     * @see JScrollPane
     * @return built JPanel
     */
    public static JScrollPane ScrollPaneMenuPanel(JComponent jComponent) {
        loggerWrite("jScrollPane");
        return new JScrollPane(jComponent);
    }

    /**
     * Build a default JPanel accommodating game-menu Convention
     * @return built JPanel
     */
    public static JPanel flowMenuPanel() {
        loggerWrite("flowMenuPanel");
        JPanel newPanel = new JPanel();
        newPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        return newPanel;
    }

    /**
     * Build a default JPanel accommodating game-menu Convention
     * @param components other components that must be present within the JPanel
     * @return built JPanel
     */
    public static JPanel flowMenuPanel(JComponent... components) {
        JPanel newPanel = flowMenuPanel();
        for (JComponent component : components) {
            newPanel.add(component);
            loggerWrite("flowMenuPanel",component.getClass().getName()+" Object assigned name "+component.getName());
        }
        return newPanel;
    }

    /**
     * Build a GridLayout JPanel accommodating game-menu Convention
     * @param r number of rows
     * @param c number of columns
     * @see GridLayout
     * @return built JPanel
     */
    public static JPanel gridMenuPanel(int r, int c) {
        JPanel newPanel = new JPanel();
        loggerWrite("gridMenuPanel r="+r+" c="+c);
        newPanel.setLayout(new GridLayout(r, c));
        return newPanel;
    }

    /**
     * Build a GridLayout JPanel accommodating game-menu Convention
     * @param r number of rows
     * @param c number of columns
     * @param heightGap height gap between rows
     * @param widthGap width gap between rows
     * @see GridLayout
     * @return built JPanel
     */
    public static JPanel gridMenuPanel(int r, int c,int heightGap,int widthGap) {
        JPanel newPanel = new JPanel();
        loggerWrite("gridMenuPanel r="+r+" c="+c+" heightGap="+heightGap+" widthGap="+widthGap);
        newPanel.setLayout(new GridLayout(r, c,heightGap,widthGap));
        return newPanel;
    }

    /**
     * Build a GridLayout JPanel accommodating game-menu Convention
     * @param r number of rows
     * @param c number of columns
     * @param components other components that must be present within the JPanel
     * @see GridLayout
     * @return built JPanel
     */
    public static JPanel gridMenuPanel(int r, int c, JComponent... components) {
        JPanel newPanel = gridMenuPanel(r,c);
        for (JComponent component : components) {
            newPanel.add(component);
            loggerWrite("gridMenuPanel r="+r+" c="+c,component.getClass().getName()+" Object assigned name "+component.getName());
        } return newPanel;
    }

    /**
     * Build a GridLayout JPanel accommodating game-menu Convention
     * @param r number of rows
     * @param c number of columns
     * @param heightGap height gap between rows
     * @param widthGap width gap between rows
     * @param components other components that must be present within the JPanel
     * @see GridLayout
     * @return built JPanel
     */
    public static JPanel gridMenuPanel(int r, int c,int heightGap,int widthGap, JComponent... components) {
        JPanel newPanel = gridMenuPanel(r,c,heightGap,widthGap);
        for (JComponent component : components) {
            newPanel.add(component);
            loggerWrite("gridMenuPanel r="+r+" c="+c+" heightGap="+heightGap+" widthGap="+widthGap,component.getClass().getName()+" Object assigned name "+component.getName());
        } return newPanel;
    }

    /**
     * Build a BorderLayout JPanel accommodating game-menu Convention
     * @see BorderLayout
     * @return built JPanel
     */
    public static JPanel borderMenuPanel() {
        loggerWrite("borderMenuPanel");
        JPanel newPanel = new JPanel();
        newPanel.setLayout(new BorderLayout());
        return newPanel;
    }

    /**
     * Build a BoxLayout JPanel accommodating game-menu Convention
     * @see BoxLayout
     * @param axis BoxLayout.Y_AXIS or BoxLayout.X_AXIS  0 for X and 1 for Y
     * @return built JPanel
     */
    public static JPanel boxMenuPanel(int axis) {
        JPanel newPanel = new JPanel();
        if (axis >= 0 && axis <= 4) {
            newPanel.setLayout(new BoxLayout(newPanel, axis));
        }
        loggerWrite("boxMenuPanel generated");
        return newPanel;
    }

    public static JPanel SelectionZone(){
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        return contentPanel;
    }

    /**
     */
    public static void setBorderIn(JComponent jComponent, Border border) {
        loggerWrite("jComponent"+jComponent.getName(),"setBorder");
        for(Component ComponentIn : jComponent.getComponents()){
            JComponent jComponentIn = (JComponent) ComponentIn;
            jComponentIn.setBorder(border);
        }
    }

}
