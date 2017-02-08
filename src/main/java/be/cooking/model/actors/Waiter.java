package be.cooking.model.actors;

import be.cooking.generic.Publisher;
import be.cooking.model.ItemCode;
import be.cooking.model.Order;
import be.cooking.model.messages.OrderPlaced;

import java.util.Random;
import java.util.UUID;

public class Waiter {

    private static final Random RANDOM = new Random();
    private final Publisher publisher;

    public Waiter(Publisher publisher) {
        this.publisher = publisher;
    }

    private static Order buildRandomOrder(int tableNumber) {
        final int orderType = RANDOM.nextInt(5);
        switch (orderType) {
            case 0:
                return buildFrietOrder(tableNumber);
            case 1:
                return buildSteakOrder(tableNumber);
            case 2:
                return buildSoepOrder(tableNumber);
            case 3:
                return buildFrietOrder(tableNumber);
            case 4:
                return buildSpaghettiOrder(tableNumber);
            default:
                throw new RuntimeException("Unmatch orderType " + orderType);
        }
    }

    private static Order buildFrietOrder(int tableNumber) {
        return Order.newBuilder()
                .withTableNumber(tableNumber)
                .addItem(ItemCode.JUPILER)
                .addItem(ItemCode.FRIETEN)
                .addItem(ItemCode.BITTER_BALLEN)
                .addTimeToLive(5000)
                .build();
    }

    private static Order buildSteakOrder(int tableNumber) {
        return Order.newBuilder()
                .withTableNumber(tableNumber)
                .addItem(ItemCode.WINE)
                .addItem(ItemCode.STEAK)
                .addTimeToLive(4500)
                .build();
    }

    private static Order buildSoepOrder(int tableNumber) {
        return Order.newBuilder()
                .withTableNumber(tableNumber)
                .addItem(ItemCode.SOEP)
                .addTimeToLive(5500)
                .build();
    }

    private static Order buildSpaghettiOrder(int tableNumber) {
        return Order.newBuilder()
                .withTableNumber(tableNumber)
                .addItem(ItemCode.SPAGHETTI)
                .addTimeToLive(5000)
                .build();
    }

    public UUID takeOrder(int tableNumber) {
        final Order order = buildRandomOrder(tableNumber);
        System.out.println("Waiter taking Order.." + order.getOrderUUID());


        final OrderPlaced orderPlaced = new OrderPlaced(order);
        publisher.publish(orderPlaced);

        return order.getOrderUUID();
    }
}
