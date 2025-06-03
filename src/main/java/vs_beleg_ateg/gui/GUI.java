// Auflösung: 1024 x 768
// Zoompunkt: -0.34837308755059104, -0.6065038451823017
// Zoomfaktor: 0.8
// Stufenanzahl: 100
// Anzahl Worker: 4 Rechner
// Iterationsanzahl: max. 1000

package vs_beleg_ateg.gui;

import vs_beleg_ateg.controller.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
//import java.lang.ModuleLayer.Controller;

import javax.imageio.ImageIO;

import vs_beleg_ateg.controller.Controller;

public class GUI extends JPanel{
    static Controller controller;

    static GUI panel;
    static JFrame frame;
    static int frame_width = 1200;
    static int frame_height = 1000;
    
    static Graphics2D g2d;

    static BufferedImage img;
    static int imagesDone = 0;
    static int imageCount = 0;

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

    static JProgressBar ProgressBar;
    static JTextArea Status_TextArea;

    // Konstruktor
    public GUI() throws IOException {
        // Bild erstellen
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

    void ButtonHandler(ActionEvent e){
        if (e.getActionCommand().equals("Start!")){
            System.out.println("Start Button pressed!");
            // Werte bekommen
            int ResWidth = (Integer)ResWidth_Spinner.getValue();            //width
            int ResHeight = (Integer)ResHeight_Spinner.getValue();          //height
            double ZoompointX = (Double)ZoompointX_Spinner.getValue();      //cr
            double ZoompointY = (Double)ZoompointY_Spinner.getValue();      //ci
            double Zoomfactor = (Double)Zoomfactor_Spinner.getValue();      //zoom
            int StepNumber = (Integer)StepNumber_Spinner.getValue();        //round
            int WorkerNumber = (Integer) WorkerNumber_Spinner.getValue();   //num workers for parallel calc
            int MaxIterations = (Integer)MaxIterations_Spinner.getValue();  //maxiter
            
            imagesDone = 0;
            imageCount = StepNumber;
            Start_Button.setEnabled(false);
            Start_Button.setText("Berechne...");

            img = new BufferedImage(ResWidth, ResHeight, BufferedImage.TYPE_INT_RGB);
            controller = new Controller(ResWidth, ResHeight, ZoompointX, ZoompointY, Zoomfactor, StepNumber, MaxIterations, WorkerNumber, panel);
            new Thread(() -> {
                controller.startComputation();
            }).start();
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
            gbc.weightx = 1.0; gbc.weighty = 0.001; 
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
            ZoompointX_Spinner = new JSpinner(new SpinnerNumberModel(-0.34837308755059104, -2.5, 1, 0.001));
            frame.add(ZoompointX_Spinner, gbc);
            gbc.gridx = 2; gbc.gridy = 2;
            ZoompointY_Spinner = new JSpinner(new SpinnerNumberModel(-0.6065038451823017, -1.5, 1.5, 0.001));
            frame.add(ZoompointY_Spinner, gbc);
    
            gbc.gridx = 0; gbc.gridy = 3;
            Zoomfactor_Label = new JLabel("Zoomfaktor: ");
            frame.add(Zoomfactor_Label, gbc);
            gbc.gridx = 1; gbc.gridy = 3;
            Zoomfactor_Spinner = new JSpinner(new SpinnerNumberModel(1.3, 0.1, 2, 0.001));
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
                    panel.ButtonHandler(e);
            } 
          } );
        frame.add(Start_Button, gbc);

        gbc.gridx = 0; gbc.gridy = 8; gbc.gridwidth = 3;
        ProgressBar = new JProgressBar(SwingConstants.HORIZONTAL);
        frame.add(ProgressBar, gbc);
        ProgressBar.setValue(50);

        gbc.gridx = 0; gbc.gridy = 9; gbc.gridwidth = 3;
        Status_TextArea = new JTextArea("");
        Status_TextArea.setEditable(false);
        frame.add(Status_TextArea , gbc);

        frame.setVisible(true);
        panel.repaint();
    }
    
    public void givePixelData(int[][] c, int xpix, int ypix) {
        // GUI updaten
        imagesDone++;

        Status_TextArea.setText("Bilder berechnet: " + imagesDone + "/" + imageCount);
        double progressPercent =  (double)imagesDone/imageCount * 100;
        ProgressBar.setValue((int)progressPercent);
        
        if (imagesDone == imageCount){ // fertig
            Status_TextArea.setText("Fertig!");
            Start_Button.setEnabled(true);
            Start_Button.setText("Start!");
            
        }

        for (int y = 0; y < ypix; y++) {
            for (int x = 0; x < xpix; x++) {
                //System.out.println(c[x][y].getRGB());
                //if (c[x][y] != 0) 
                    img.setRGB(x, y, c[x][y]);
                    //System.out.println("Farbe: "+c[x][y]);
            }
        }
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
