package be.cooking.model;

import be.cooking.Sleep;

public class Manager implements HandleOrder {

    private final HandleOrder next;

    public Manager(HandleOrder next) {
        this.next = next;
    }

    public void handle(Order order) {
        calculate(order);
        next.handle(order);
    }

    private void calculate(Order order) {
        System.out.println("Calculating..");
        order.addPrices(21,121,100);
        Sleep.sleep(1);
        System.out.println("Calculating done");
    }


}
