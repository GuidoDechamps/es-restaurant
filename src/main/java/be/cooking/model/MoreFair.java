package be.cooking.model;

import be.cooking.model.messages.MessageBase;

import java.util.LinkedList;
import java.util.Queue;

import static be.cooking.Sleep.sleep;

public class MoreFair<T extends MessageBase> implements Handler<T> {
    private static final int SLEEP_TIME = 1000;
    private static final int MAX_QUEUE_SIZE = 5;
    private final Queue<ThreadedHandler> handlers;

    private MoreFair(Builder builder) {
        handlers = builder.handlers;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public void handle(T message) {
        while (true) {
            for (int i = 0; i < handlers.size(); i++)
                if (handleSingle(message))
                    return;
            sleep(SLEEP_TIME);
        }
    }

    private boolean handleSingle(T message) {
        final ThreadedHandler peek = handlers.peek();
        if (peek.size() < MAX_QUEUE_SIZE) {
            return handleMessage(message, peek);
        } else {
            moveFirstToLastPlaceInQueue();
            return false;
        }
    }

    private boolean handleMessage(T message, ThreadedHandler handler) {
        handler.handle(message);
        moveFirstToLastPlaceInQueue();
        return true;
    }

    private void moveFirstToLastPlaceInQueue() {
        handlers.add(handlers.remove());
    }


    public static final class Builder {
        private Queue<ThreadedHandler> handlers = new LinkedList<>();

        private Builder() {
        }

        public Builder withHandler(ThreadedHandler val) {
            handlers.add(val);
            return this;
        }

        public MoreFair build() {
            return new MoreFair(this);
        }
    }
}
