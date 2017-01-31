package be.cooking.model;

import java.util.UUID;

public class Waiter {

    private final HandleOrder handler;

    public Waiter(HandleOrder handler) {
        this.handler = handler;
    }

    public UUID takeOrder() {
        final Order order = buildOrder();
        handler.handle(order);
        return order.getOrderUUID();
    }

    private static Order buildOrder() {
        return Order.newBuilder()
                .withCookTime(12)
                .withIngredients("ingredienten")
                .withSubtotal(456)
                .withTableNumber(4)
                .withTax(4)
                .addItem(new Item("Jupiler"))
                .addItem(new Item("Frietje"))
                .addItem(new Item("Bitterballen"))
                .build();
    }
}
