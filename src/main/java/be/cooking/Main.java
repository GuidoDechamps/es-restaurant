package be.cooking;

import be.cooking.model.Order;
import be.cooking.model.actors.Waiter;
import be.cooking.generic.ThreadedHandler;

public class Main {

    private static final int NR_OF_ORDERS_TAKEN = 100;

    public static void main(String[] args) {
        final Context context = Context.create();
        startWorking(context.waiter);

        waitUntilAllOrdersAreDone(context);
        printStatusReport(context);
        stopAllThreads(context);
    }

    private static void stopAllThreads(Context context) {
        System.out.println("-------------------------------");
        ThreadedHandler.stopAll();
    }

    private static void printStatusReport(Context context) {
        System.out.println("----------Status Report---------------------");
        context.cooks.forEach(c -> System.out.println("Cook " + c.getName() + ": " + c.getCount()));
        System.out.println("Dropped orders: " + context.orderRepository.getList().stream().filter(e -> e.getStatus() == Order.Status.DROPPED).count());
        System.out.println("Finished orders: " + context.orderRepository.getList().stream().filter(e -> e.getStatus() == Order.Status.DONE).count());

    }

    private static void waitUntilAllOrdersAreDone(Context context) {
        while (context.orderRepository.getList().stream().filter(e -> e.getStatus() == Order.Status.DONE).count() < NR_OF_ORDERS_TAKEN) {
            Sleep.sleep(200);
        }
    }

    private static void print(ThreadedHandler threadedHandler) {
        System.out.println("Queue " + threadedHandler.getName() + " size = " + threadedHandler.size());
    }

    private static void startWorking(Waiter waiter) {
        long start = System.currentTimeMillis();
        takeOrders(waiter);
        long end = System.currentTimeMillis();
        System.out.println("Processing " + NR_OF_ORDERS_TAKEN + " took " + (end - start) / 1000 + " seconds");
    }

    private static void takeOrders(Waiter waiter) {
        for (int i = 0; i < NR_OF_ORDERS_TAKEN; i++)
            waiter.takeOrder(1);
    }


}
