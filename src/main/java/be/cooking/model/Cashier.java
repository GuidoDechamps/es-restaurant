package be.cooking.model;

public class Cashier implements HandleOrder {

    private final HandleOrder next;

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
        System.out.println("Order PAID");
    }

    private void sleep() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
