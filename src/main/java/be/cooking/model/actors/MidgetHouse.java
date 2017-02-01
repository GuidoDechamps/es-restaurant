package be.cooking.model.actors;

import be.cooking.generic.DevNullPublisher;
import be.cooking.generic.Handler;
import be.cooking.generic.ThreadedHandler;
import be.cooking.generic.Topic;
import be.cooking.generic.messages.MessageBase;
import be.cooking.model.Order;
import be.cooking.model.messages.OrderPlaced;
import be.cooking.model.messages.WorkDone;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MidgetHouse {

    private final Map<UUID, MidgetStrategy> midgetStrategy = new HashMap<>();
    private final Map<UUID, ThreadedHandler<MessageBase>> processHandlers = new HashMap<>();
    private final Topic topic;
    private final MidgetFactory midgetFactory;
    private final MidgetStrategy payLastStrategy = new PayLastStrategy();
    private final MidgetStrategy payFirstStrategy = new PayFirstStrategy();


    public MidgetHouse(Topic topic) {
        this.topic = topic;
        this.midgetFactory = new MidgetFactory(topic);
    }

//
//    private void checkOrderShouldNotAlreadyExists(OrderPlaced orderPlaced) {
//        if (midgets.containsKey(orderPlaced.getCorrelationUUID()))
//            throw new RuntimeException("CorrelationUUid already exists");
//    }


    public class OrderPlaceHandler implements Handler<OrderPlaced> {
        @Override
        public void handle(OrderPlaced orderPlaced) {
//            checkOrderShouldNotAlreadyExists(orderPlaced);
            registerStrategy(orderPlaced);
            createProcessHandlerForCorrelationUUID(orderPlaced);
        }

        private void createProcessHandlerForCorrelationUUID(OrderPlaced orderPlaced) {
            final ThreadedHandler<MessageBase> messageBaseThreadedHandler = new ThreadedHandler<>("MidgetHouse " + orderPlaced.getCorrelationUUID(), new MessageBaseHandler());
            processHandlers.put(orderPlaced.getCorrelationUUID(), messageBaseThreadedHandler);

            topic.subscribe(orderPlaced.getCorrelationUUID(), messageBaseThreadedHandler);
            messageBaseThreadedHandler.start();
        }

        private MidgetStrategy determineStrategy(Order order) {
            if (order.isDodgyCustomer())
                return payFirstStrategy;
            else
                return payLastStrategy;
        }

        private void registerStrategy(OrderPlaced orderPlaced) {
            final MidgetStrategy midget = determineStrategy(orderPlaced.getOrder());
            midgetStrategy.put(orderPlaced.getCorrelationUUID(), midget);
        }


    }

    public class MessageBaseHandler implements Handler<MessageBase> {
        @Override
        public void handle(MessageBase value) {
            final Midget midget = new Midget(DevNullPublisher.INSTANCE, midgetStrategy.get(value.getCorrelationUUID()));
            final List<MessageBase> history = topic.getHistory(value.getCorrelationUUID());
            history.forEach(midget::handle);
            midget.setPublisher(topic);
            midget.handle(value);
        }
    }

    public class WorkDoneHandler implements Handler<WorkDone> {
        @Override
        public void handle(WorkDone value) {
            final UUID correlationUUID = value.getCorrelationUUID();
            removeHandler(correlationUUID);
            removeWidget(correlationUUID);
        }

        private void removeWidget(UUID correlationUUID) {
            checkMidgetExists(correlationUUID);
            midgetStrategy.remove(correlationUUID);
        }

        private void removeHandler(UUID correlationUUID) {
            checkThreadHandlerExists(correlationUUID);
            final ThreadedHandler<MessageBase> threadedHandler = processHandlers.remove(correlationUUID);
            threadedHandler.stop();
        }

        private void checkMidgetExists(UUID correlationId) {
            if (!midgetStrategy.containsKey(correlationId))
                throw new RuntimeException("Cannot clean midget because midget is not found");
        }

        private void checkThreadHandlerExists(UUID correlationId) {
            if (!processHandlers.containsKey(correlationId))
                throw new RuntimeException("Cannot clean processHandler because handler is not found");
        }
    }
}
