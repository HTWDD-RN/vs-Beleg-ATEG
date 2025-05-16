package vs_beleg_ateg.worker;

import vs_beleg_ateg.mandelbrotengine.MandelbrotCalculator;
import vs_beleg_ateg.worker.Task;

public class Worker extends Thread {
    private final ThreadPool taskQueue;  // Eine Warteschlange von Tasks
    public Worker(ThreadPool taskQueue) {
        this.taskQueue = taskQueue;
    }

    @Override
    public void run() {
        Task task = getTask();  // Holt den nächsten Task
        int i,j = 0;
        if (task != null) {
            int iteration = task.getIteration();
            //Verarbeite den Task (Berechnung)
            for(i= task.getStartX() ;i< task.getWidth();i++)
                for(j = task.getStartY(); i< task.getHeight();i++)
                    computeMandelbrot(i, j,iteration);
        }
    }
 
    public synchronized Task getTask() {
        return taskQueue.pull();  // Holt den nächsten Task aus der Warteschlange
    }

    public void computeMandelbrot(double x, double y, int Iteration) {
        
        //MandelbrotCalculator MbEngine = new MandelbrotCalculator();
        int pix = MandelbrotCalculator.MandelbrotCalculator(x, y, Iteration);
    }
}


/* 
public class MyThread extends Thread{

    private String myName;
    int ctr=0;
    int startidx=0;
    static int threadcount=8;
    private static boolean[] Feld = new boolean[50000000]; //noch 5 Mio

    public MyThread(String name,int idx,int tcou){
        myName=name;
        startidx = idx;
        threadcount=tcou;
    }

    public void run(){
        //System.out.println("Hallo vom Thread "+getName());
        for(int j=startidx;j<startidx+(Feld.length/threadcount);j++)
            if(Feld[j]) {
                ctr++;
                //System.out.println("Eins in Feld "+j);
            }
        //System.out.println(getName()+ ": Anzahl 1en: " + ctr);
    }

    public int getCtr(){
        return ctr;
    }

    public static void main(String[] args) {
        int start = 0;
        int Anzahl = 0;
        int i = 0;
        for (i = 0; i < Feld.length; i++) {
            int rand = ((int) (Math.random() * Feld.length)) % 2;
            Feld[i] = (rand == 1);
        }

        System.out.println("Verfügbare Kerne: " + getRuntime().availableProcessors());
        long Starttime = System.currentTimeMillis();
        //threadcount = Integer.parseInt(args[0]);
        MyThread thList[] = new MyThread[threadcount];

        for (int l = 0; l < thList.length; l++) {
            start = l * (Feld.length / threadcount);
            thList[l] = new MyThread("Th(" + i + ")", start, threadcount);
            thList[l].start();
        }
        for (int l = 0; l < thList.length; l++) {
            try {
                thList[l].join();
            } catch (InterruptedException e) {
                System.out.println("InterruptExcep");
            }
            Anzahl += thList[l].getCtr();
        }
        System.out.println("Insgesamt 1en: "+Anzahl+ "\n"+
                "Thread-Anzahl: "+ threadcount+ "\n"+
                "Laufzeit:" + (System.currentTimeMillis()-Starttime));
        //Problem: 1en zusammenzählen


    }
*/
