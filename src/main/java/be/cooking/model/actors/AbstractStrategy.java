package be.cooking.model.actors;

import be.cooking.generic.messages.MessageBase;
import be.cooking.model.Order;
import be.cooking.model.messages.*;

import java.util.Arrays;
import java.util.List;

import static java.util.Collections.singletonList;

abstract class AbstractStrategy implements MidgetStrategy {

    static final int TIME_TO_PUBLISH = 2500;
    static final int ORDER_RETRIES = 3;

    void setOrderToFinished(OrderPaid m) {
        setOrderToFinished(m.getOrder());
    }

    void setOrderToFinished(Order m) {
        m.done();
    }

    CookFood createCookFood(OrderPlaced orderPlaced) {
        return new CookFood(orderPlaced.getOrder(), orderPlaced.getCorrelationUUID(), orderPlaced.getMessageUUID());
    }

    WorkDone createWorkDone(OrderPaid orderPlaced) {
        return new WorkDone(orderPlaced.getOrder(), orderPlaced.getCorrelationUUID(), orderPlaced.getMessageUUID());
    }

    List<MessageBase> handleToManyRetries(CookingTimedOut cookingTimedOut) {
        cookingTimedOut.getOrder().drop();
        return singletonList(createWorkDone(cookingTimedOut));
    }

    WorkDone createWorkDone(CookingTimedOut cookingTimedOut) {
        return new WorkDone(cookingTimedOut.getOrder(), cookingTimedOut.getCorrelationUUID(), cookingTimedOut.getMessageUUID());
    }

    PriceOrder createToThePayment(OrderCooked orderCooked) {
        return new PriceOrder(orderCooked.getOrder(), orderCooked.getCorrelationUUID(), orderCooked.getMessageUUID());
    }


    CookFood createCookFood(CookingTimedOut cookingTimedOut) {
        return new CookFood(cookingTimedOut.getOrder(), cookingTimedOut.getCorrelationUUID(), cookingTimedOut.getMessageUUID());
    }

    PublishAt createPublishAt(OrderPlaced orderPlaced) {
        final CookingTimedOut cookingTimedOut = new CookingTimedOut(orderPlaced.getOrder(), orderPlaced.getCorrelationUUID(), orderPlaced.getMessageUUID());
        return new PublishAt(cookingTimedOut, orderPlaced.getCorrelationUUID(), orderPlaced.getMessageUUID(), System.currentTimeMillis() + TIME_TO_PUBLISH);
    }

    PublishAt createPublishAt(CookingTimedOut timedOut) {
        final CookingTimedOut cookingTimedOut = new CookingTimedOut(timedOut.getOrder(), timedOut.getCorrelationUUID(), timedOut.getMessageUUID());
        return new PublishAt(cookingTimedOut, timedOut.getCorrelationUUID(), timedOut.getMessageUUID(), System.currentTimeMillis() + TIME_TO_PUBLISH);
    }

    List<MessageBase> createCookFoodCommands(CookingTimedOut orderPlaced) {
        return Arrays.asList(createCookFood(orderPlaced), createPublishAt(orderPlaced));
    }

    List<MessageBase> createCookFoodCommands(OrderPlaced orderPlaced) {
        return Arrays.asList(createCookFood(orderPlaced), createPublishAt(orderPlaced));
    }

//    PublishAt createPublishAt(OrderPlaced orderPlaced) {
//        final CookingTimedOut cookingTimedOut = new CookingTimedOut(orderPlaced.getOrder(), orderPlaced.getCorrelationUUID(), orderPlaced.getMessageUUID());
//        return new PublishAt(cookingTimedOut, orderPlaced.getCorrelationUUID(), orderPlaced.getMessageUUID(), System.currentTimeMillis() + TIME_TO_PUBLISH);
//    }

    WorkDone createWorkDone(OrderCooked orderCooked) {
        return new WorkDone(orderCooked.getOrder(), orderCooked.getCorrelationUUID(), orderCooked.getMessageUUID());
    }

    void setOrderToFinished(OrderCooked m) {
        m.getOrder().done();
    }

    CookFood createCookFood(OrderPaid orderPaid) {
        return new CookFood(orderPaid.getOrder(), orderPaid.getCorrelationUUID(), orderPaid.getMessageUUID());
    }

//    CookFood createCookFood(CookingTimedOut cookingTimedOut) {
//        return new CookFood(cookingTimedOut.getOrder(), cookingTimedOut.getCorrelationUUID(), cookingTimedOut.getMessageUUID());
//    }

    PriceOrder createToThePricing(OrderPlaced orderPlaced) {
        return new PriceOrder(orderPlaced.getOrder(), orderPlaced.getCorrelationUUID(), orderPlaced.getMessageUUID());
    }

    ToThePayment createToThePayment(OrderPriced orderPriced) {
        return new ToThePayment(orderPriced.getOrder(), orderPriced.getCorrelationUUID(), orderPriced.getMessageUUID());
    }


}
