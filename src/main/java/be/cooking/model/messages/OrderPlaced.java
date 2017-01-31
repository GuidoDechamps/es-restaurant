package be.cooking.model.messages;

import be.cooking.model.Expirable;
import be.cooking.model.Order;

public class OrderPlaced extends MessageBase implements Expirable {

    private final Order order;

    public OrderPlaced(Order order) {
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
