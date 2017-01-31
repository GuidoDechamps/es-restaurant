package be.cooking;

import be.cooking.model.HandleOrder;
import be.cooking.model.Order;

public class TTLChecker implements HandleOrder {

    private HandleOrder handleOrder;
    private int droppedOrders;

    public TTLChecker(HandleOrder handleOrder) {
        this.handleOrder = handleOrder;
        this.droppedOrders = 0;
    }

    public int getDroppedOrders() {
        return droppedOrders;
    }

    @Override
    public void handle(Order order) {
        if (order.isExpired()) {
            System.out.println("Drop order: " + order.getOrderUUID());
            droppedOrders++;
        } else
            handleOrder.handle(order);
    }
}
