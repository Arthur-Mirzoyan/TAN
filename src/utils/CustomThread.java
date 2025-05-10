package utils;

/**
 * A utility class for running a task repeatedly in a background thread with a fixed delay.
 */
public class CustomThread {
    private volatile boolean isRunning = true;
    private volatile int ms;

    /**
     * Constructs a CustomThread with the specified interval in milliseconds.
     *
     * @param ms delay between executions in milliseconds
     */
    public CustomThread(int ms) {
        this.ms = ms;
    }

    /**
     * Constructs a CustomThread with a default delay of 100 milliseconds.
     */
    public CustomThread() {
        this(100);
    }

    /**
     * Runs the provided action in a loop on a background thread,
     * waiting {@code ms} milliseconds between each run.
     *
     * @param action the action to execute repeatedly
     */
    public void run(Runnable action) {
        new Thread(() -> {
            while (isRunning) {
                try {
                    action.run();
                    Thread.sleep(ms); // To reduce CPU usage
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    isRunning = false;
                    break;
                }
            }
        }).start();
    }

    /**
     * Stops the running thread loop.
     */
    public void stop() {
        isRunning = false;
    }
}
