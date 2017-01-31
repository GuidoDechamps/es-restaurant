package be.cooking;

import be.cooking.model.Cook;
import be.cooking.model.OrderPrinter;
import be.cooking.model.Order;
import be.cooking.model.Waiter;

public class Main {

    public static void main(String[] args) {
        final Order order = buildOrder();
        final OrderPrinter orderPrinter = new OrderPrinter();
        final Cook cook = new Cook(orderPrinter);
        final Waiter waiter = new Waiter(cook);

        waiter.placeOrder(order);


    }

    private static Order buildOrder() {
        return Order.newBuilder()
                    .withCookTime(12)
                    .withIngredients("ingredienten")
                    .withSubtotal(456)
                    .withTableNumber(4)
                    .withTax(4)
                    .build();
    }
}
