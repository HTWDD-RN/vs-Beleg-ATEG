// GUI soll enthalten
// Parameter Einstellbar über Textboxen
// Bsp:
// Auflösung: 1024 x 768
// Zoompunkt: -0.34837308755059104, -0.6065038451823017
// Zoomfaktor: 0.8
// Stufenanzahl: 100
// Anzahl Worker: 4 Rechner
// Iterationsanzahl: max. 1000

// Anzahl Worker
// StartButton
// Fortschrittsleiste (1/4 Workern fertig)
// -> GUI gibt an Controller weiter
package vs_beleg_ateg.gui;

import vs_beleg_ateg.controller.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
//import java.lang.ModuleLayer.Controller;

import javax.imageio.ImageIO;
import javax.swing.*;

class GUI extends JPanel implements guiInterface{
    static Controller controller;

    static GUI panel;
    static JFrame frame;
    static int frame_width = 600;
    static int frame_height = 700;
    
    static Graphics2D g2d;

    static BufferedImage img;
    static int bufimg_width = 600;
    static int bufimg_height = 300;

    static JLabel Res_Label;
    static JSpinner ResWidth_Spinner;
    static JSpinner ResHeight_Spinner;
 
    static JLabel Zoompoint_Label;
    static JSpinner ZoompointX_Spinner;
    static JSpinner ZoompointY_Spinner;

    static JSpinner Zoomfactor_Spinner;
    static JLabel Zoomfactor_Label;

    static JLabel StepNumber_Label;
    static JSpinner StepNumber_Spinner;

    static JLabel WorkerNumber_Label;
    static JSpinner WorkerNumber_Spinner; 

    static JLabel MaxIterations_Label;
    static JSpinner MaxIterations_Spinner;

    static JButton Start_Button;

    // Konstruktor
    public GUI() throws IOException {
        // Bild erstellen
        img = new BufferedImage(bufimg_width, bufimg_height, BufferedImage.TYPE_INT_ARGB);
        /*System.out.println("Working Directory: " + System.getProperty("user.dir"));
        File file = new File("src/main/java/vs_beleg_ateg/gui/mandel.jpg");
        BufferedImage mandelimage = ImageIO.read(file);

        Graphics2D g2d = img.createGraphics();
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, bufimg_width, bufimg_height); // Bild mit schwarzem Hintergrund füllen
        g2d.setColor(Color.WHITE);
        g2d.drawImage(mandelimage, 100, 0, 400, 300, this);
        Font font = new Font("Bitmap", Font.PLAIN, 64);
        g2d.setFont(font);
        g2d.drawString("Hello World GUI :)", 20, 70);
        g2d.dispose();  // Grafikobjekt freigeben
        */
    }

    static void ButtonHandler(ActionEvent e){
        if (e.getActionCommand().equals("Start!")){
            System.out.println("Start Button pressed!");
            // Werte bekommen
            int ResWidth = (Integer)ResWidth_Spinner.getValue();
            int ResHeight = (Integer)ResHeight_Spinner.getValue();
            double ZoompointX = (Double)ZoompointX_Spinner.getValue();
            double ZoompointY =  (Double)ZoompointY_Spinner.getValue();
            double Zoomfactor = (Double)Zoomfactor_Spinner.getValue();
            int StepNumber = (Integer)StepNumber_Spinner.getValue(); 
            int WorkerNumber = (Integer) WorkerNumber_Spinner.getValue();  
            int MaxIterations = (Integer)MaxIterations_Spinner.getValue();
            // an Controller übergeben
            controller = new Controller(ResWidth, ResHeight, ZoompointX, ZoompointY, Zoomfactor, StepNumber, MaxIterations, WorkerNumber);
        }
    }
    
