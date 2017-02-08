package be.cooking.model.messages;

import be.cooking.generic.messages.MessageBase;
import be.cooking.model.Order;

public class CookFood extends MessageBase<Order> {
    private final Order order;

    public CookFood(MessageBase<Order> message) {
        super(message);
        this.order = message.getContent();
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
