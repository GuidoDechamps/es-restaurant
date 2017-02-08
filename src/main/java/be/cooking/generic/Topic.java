package be.cooking.generic;

import be.cooking.generic.messages.MessageBase;

import java.util.List;
import java.util.UUID;

public class Topic implements Publisher {

    private final EventMap eventMap = new EventMap();

    @Override
    public <T extends MessageBase> void publish(T message) {
        System.out.println("Publishing " + message.getClass().getSimpleName() + " for cor " + message.getCorrelationUUID());
        eventMap.addEvent(message);

        final List<Handler<T>> eventHandlers = eventMap.getEventHandlers(message);
        eventHandlers.forEach(x -> x.handle(message));

        final List<Handler<T>> eventHandlersByCorrelationId = eventMap.getEventHandlers(message.getCorrelationUUID());
        eventHandlersByCorrelationId.forEach(x -> x.handle(message));
    }


    public <T extends MessageBase> void subscribe(Class<T> messageType, Handler<T> handler) {
        eventMap.subscribe(messageType, handler);
    }

    public <T extends MessageBase> void subscribe(UUID correlationId, Handler<T> handler) {
        eventMap.subscribe(correlationId, handler);
    }


    public List<MessageBase> getHistory(UUID correlationUUID) {
        return eventMap.getHistory(correlationUUID);
    }
}
