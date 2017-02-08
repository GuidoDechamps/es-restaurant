package be.cooking.model.actors;

import be.cooking.generic.Publisher;
import be.cooking.generic.messages.MessageBase;
import be.cooking.model.Order;

import java.util.List;
import java.util.Optional;

class MidgetFactory {
    private static final Order DUMMY_ORDER = Order.newBuilder().build();
    private final Publisher publisher;


    MidgetFactory(Publisher publisher) {
        this.publisher = publisher;
    }

    Midget createMidget(MessageBase messageBase, List<MessageBase> historyEvents) {
        final Optional<Order> order = extractOrder(messageBase);
        return buildMidgetForOrder(historyEvents, order.orElse(DUMMY_ORDER));
    }

    private Midget buildMidgetForOrder(List<MessageBase> historyEvents, Order order) {
        if (order.isDodgyCustomer())
            return new PayFirstMidget(publisher, historyEvents);
        else
            return new PayLastMidget(publisher, historyEvents);
    }


    private Optional<Order> extractOrder(MessageBase messageBase) {
        if (messageBase.getContent() instanceof Order)
            return Optional.of(Order.class.cast(messageBase.getContent()));
        else
            return Optional.empty();
    }
}
