package vs_beleg_ateg.worker;

import java.io.Serializable;

public class TaskResult implements Serializable {
    private int[][] pixelData;
    private int width, height;

    public TaskResult(int width, int height){
        this.pixelData = new int[width][height];
        this.width = width;
        this.height = height;
    }

    public void setPixelData(int[][] pixelData) {
        this.pixelData = pixelData;
    }

    public void setPixel(int value,int x,int y){
        this.pixelData[x][y] = value;
    }

    public int[][] getPixelData(){
        return pixelData;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
