package be.cooking.model;

public class Cashier implements HandleOrder {

    private final HandleOrder next;
    private int nrOfOrders = 0;

    public Cashier(HandleOrder next) {
        this.next = next;
    }

    public void handle(Order order) {
        calculate(order);
        next.handle(order);
    }

    private void calculate(Order order) {
        System.out.println("Receiving money..");
        sleep();
        order.payed();
        System.out.println("Order " + ++nrOfOrders + " PAID");
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
}
