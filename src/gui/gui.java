// GUI soll enthalten
// Parameter Einstellbar über Textboxen
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
    static JFrame f;
    
    static Graphics2D g2d;

    static BufferedImage img;
    static int bufimg_width = 600;
    static int bufimg_height = 600;

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
        g2d.drawImage(mandelimage, 0, 0, 500, 400, this);
        Font font = new Font("Bitmap", Font.PLAIN, 64);
        g2d.setFont(font);
        g2d.drawString("Hello World", 20, 50);
        g2d.dispose();  // Grafikobjekt freigeben
    }

    public static void main(String[] args) throws IOException{
        f = new JFrame("GUI for Mandelbrot");
        f.setSize(640, 480);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // JPanel erstellen, zum frame hinzufügen
        gui panel = new gui();
        f.getContentPane().add(panel);

        JButton myButton = new JButton("Testbutton");
        myButton.setSize(100, 30);
        panel.add(myButton);

        //f.pack();
        f.setVisible(true);

        panel.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, this);          // uses buffered image
    }

}