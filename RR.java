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
            currentTask.setBurst(currentTask.getBurst() - Math.min(quantum, currentTask.getBurst()));

            // Check to remove the task from the queue
            if (currentTask.getBurst() <= 0) {
                System.out.println("Task " + currentTask.getName() + " finished.\n");
                queue.remove(currentTask);
                nextTaskIndex--;
            }

            // Update next task index
            nextTaskIndex++;
            if (!queue.isEmpty()) {
                nextTaskIndex = nextTaskIndex % queue.size();
            }
        }
    }

    public Task pickNextTask() {
        // Pick the next task in the queue
        return queue.get(nextTaskIndex);
    }
}
