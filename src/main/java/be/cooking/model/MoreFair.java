package be.cooking.model;

import be.cooking.Sleep;

import java.util.LinkedList;
import java.util.Queue;

public class MoreFair implements HandleOrder {
    private final Queue<ThreadedHandler> handlers;

    private MoreFair(Builder builder) {
        handlers = builder.handlers;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public void handle(Order order) {

        while (true) {

            for (ThreadedHandler handler : handlers) {
                if (handleSingle(order))
                    return;
            }

            Sleep.sleep(1000);
        }
    }

    private boolean handleSingle(Order order) {
        ThreadedHandler peek = handlers.peek();
        if (peek.size() < 5) {
            peek.handle(order);
            handlers.add(handlers.remove());
            return true;
        }
        return false;
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
