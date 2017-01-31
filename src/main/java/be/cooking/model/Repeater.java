package be.cooking.model;

import java.util.ArrayList;
import java.util.List;

public class Repeater implements HandleOrder {
    private final List<HandleOrder> handlers;

    private Repeater(Builder builder) {
        handlers = builder.handlers;
    }

    public static Builder newBuilder() {
        return new Builder();
    }


    @Override
    public void handle(Order order) {
        handlers.forEach(x -> x.handle(order));
    }

    public static final class Builder {
        private List<HandleOrder> handlers = new ArrayList<>();

        private Builder() {
        }

        public Builder withHandler(HandleOrder val) {
            handlers.add(val);
            return this;
        }

        public Repeater build() {
            return new Repeater(this);
        }
    }
}
