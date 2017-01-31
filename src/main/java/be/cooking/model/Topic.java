package be.cooking.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Topic implements Publisher {

    private final Map<String, List<HandleOrder>> topicSubscriptions = new HashMap<>();

    @Override
    public void publish(String topic, Order order) {
        final List<HandleOrder> handleOrders = topicSubscriptions.get(topic);
        handleOrders.forEach(x -> x.handle(order));
    }

    public void subscribe(String topic, HandleOrder orderhandler) {
        createTopic(topic);
        topicSubscriptions.get(topic).add(orderhandler);
    }

    private void createTopic(String topic) {
        if (!topicSubscriptions.containsKey(topic))
            topicSubscriptions.put(topic, new ArrayList<>());
    }
}
