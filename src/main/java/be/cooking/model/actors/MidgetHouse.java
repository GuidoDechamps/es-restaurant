package be.cooking.model.actors;

import be.cooking.generic.Handler;
import be.cooking.generic.ThreadedHandler;
import be.cooking.generic.Topic;
import be.cooking.generic.messages.MessageBase;
import be.cooking.model.Order;
import be.cooking.model.Repository;
import be.cooking.model.messages.OrderPlaced;
import be.cooking.model.messages.WorkDone;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MidgetHouse {

    private final Map<UUID, OrderStatus> orderStates = new HashMap<>();
    private final Map<UUID, ThreadedHandler<MessageBase>> processHandlers = new HashMap<>();
    private final Topic topic;
    private final MidgetFactory midgetFactory;
    private final Repository<Order> repository;


    public MidgetHouse(Topic topic, Repository<Order> repository) {
        this.topic = topic;
        this.midgetFactory = new MidgetFactory(topic);
        this.repository = repository;
    }


    public class OrderPlaceHandler implements Handler<OrderPlaced> {
        @Override
        public void handle(OrderPlaced orderPlaced) {
            System.out.println("Work started for order " + orderPlaced.getCorrelationUUID());
            registerOrderState(orderPlaced);
            createProcessHandlerForCorrelationUUID(orderPlaced);
        }

        private void createProcessHandlerForCorrelationUUID(OrderPlaced orderPlaced) {
            final ThreadedHandler<MessageBase> messageBaseThreadedHandler = new ThreadedHandler<>("MidgetHouse " + orderPlaced.getCorrelationUUID(), new MessageBaseHandler());
            processHandlers.put(orderPlaced.getCorrelationUUID(), messageBaseThreadedHandler);

            topic.subscribe(orderPlaced.getCorrelationUUID(), messageBaseThreadedHandler);
            messageBaseThreadedHandler.start();
        }


        private void registerOrderState(OrderPlaced orderPlaced) {
            orderStates.put(orderPlaced.getCorrelationUUID(), OrderStatus.ORDER_PLACED);
        }


    }

    public class MessageBaseHandler implements Handler<MessageBase> {
        @Override
        public void handle(MessageBase value) {
            final List<MessageBase> history = topic.getHistory(value.getCorrelationUUID());
            final Midget midget = midgetFactory.createMidget(value, history);
            midget.handle(value);
        }
    }

    public class WorkDoneHandler implements Handler<WorkDone> {
        @Override
        public void handle(WorkDone value) {
            repository.save(value.getContent());
            System.out.println("Work Done for order" + value.getCorrelationUUID());
            final UUID correlationUUID = value.getCorrelationUUID();
            removeHandler(correlationUUID);
            removeWidget(correlationUUID);
        }

        private void removeWidget(UUID correlationUUID) {
            checkMidgetExists(correlationUUID);
            orderStates.remove(correlationUUID);

        }

        private void removeHandler(UUID correlationUUID) {
            checkThreadHandlerExists(correlationUUID);
            final ThreadedHandler<MessageBase> threadedHandler = processHandlers.remove(correlationUUID);
            threadedHandler.stop();
        }

        private void checkMidgetExists(UUID correlationId) {
            if (!orderStates.containsKey(correlationId))
                throw new RuntimeException("Cannot clean midget because midget is not found");
        }

        private void checkThreadHandlerExists(UUID correlationId) {
            if (!processHandlers.containsKey(correlationId))
                throw new RuntimeException("Cannot clean processHandler because handler is not found");
        }
    }
}
