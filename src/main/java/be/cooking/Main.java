package be.cooking;

import be.cooking.model.OrderPrinter;
import be.cooking.model.Order;
import be.cooking.model.Waiter;

public class Main {

    public static void main(String[] args) {
        Order order = Order.newBuilder()
                .withCookTime(12)
                .withIngredients("ingredienten")
                .withSubtotal(456)
                .withTableNumber(4)
                .withTax(4)
                .build();
        OrderPrinter orderPrinter = new OrderPrinter();
        Waiter waiter = new Waiter(orderPrinter);
        waiter.placeOrder(order);


    }
}
