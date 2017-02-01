package be.cooking.model.actors;

import be.cooking.generic.Publisher;
import be.cooking.model.Order;

class MidgetFactory {
    private final MidgetStrategy payLastStrategy = new PayLastStrategy();
    private final MidgetStrategy payFirstStrategy = new PayFirstStrategy();
    private final Publisher publisher;

    public MidgetFactory(Publisher publisher) {
        this.publisher = publisher;
    }

    Midget createMidget(Order order) {
        return new Midget(publisher,order.getOrderUUID(), determineStrategy(order));
    }

    private MidgetStrategy determineStrategy(Order order) {
        if (order.isDodgyCustomer())
            return payFirstStrategy;
        else
            return payLastStrategy;
    }
}
