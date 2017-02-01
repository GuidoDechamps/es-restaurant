package be.cooking.generic;

import be.cooking.Sleep;
import be.cooking.generic.messages.MessageBase;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

public class ThreadedHandler<T extends MessageBase> implements Handler<T>, Startable {
    private static final Queue<ThreadedHandler> ALL_THREADS = new ConcurrentLinkedDeque<>();
    private final Queue<T> messages = new ConcurrentLinkedDeque<>();
    private final Handler<T> handler;
    private final Thread thread;
    private final String name;
    private boolean keepRunning = true;

    public ThreadedHandler(String name, Handler<T> handler) {
        this.handler = handler;
        this.name = name;
        thread = new Thread(this::handleExistingOrders);
        ALL_THREADS.add(this);
    }

    public static void stopAll() {
        ALL_THREADS.forEach(ThreadedHandler::stop);
    }

    @Override
    public void handle(T message) {
        messages.add(message);
    }

    public String getName() {
        return name;
    }

    public int size() {
        return messages.size();
    }

    @Override
    public void start() {
        thread.start();
    }

    @Override
    public void stop() {
        keepRunning = false;
    }

    private void handleExistingOrders() {
        System.out.println("Started thread " + name);
        while (keepRunning) {
            if (messages.size() > 0)
                handler.handle(messages.remove());
            else
                Sleep.sleep(10);
        }
        System.out.println("Thread " + name + " is stopped.");
    }

}
