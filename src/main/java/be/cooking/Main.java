package be.cooking;

import be.cooking.generic.ThreadedHandler;
import be.cooking.model.actors.Waiter;

public class Main {


    public static void main(String[] args) {
        int nr = getNrOfOrders(args);
        final Context context = Context.create(nr);
        startWorking(context.waiter,nr);

        waitUntilAllOrdersAreDone(context);
        printStatusReport(context);
        stopAllThreads();
    }

    private static int getNrOfOrders(String[] args) {
        if (args.length == 1)
            return Integer.parseInt(args[0]);
        throw new RuntimeException("The number of orders is required");
    }

    private static void stopAllThreads() {
        System.out.println("-------------------------------");
        ThreadedHandler.stopAll();
    }

    private static void printStatusReport(Context context) {
        System.out.println("----------Status Report---------------------");
        context.cooks.forEach(c -> System.out.println("Cook " + c.getName() + ": " + c.getCount()));
//        System.out.println("Dropped orders: " + context.orderRepository.getList().stream().filter(e -> e.getStatus() == Order.Status.DROPPED).count());
//        System.out.println("Finished orders: " + context.orderRepository.getList().stream().filter(e -> e.getStatus() == Order.Status.DONE).count());

    }

    private static void waitUntilAllOrdersAreDone(Context context) {
        while (notAllOrdersAreDone(context)) {
            Sleep.sleep(200);
        }
    }

    private static boolean notAllOrdersAreDone(Context context) {
        return context.orderRepository.getList().size() < context.nrOfOrders;
    }


    private static void startWorking(Waiter waiter,int nrOfOrders) {
        long start = System.currentTimeMillis();
        takeOrders(waiter,nrOfOrders);
        long end = System.currentTimeMillis();
        System.out.println("Processing " + nrOfOrders + " took " + (end - start) / 1000 + " seconds");
    }

    private static void takeOrders(Waiter waiter,int nrOfOrders) {
        for (int i = 0; i < nrOfOrders; i++)
            waiter.takeOrder(1);
    }


}
