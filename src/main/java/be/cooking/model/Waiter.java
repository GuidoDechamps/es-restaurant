package be.cooking.model;

import java.util.UUID;

public class Waiter {

    private final HandleOrder handler;

    public Waiter(HandleOrder handler) {
        this.handler = handler;
    }

    public UUID takeOrder(int tableNumber) {
        final Order order = buildRandomOrder(tableNumber);
        System.out.println("Taking Order.." + order);
        handler.handle(order);
        return order.getOrderUUID();
    }

    private static Order buildRandomOrder(int tableNumber) {
        return buildFrietOrder(tableNumber);
    }

    private static Order buildFrietOrder(int tableNumber) {
        return Order.newBuilder()
                .withTableNumber(tableNumber)
                .addItem(ItemCode.JUPILER)
                .addItem(ItemCode.FRIETEN)
                .addItem(ItemCode.BITTER_BALLEN)
                .build();
    }
}
