package be.cooking.generic;

import be.cooking.generic.messages.MessageBase;

import java.util.*;

class EventMap {
    private final Map<String, List> topicEventSubscriptions = new HashMap<>();

    <T extends MessageBase> List<Handler<T>> getEventHandlers(T message) {
        final String typeName = getTypeName(message);
        return getHandlers(typeName);
    }

   <T extends MessageBase> List<Handler<T>> getEventHandlers(UUID correlationID) {
        final String typeName = correlationID.toString();
        return getHandlers(typeName);
    }

    <T extends MessageBase> void subscribe(Class<T> messageType, Handler<T> handler) {
        final String typeName = getTypeName(messageType);
        createTopic(typeName);
        subscribe(handler, typeName);
    }

    <T extends MessageBase> void subscribe(UUID correlationId, Handler<T> handler) {
        final String typeName = correlationId.toString();
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
