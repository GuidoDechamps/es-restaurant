package be.cooking.model.messages;

import be.cooking.model.Order;

public class OrderPaid extends MessageBase {

    private final Order order;

    public OrderPaid(Order order) {
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }
}
