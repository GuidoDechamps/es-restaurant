package be.cooking.model.messages;

import be.cooking.model.Order;

public class OrderPlaced extends MessageBase {

    private final Order order;

    public OrderPlaced(Order order) {
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }
}
