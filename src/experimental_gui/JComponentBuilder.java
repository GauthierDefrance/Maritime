package experimental_gui;

import config.GameConfiguration;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Class containing methods to build (Components)
 * @author Zue Jack-Arthur
 * @see Component
 * @version 0.1
 */
public class JComponentBuilder {

    /**
     * Build a JButton accommodating game-menu Convention
     * @param text JButton text
     * @return Built JButton
     */
    public static JButton menuButton(String text) {
        JButton newButton = new JButton(text);
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
        JButton newButton = menuButton(text);
        newButton.addActionListener(action);
        return newButton;
    }

    /**
     * Build a JLabel accommodating game-menu Convention
     * @param text JLabel text
     * @return Built JLabel
     */
    public static JLabel menuLabel(String text) {
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
        JLabel newLabel = new JLabel(text);
        newLabel.setFont(GameConfiguration.TITLE_FONT);
        return newLabel;
    }

    /**
     * Build a default JPanel accommodating game-menu Convention
     * @return built JPanel
     */
    public static JPanel flowMenuPanel() {
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
        } return newPanel;
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
        newPanel.setLayout(new GridLayout(r, c));
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
    public static JPanel gridMenuPanel(int r, int c,JComponent... components) {
        JPanel newPanel = gridMenuPanel(r,c);
        for (JComponent component : components) {
            newPanel.add(component);
        } return newPanel;
    }

    /**
     * Build a GridLayout JPanel accommodating game-menu Convention
     * @param r number of rows
     * @param c number of columns
     * @param hgap height gap between rows
     * @param vgap width gap between rows
     * @see GridLayout
     * @return built JPanel
     */
    public static JPanel gridMenuPanel(int r, int c,int hgap,int vgap) {
        JPanel newPanel = new JPanel();
        newPanel.setLayout(new GridLayout(r, c,hgap,vgap));
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
    public static JPanel gridMenuPanel(int r, int c,int hgap,int vgap, JComponent... components) {
        JPanel newPanel = gridMenuPanel(r,c,hgap,vgap);
        for (JComponent component : components) {
            newPanel.add(component);
        } return newPanel;
    }

    /**
     * Build a BorderLayout JPanel accommodating game-menu Convention
     * @see BorderLayout
     * @return built JPanel
     */
    public static JPanel borderMenuPanel() {
        JPanel newPanel = new JPanel();
        newPanel.setLayout(new BorderLayout());
        return newPanel;
    }
}
