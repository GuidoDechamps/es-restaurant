package be.cooking.app;

import be.cooking.model.Order;

public class OrderPrinter implements HandleOrder {
    @Override
    public void handle(Order order) {
        System.out.println("order received: " + order);
    }
}
