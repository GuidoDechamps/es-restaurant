package be.cooking.model;

public class OrderPrinter implements HandleOrder {
    @Override
    public void handle(Order order) {
        System.out.println("order received: " + order);
    }
}
