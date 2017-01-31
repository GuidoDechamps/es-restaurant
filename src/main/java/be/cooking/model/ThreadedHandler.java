package be.cooking.model;

import be.cooking.Sleep;

import java.util.LinkedList;
import java.util.Queue;

public class ThreadedHandler implements HandleOrder, Startable {
    private final Queue<Order> orders = new LinkedList();
    private final HandleOrder handler;
    private final Thread thread;
    private final String name;
    private boolean keepRunning = true;

    public ThreadedHandler(String name, HandleOrder handler) {
        this.handler = handler;
        this.name = name;
        thread = new Thread(this::handleExistingOrders);
    }

    @Override
    public void handle(Order order) {
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
