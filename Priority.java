import java.util.List;

public class Priority implements Algorithm {
    private List<Task> queue;
    private Task currentTask;

    private int tasksRun;

    public Priority(List<Task> queue) {
        this.queue = queue;
        this.tasksRun = queue.size();
    }

    public void schedule() {
        System.out.println("Priority Scheduling \n");

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
        // Create a temporary variable to hold the highest priority task
        Task highestPriority = new Task("", Integer.MIN_VALUE, Integer.MIN_VALUE);

        // Look for the highest priority task in the queue
        for (int i = 0; i < queue.size(); i++) {
            if (queue.get(i).getPriority() > highestPriority.getPriority()) {
                highestPriority = queue.get(i);
            }
        }

        // Return the highest priority task
        return highestPriority;
    }
}
