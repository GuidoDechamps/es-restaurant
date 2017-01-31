package be.cooking.model;

import java.util.stream.Collectors;

public class Cook implements HandleOrder {

    private static final int COOK_TIME = 2;
    private final HandleOrder next;
    private final String name;

    public Cook(HandleOrder next, String name) {
        this.next = next;
        this.name = name;
    }

    public void handle(Order order) {
        cook(order);
        next.handle(order);
    }

    private void cook(Order order) {
        System.out.println(name+ " is cooking..");
        final String ingredients = buildIngredients(order);
        order.addCookInfo(COOK_TIME, ingredients);
        sleep();
        System.out.println(name + " is done cooking");
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
