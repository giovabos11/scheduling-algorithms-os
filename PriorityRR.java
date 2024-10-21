import java.util.ArrayList;
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

        // While there are items in the ready queue
        while (!queue.isEmpty()) {
            // Pick the tasks with the highest priority
            List<Task> currentTasks = pickNextTasks();

            // If there is only one task with the highest priority, run that task for the full time
            if (currentTasks.size() == 1) {
                // Pick the next task to run
                currentTask = currentTasks.get(0);

                // Run process for the entire time
                CPU.run(currentTask, currentTask.getBurst());

                // Update process burst time
                currentTask.setBurst(0);

                // Remove the task from the queues
                System.out.println("Task " + currentTask.getName() + " finished.\n");
                currentTasks.remove(currentTask);
                queue.remove(currentTask);
            }
            // Otherwise, more than one task with the same priority, do round-robin
            else {
                int currentTasksIndex = 0;
                // Iterate through the queue
                while (!currentTasks.isEmpty()) {
                    // Pick the next task to run
                    currentTask = currentTasks.get(currentTasksIndex);

                    // Run process for quantum time or remaining
                    CPU.run(currentTask, Math.min(quantum, currentTask.getBurst()));

                    // Update process burst time
                    currentTask.setBurst(currentTask.getBurst() - Math.min(quantum, currentTask.getBurst()));

                    // Check to remove the task from the queues
                    if (currentTask.getBurst() <= 0) {
                        System.out.println("Task " + currentTask.getName() + " finished.\n");
                        currentTasks.remove(currentTask);
                        queue.remove(currentTask);
                        currentTasksIndex--;
                    }

                    // Update the current tasks index
                    currentTasksIndex++;
                    if (!currentTasks.isEmpty()) {
                        currentTasksIndex = currentTasksIndex % currentTasks.size();
                    }
                }
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

    /**
     * This method gets a list of all the processes with the highest priority
     */
    public List<Task> pickNextTasks() {
        // Create a temporary variable to hold the highest priority task
        Task highestPriority = new Task("", Integer.MIN_VALUE, Integer.MIN_VALUE);
        // Create a temporary tasks list
        List<Task> tasks = new ArrayList<>();

        // Look for the highest priority task in the queue (and add ties)
        for (int i = 0; i < queue.size(); i++) {
            if (queue.get(i).getPriority() > highestPriority.getPriority()) {
                highestPriority = queue.get(i);
                tasks.clear();
                tasks.add(queue.get(i));
            }
            else if (queue.get(i).getPriority() == highestPriority.getPriority()) {
                tasks.add(queue.get(i));
            }
        }

        // Return the highest priority tasks (if they are tied)
        return tasks;
    }
}
