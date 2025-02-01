package maritime.gui;

import javax.swing.*;
import java.awt.*;

public class GameDisplay extends JPanel {

    private PaintStrategy paintStrategy = new PaintStrategy();


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.scale((double) getWidth() / 640, (double) getHeight() / 360);
    }
}
