public class Task {
    private int startX;
    private int startY;
    private int width;
    private int height;

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
}