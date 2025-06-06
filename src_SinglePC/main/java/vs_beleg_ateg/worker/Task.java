package vs_beleg_ateg.worker;

public class Task {
    private double startX; //Startkoordinate x
    private double startY; //Startkoordinate x
    private double endX; //Endkoordinate x
    private double endY; //Endkoordinate y
    private int width; //Anzahl der Pixel in der Breite
    private int height; //Anzahl der Pixel in der Höhe
    private int iteration;  //Tiefe der Berechnung

    //Anpassen Bild wird in 4 vertikale Streifen geteilt, Hohe bleibt gleich
    public Task(double startX, double startY,double endX,double endY, int width, int height, int iteration) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.width = width;
        this.height = height;
        this.iteration = iteration;
    }

    // Getter-Methoden für die Task-Parameter
    public double getStartX() { return startX; }
    public double getEndX() {return endX;
    }
    public double getEndY() {return endY;
    }
    public double getStartY() { return startY; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public int getIteration() { return iteration; }

    public void setIteration(int iteration) { this.iteration = iteration; }
}