package be.cooking.generic;

import be.cooking.generic.messages.MessageBase;

public interface Publisher {

    <T extends MessageBase> void publish(T message);
}
