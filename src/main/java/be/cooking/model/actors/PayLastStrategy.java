package be.cooking.model.actors;

import be.cooking.generic.messages.MessageBase;
import be.cooking.model.Order;
import be.cooking.model.messages.*;

import java.util.Collections;
import java.util.List;

import static java.util.Collections.singletonList;

class PayLastStrategy extends AbstractStrategy {

    public List<MessageBase> handleEvent(MessageBase m) {
        if (m instanceof OrderPlaced) {
            final OrderPlaced orderPlaced = (OrderPlaced) m;
            return createCookFoodCommands(orderPlaced);
        } else if (m instanceof OrderCooked) {
            final OrderCooked orderCooked = (OrderCooked) m;
            return singletonList(createToThePayment(orderCooked));
        } else if (m instanceof OrderPriced) {
            final OrderPriced orderPriced = (OrderPriced) m;
            return singletonList(createToThePayment(orderPriced));
        } else if (m instanceof CookingTimedOut) {
            return handleCookingTimeOutEvent((CookingTimedOut) m);
        } else if (m instanceof OrderPaid) {
            return handleOrderPaid((OrderPaid) m);
        }
        return Collections.emptyList();
    }

    private List<MessageBase> handleOrderPaid(OrderPaid orderPaid) {
        setOrderToFinished(orderPaid);
        return singletonList(createWorkDone(orderPaid));
    }


    private List<MessageBase> handleCookingTimeOutEvent(CookingTimedOut cookingTimedOut) {
        final Order order = cookingTimedOut.getOrder();
        if (order.getStatus() != Order.Status.ORDER_PLACED) {
            System.out.println("Order " + order.getOrderUUID() + " already cook " + order.getStatus());
            return Collections.emptyList();
        } else {
            order.increaseTries();
            if (order.getNrOfTries() > ORDER_RETRIES) {
                System.out.println("To many retries " + order.getOrderUUID());
                return handleToManyRetries(cookingTimedOut);
            } else
                return createCookFoodCommands(cookingTimedOut);
        }
    }


}
