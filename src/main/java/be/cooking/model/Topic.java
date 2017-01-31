package be.cooking.model;

import be.cooking.model.messages.MessageBase;

import java.util.List;

public class Topic implements Publisher {

    private final EventMap eventMap = new EventMap();

    @Override
    public <T extends MessageBase> void publish(T message) {
        final List<Handler<T>> eventHandlers = eventMap.getEventHandlers(message);
        eventHandlers.forEach(x -> x.handle(message));
    }

    public <T extends MessageBase> void subscribe(Class<T> messageType, Handler<T> handler) {
        eventMap.subscribe(messageType, handler);
    }


}
