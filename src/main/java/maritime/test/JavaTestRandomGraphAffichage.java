package maritime.test;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

import maritime.engine.graph.*;

public class JavaTestRandomGraphAffichage extends JPanel {
    private static ArrayList<GraphPoint> lstSegmentResult;
    private List<GraphPoint> points;

    // Constructeur pour initialiser la liste de points
    public JavaTestRandomGraphAffichage(List<GraphPoint> points) {
        this.points = points;
        setPreferredSize(new Dimension(640, 360));
    }

    // Méthode de dessin des points et des segments
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        double scale = Math.min((double)getWidth()/640,(double) getHeight() /360);

        g2d.scale(scale,scale);
        g2d.setStroke(new BasicStroke(1));  // Ligne plus épaisse pour les segments
        // Dessiner les segments dans l'ordre de lstSegmentResult (en bleu)
        if (lstSegmentResult != null && lstSegmentResult.size() > 1) {
            g2d.setColor(Color.BLUE);  // Segment en bleu pour le chemin
            g2d.setStroke(new BasicStroke(3));  // Ligne plus épaisse pour les segments

            for (int i = 0; i < lstSegmentResult.size() - 1; i++) {
                GraphPoint point1 = lstSegmentResult.get(i);
                GraphPoint point2 = lstSegmentResult.get(i + 1);

                Point p1 = point1.getPoint();
                Point p2 = point2.getPoint();
                g2d.drawLine(p1.x, p1.y, p2.x, p2.y);  // Tracer une ligne entre les points successifs
            }
        }

        // Dessiner tous les autres segments (en noir)
        g2d.setColor(Color.BLACK);  // Segment en noir pour les autres
        g2d.setStroke(new BasicStroke(1));  // Ligne plus fine pour les segments

        // Parcourir tous les points pour dessiner leurs segments
        for (GraphPoint point : points) {
            Point p1 = point.getPoint();

            for (GraphSegment segment : point.getSegmentHashMap().values()) {
                // Si le segment fait partie du chemin, on l'ignore, il a déjà été tracé en bleu
                if (!lstSegmentResult.contains(segment.getGraphPoint())) {
                    Point p2 = segment.getGraphPoint().getPoint();
                    g2d.drawLine(p1.x, p1.y, p2.x, p2.y);  // Tracer les autres segments
                }
            }
        }

        // Dessiner les points
        g2d.setColor(Color.RED);
        for (GraphPoint point : points) {
            Point p = point.getPoint();
            g2d.fillOval(p.x - 5, p.y - 5, 10, 10); // Dessine un petit cercle pour chaque point
            g2d.drawString(point.getIdPoint(), p.x + 5, p.y - 5); // Affiche l'ID du point
        }
    }
    public static void main(String[] args) {
        List<GraphPoint> points = new ArrayList<>();
        Random rand = new Random();

        // Création des 30 points avec des coordonnées aléatoires
        for (int i = 0; i < 15; i++) {
            points.add(new GraphPoint(new Point(rand.nextInt(640), rand.nextInt(360)), String.valueOf((char)('A' + i))));
        }

        // Générer les connexions
        for (int i = 0; i < points.size(); i++) {
            GraphPoint point1 = points.get(i);
            // Utiliser un ArrayList pour stocker les points déjà connectés
            List<GraphPoint> connectedPoints = new ArrayList<>();
            int numConnections = rand.nextInt(5) + 1;  // Génère un nombre aléatoire entre 3 et 4 (inclus)

            while (connectedPoints.size() < numConnections) {
                // Sélectionner un autre point aléatoire
                int randomIndex = rand.nextInt(points.size());
                GraphPoint point2 = points.get(randomIndex);

                // S'assurer que ce n'est pas le même point et que la connexion n'existe pas déjà
                if (!point1.equals(point2) && !connectedPoints.contains(point2)) {
                    connectedPoints.add(point2);
                    int cost = (int)point1.getPoint().distance(point2.getPoint()); // Le coût est un nombre aléatoire entre 1 et 10
                    // Ajouter des segments bidirectionnels
                    point1.getPoint().distance(point2.getPoint());
                    point1.addSegment(new GraphSegment(point2, (int)point1.getPoint().distance(point2.getPoint())));
                    point2.addSegment(new GraphSegment(point1, (int)point2.getPoint().distance(point1.getPoint())));
                }
            }
        }

        lstSegmentResult = SearchInGraph.findPath(points.get(0),points.get(10));
        lstSegmentResult.addFirst(points.get(0));

        // Crée la fenêtre avec un panneau pour afficher les points
        JFrame frame = new JFrame("Graph Points Display");
        JavaTestRandomGraphAffichage panel = new JavaTestRandomGraphAffichage(points);
        frame.add(panel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}