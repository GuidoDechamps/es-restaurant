package be.cooking.model;

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
        sleep();
        order.payed();
        System.out.println("Order " + ++nrOfOrders + " PAID");
        return order;
    }

    public int getNrOfOrdersProcessed() {
        return nrOfOrders;
    }

    private void sleep() {
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handle(OrderPriced event) {
        Order order = calculate(event.getOrder());
        publisher.publish(new OrderPaid(order));
    }
}
