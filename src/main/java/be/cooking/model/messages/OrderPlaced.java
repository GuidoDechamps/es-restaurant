package be.cooking.model.messages;

import be.cooking.generic.Expirable;
import be.cooking.generic.messages.MessageBase;
import be.cooking.model.Order;

import java.util.UUID;

public class OrderPlaced extends MessageBase implements Expirable {

    private final Order order;

    public OrderPlaced(Order order) {
        super(UUID.randomUUID());
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }

    @Override
    public boolean isExpired() {
        return order.isExpired();
    }

    @Override
    public void drop() {
        order.drop();
    }
}
