package be.cooking.model;

public class Cashier implements HandleOrder {

    private final Publisher publisher;
    private int nrOfOrders = 0;

    public Cashier(Publisher next) {
        this.publisher = next;
    }

    public void handle(Order order) {
        calculate(order);
        publisher.publish(Topics.PAYMENT_DONE,order);
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
