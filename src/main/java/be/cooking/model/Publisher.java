package be.cooking.model;

import be.cooking.model.messages.MessageBase;

public interface Publisher {

    void publish(String topic, Order order);

    <T extends MessageBase> void publish(T message);
}
