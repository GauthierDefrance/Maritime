package gui.utilities;

import config.GameConfiguration;
import engine.entity.Entity;
import engine.entity.Harbor;
import engine.entity.Name;
import engine.entity.boats.Boat;
import engine.entity.boats.Fleet;
import engine.trading.SeaRoad;
import gui.process.ImageStock;
import log.LoggerUtility;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Class containing methods to build (Components)
 * @author Zue Jack-Arthur
 * @author Kenan Ammad
 * @see Component
 * @version 0.4
 */
public class JComponentBuilder {
    private static Logger logger = LoggerUtility.getLogger(JComponentBuilder.class);

    /**
     * Write log in a logger
     * @param name  text
     */
    private static void loggerWrite(String name) {
        logger.info("creation "+name);
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
     * @param entity the Entity Object attached to JButton
     * @return Built JButton
     */
    public static JButton menuButton(Entity entity) {
        JButton newButton = menuButton(entity.getName());
        loggerWrite("menuButton name "+entity.getName()," Object assigned name : "+entity.getName());
        newButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        newButton.setHorizontalTextPosition(SwingConstants.CENTER);
        newButton.setBackground(Color.DARK_GRAY);
        newButton.setForeground(Color.WHITE);
        newButton.setFocusPainted(false);
        newButton.setBorderPainted(false);
        if (entity instanceof Harbor) {
            newButton.setIcon( new ImageIcon(ImageStock.getImage((Harbor) entity)));
        } else if (entity instanceof Boat) {
            newButton.setIcon( new ImageIcon(ImageStock.getImage((Boat) entity)));
        }
        return newButton;
    }

//    /**
//     * Build a JButton for an Entity
//     * @param entity the Entity Object attached to JButton
//     * @return Built JButton
//     */
//    public static JButton menuButton(Name entity) {
//        JButton newButton = menuButton(entity.getName());
//        loggerWrite("menuButton name "+entity.getName()," Object assigned name : "+entity.getName());
//        newButton.setVerticalTextPosition(SwingConstants.BOTTOM);
//        newButton.setHorizontalTextPosition(SwingConstants.CENTER);
//        newButton.setBackground(Color.DARK_GRAY);
//        newButton.setForeground(Color.WHITE);
//        newButton.setFocusPainted(false);
//        newButton.setBorderPainted(false);
//        if (entity instanceof Harbor) {
//            newButton.setIcon( new ImageIcon(ImageStock.getImage((Harbor) entity)));
//        }
//        else if (entity instanceof Boat) {
//            newButton.setIcon( new ImageIcon(ImageStock.getImage((Boat) entity)));
//        }
//        else if (entity instanceof Fleet) {
//            newButton.setIcon( new ImageIcon(ImageStock.getImage((Boat) entity)));
//        }
//        else if (entity instanceof SeaRoad) {
//            newButton.setIcon( new ImageIcon(ImageStock.getImage((Boat) entity)));
//        }
//        return newButton;
//    }

    /**
     * Build a JButton for an Fleet
     * @param fleet the Fleet Object attached to JButton
     * @return Built JButton
     */
    public static JButton menuButton(Fleet fleet) {
        JButton newButton = menuButton(fleet.getName());
        loggerWrite("menuButton name " + fleet.getName(), " Object assigned name : " + fleet.getName());
        newButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        newButton.setHorizontalTextPosition(SwingConstants.CENTER);
        newButton.setBackground(Color.DARK_GRAY);
        newButton.setForeground(Color.WHITE);
        newButton.setFocusPainted(false);
        newButton.setBorderPainted(false);
        newButton.setIcon( new ImageIcon(ImageStock.getImage(fleet)));
        return newButton;
    }

    /**
     * Build a JButton for an SeaRoad
     * @param seaRoad the SeaRoad Object attached to JButton
     * @return Built JButton
     */
    public static JButton menuButton(SeaRoad seaRoad) {
        JButton newButton = menuButton(seaRoad.getName());
        loggerWrite("menuButton name " + seaRoad.getName(), " Object assigned name : " + seaRoad.getName());
        newButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        newButton.setHorizontalTextPosition(SwingConstants.CENTER);
        newButton.setBackground(Color.DARK_GRAY);
        newButton.setForeground(Color.WHITE);
        newButton.setFocusPainted(false);
        newButton.setBorderPainted(false);
        newButton.setIcon( new ImageIcon(ImageStock.getImage(seaRoad)));
        return newButton;
    }

    /**
     * Build a JButton for an Entity with its given actionListener implemented
     * @param entity the Entity Object attached to JButton
     * @param action actionListener
     * @return Built JButton
     */
    public static JButton menuButton(Entity entity, ActionListener action) {
        JButton newButton = menuButton(entity);
        loggerWrite("menuButton name : "+entity.getName()," --> ActionListener assigned name "+action.getClass().getName());
        newButton.addActionListener(action);
        return newButton;
    }

    /**
     * Build a JButton for an Entity with its given MouseListener implemented
     * @param entity the Entity Object attached to JButton
     * @param mouseAction MouseListener
     * @return Built JButton
     */
    public static JButton menuButton(Entity entity, MouseListener mouseAction) {
        JButton newButton = menuButton(entity);
        loggerWrite("menuButton name "+entity.getName(),"MouseListener assigned name "+mouseAction.getClass().getName());
        newButton.addMouseListener(mouseAction);
        return newButton;
    }

    public static JButton ImageButton(ImageIcon image) {
        JButton newButton = new JButton(image);
        newButton.setFocusable(false);
        newButton.setFocusPainted(false);
        newButton.setContentAreaFilled(false);
        loggerWrite("ImageButton Created");

        return newButton;
    }

    public static JButton ImageButton(ImageIcon image, ActionListener action) {
        JButton newButton = new JButton(image);
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
        newLabel.setFont(GameConfiguration.TITLE_FONT);
        return newLabel;
    }

    public static JLabel ImageLabel(ImageIcon image) {
        return new JLabel(image);
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

    public static JPanel SelectionZone(){
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        return contentPanel;
    }

}
