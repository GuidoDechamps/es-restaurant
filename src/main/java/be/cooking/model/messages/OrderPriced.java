package be.cooking.model.messages;

import be.cooking.model.Order;

public class OrderPriced extends MessageBase {

    private final Order order;

    public OrderPriced(Order order) {
        this.order = order;
    }


    public Order getOrder() {
        return order;
    }
}
