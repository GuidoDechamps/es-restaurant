package be.cooking.model.actors;

import be.cooking.generic.messages.MessageBase;
import be.cooking.model.messages.*;

import java.util.Optional;

import static java.util.Optional.of;

class PayLastStrategy implements MidgetStrategy {

    public Optional<MessageBase> handleEvent(MessageBase m) {
        if (m instanceof OrderPlaced) {
            final OrderPlaced orderPlaced = (OrderPlaced) m;
            return of(createCookFood(orderPlaced));
        } else if (m instanceof OrderCooked) {
            final OrderCooked orderCooked = (OrderCooked) m;
            return of(createToThePayment(orderCooked));
        } else if (m instanceof OrderPriced) {
            final OrderPriced orderPriced = (OrderPriced) m;
            return of(createToThePayment(orderPriced));
        } else if (m instanceof OrderPaid) {
            setOrderToFinished((OrderPaid) m);
        }
        return Optional.empty();
    }

    private void setOrderToFinished(OrderPaid m) {
        m.getOrder().done();
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
