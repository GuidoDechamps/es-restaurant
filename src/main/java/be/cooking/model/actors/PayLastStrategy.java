package be.cooking.model.actors;

import be.cooking.generic.messages.MessageBase;
import be.cooking.model.Order;
import be.cooking.model.messages.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.singletonList;

class PayLastStrategy implements MidgetStrategy {
    private static final int ORDER_RETRIES = 3;
    private static final int TIME_TO_PUBLISH = 2500;

    public List<MessageBase> handleEvent(MessageBase m) {
        if (m instanceof OrderPlaced) {
            final OrderPlaced orderPlaced = (OrderPlaced) m;
            return Arrays.asList(createCookFood(orderPlaced), createPublishAt(orderPlaced));
        } else if (m instanceof OrderCooked) {
            final OrderCooked orderCooked = (OrderCooked) m;
            return singletonList(createToThePayment(orderCooked));
        } else if (m instanceof OrderPriced) {
            final OrderPriced orderPriced = (OrderPriced) m;
            return singletonList(createToThePayment(orderPriced));
        } else if (m instanceof CookingTimedOut) {
            return handleCookingTimeOutEvent((CookingTimedOut) m);
        } else if (m instanceof OrderPaid) {
            final OrderPaid orderPaid = (OrderPaid) m;
            setOrderToFinished(orderPaid);
            return singletonList(createWorkDone(orderPaid));
        }
        return Collections.emptyList();
    }

    private PublishAt createPublishAt(OrderPlaced orderPlaced) {
        final CookingTimedOut cookingTimedOut = new CookingTimedOut(orderPlaced.getOrder(), orderPlaced.getCorrelationUUID(), orderPlaced.getMessageUUID());
        return new PublishAt(cookingTimedOut, orderPlaced.getCorrelationUUID(), orderPlaced.getMessageUUID(), System.currentTimeMillis() + TIME_TO_PUBLISH);
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
                return Collections.emptyList();
            } else
                return singletonList(createCookFood(cookingTimedOut));
        }
    }


    private void setOrderToFinished(OrderPaid m) {
        m.getOrder().done();
    }

    private CookFood createCookFood(OrderPlaced orderPlaced) {
        return new CookFood(orderPlaced.getOrder(), orderPlaced.getCorrelationUUID(), orderPlaced.getMessageUUID());
    }

    private WorkDone createWorkDone(OrderPaid orderPlaced) {
        return new WorkDone(orderPlaced.getOrder(), orderPlaced.getCorrelationUUID(), orderPlaced.getMessageUUID());
    }

    private PriceOrder createToThePayment(OrderCooked orderCooked) {
        return new PriceOrder(orderCooked.getOrder(), orderCooked.getCorrelationUUID(), orderCooked.getMessageUUID());
    }

    private ToThePayment createToThePayment(OrderPriced orderPriced) {
        return new ToThePayment(orderPriced.getOrder(), orderPriced.getCorrelationUUID(), orderPriced.getMessageUUID());
    }

    private CookFood createCookFood(CookingTimedOut cookingTimedOut) {
        return new CookFood(cookingTimedOut.getOrder(), cookingTimedOut.getCorrelationUUID(), cookingTimedOut.getMessageUUID());
    }
}
