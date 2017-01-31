package be.cooking;

import be.cooking.model.*;

import java.util.Arrays;
import java.util.List;

public class Main {

    private static final int NR_OF_ORDERS_TAKEN = 100;

    public static void main(String[] args) {
        final ThreadedHandler orderPrinter = createActor("Printer", new OrderPrinter());
        final Cashier cashier1 = new Cashier(orderPrinter);
        final ThreadedHandler cashier = createActor("MoneyMan", cashier1);
        final ThreadedHandler manager = createActor("Manager", new Manager(cashier));
        final ThreadedHandler koen = createActor("Cook Koen", new Cook(manager, "Koen", 350));
        final ThreadedHandler guido = createActor("Cook Guido", new Cook(manager, "Guido", 200));
        final ThreadedHandler greg = createActor("Cook Greg", new Cook(manager, "Greg", 600));
        final RoundRobin repeater = RoundRobin.newBuilder()
                .withHandler(koen)
                .withHandler(guido)
                .withHandler(greg)
                .build();
        final ThreadedHandler threadedHandler = new ThreadedHandler("HandlerBob", repeater);
        final Waiter waiter = new Waiter(repeater);
        final List<ThreadedHandler> threadedHandlers = Arrays.asList(orderPrinter, cashier, manager, threadedHandler, koen, greg, guido);

        threadedHandlers.forEach(ThreadedHandler::start);

        startWorking(waiter);

        waitUntilAllOrdersAreDone(cashier1, threadedHandlers);
        threadedHandler.stop();


    }

    private static void waitUntilAllOrdersAreDone(Cashier cashier, List<ThreadedHandler> threadedHandlers) {
        while (cashier.getNrOfOrdersProcessed() != NR_OF_ORDERS_TAKEN) {
            threadedHandlers.forEach(Main::print);
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
        for (int i = 0; i < NR_OF_ORDERS_TAKEN; i++) {
            waiter.takeOrder(1);
        }
    }

    private static ThreadedHandler createActor(String name, HandleOrder handler) {
        return new ThreadedHandler(name, handler);
    }


}
