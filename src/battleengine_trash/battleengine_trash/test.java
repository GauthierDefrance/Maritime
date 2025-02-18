package battleengine_trash.battleengine_trash;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.Graphics2D;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class test extends JPanel {
    protected void paintComponent(Graphics2D g) {
        super.paintComponent(g);
        // Vérification si 'g' est une instance de Graphics2D
        if (g instanceof Graphics2D) {
            // Si c'est le cas, on peut effectuer le casting
            Graphics2D g2d = (Graphics2D) g;

            // Créer un rectangle
            Rectangle2D.Double rect = new Rectangle2D.Double(100, 100, 200, 100);  // Position et taille du rectangle

            // Rotation de 45 degrés autour du centre du rectangle
            g2d.rotate(Math.toRadians(45), rect.getCenterX(), rect.getCenterY());

            // Dessiner le rectangle après transformation
            g2d.draw(rect);
        } else {
            System.out.println("Erreur : L'objet Graphics n'est pas une instance de Graphics2D.");
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.add(new test());
        frame.setVisible(true);
    }
}
