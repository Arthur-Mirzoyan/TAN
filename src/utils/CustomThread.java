package utils;

public class CustomThread {
    private volatile boolean isRunning = true;
    private volatile int ms;

    public CustomThread(int ms) {
        this.ms = ms;
    }

    public CustomThread() {
        this(100);
    }

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

    public void stop() {
        isRunning = false;
    }
}
