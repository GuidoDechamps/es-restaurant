package be.cooking.model;

import java.util.Random;
import java.util.UUID;

public class Waiter {

    private static final Random RANDOM = new Random();
    private final HandleOrder handler;

    public Waiter(HandleOrder handler) {
        this.handler = handler;
    }

    public UUID takeOrder(int tableNumber) {
        final Order order = buildRandomOrder(tableNumber);
        System.out.println("Taking Order.." + order);
        handler.handle(order);
        return order.getOrderUUID();
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
                .addTimeToLive(200)
                .build();
    }

    private static Order buildSteakOrder(int tableNumber) {
        return Order.newBuilder()
                .withTableNumber(tableNumber)
                .addItem(ItemCode.WINE)
                .addItem(ItemCode.STEAK)
                .addTimeToLive(400)
                .build();
    }

    private static Order buildSoepOrder(int tableNumber) {
        return Order.newBuilder()
                .withTableNumber(tableNumber)
                .addItem(ItemCode.SOEP)
                .addTimeToLive(350)
                .build();
    }

    private static Order buildSpaghettiOrder(int tableNumber) {
        return Order.newBuilder()
                .withTableNumber(tableNumber)
                .addItem(ItemCode.SPAGHETTI)
                .addTimeToLive(450)
                .build();
    }
}
