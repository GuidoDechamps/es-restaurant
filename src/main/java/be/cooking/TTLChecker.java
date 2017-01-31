package be.cooking;

import be.cooking.model.HandleOrder;
import be.cooking.model.Order;

public class TTLChecker implements HandleOrder {

    private HandleOrder handleOrder;

    public TTLChecker(HandleOrder handleOrder) {
        this.handleOrder = handleOrder;
    }

    @Override
    public void handle(Order order) {
        if (order.isExpired())
            System.out.println("Drop order: " + order.getOrderUUID());
        else
            handleOrder.handle(order);
    }
}