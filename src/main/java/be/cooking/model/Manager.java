package be.cooking.model;

public class Manager implements HandleOrder {

    private final HandleOrder next;

    public Manager(HandleOrder next) {
        this.next = next;
    }

    public void handle(Order order) {
        calculate();
        next.handle(order);
    }

    private void calculate() {
        System.out.println("Calculating..");
        sleep();
        System.out.println("Calculating done");
    }

    private void sleep() {
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
