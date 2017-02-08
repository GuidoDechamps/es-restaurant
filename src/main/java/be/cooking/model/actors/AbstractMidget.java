package be.cooking.model.actors;

import be.cooking.generic.Publisher;
import be.cooking.generic.messages.MessageBase;
import be.cooking.model.Order;
import be.cooking.model.messages.*;

import java.util.*;

import static java.util.Collections.singletonList;

abstract class AbstractMidget implements Midget {

    static final int TIME_TO_PUBLISH = 2500;
    static final int ORDER_RETRIES = 3;
    private final Map<UUID, OrderStatus> orderStates = new HashMap<>();
    private final Publisher publisher;

    AbstractMidget(Publisher publisher, List<MessageBase> historyEvents) {
        this.publisher = publisher;
        historyEvents.forEach(this::handleEvent);
    }

    AbstractMidget(Publisher publisher) {
        this(publisher, Collections.emptyList());
    }

    public void handle(MessageBase m) {
        logMessage("handling " + m.getClass().getSimpleName(), m);
        final List<MessageBase> command = handleEvent(m);
        command.forEach(publisher::publish);
    }

    void logMessage(String logMessage, MessageBase eventMessage) {
        System.out.println(this.getClass().getSimpleName() + " " + logMessage + " " + eventMessage.getCorrelationUUID());
    }


    abstract List<MessageBase> handleEvent(MessageBase m);


    void registerOrderAsFinished(OrderPaid m) {
        registerOrderAsFinished(m.getContent());
    }

    OrderStatus getOrderState(Order m) {
        return orderStates.get(m.getOrderUUID());
    }

    WorkDone createWorkDone(OrderPaid orderPlaced) {
        return new WorkDone(orderPlaced);
    }

    List<MessageBase> handleToManyRetries(CookingTimedOut cookingTimedOut) {
        dropOrder(cookingTimedOut.getContent());
        return singletonList(createWorkDone(cookingTimedOut));
    }

    PriceOrder createToThePayment(OrderCooked orderCooked) {
        return new PriceOrder(orderCooked);
    }

    CookFood createCookFood(CookingTimedOut cookingTimedOut) {
        return new CookFood(cookingTimedOut);
    }

    PublishAt createPublishAt(OrderPlaced orderPlaced) {
        final CookingTimedOut cookingTimedOut = new CookingTimedOut(orderPlaced);
        return new PublishAt(cookingTimedOut, orderPlaced.getCorrelationUUID(), orderPlaced.getMessageUUID(), System.currentTimeMillis() + TIME_TO_PUBLISH);
    }

    List<MessageBase> createCookFoodCommands(CookingTimedOut orderPlaced) {
        return Arrays.asList(createCookFood(orderPlaced), createPublishAt(orderPlaced));
    }

    List<MessageBase> createCookFoodCommands(OrderPlaced orderPlaced) {
        return Arrays.asList(createCookFood(orderPlaced), createPublishAt(orderPlaced));
    }

    void registerOrderAsFinished(OrderCooked m) {
        registerOrderAsFinished(m.getContent());
    }

    WorkDone createWorkDone(OrderCooked orderCooked) {
        return new WorkDone(orderCooked);
    }

    CookFood createCookFood(OrderPaid orderPaid) {
        return new CookFood(orderPaid);
    }

    PriceOrder createToThePricing(OrderPlaced orderPlaced) {
        return new PriceOrder(orderPlaced);
    }

    ToThePayment createToThePayment(OrderPriced orderPriced) {
        return new ToThePayment(orderPriced);
    }

    private CookFood createCookFood(OrderPlaced orderPlaced) {
        return new CookFood(orderPlaced);
    }

    private WorkDone createWorkDone(CookingTimedOut cookingTimedOut) {
        return new WorkDone(cookingTimedOut);
    }

    private PublishAt createPublishAt(CookingTimedOut timedOut) {
        final CookingTimedOut cookingTimedOut = new CookingTimedOut(timedOut);
        return new PublishAt(cookingTimedOut, timedOut.getCorrelationUUID(), timedOut.getMessageUUID(), System.currentTimeMillis() + TIME_TO_PUBLISH);
    }

    private void registerOrderAsFinished(Order m) {
        orderStates.put(m.getOrderUUID(), OrderStatus.DONE);
    }

    private void dropOrder(Order m) {
        orderStates.put(m.getOrderUUID(), OrderStatus.DROPPED);
    }


}
