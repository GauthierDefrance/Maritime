package battleengine_trash.battleengine_trash;

import battleengine_trash.battleengine_trash.team.Team;
import config.GameConfiguration;
import engine.entity.boats.*;
import engine.graph.GraphPoint;
import gui.PaintBackGround;
import gui.PaintEntity;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PlacingTest extends JFrame {
    private final PaintEntity painter;
    private Team A;
    private Team B;
    private JPanel buttons;
    private JButton currentButton;
    private Boat currentBoat;
    private JPanel dashboardPanel;
    private boolean isPlaced=true;
    private  PaintBackGround paintBackGround;

    public PlacingTest() throws InterruptedException {
        painter = new PaintEntity();
        this.paintBackGround = new PaintBackGround();
        try {
            Fleet tmpAf = new Fleet();
            Fleet tmpBf = new Fleet();
            Fleet Af = new Fleet();
            Fleet Bf = new Fleet();
            ArrayList<Boat> boats = new ArrayList<>();

            // Création de 20 bateaux avec des types et des coordonnées différentes
            boats.add(new Military("military1", "blue", new GraphPoint(new Point(10, 10), null)));
            boats.add(new Merchant("merchant1", "blue", new GraphPoint(new Point(50, 50), null)));
            boats.add(new Merchant("merchant2", "blue", new GraphPoint(new Point(100, 100), null)));
            boats.add(new Standard("standard1", "blue", new GraphPoint(new Point(150, 150), null)));
            boats.add(new Fodder("fodder1", "blue", new GraphPoint(new Point(200, 200), null)));
            boats.add(new Military("military2", "blue", new GraphPoint(new Point(250, 250), null)));
            boats.add(new Military("military3", "blue", new GraphPoint(new Point(300, 300), null)));
            boats.add(new Merchant("merchant3", "blue", new GraphPoint(new Point(350, 350), null)));
            boats.add(new Merchant("merchant4", "blue", new GraphPoint(new Point(400, 400), null)));
            boats.add(new Standard("standard2", "blue", new GraphPoint(new Point(450, 450), null)));
            boats.add(new Fodder("fodder2", "blue", new GraphPoint(new Point(500, 500), null)));
            boats.add(new Military("military4", "blue", new GraphPoint(new Point(550, 550), null)));
            boats.add(new Military("military5", "blue", new GraphPoint(new Point(600, 600), null)));
            boats.add(new Merchant("merchant5", "blue", new GraphPoint(new Point(650, 650), null)));
            boats.add(new Merchant("merchant6", "blue", new GraphPoint(new Point(700, 700), null)));
            boats.add(new Standard("standard3", "blue", new GraphPoint(new Point(750, 750), null)));
            boats.add(new Fodder("fodder3", "blue", new GraphPoint(new Point(800, 800), null)));
            boats.add(new Military("military6", "blue", new GraphPoint(new Point(850, 850), null)));
            boats.add(new Military("military7", "blue", new GraphPoint(new Point(900, 900), null)));
            boats.add(new Merchant("merchant7", "blue", new GraphPoint(new Point(950, 950), null)));

            Fodder fodder2 = new Fodder("fodder2","red",new GraphPoint(new Point(300,200),null));

            for (Boat boat : boats) {
                tmpAf.add(boat);
            }

            ArrayList<GraphPoint> points = new ArrayList<>();
            points.add(new GraphPoint(new Point(10,10),null));
            points.add(new GraphPoint(new Point(300,500),null));
            points.add(new GraphPoint(new Point(500,200),null));

            for(Boat boat : Af.getArrayListFleet()){
                boat.setPath(points);
                boat.setContinuePath(true);
            }

            tmpBf.add(fodder2);

            A = new Team(tmpAf);
            B = new Team(tmpBf);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        setTitle("Placing Test");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel(new BorderLayout());

        // Créer un panneau pour les boutons avec un BoxLayout pour les aligner verticalement
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(0,2)); //Game config a ajouter

        int i=0;
        JButton tmpbutton;
        for(Boat boat : A.getFleet().getArrayListFleet()){
            String text = "";
            switch (boat.getClass().getName()) {
                case "engine.entity.boats.Standard": {
                    text = "standard";
                    break;
                }
                case "engine.entity.boats.Fodder": {
                    text = "fodder";
                    break;
                }
                case "engine.entity.boats.Merchant": {
                    text = "merchant";
                    break;
                }
                case "engine.entity.boats.Military": {
                    text = "military";
                    break;
                }
                default: {
                    text = "standard";
                }
            }
            tmpbutton = new JButton(text);
            tmpbutton.setVerticalTextPosition(SwingConstants.BOTTOM);
            tmpbutton.setHorizontalTextPosition(SwingConstants.CENTER);
            tmpbutton.addActionListener(new ButtonSelected(boat));
            tmpbutton.setBackground(new Color(78, 172, 233));
            tmpbutton.setFocusPainted(false);
            tmpbutton.setBorderPainted(false);
            tmpbutton.setIcon(new ImageIcon(GameConfiguration.START_FILE_PATH + "/boat/"+text+".png"));
            buttonPanel.add(tmpbutton);
            i++;
        }

        // Créer un JScrollPane autour du panneau de boutons pour permettre le défilement
        JScrollPane scrollPane = new JScrollPane(buttonPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // Activer le défilement vertical

        // Ajouter le JScrollPane (et non buttonPanel directement) au panneau principal
        panel.add(scrollPane, BorderLayout.WEST); // Ajout du JScrollPane pour avoir la barre de défilement

        // Ajouter le dashboard (panneau central)
        DrawingDashboard dashboardPanel = new DrawingDashboard();
        dashboardPanel.addMouseListener(new MouseClickListener());
        dashboardPanel.addMouseMotionListener(new MouseMotionListener());
        panel.add(dashboardPanel, BorderLayout.CENTER);

        // Ajouter le panneau principal à la fenêtre
        add(panel);

        // Rendre la fenêtre visible
        setVisible(true);
        int tech=0;
        while(true){

            Thread.sleep(10); //Temps d'attente à débattre
            tech++;
            if(tech==10){
                tech=0;
                //100ms se sont écoulé
                //Chargement BG suivant
            }
            dashboardPanel.repaint();
        }
    }

    private class ButtonSelected implements ActionListener {
        private Boat boat;
        public ButtonSelected(Boat boat) {
            this.boat = boat;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            currentButton = (JButton) e.getSource();
            currentBoat = boat;
            isPlaced=false;
            System.out.println(this.boat);
        }
    }

    private class MouseClickListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            //System.out.println("Clic détecté à la position (" + e.getX() + ", " + e.getY() + ")");
            if(currentButton != null){
                //System.out.println(e.getButton());
                if(e.getButton() == MouseEvent.BUTTON1){LeftClick(e);}
                else if(e.getButton() == MouseEvent.BUTTON3){RightClick(e);}
            }
        }
        public void LeftClick(MouseEvent e){
            currentBoat.setPosition(e.getX(), e.getY());
            System.out.println(currentBoat.getPosition());
            if(!isPlaced){
                System.out.println("Le bateau "+currentBoat.getName() +" a été lock");
                isPlaced=true;
            }
            else {
                System.out.println("Le bateau "+currentBoat.getName() +" a été unlock");
                isPlaced=false;
            }

        }
        public void RightClick(MouseEvent e){
            double x1 = e.getX();
            double y1 = e.getY();
            double x2 = currentBoat.getPosition().getX();
            double y2 = currentBoat.getPosition().getY();
            double distance = currentBoat.getPosition().distance(new Point(e.getX(), e.getY()));
            currentBoat.setAngle(Math.atan2(y1 - y2, x1 - x2));
            System.out.println(currentBoat.getAngle());
        }

    }
    private class MouseMotionListener extends MouseAdapter {
        @Override
        public void mouseMoved(MouseEvent e) {
            //System.out.println("Clic détecté à la position (" + e.getX() + ", " + e.getY() + ")");
            if(currentButton != null && !isPlaced){
                currentBoat.setPosition(e.getX(), e.getY());
                //System.out.println(currentButton.getBoat().getPosition());
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // Créer et afficher la fenêtre avec le tableau de bord et les boutons
        new PlacingTest();

    }

    // Classe pour créer un tableau de bord avec la possibilité de dessiner
    private class DrawingDashboard extends JPanel {
        // Redéfinir la méthode paintComponent pour dessiner avec Graphics2D
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Créer un objet Graphics2D pour un meilleur contrôle
            Graphics2D g2d = (Graphics2D) g;
//            double scale = Math.min((double)getWidth()/640,(double) getHeight() /360);
//            g2d.scale(scale,scale);
//            paintBackGround.paint(g2d);
//            g2d.scale((double) 1 /GameConfiguration.GAME_SCALE, (double) 1 /GameConfiguration.GAME_SCALE);

            if(A != null){
                for(Boat boat : A.getFleet().getArrayListFleet()){
                    painter.paint(boat, g2d);
                }
            }
            if(B != null){
                for(Boat boat : B.getFleet().getArrayListFleet()){
                    painter.paint(boat,g2d);
                }
            }

        }
    }
}