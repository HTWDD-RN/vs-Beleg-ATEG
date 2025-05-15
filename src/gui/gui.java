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

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

class gui extends JPanel{
    static JFrame frame;
    static int frame_width = 600;
    static int frame_height = 700;
    
    static Graphics2D g2d;

    static BufferedImage img;
    static int bufimg_width = 600;
    static int bufimg_height = 300;

    // Konstruktor
    public gui() throws IOException {
        // Bild erstellen
        img = new BufferedImage(bufimg_width, bufimg_height, BufferedImage.TYPE_INT_ARGB);
        File file = new File("mandel.jpg");
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
    }

    public static void main(String[] args) throws IOException{
        frame = new JFrame("GUI für Mandelbrot");
        frame.setSize(frame_width, frame_height);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // JPanel erstellen, zum frame hinzufügen
        gui panel = new gui();
        frame.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
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
        JLabel Res_Label = new JLabel("Auflösung (Breite/Höhe): ");
        frame.add(Res_Label, gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        JSpinner ResWidth_Spinner = new JSpinner(new SpinnerNumberModel(1024, 1, 10000, 10));
        frame.add(ResWidth_Spinner, gbc);
        gbc.gridx = 2; gbc.gridy = 1;
        JSpinner ResHeight_Spinner = new JSpinner(new SpinnerNumberModel(768, 1, 10000, 10));
        frame.add(ResHeight_Spinner, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        JLabel Zoompoint_Label = new JLabel("Zoompunkt (X/Y): ");
        frame.add(Zoompoint_Label, gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        JSpinner ZoompointX_Spinner = new JSpinner(new SpinnerNumberModel(0.5, -2.5, 1, 0.001));
        frame.add(ZoompointX_Spinner, gbc);
        gbc.gridx = 2; gbc.gridy = 2;
        JSpinner ZoompointY_Spinner = new JSpinner(new SpinnerNumberModel(0.5, -1.5, 1.5, 0.001));
        frame.add(ZoompointY_Spinner, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        JLabel Zoomfactor_Label = new JLabel("Zoomfaktor: ");
        frame.add(Zoomfactor_Label, gbc);
        gbc.gridx = 1; gbc.gridy = 3;
        JSpinner Zoomfactor_Spinner = new JSpinner(new SpinnerNumberModel(0.8, 0.1, 2, 0.001));
        frame.add(Zoomfactor_Spinner, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        JLabel StepNumber_Label = new JLabel("Stufenanzahl: ");
        frame.add(StepNumber_Label, gbc);
        gbc.gridx = 1; gbc.gridy = 4;
        JSpinner StepNumber_Spinner = new JSpinner(new SpinnerNumberModel(100, 1, 1000, 1));
        frame.add(StepNumber_Spinner, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        JLabel WorkerNumber_Label = new JLabel("Anzahl Worker: ");
        frame.add(WorkerNumber_Label, gbc);
        gbc.gridx = 1; gbc.gridy = 5;
        JSpinner WorkerNumber_Spinner = new JSpinner(new SpinnerNumberModel(4, 1, 64, 1));
        frame.add(WorkerNumber_Spinner, gbc);

        gbc.gridx = 0; gbc.gridy = 6;
        JLabel MaxIterations_Label = new JLabel("Max. Iterationsanzahl: ");
        frame.add(MaxIterations_Label, gbc);
        gbc.gridx = 1; gbc.gridy = 6;
        JSpinner MaxIterations_Spinner = new JSpinner(new SpinnerNumberModel(1000, 1, 10000, 10));
        frame.add(MaxIterations_Spinner, gbc);
  
        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 3;
        JButton Start_Button = new JButton("Start!");
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, this);          // uses buffered image
    }

}