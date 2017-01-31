package be.cooking.model;

import java.util.UUID;

public class Waiter {

    private final HandleOrder handler;

    public Waiter(HandleOrder handler) {
        this.handler = handler;
    }

    public UUID placeOrder(Order order) {
        handler.handle(order);
        return order.getOrderUUID();
    }
}