        public static void main(String[] args) throws IOException{
            frame = new JFrame("GUI für Mandelbrot");
            frame.setSize(frame_width, frame_height);
            frame.setMinimumSize(new Dimension(frame_width, frame_height));
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
    
            // JPanel erstellen, zum frame hinzufügen
            panel = new GUI();
            frame.setLayout(new GridBagLayout());
    
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.BOTH;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.insets = new Insets(5, 5, 5, 5); // Abstand zwischen Komponenten
    
            gbc.gridx = 0; gbc.gridy = 0;
            gbc.gridwidth = 3; 
            gbc.weightx = 1.0; gbc.weighty = 1; 
            frame.add(panel, gbc); // BufferedImage
    
            gbc.gridwidth = 1;
            gbc.weightx = 1.0; gbc.weighty = 0.03; 
            gbc.fill = GridBagConstraints.HORIZONTAL;
    
            // Komponenten hinzufügen
            gbc.gridx = 0; gbc.gridy = 1;
            Res_Label = new JLabel("Auflösung (Breite/Höhe): ");
            frame.add(Res_Label, gbc);
            gbc.gridx = 1; gbc.gridy = 1;
            ResWidth_Spinner = new JSpinner(new SpinnerNumberModel(1024, 1, 10000, 10));
            frame.add(ResWidth_Spinner, gbc);
            gbc.gridx = 2; gbc.gridy = 1;
            ResHeight_Spinner = new JSpinner(new SpinnerNumberModel(768, 1, 10000, 10));
            frame.add(ResHeight_Spinner, gbc);
    
            gbc.gridx = 0; gbc.gridy = 2;
            Zoompoint_Label = new JLabel("Zoompunkt (X/Y): ");
            frame.add(Zoompoint_Label, gbc);
            gbc.gridx = 1; gbc.gridy = 2;
            ZoompointX_Spinner = new JSpinner(new SpinnerNumberModel(0.5, -2.5, 1, 0.001));
            frame.add(ZoompointX_Spinner, gbc);
            gbc.gridx = 2; gbc.gridy = 2;
            ZoompointY_Spinner = new JSpinner(new SpinnerNumberModel(0.5, -1.5, 1.5, 0.001));
            frame.add(ZoompointY_Spinner, gbc);
    
            gbc.gridx = 0; gbc.gridy = 3;
            Zoomfactor_Label = new JLabel("Zoomfaktor: ");
            frame.add(Zoomfactor_Label, gbc);
            gbc.gridx = 1; gbc.gridy = 3;
            Zoomfactor_Spinner = new JSpinner(new SpinnerNumberModel(0.8, 0.1, 2, 0.001));
            frame.add(Zoomfactor_Spinner, gbc);
    
            gbc.gridx = 0; gbc.gridy = 4;
            StepNumber_Label = new JLabel("Stufenanzahl: ");
            frame.add(StepNumber_Label, gbc);
            gbc.gridx = 1; gbc.gridy = 4;
            StepNumber_Spinner = new JSpinner(new SpinnerNumberModel(100, 1, 1000, 1));
            frame.add(StepNumber_Spinner, gbc);
    
            gbc.gridx = 0; gbc.gridy = 5;
            WorkerNumber_Label = new JLabel("Anzahl Worker: ");
            frame.add(WorkerNumber_Label, gbc);
            gbc.gridx = 1; gbc.gridy = 5;
            WorkerNumber_Spinner = new JSpinner(new SpinnerNumberModel(4, 1, 64, 1));
            frame.add(WorkerNumber_Spinner, gbc);
    
            gbc.gridx = 0; gbc.gridy = 6;
            MaxIterations_Label = new JLabel("Max. Iterationsanzahl: ");
            frame.add(MaxIterations_Label, gbc);
            gbc.gridx = 1; gbc.gridy = 6;
            MaxIterations_Spinner = new JSpinner(new SpinnerNumberModel(1000, 1, 10000, 10));
            frame.add(MaxIterations_Spinner, gbc);
      
            gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 3;
            Start_Button = new JButton("Start!");
            Start_Button.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent e) { 
                  ButtonHandler(e);
            } 
          } );
        frame.add(Start_Button, gbc);

        gbc.gridx = 0; gbc.gridy = 8; gbc.gridwidth = 3;
        JProgressBar ProgressBar = new JProgressBar(SwingConstants.HORIZONTAL);
        frame.add(ProgressBar, gbc);
        ProgressBar.setValue(50);

        gbc.gridx = 0; gbc.gridy = 9; gbc.gridwidth = 3;
        JTextArea Status_TextArea = new JTextArea("2/4 Workers finished.");
        Status_TextArea.setEditable(false);
        frame.add(Status_TextArea , gbc);

        frame.setVisible(true);
        panel.repaint();
    }

    public void givePixelData(BufferedImage newImage) {
        System.out.printf("Got stuff!");
        img = newImage;
        repaint(); // forciert Neuzeichnung
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (img != null) { // zentriert zeichnen
            int x = (getWidth() - img.getWidth()) / 2;
            int y = (getHeight() - img.getHeight()) / 2;
            g.drawImage(img, x, y, this);
        }
    }



}