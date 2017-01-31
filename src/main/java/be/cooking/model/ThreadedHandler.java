package be.cooking.model;

import be.cooking.Sleep;

import java.util.LinkedList;
import java.util.Queue;

public class ThreadedHandler implements HandleOrder, Startable {
    private final Queue<Order> orders = new LinkedList();
    private final HandleOrder handler;
    private final Thread thread;
    private boolean keepRunning = true;

    public ThreadedHandler(HandleOrder handler) {
        this.handler = handler;
        thread = new Thread(this::handleExistingOrders);
    }

    @Override
    public void handle(Order order) {
        orders.add(order);
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
        System.out.println("In my thread");
        while (keepRunning) {
            if (orders.size() > 0)
                handler.handle(orders.remove());
            else
                Sleep.sleep(10);
        }
        System.out.println("Time to go home");
    }

}
