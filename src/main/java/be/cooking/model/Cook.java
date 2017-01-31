package be.cooking.model;

import java.util.stream.Collectors;

public class Cook implements HandleOrder {

    private static final int COOK_TIME = 2000;
    private final HandleOrder next;

    public Cook(HandleOrder next) {
        this.next = next;
    }

    public void handle(Order order) {
        cook(order);
        next.handle(order);
    }

    private void cook(Order order) {
        System.out.println("Cooking..");
        final String ingredients = buildIngredients(order);
        order.addCookInfo(COOK_TIME, ingredients);
        sleep();
        System.out.println("Cooking done");
    }

    private String buildIngredients(Order order) {
        return order.getItems().stream().map(Item::getItem).map( x -> x + " ").collect(Collectors.joining());
    }

    private void sleep() {
        try {
            Thread.sleep(COOK_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
