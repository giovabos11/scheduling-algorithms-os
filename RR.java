import java.util.List;

public class RR implements Algorithm {
    private List<Task> queue;
    private Task currentTask;

    private int tasksRun;
    private int nextTaskIndex;

    private final int quantum = 10; // Default quantum of 10 milliseconds

    public RR(List<Task> queue) {
        this.queue = queue;
        this.tasksRun = queue.size();
    }

    public void schedule() {
        System.out.println("RR Scheduling \n");

        nextTaskIndex = 0;

        // While there are items in the ready queue
        while (!queue.isEmpty()) {
            // Pick the next task to run
            currentTask = pickNextTask();

            // Run process
            CPU.run(currentTask, Math.min(quantum, currentTask.getBurst()));

            // Update process burst time
            currentTask.setBurst(currentTask.getBurst() - quantum);

            // Check to remove the task from the queue
            if (currentTask.getBurst() <= 0) {
                System.out.println("Task " + currentTask.getName() + " Finished\n");
                queue.remove(currentTask);
            }

            // Update next task index
            nextTaskIndex++;
            nextTaskIndex = nextTaskIndex % Math.max(queue.size(), 1);
        }
    }

    public Task pickNextTask() {
        return queue.get(nextTaskIndex);
    }
}
