package vs_beleg_ateg.worker;

public class Task {
    private int startX;
    private int startY;
    private int width;
    private int height;
    private int iteration;

    
    public Task(int startX, int startY, int width, int height) {
        this.startX = startX;
        this.startY = startY;
        this.width = width;
        this.height = height;
    }

    // Getter-Methoden für die Task-Parameter
    public int getStartX() { return startX; }
    public int getStartY() { return startY; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public int getIteration() { return iteration; }
    public void setIteration(int iteration) { this.iteration = iteration; }
    
}