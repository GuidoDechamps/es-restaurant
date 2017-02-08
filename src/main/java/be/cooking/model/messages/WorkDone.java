package be.cooking.model.messages;

import be.cooking.generic.messages.MessageBase;
import be.cooking.model.Order;

public class WorkDone extends MessageBase<Order> {
    private final Order order;


    public WorkDone(MessageBase<Order> m) {
        super(m);
        this.order = m.getContent();
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