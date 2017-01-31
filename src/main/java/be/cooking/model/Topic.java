package be.cooking.model;

import be.cooking.model.messages.MessageBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Topic implements Publisher {

    private final Map<String, List<HandleOrder>> topicSubscriptions = new HashMap<>();
    private final EventMap eventMap = new EventMap();


    @Override
    public void publish(String topic, Order order) {
        final List<HandleOrder> handleOrders = topicSubscriptions.get(topic);
        handleOrders.forEach(x -> x.handle(order));
    }

    @Override
    public <T extends MessageBase> void publish(T message) {
        final List<Handler<T>> eventHandlers = eventMap.getEventHandlers(message);
        eventHandlers.forEach(x -> x.handle(message));
    }

    public <T extends MessageBase> void subscribe(Class<T> messageType, Handler<T> handler) {
        eventMap.subscribe(messageType, handler);
    }

    private void createTopic(String topic) {
        if (!topicSubscriptions.containsKey(topic))
            topicSubscriptions.put(topic, new ArrayList<>());
    }


}
