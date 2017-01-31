package be.cooking.model;

import be.cooking.Sleep;

import java.util.stream.Collectors;

public class Cook implements HandleOrder {

    private final int cookTime;
    private final HandleOrder next;
    private final String name;

    public Cook(HandleOrder next, String name, int cookTime) {
        this.next = next;
        this.name = name;
        this.cookTime = cookTime;
    }

    public void handle(Order order) {
        cook(order);
        next.handle(order);
    }

    private void cook(Order order) {
        System.out.println(name + " is cooking..");
        final String ingredients = buildIngredients(order);
        order.addCookInfo(cookTime, ingredients);
        Sleep.sleep(cookTime);
        System.out.println(name + " is done cooking");
    }

    private String buildIngredients(Order order) {
        return order.getItems().stream().map(Item::getItem).map(x -> x + " ").collect(Collectors.joining());
    }


}
