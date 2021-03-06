package be.cooking.model.actors;

import be.cooking.Sleep;
import be.cooking.generic.Handler;
import be.cooking.generic.Publisher;
import be.cooking.model.Item;
import be.cooking.model.Order;
import be.cooking.model.messages.CookFood;
import be.cooking.model.messages.OrderCooked;

import java.util.stream.Collectors;

public class Cook implements Handler<CookFood> {

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

    public void handle(CookFood command) {
        if (command.getOrder().isDropped())
            System.out.println("The order is dropped. Not cooking");
        else {
            if (command.getOrder().isDone())
                System.out.println("The order is done. Not cooking");
            else {
                cook(command);
            }
        }
    }

    private void cook(CookFood command) {
        Order order = cook(command.getOrder());
        publisher.publish(new OrderCooked(order, command.getCorrelationUUID(), command.getMessageUUID()));
    }

    private Order cook(Order order) {
        System.out.println(name + " is cooking his " + (++count) + "th order.");
        final String ingredients = buildIngredients(order);
        order.addCookInfo(cookTime, ingredients);

        Sleep.sleep(cookTime);
        System.out.println(name + " is done cooking");
        order.cooked();
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
