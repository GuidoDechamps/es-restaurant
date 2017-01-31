package be.cooking.model;

import be.cooking.model.messages.MessageBase;

public interface Publisher {

    <T extends MessageBase> void publish(T message);
}
