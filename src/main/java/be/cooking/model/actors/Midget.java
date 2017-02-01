package be.cooking.model.actors;

import be.cooking.generic.Handler;
import be.cooking.generic.Publisher;
import be.cooking.generic.messages.MessageBase;

import java.util.List;

public class Midget implements Handler<MessageBase> {

    private final MidgetStrategy midgetStrategy;
    private Publisher publisher;

    public Midget(Publisher publisher, MidgetStrategy midgetStrategy) {
        this.publisher = publisher;
        this.midgetStrategy = midgetStrategy;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public void handle(MessageBase m) {
        final List<MessageBase> command = midgetStrategy.handleEvent(m);
        command.forEach(publisher::publish);
    }

}
