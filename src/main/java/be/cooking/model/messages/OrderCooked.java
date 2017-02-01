package be.cooking.model.messages;

import be.cooking.generic.messages.MessageBase;
import be.cooking.model.Order;

public class OrderCooked extends MessageBase {

    private final Order order;

    public OrderCooked(Order order) {
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }
}
