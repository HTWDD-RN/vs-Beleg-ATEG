package vs_beleg_ateg.worker;

public class Task {
    private double startX;
    private double startY;
    private double endX;
    private double endY;
    private int width;
    private int height;
    private int iteration;

    
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
    public double getStartY() { return startY; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public int getIteration() { return iteration; }

    public void setIteration(int iteration) { this.iteration = iteration; }
}