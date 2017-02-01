package be.cooking.model.actors;

import be.cooking.Sleep;
import be.cooking.generic.Handler;
import be.cooking.generic.Publisher;
import be.cooking.model.Order;
import be.cooking.model.messages.OrderPriced;
import be.cooking.model.messages.PriceOrder;

public class Manager implements Handler<PriceOrder> {

    private final Publisher publisher;

    public Manager(Publisher publisher) {
        this.publisher = publisher;
    }

    public void handle(PriceOrder priceOrder) {
        Order order = calculate(priceOrder.getOrder());
        publisher.publish(new OrderPriced(order, priceOrder.getCorrelationUUID(), priceOrder.getMessageUUID()));
    }

    private Order calculate(Order order) {
        System.out.println("Calculating..");
        order.addPrices(21, 121, 100);
        Sleep.sleep(1);
        System.out.println("Calculating done");
        return order;
    }


}
