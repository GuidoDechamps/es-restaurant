package be.cooking.model;

import be.cooking.Sleep;

import java.util.stream.Collectors;

public class Cook implements HandleOrder {

    private final int cookTime;
    private final Publisher publisher;
    private final String name;
    private int count;

    public Cook(Publisher publisher, String name, int cookTime) {
        this.publisher = publisher;
        this.name = name;
        this.count = 0;
        this.cookTime = cookTime;
    }

    public void handle(Order order) {
        cook(order);
        publisher.publish(Topics.FOOD_READY, order);
    }

    private void cook(Order order) {
        System.out.println(name + " is cooking his " + (++count) + "th order.");
        final String ingredients = buildIngredients(order);
        order.addCookInfo(cookTime, ingredients);

        Sleep.sleep(cookTime);
        System.out.println(name + " is done cooking");
    }

    public int getCount() {
        return count;
    }

    public String getName() {
        return name;
    }

    private String buildIngredients(Order order) {
        return order.getItems().stream().map(Item::getItem).map(x -> x + " ").collect(Collectors.joining());
    }


}
