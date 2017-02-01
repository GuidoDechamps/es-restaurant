package be.cooking.model.actors;

import be.cooking.generic.Handler;
import be.cooking.generic.Publisher;
import be.cooking.generic.messages.MessageBase;

import java.util.Optional;
import java.util.UUID;

public class Midget implements Handler<MessageBase> {

    private final Publisher publisher;
    private final MidgetStrategy midgetStrategy;
    private UUID correlationId;

    public Midget(Publisher publisher, UUID correlationId, MidgetStrategy midgetStrategy) {
        this.publisher = publisher;
        this.correlationId = correlationId;
        this.midgetStrategy = midgetStrategy;
    }

    public void handle(MessageBase m) {
        final Optional<MessageBase> command = midgetStrategy.handleEvent(m);
        command.ifPresent(publisher::publish);
    }

}
