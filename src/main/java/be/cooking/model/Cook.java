package be.cooking.model;

public class Cook implements HandleOrder {

    private final HandleOrder next;

    public Cook(HandleOrder next) {
        this.next = next;
    }

    public void handle(Order order) {
        cook();
        next.handle(order);
    }

    private void cook() {
        System.out.println("Cooking..");
        sleep();
        System.out.println("Cooking done");
    }

    private void sleep() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
