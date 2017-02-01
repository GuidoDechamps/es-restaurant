package be.cooking.model.actors;

import be.cooking.generic.Handler;
import be.cooking.generic.ThreadedHandler;
import be.cooking.generic.Topic;
import be.cooking.generic.messages.MessageBase;
import be.cooking.model.messages.OrderPlaced;
import be.cooking.model.messages.WorkDone;

import java.util.*;

public class MidgetHouse {

    private final Map<UUID, Midget> midgets = new HashMap<>();
    private final List<UUID> processedEvents = new ArrayList<>();
    private final Map<UUID, ThreadedHandler<MessageBase>> processHandlers = new HashMap<>();
    private final Topic topic;
    private final MidgetFactory midgetFactory;


    public MidgetHouse(Topic topic) {
        this.topic = topic;
        this.midgetFactory = new MidgetFactory(topic);
    }

    private boolean isUniqueEvent(MessageBase event) {
        if (processedEvents.contains(event))
            return false;
        else {
            processedEvents.add(event.getMessageUUID());
            return true;
        }
    }

    private void checkOrderShouldNotAlreadyExists(OrderPlaced orderPlaced) {
        if (midgets.containsKey(orderPlaced.getCorrelationUUID()))
            throw new RuntimeException("CorrelationUUid already exists");
    }


    public class OrderPlaceHandler implements Handler<OrderPlaced> {
        @Override
        public void handle(OrderPlaced orderPlaced) {
            if (isUniqueEvent(orderPlaced)) {
                checkOrderShouldNotAlreadyExists(orderPlaced);
                createMidget(orderPlaced);
                createProcessHandlerForCorrelationUUID(orderPlaced);
            }
        }

        private void createProcessHandlerForCorrelationUUID(OrderPlaced orderPlaced) {
            final ThreadedHandler<MessageBase> messageBaseThreadedHandler = new ThreadedHandler<>("MidgetHouse " + orderPlaced.getCorrelationUUID(), new MessageBaseHandler());
            processHandlers.put(orderPlaced.getCorrelationUUID(), messageBaseThreadedHandler);

            topic.subscribe(orderPlaced.getCorrelationUUID(), messageBaseThreadedHandler);
            messageBaseThreadedHandler.start();
        }

        private void createMidget(OrderPlaced orderPlaced) {
            final Midget midget = midgetFactory.createMidget(orderPlaced.getOrder());
            midgets.put(orderPlaced.getCorrelationUUID(), midget);
        }


    }

    public class MessageBaseHandler implements Handler<MessageBase> {
        @Override
        public void handle(MessageBase value) {
            if (isUniqueEvent(value)) {
                final Midget midget = midgets.get(value.getCorrelationUUID());
                if (midget != null) {
                    midget.handle(value);
                }
            }
        }
    }

    public class WorkDoneHandler implements Handler<WorkDone> {
        @Override
        public void handle(WorkDone value) {
            if (isUniqueEvent(value)) {
                final UUID correlationUUID = value.getCorrelationUUID();
                removeHandler(correlationUUID);
                removeWidget(correlationUUID);
            }

        }

        private void removeWidget(UUID correlationUUID) {
            checkMidgetExists(correlationUUID);
            midgets.remove(correlationUUID);
        }

        private void removeHandler(UUID correlationUUID) {
            checkThreadHandlerExists(correlationUUID);
            final ThreadedHandler<MessageBase> threadedHandler = processHandlers.remove(correlationUUID);
            threadedHandler.stop();
        }

        private void checkMidgetExists(UUID correlationId) {
            if (!midgets.containsKey(correlationId))
                throw new RuntimeException("Cannot clean midget because midget is not found");
        }

        private void checkThreadHandlerExists(UUID correlationId) {
            if (!processHandlers.containsKey(correlationId))
                throw new RuntimeException("Cannot clean processHandler because handler is not found");
        }
    }
}
