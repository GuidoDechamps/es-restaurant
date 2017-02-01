package be.cooking.model.actors;

import be.cooking.Sleep;
import be.cooking.generic.Handler;
import be.cooking.generic.Publisher;
import be.cooking.model.Order;
import be.cooking.model.messages.OrderPaid;
import be.cooking.model.messages.OrderPriced;

public class Cashier implements Handler<OrderPriced> {

    private final Publisher publisher;
    private int nrOfOrders = 0;

    public Cashier(Publisher next) {
        this.publisher = next;
    }


    private Order calculate(Order order) {
        System.out.println("Receiving money..");
        Sleep.sleep(5);
        order.payed();
        System.out.println("Order " + ++nrOfOrders + " PAID");
        return order;
    }

    public int getNrOfOrdersProcessed() {
        return nrOfOrders;
    }

    @Override
    public void handle(OrderPriced event) {
        Order order = calculate(event.getOrder());
        publisher.publish(new OrderPaid(order, event.getCorrelationUUID(), event.getMessageUUID()));
    }
}
