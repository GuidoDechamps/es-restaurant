package be.cooking.generic;

import be.cooking.generic.messages.MessageBase;

import java.util.*;

class EventMap {
    private final Map<String, List> topicEventSubscriptions = new HashMap<>();
    private final Map<String, List<MessageBase>> history = new HashMap<>();

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
        final String corr = correlationId.toString();
        createTopic(corr);
        subscribe(handler, corr);
    }

    @SuppressWarnings("unchecked")
    public void addEvent(MessageBase messageBase) {
        final List<MessageBase> list = getEventStore(messageBase);
        list.add(messageBase);
    }

    private List<MessageBase> getEventStore(MessageBase messageBase) {
        final UUID correlationUUID = messageBase.getCorrelationUUID();
        return getEventStore(correlationUUID);
    }

    private List<MessageBase> getEventStore(UUID correlationUUID) {
        final String id = correlationUUID.toString();
        if (!history.containsKey(id)) {
            history.put(id, new ArrayList<>());
        }
        return history.get(id);
    }

    @SuppressWarnings("unchecked")
    private <T extends MessageBase> List<Handler<T>> getHandlers(String typeName) {
        if (topicEventSubscriptions.containsKey(typeName))
            return (List<Handler<T>>) topicEventSubscriptions.get(typeName);
        else
            return Collections.emptyList();
    }

    private <T extends MessageBase> String getTypeName(T message) {
        final Class<? extends MessageBase> aClass = message.getClass();
        return getTypeName(aClass);
    }

    private String getTypeName(Class<? extends MessageBase> aClass) {
        return aClass.getCanonicalName();
    }

    private void createTopic(String typeName) {
        if (!topicEventSubscriptions.containsKey(typeName)) {
            topicEventSubscriptions.put(typeName, new ArrayList<>());
        }
    }

    @SuppressWarnings("unchecked")
    private <T extends MessageBase> void subscribe(Handler<T> handler, String typeName) {
        topicEventSubscriptions.get(typeName).add(handler);
    }

    public List<MessageBase> getHistory(UUID correlationUUID) {
        final List<MessageBase> correlationHistory = new ArrayList<>();//TODO ugly as hell
        correlationHistory.addAll(this.getEventStore(correlationUUID));
        return Collections.unmodifiableList(correlationHistory);
    }
}
