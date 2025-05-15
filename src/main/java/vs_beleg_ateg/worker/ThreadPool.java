package vs_beleg_ateg.worker;

import java.util.*;
import vs_beleg_ateg.worker.Task;

public class ThreadPool {
    List<Task> TaskQueue;

    public boolean submit(Task task){
        return true;
    }

    public Task pull(){
        return TaskQueue.getFirst();
    }
    
}
