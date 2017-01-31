package be.cooking;

import be.cooking.model.*;

public class Main {

    private static final int NR_OF_ORDERS_TAKEN = 10;

    public static void main(String[] args) {
        final Waiter waiter = buildActors();
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

    private static Waiter buildActors() {
        final OrderPrinter orderPrinter = new OrderPrinter();
        final Cashier cashier = new Cashier(orderPrinter);
        final Manager manager = new Manager(cashier);
        final RoundRobin repeater = RoundRobin.newBuilder()
                .withHandler(new Cook(manager, "Koen"))
                .withHandler(new Cook(manager, "Guido"))
                .withHandler(new Cook(manager, "Greg"))
                .build();
        return new Waiter(repeater);
    }


}
