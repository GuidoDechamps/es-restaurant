package be.cooking.model;

import be.cooking.model.messages.MessageBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class EventMap {
    private final Map<String, List> topicEventSubscriptions = new HashMap<>();

    <T extends MessageBase> List<Handler<T>> getEventHandlers(T message) {
        final String typeName = getTypeName(message);
        return getHandlers(typeName);
    }

    <T extends MessageBase> void subscribe(Class<T> messageType, Handler<T> handler) {
        final String typeName = getTypeName(messageType);
        createTopic(typeName);
        subscribe(handler, typeName);
    }

    @SuppressWarnings("unchecked")
    private <T extends MessageBase> List<Handler<T>> getHandlers(String typeName) {
        return (List<Handler<T>>) topicEventSubscriptions.get(typeName);
    }

    private <T extends MessageBase> String getTypeName(T message) {
        final Class<? extends MessageBase> aClass = message.getClass();
        return getTypeName(aClass);
    }

    private String getTypeName(Class<? extends MessageBase> aClass) {
        return aClass.getCanonicalName();
    }

    private void createTopic(String typeName) {
        if (!topicEventSubscriptions.containsKey(typeName))
            topicEventSubscriptions.put(typeName, new ArrayList<>());
    }

    @SuppressWarnings("unchecked")
    private <T extends MessageBase> void subscribe(Handler<T> handler, String typeName) {
        topicEventSubscriptions.get(typeName).add(handler);
    }
}
