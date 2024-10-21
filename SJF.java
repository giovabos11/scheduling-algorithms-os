import java.util.List;

public class SJF implements Algorithm {
    private List<Task> queue;
    private Task currentTask;

    private int tasksRun;

    public SJF(List<Task> queue) {
        this.queue = queue;
        tasksRun = queue.size();
    }

    public void schedule() {
        System.out.println("SJF Scheduling \n");

        // While there are items in the ready queue
        while (!queue.isEmpty()) {
            // Pick the next task to run
            currentTask = pickNextTask();

            // Run process
            CPU.run(currentTask, currentTask.getBurst());

            // Remove the task from the queue
            System.out.println("Task " + currentTask.getName() + " finished.\n");
            queue.remove(currentTask);
        }
    }

    public Task pickNextTask() {
        // Create a temporary variable to hold the shortest task
        Task shortest = new Task("", Integer.MAX_VALUE, Integer.MAX_VALUE);

        // Look for the shortest task in the queue
        for (int i = 0; i < queue.size(); i++) {
            if (queue.get(i).getBurst() < shortest.getBurst()) {
                shortest = queue.get(i);
            }
        }

        // Return the shortest task
        return shortest;
    }
}
