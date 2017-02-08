package be.cooking.model.actors;

import be.cooking.Sleep;
import be.cooking.generic.Handler;
import be.cooking.generic.Publisher;
import be.cooking.generic.Startable;
import be.cooking.model.messages.PublishAt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class AlarmClock implements Handler<PublishAt>, Startable {

    private final Publisher publisher;
    private final List<PublishAt> toBeNotified = Collections.synchronizedList(new ArrayList());
    private boolean keepRunning = true;
    private final Thread thread = new Thread(this::loopNotifications);

    public AlarmClock(Publisher next) {
        this.publisher = next;
    }

    @Override
    public void start() {
        thread.start();
    }

    @Override
    public void stop() {
        keepRunning = false;
    }

    @Override
    public void handle(PublishAt command) {
        toBeNotified.add(command);
    }

    private void loopNotifications() {
        System.out.println("Started AlarmClock thread " + this);
        while (keepRunning) {
            tryNotify();
            Sleep.sleep(10);
        }
        System.out.println("AlarmClock Thread " + this + " is stopped.");
    }

    private void tryNotify() {
        final List<PublishAt> expiredMessages = getExpiredMessages();
        print(expiredMessages);
        if (expiredMessages.size() > 0)
            System.out.println("AlarmClock nr of expired messages " + expiredMessages.size());
        expiredMessages.stream()
                .map(PublishAt::getMessageToPublish)
                .forEach(publisher::publish);
        toBeNotified.removeAll(expiredMessages);
    }

    private List<PublishAt> getExpiredMessages() {

        final List<PublishAt> publishAts = new ArrayList<>();
        publishAts.addAll(toBeNotified);
        return publishAts.stream()
                .filter(x -> x.getTimeToPublish() < System.currentTimeMillis())
                .collect(Collectors.toList());
    }

    private void print(List<PublishAt> publishAts) {
        publishAts.forEach(x -> {
            System.out.println("------------");
            System.out.println("Event[" + x.getMessageUUID() + "]");
            System.out.println("TimeToPublish[" + x.getTimeToPublish() + "]");
            System.out.println("In the past[" + (x.getTimeToPublish() < System.currentTimeMillis()) + "]");
        });
    }


}
