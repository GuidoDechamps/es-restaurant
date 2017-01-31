package be.cooking.model;

import java.util.LinkedList;
import java.util.Queue;

public class RoundRobin implements HandleOrder {
    private final Queue<HandleOrder> handlers;

    private RoundRobin(Builder builder) {
        handlers = builder.handlers;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public void handle(Order order) {
        handlers.peek().handle(order);
        handlers.add(handlers.remove());
    }

    public static final class Builder {
        private Queue<HandleOrder> handlers = new LinkedList<>();

        private Builder() {
        }

        public Builder withHandler(HandleOrder val) {
            handlers.add(val);
            return this;
        }

        public RoundRobin build() {
            return new RoundRobin(this);
        }
    }
}
