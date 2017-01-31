package be.cooking.model;

import java.util.LinkedList;
import java.util.Queue;

import static be.cooking.Sleep.sleep;

public class MoreFair implements HandleOrder {
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
    public void handle(Order order) {
        while (true) {
            for (int i = 0; i < handlers.size(); i++)
                if (handleSingle(order))
                    return;
            sleep(SLEEP_TIME);
        }
    }

    private boolean handleSingle(Order order) {
        final ThreadedHandler peek = handlers.peek();
        if (peek.size() < MAX_QUEUE_SIZE) {
            return handleOrder(order, peek);
        } else {
            moveFirstToLastPlaceInQueue();
            return false;
        }
    }

    private boolean handleOrder(Order order, ThreadedHandler handler) {
        handler.handle(order);
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
