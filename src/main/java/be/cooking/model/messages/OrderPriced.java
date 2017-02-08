package be.cooking.model.messages;

import be.cooking.generic.messages.MessageBase;
import be.cooking.model.Order;

import java.util.UUID;

public class OrderPriced extends MessageBase<Order> {

    private final Order order;

    public OrderPriced(Order order, UUID correlationUUID, UUID causeUUID) {
        super(correlationUUID, causeUUID);
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
