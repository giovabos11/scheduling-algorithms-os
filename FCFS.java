/**
 * FCFS scheduling algorithm.
 */
 
import java.util.*;

public class FCFS implements Algorithm
{
    private List<Task> queue;
    private Task currentTask;

    private int tasksRun;

    public FCFS(List<Task> queue) {
        this.queue = queue;
        tasksRun = queue.size();
    }

    public void schedule() {
        System.out.println("FCFS Scheduling \n");

        // While there are items in the ready queue
        while (!queue.isEmpty()) {
            // Pick the next task to run
            currentTask = pickNextTask();

            // Run process
            CPU.run(currentTask, currentTask.getBurst());

            // Remove the task from the queue
            queue.remove(currentTask);
        }
    }

    public Task pickNextTask() {
        return queue.get(0);
    }
}
