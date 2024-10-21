import java.util.List;

public class PriorityRR implements Algorithm {
    private List<Task> queue;
    private Task currentTask;

    private int tasksRun;
    private int nextTaskIndex;

    private final int quantum = 10; // Default quantum of 10 milliseconds

    public PriorityRR(List<Task> queue) {
        this.queue = queue;
        this.tasksRun = queue.size();
    }

    public void schedule() {
        System.out.println("Priority with RR Scheduling \n");

        nextTaskIndex = 0;

        // While there are items in the ready queue
        while (!queue.isEmpty()) {
            // Pick the next task to run
            currentTask = pickNextTask();

            // If there are no other processes with the same priority
            if (isHighestTaskPriority(currentTask)) {
                // Run process for the entire time
                CPU.run(currentTask, currentTask.getBurst());

                // Update process burst time
                currentTask.setBurst(0);
            }
            // Otherwise, round-robin
            else {
                // Run process for quantum time or remaining
                CPU.run(currentTask, Math.min(quantum, currentTask.getBurst()));

                // Update process burst time
                currentTask.setBurst(currentTask.getBurst() - Math.min(quantum, currentTask.getBurst()));
            }

            // Check to remove the task from the queue
            if (currentTask.getBurst() <= 0) {
                System.out.println("Task " + currentTask.getName() + " Finished\n");
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

    private boolean isHighestTaskPriority(Task task) {
        return true;
    }
}
