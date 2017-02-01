package be.cooking.model.messages;

import be.cooking.generic.messages.MessageBase;
import be.cooking.model.Order;

public class CookFood extends MessageBase {
    private final Order order;

    public CookFood(Order order) {
        super(order.getOrderUUID());
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }
}
