package be.cooking.model;

import be.cooking.Sleep;
import be.cooking.model.messages.MessageBase;

import java.util.LinkedList;
import java.util.Queue;

public class ThreadedHandler<T extends MessageBase> implements Handler<T>, Startable {
    private final Queue<T> orders = new LinkedList();
    private final Handler<T> handler;
    private final Thread thread;
    private final String name;
    private boolean keepRunning = true;

    public ThreadedHandler(String name, Handler<T> handler) {
        this.handler = handler;
        this.name = name;
        thread = new Thread(this::handleExistingOrders);
    }

    @Override
    public void handle(T order) {
        orders.add(order);
    }

    public String getName() {
        return name;
    }

    public int size() {
        return orders.size();
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
            if (orders.size() > 0)
                handler.handle(orders.remove());
            else
                Sleep.sleep(10);
        }
        System.out.println("Thread " + name + " is stopped.");
    }

}
