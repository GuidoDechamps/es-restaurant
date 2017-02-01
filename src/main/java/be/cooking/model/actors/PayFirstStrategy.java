package be.cooking.model.actors;

import be.cooking.generic.messages.MessageBase;
import be.cooking.model.messages.*;

import java.util.Optional;

import static java.util.Optional.of;

class PayFirstStrategy implements MidgetStrategy {

    public Optional<MessageBase> handleEvent(MessageBase m) {
        if (m instanceof OrderPlaced) {
            final OrderPlaced orderPlaced = (OrderPlaced) m;
            return of(createToThePricing(orderPlaced));
        } else if (m instanceof OrderPriced) {
            final OrderPriced orderPriced = (OrderPriced) m;
            return of(createToThePayment(orderPriced));
        } else if (m instanceof OrderPaid) {
            final OrderPaid orderPriced = (OrderPaid) m;
            return of(createCookFood(orderPriced));
        } else if (m instanceof OrderCooked) {
            final OrderCooked orderCooked = (OrderCooked) m;
            setOrderToFinished(orderCooked);//TODO must be elsewhere
            return of(createWorkDone(orderCooked));
        }
        return Optional.empty();

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

    private PriceOrder createToThePricing(OrderPlaced orderPlaced) {
        return new PriceOrder(orderPlaced.getOrder(), orderPlaced.getCorrelationUUID(), orderPlaced.getMessageUUID());
    }

    private ToThePayment createToThePayment(OrderPriced orderPriced) {
        return new ToThePayment(orderPriced.getOrder(), orderPriced.getCorrelationUUID(), orderPriced.getMessageUUID());
    }
}
