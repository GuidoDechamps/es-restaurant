package be.cooking.model.actors;

import be.cooking.Sleep;
import be.cooking.generic.Handler;
import be.cooking.generic.Publisher;
import be.cooking.model.Item;
import be.cooking.model.Order;
import be.cooking.model.messages.OrderCooked;
import be.cooking.model.messages.OrderPlaced;

import java.util.stream.Collectors;

public class Cook implements Handler<OrderPlaced> {

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

    public void handle(OrderPlaced event) {
        Order order = cook(event.getOrder());
        publisher.publish(new OrderCooked(order, event.getCorrelationUUID(), event.getMessageUUID()));
    }

    private Order cook(Order order) {
        System.out.println(name + " is cooking his " + (++count) + "th order.");
        final String ingredients = buildIngredients(order);
        order.addCookInfo(cookTime, ingredients);

        Sleep.sleep(cookTime);
        System.out.println(name + " is done cooking");
        return order;
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
