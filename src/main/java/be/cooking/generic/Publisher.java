package be.cooking.generic;

import be.cooking.generic.messages.MessageBase;

import java.util.UUID;

public interface Publisher {

    <T extends MessageBase> void publish(T message);
}
