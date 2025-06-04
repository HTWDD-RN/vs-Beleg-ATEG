// Standardwerte
// Auflösung: 1024 x 768
// Zoompunkt: -0.34837308755059104, -0.6065038451823017
// Zoomfaktor: 0.8
// Stufenanzahl: 100
// Anzahl Worker: 4 Rechner
// Iterationsanzahl: max. 1000

package vs_beleg_ateg.gui;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.imageio.ImageIO;

import vs_beleg_ateg.controller.Controller;

public class GUI extends JPanel{
    static Controller controller;

    static GUI panel;
    static JFrame frame;
    static int frame_width = 1600;
    static int frame_height = 900;

    boolean save_img = false;
    
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

    static JLabel MaxIterations_Label;
    static JSpinner MaxIterations_Spinner;

    static JLabel StepNumber_Label;
    static JSpinner StepNumber_Spinner;

    static JLabel WorkerNumber_Label;
    static JSpinner WorkerNumber_Spinner; 

    static JButton Start_Button;
    static JButton Save_Button;

    static JFileChooser chooser;

    static JCheckBox ImgResize_CheckBox;

    static JProgressBar ProgressBar;
    static JTextArea Status_TextArea;

    // Konstruktor
    public GUI() throws IOException {

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
            int WorkerNumber = (Integer)WorkerNumber_Spinner.getValue();    //num workers for parallel calc
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
        else if (e.getActionCommand().equals("Stop!")){
            // Variable setzen für Controller, welcher immer diese überprüft und dann alle Threads stoppt
        }
        else if (e.getActionCommand().equals("Bild speichern...")){
            if (img == null){
                JOptionPane.showMessageDialog(frame, "Kein Bild zum Speichern vorhanden!");
                return;
            }
            if (imageCount == imagesDone){ // hier kann direkt gespeichert werden, da BufferedImage nicht mehr geändert wird
                saveImage();
            }
            else { // warten bis neues Bild fertiggezeichnet ist und dann speichern
                this.save_img = true;
            }
        }
    }
    
