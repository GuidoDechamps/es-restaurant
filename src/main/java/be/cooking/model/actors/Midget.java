package be.cooking.model.actors;

import be.cooking.generic.Handler;
import be.cooking.generic.Publisher;
import be.cooking.generic.messages.MessageBase;
import be.cooking.model.messages.*;

import java.util.UUID;
import java.util.function.Consumer;

public class Midget implements Handler<MessageBase> {

    private final Publisher publisher;
    private final Consumer<Midget> midgetDone;
    private UUID correlationId;

    public Midget(Publisher publisher, UUID correlationId, Consumer<Midget> midgetDone) {
        this.publisher = publisher;
        this.correlationId = correlationId;
        this.midgetDone = midgetDone;
    }

    public void handle(MessageBase m) {
        if (m instanceof OrderPlaced) {
            final OrderPlaced orderPlaced = (OrderPlaced) m;
            publisher.publish(createCookFood(orderPlaced));
        } else if (m instanceof OrderCooked) {
            final OrderCooked orderCooked = (OrderCooked) m;
            publisher.publish(createToThePayment(orderCooked));
        } else if (m instanceof OrderPriced) {
            final OrderPriced orderPriced = (OrderPriced) m;
            publisher.publish(createToThePayment(orderPriced));
        } else if (m instanceof OrderPaid) {
            midgetDone.accept(this);
        } else
            System.out.println("Midget ignores " + m);
    }

    public UUID getCorrelationId() {
        return correlationId;
    }

    private CookFood createCookFood(OrderPlaced orderPlaced) {
        return new CookFood(orderPlaced.getOrder(), orderPlaced.getCorrelationUUID(), orderPlaced.getMessageUUID());
    }

    private PriceOrder createToThePayment(OrderCooked orderCooked) {
        return new PriceOrder(orderCooked.getOrder(), orderCooked.getCorrelationUUID(), orderCooked.getMessageUUID());
    }

    private ToThePayment createToThePayment(OrderPriced orderPriced) {
        return new ToThePayment(orderPriced.getOrder(), orderPriced.getCorrelationUUID(), orderPriced.getMessageUUID());
    }
}
