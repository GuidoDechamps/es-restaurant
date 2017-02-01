package be.cooking.generic;

import be.cooking.generic.messages.MessageBase;

//Also used for unit test?
public enum DevNullPublisher implements Publisher {
    INSTANCE;
    @Override
    public <T extends MessageBase> void publish(T message) {

    }
}
