package be.cooking.model;

import be.cooking.model.messages.OrderPaid;

public class OrderPrinter implements Handler<OrderPaid> {
    @Override
    public void handle(OrderPaid event) {
        System.out.println("order : " + event.getOrder());
    }
}
