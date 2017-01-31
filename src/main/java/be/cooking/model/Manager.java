package be.cooking.model;

import be.cooking.Sleep;

public class Manager implements HandleOrder {

    private final Publisher publisher;

    public Manager(Publisher publisher) {
        this.publisher = publisher;
    }

    public void handle(Order order) {
        calculate(order);
        publisher.publish(Topics.PRICE_CALCULATED, order);
    }

    private void calculate(Order order) {
        System.out.println("Calculating..");
        order.addPrices(21, 121, 100);
        Sleep.sleep(1);
        System.out.println("Calculating done");
    }


}
