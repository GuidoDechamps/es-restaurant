package be.cooking;

import be.cooking.model.*;

public class Main {

    public static void main(String[] args) {
        final Waiter waiter = buildActors();
        waiter.takeOrder();
    }

    private static Waiter buildActors() {
        final OrderPrinter orderPrinter = new OrderPrinter();
        final Cashier cashier = new Cashier(orderPrinter);
        final Manager manager = new Manager(cashier);
        final Cook cook = new Cook(manager);
        return new Waiter(cook);
    }


}
