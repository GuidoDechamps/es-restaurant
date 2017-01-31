package be.cooking.model;

import be.cooking.Sleep;

import java.util.LinkedList;
import java.util.List;

public class MoreFair implements HandleOrder {
    private final List<ThreadedHandler> handlers;

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
                if (handleSingle(order, handler)) return;
            }

            Sleep.sleep(1000);
        }
    }

    private boolean handleSingle(Order order, ThreadedHandler handler) {
        if (handler.size() < 5) {
            handler.handle(order);
            return true;
        }
        return false;
    }


    public static final class Builder {
        private List<ThreadedHandler> handlers = new LinkedList<>();

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
