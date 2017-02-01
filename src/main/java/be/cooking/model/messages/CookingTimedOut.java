package be.cooking.model.messages;

import be.cooking.generic.messages.MessageBase;
import be.cooking.model.Order;

import java.util.UUID;

public class CookingTimedOut extends MessageBase {
    private final Order order;

    public CookingTimedOut(Order order, UUID correlationUUID, UUID causeUUID) {
        super(correlationUUID, causeUUID);
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }
}
