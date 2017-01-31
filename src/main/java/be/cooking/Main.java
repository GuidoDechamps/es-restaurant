package be.cooking;

import be.cooking.model.Cashier;
import be.cooking.model.ThreadedHandler;
import be.cooking.model.Waiter;

import java.util.IntSummaryStatistics;
import java.util.List;

import static java.util.stream.Collectors.summingInt;

public class Main {

    private static final int NR_OF_ORDERS_TAKEN = 100;

    public static void main(String[] args) {
        final Context context = new Context();
        context.threadedHandlers.forEach(ThreadedHandler::start);

        startWorking(context.waiter);

        waitUntilAllOrdersAreDone(context.cashier, context.threadedHandlers);
        context.threadedHandlers.forEach(ThreadedHandler::stop);
        printStatusReport(context);
    }

    private static void printStatusReport(Context context) {
        context.cooks.forEach(c -> System.out.println("Cook " + c.getName() + ": " + c.getCount()));
        System.out.println("Dropped orders: " + context.ttlCheckers.stream().map(x -> x.getDroppedOrders()).collect(summingInt(Integer::intValue)));
    }

    private static void waitUntilAllOrdersAreDone(Cashier cashier, List<ThreadedHandler> threadedHandlers) {
        while (cashier.getNrOfOrdersProcessed() != NR_OF_ORDERS_TAKEN) {
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
