package be.cooking;

import be.cooking.model.Cook;
import be.cooking.model.OrderPrinter;
import be.cooking.model.Waiter;

public class Main {

    public static void main(String[] args) {
        final Waiter waiter = buildActors();
        waiter.takeOrder();
    }

    private static Waiter buildActors() {
        final OrderPrinter orderPrinter = new OrderPrinter();
        final Cook cook = new Cook(orderPrinter);
        return new Waiter(cook);
    }


}
