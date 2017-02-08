package be.cooking.model.actors;

import be.cooking.Sleep;
import be.cooking.generic.Handler;
import be.cooking.generic.Publisher;
import be.cooking.model.Order;
import be.cooking.model.messages.OrderPaid;
import be.cooking.model.messages.ToThePayment;

public class Cashier implements Handler<ToThePayment> {

    private final Publisher publisher;
    private int nrOfOrders = 0;

    public Cashier(Publisher next) {
        this.publisher = next;
    }


    @Override
    public void handle(ToThePayment command) {
        Order order = calculate(command.getOrder());
        publisher.publish(new OrderPaid(order, command.getCorrelationUUID(), command.getMessageUUID()));
    }

    private Order calculate(Order order) {
        System.out.println("Receiving money..");
        Sleep.sleep(5);
        System.out.println("Order " + ++nrOfOrders + " PAID");
        return order;
    }

}
