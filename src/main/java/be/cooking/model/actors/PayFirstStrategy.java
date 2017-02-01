package be.cooking.model.actors;

import be.cooking.generic.messages.MessageBase;
import be.cooking.model.Order;
import be.cooking.model.messages.*;

import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

class PayFirstStrategy implements MidgetStrategy {

    private static final int TIME_TO_PUBLISH = 20;
    private static final int ORDER_RETRIES = 3;

    public List<MessageBase> handleEvent(MessageBase m) {
        if (m instanceof OrderPlaced) {
            final OrderPlaced orderPlaced = (OrderPlaced) m;
            return asList(createToThePricing(orderPlaced), createPublishAt(orderPlaced));
        } else if (m instanceof OrderPriced) {
            final OrderPriced orderPriced = (OrderPriced) m;
            return singletonList(createToThePayment(orderPriced));
        } else if (m instanceof OrderPaid) {
            final OrderPaid orderPriced = (OrderPaid) m;
            return handleOrderPaid(createCookFood(orderPriced));
        } else if (m instanceof CookingTimedOut) {
            return handleCookingTimeOutEvent((CookingTimedOut) m);
        } else if (m instanceof OrderCooked) {
            return handleOrderCooked((OrderCooked) m);
        }
        return Collections.emptyList();

    }

    private List<MessageBase> handleOrderPaid(CookFood cookFood) {
        return singletonList(cookFood);
    }

    private List<MessageBase> handleOrderCooked(OrderCooked orderCooked) {
        setOrderToFinished(orderCooked);
        return singletonList(createWorkDone(orderCooked));
    }

    private List<MessageBase> handleCookingTimeOutEvent(CookingTimedOut cookingTimedOut) {
        final Order order = cookingTimedOut.getOrder();
        if (order.getStatus() != Order.Status.COOKED) {
            System.out.println("Order " + order.getOrderUUID() + " already cooked " + order.getStatus());
            return Collections.emptyList();
        } else {
            System.out.println("Order " + order.getOrderUUID() + " not cooked on time. Retry");
            order.increaseTries();
            if (order.getNrOfTries() > ORDER_RETRIES) {
                System.out.println("To many retries " + order.getOrderUUID());
                return Collections.emptyList();
            } else
                return singletonList(createCookFood(cookingTimedOut));
        }
    }

    private PublishAt createPublishAt(OrderPlaced orderPlaced) {
        final CookingTimedOut cookingTimedOut = new CookingTimedOut(orderPlaced.getOrder(), orderPlaced.getCorrelationUUID(), orderPlaced.getMessageUUID());
        return new PublishAt(cookingTimedOut, orderPlaced.getCorrelationUUID(), orderPlaced.getMessageUUID(), System.currentTimeMillis() + TIME_TO_PUBLISH);
    }

    private WorkDone createWorkDone(OrderCooked orderCooked) {
        return new WorkDone(orderCooked.getOrder(), orderCooked.getCorrelationUUID(), orderCooked.getMessageUUID());
    }

    private void setOrderToFinished(OrderCooked m) {
        m.getOrder().done();
    }

    private CookFood createCookFood(OrderPaid orderPaid) {
        return new CookFood(orderPaid.getOrder(), orderPaid.getCorrelationUUID(), orderPaid.getMessageUUID());
    }

    private CookFood createCookFood(CookingTimedOut cookingTimedOut) {
        return new CookFood(cookingTimedOut.getOrder(), cookingTimedOut.getCorrelationUUID(), cookingTimedOut.getMessageUUID());
    }

    private PriceOrder createToThePricing(OrderPlaced orderPlaced) {
        return new PriceOrder(orderPlaced.getOrder(), orderPlaced.getCorrelationUUID(), orderPlaced.getMessageUUID());
    }

    private ToThePayment createToThePayment(OrderPriced orderPriced) {
        return new ToThePayment(orderPriced.getOrder(), orderPriced.getCorrelationUUID(), orderPriced.getMessageUUID());
    }
}
