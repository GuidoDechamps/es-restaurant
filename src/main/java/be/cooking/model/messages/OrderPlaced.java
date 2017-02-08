package be.cooking.model.messages;

import be.cooking.generic.messages.MessageBase;
import be.cooking.model.Order;

import java.util.UUID;

public class OrderPlaced extends MessageBase<Order> {

    private final Order order;

    public OrderPlaced(Order order) {
        super(UUID.randomUUID());
        this.order = order;
    }

    @Deprecated
    public Order getOrder() {
        return order;
    }

    @Override
    public Order getContent() {
        return getOrder();
    }
}
