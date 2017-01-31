package be.cooking.model;

import be.cooking.Sleep;
import be.cooking.model.messages.OrderCooked;
import be.cooking.model.messages.OrderPriced;

public class Manager implements Handler<OrderCooked> {

    private final Publisher publisher;

    public Manager(Publisher publisher) {
        this.publisher = publisher;
    }

    public void handle(OrderCooked event) {
        Order order = calculate(event.getOrder());
        publisher.publish(new OrderPriced(order));
    }

    private Order calculate(Order order) {
        System.out.println("Calculating..");
        order.addPrices(21, 121, 100);
        Sleep.sleep(1);
        System.out.println("Calculating done");
        return order;
    }


}