    public static void main(String[] args) throws IOException{
        frame = new JFrame("Mandelbrot-ATEG GUI");
        frame.setSize(frame_width, frame_height);
        frame.setMinimumSize(new Dimension(frame_width, frame_height));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // JPanel erstellen, zum frame hinzufügen
        panel = new GUI();
        
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(5, 5, 5, 5); // Abstand zwischen Komponenten

        // Linkes Panel
        JPanel leftPanel = new JPanel(new GridBagLayout());
        gbc.gridwidth = 1;
        gbc.weightx = 1.0; gbc.weighty = 0; 
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Komponenten hinzufügen
        gbc.gridx = 0; gbc.gridy = 1;
        Res_Label = new JLabel("Auflösung (Breite/Höhe): ");
        leftPanel.add(Res_Label, gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        ResWidth_Spinner = new JSpinner(new SpinnerNumberModel(1024, 1, 10000, 10));
        leftPanel.add(ResWidth_Spinner, gbc);
        gbc.gridx = 2; gbc.gridy = 1;
        ResHeight_Spinner = new JSpinner(new SpinnerNumberModel(768, 1, 10000, 10));
        leftPanel.add(ResHeight_Spinner, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        Zoompoint_Label = new JLabel("Zoompunkt (X/Y): ");
        leftPanel.add(Zoompoint_Label, gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        ZoompointX_Spinner = new JSpinner(new SpinnerNumberModel(-0.34837308755059104, -2.5, 1, 0.0001));
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(ZoompointX_Spinner, "0.##################");
        ZoompointX_Spinner.setEditor(editor);
        JFormattedTextField tf = editor.getTextField();
        tf.setColumns(9);

        leftPanel.add(ZoompointX_Spinner, gbc);
        gbc.gridx = 2; gbc.gridy = 2;
        ZoompointY_Spinner = new JSpinner(new SpinnerNumberModel(-0.6065038451823017, -1.5, 1.5, 0.0001));
        leftPanel.add(ZoompointY_Spinner, gbc);
        JSpinner.NumberEditor editor2 = new JSpinner.NumberEditor(ZoompointY_Spinner, "0.##################");
        ZoompointY_Spinner.setEditor(editor2);
        JFormattedTextField tf2 = editor2.getTextField();
        tf2.setColumns(9);

        gbc.gridx = 0; gbc.gridy = 3;
        Zoomfactor_Label = new JLabel("Zoomfaktor: ");
        leftPanel.add(Zoomfactor_Label, gbc);
        gbc.gridx = 1; gbc.gridy = 3;
        Zoomfactor_Spinner = new JSpinner(new SpinnerNumberModel(1.3, 0.1, 2, 0.05));
        leftPanel.add(Zoomfactor_Spinner, gbc);
        gbc.gridx = 0; gbc.gridy = 4;
        MaxIterations_Label = new JLabel("Max. Iterationsanzahl: ");
        leftPanel.add(MaxIterations_Label, gbc);
        gbc.gridx = 1; gbc.gridy = 4;
        MaxIterations_Spinner = new JSpinner(new SpinnerNumberModel(1000, 1, 10000, 10));
        leftPanel.add(MaxIterations_Spinner, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        StepNumber_Label = new JLabel("Stufenanzahl: ");
        leftPanel.add(StepNumber_Label, gbc);
        gbc.gridx = 1; gbc.gridy = 5;
        StepNumber_Spinner = new JSpinner(new SpinnerNumberModel(100, 1, 5000, 1));
        leftPanel.add(StepNumber_Spinner, gbc);

        gbc.gridx = 0; gbc.gridy = 6;
        WorkerNumber_Label = new JLabel("Anzahl Worker: ");
        leftPanel.add(WorkerNumber_Label, gbc);
        gbc.gridx = 1; gbc.gridy = 6;
        WorkerNumber_Spinner = new JSpinner(new SpinnerNumberModel(4, 1, 64, 1));
        leftPanel.add(WorkerNumber_Spinner, gbc);
    
        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 3;
        Start_Button = new JButton("Start!");
        Start_Button.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                panel.ButtonHandler(e);
            } 
        });
        leftPanel.add(Start_Button, gbc);

        gbc.gridx = 0; gbc.gridy = 8; gbc.gridwidth = 3;
        Save_Button = new JButton("Bild speichern...");
        Save_Button.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                panel.ButtonHandler(e);
            } 
        });
        leftPanel.add(Save_Button, gbc);

        gbc.gridx = 0; gbc.gridy = 9; gbc.gridwidth = 3;
        ImgResize_CheckBox = new JCheckBox("Bild skalieren");
        leftPanel.add(ImgResize_CheckBox, gbc);


        // Rechtes Panel
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.add(panel, BorderLayout.CENTER); // BufferedImage

        // SplitPane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        Dimension bla = new Dimension(0,0);
        splitPane.setMinimumSize(bla);
        
        
        // Alles zum Frame hinzufügen
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(5, 5, 5, 5); // Abstand zwischen Komponenten
        gbc.gridx = 0; gbc.gridy = 0; gbc.weighty = 2;
        frame.add(splitPane, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.weighty = 0;
        ProgressBar = new JProgressBar(SwingConstants.HORIZONTAL);
        frame.add(ProgressBar, gbc);
        ProgressBar.setValue(50);
        gbc.gridx = 0; gbc.gridy = 2; gbc.weighty = 0;
        Status_TextArea = new JTextArea("");
        Status_TextArea.setEditable(false);
        frame.add(Status_TextArea, gbc);
        
        frame.setVisible(true);
        panel.repaint();
    }

    public void saveImage(){
        String FilePath = "";
        File selectedFile;

        chooser = new JFileChooser(); 
        chooser.setCurrentDirectory(new java.io.File("."));
        //chooser.setFileHidingEnabled(false);
        FileNameExtensionFilter imageFilter = new FileNameExtensionFilter(
            "Bilddateien (*.png, *.jpg)", "png", "jpg");
        chooser.setFileFilter(imageFilter);
        chooser.setAcceptAllFileFilterUsed(true);
        
        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) { 
            selectedFile = chooser.getSelectedFile();
            FilePath = selectedFile.getAbsolutePath();
            System.out.println(FilePath);
        }
        else {
            //System.out.println("No Selection ");
            return;
        }

        if (selectedFile.exists()) { // überschreiben?
            int overwrite = JOptionPane.showConfirmDialog(null, "Die Datei existiert bereits. Überschreiben?", "Datei überschreiben?", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (overwrite != JOptionPane.YES_OPTION) {
                System.out.println("Speichern abgebrochen.");
                return;
            }
        }

        try { // speichern
            if (FilePath.endsWith(".jpg") || FilePath.endsWith(".JPG")){
                File outputfile = new File(FilePath);
                ImageIO.write(img, "jpg", outputfile);
            }
            else if (FilePath.endsWith(".png") || FilePath.endsWith(".PNG")){
                File outputfile = new File(FilePath);
                ImageIO.write(img, "png", outputfile);
            }
            else{ // nicht angeben, standard ist png
                FilePath = FilePath + ".png";
                File outputfile = new File(FilePath);
                ImageIO.write(img, "png", outputfile);
            }
            Status_TextArea.setText("Gespeichert nach: " + FilePath);
            
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return;
    }
    
    public void givePixelData(int[][] c, int xpix, int ypix) {
        // altes Bild speichern?
        if (this.save_img == true){
            saveImage();
            this.save_img = false;
        }

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

    public static boolean getImgResizeCheckboxState(){
        return ImgResize_CheckBox.isSelected();
    }

    private static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        int origWidth = originalImage.getWidth();
        int origHeight = originalImage.getHeight();

        double scaleX = (double) targetWidth / origWidth;
        double scaleY = (double) targetHeight / origHeight;

        double scale = Math.min(scaleX, scaleY);

        int newWidth = (int) (origWidth * scale);
        int newHeight = (int) (origHeight * scale);

        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        AffineTransform at = AffineTransform.getScaleInstance(scale, scale);
        AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);

        return scaleOp.filter(originalImage, resizedImage);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
         if (img != null) { 
            BufferedImage resizedImg = resizeImage(img, getWidth(), getHeight());
            
            if (getImgResizeCheckboxState() == true){
                // zentriert zeichnen
                int x = (getWidth() - resizedImg.getWidth()) / 2;
                int y = (getHeight() - resizedImg.getHeight()) / 2;

                g.drawImage(resizedImg, x, y, this);
            }
            else{
                // zentriert zeichnen
                int x = (getWidth() - img.getWidth()) / 2;
                int y = (getHeight() - img.getHeight()) / 2;

                g.drawImage(img, x, y, this);
            }
        }
    }
}
