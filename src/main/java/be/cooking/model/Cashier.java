package be.cooking.model;

public class Cashier implements HandleOrder {

    private final HandleOrder next;
    private int nrOfOrders = 0;

    public Cashier(HandleOrder next) {
        this.next = next;
    }

    public void handle(Order order) {
        calculate();
        next.handle(order);
    }

    private void calculate() {
        System.out.println("Receiving money..");
        sleep();
        System.out.println("Order " + nrOfOrders++ + " PAID");
    }

    private void sleep() {
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
