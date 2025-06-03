package vs_beleg_ateg.mandelbrotengine;

/* Klasse für die mathematische Logik
 * - Berechnung pro Punkt
 * - Umrechnung von Pixelkoordinate zu komplexe Zahlen
*/

public class MandelbrotCalculator {

  public static int MandelbrotCalculator (double re, double im, int maxIter) {

    //Parameter:
    // re : Realteil der komplexen Zahl c = re + i*im
    // im : Imaginäranteil von c
    // maxIter: Maximale Iterationen, die durchgeführt werden sollen
    //System.err.println("Re: "+re+"  im:"+im);
    
    double x = 0.0; //Realteil von z
    double y = 0.0; // Imaginärteil von z
    int iter = 0; //Zählt die Wiederholungen
    
    while (x*x + y*y <=4 && iter < maxIter){
      //Abbruchbedingungen:
      //Folge läuft so lange wie |z| <= 2
      // && Maxiter nicht erreicht

      double xNew = x*x - y*y + re;
      double yNew = 2*x*y + im;
      x = xNew;
      y = yNew;
      iter++;

      
    }
    
    if (iter == maxIter) {
      //System.err.println("Iter == maxIter");
      return 0x000000; // Schwarz = Punkt gehört zur Mandelbrot-Menge
    } 
    else {
      //System.err.println("In Else");
    return java.awt.Color.HSBtoRGB(iter / 256f, 1f, 1f); // Farbe nach Iteration
    }
  }
}  // img.setRGB(x, y, color);  // color ist int → funktioniert direkt!





