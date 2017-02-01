package be.cooking.model.actors;

import be.cooking.generic.Handler;
import be.cooking.generic.Publisher;
import be.cooking.generic.messages.MessageBase;

import java.util.List;

public class Midget implements Handler<MessageBase> {

    private final Publisher publisher;
    private final MidgetStrategy midgetStrategy;

    public Midget(Publisher publisher,  MidgetStrategy midgetStrategy) {
        this.publisher = publisher;
        this.midgetStrategy = midgetStrategy;
    }

    public void handle(MessageBase m) {
        final List<MessageBase> command = midgetStrategy.handleEvent(m);
        command.forEach(publisher::publish);
    }

}
