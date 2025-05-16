package vs_beleg_ateg.worker;

import java.util.*;
import vs_beleg_ateg.worker.Task;

public class ThreadPool {
    List<Task> TaskQueue;
    List<int[][]> ComputedPixels;

    ThreadPool(){
        TaskQueue = new ArrayList<Task>();
        ComputedPixels = new ArrayList<int[][]>();
    }
    public boolean submit(Task task){
        TaskQueue.add(task);
        return true;
    }

    public Task pull(){
        return TaskQueue.getFirst();
    }
    
    
}
