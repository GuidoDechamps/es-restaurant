package be.cooking.model;

import be.cooking.generic.Expirable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Order implements Expirable {
    private final UUID orderId;
    private int tableNumber;
    private List<ItemCode> items;
    private int subtotal;
    private int tax;
    private int total;
    private int cookTime;
    private String ingredients;
    private Status status;
    private long timestamp;
    private long timeToLive;
    private boolean isDodgyCustomer;

    private Order(Builder builder) {
        orderId = builder.orderId;
        tableNumber = builder.tableNumber;
        items = builder.items;
        subtotal = -1;
        tax = -1;
        total = -1;
        cookTime = -1;
        ingredients = "UNKNOWN";
        status = Status.IN_PROGRESS;
        timestamp = System.currentTimeMillis();
        timeToLive = builder.timeToLive;
        isDodgyCustomer = builder.isDodgyCustomer;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public boolean isExpired() {
        return System.currentTimeMillis() - timestamp > timeToLive;
    }

    public UUID getOrderUUID() {
        return orderId;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public List<ItemCode> getItems() {
        return items;
    }

    public int getSubtotal() {
        return subtotal;
    }

    public int getTax() {
        return tax;
    }

    public int getTotal() {
        return total;
    }

    public int getCookTime() {
        return cookTime;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void payed() {
        this.status = Status.PAID;
    }

    public void done() {
        this.status = Status.DONE;
    }

    @Override
    public void drop() {
        this.status = Status.DROPPED;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", tableNumber=" + tableNumber +
                ", items=" + items +
                ", subtotal=" + subtotal +
                ", tax=" + tax +
                ", total=" + total +
                ", cookTime=" + cookTime +
                ", ingredients='" + ingredients + '\'' +
                ", status=" + status +
                '}';
    }

    public void addCookInfo(int cookTime, String ingredients) {
        this.cookTime = cookTime;
        this.ingredients = ingredients;
        this.ingredients = ingredients;
    }

    public void addPrices(int tax, int total, int subtotal) {
        this.tax = tax;
        this.total = total;
        this.subtotal = subtotal;
    }

    public Status getStatus() {
        return status;
    }

    public boolean isDodgyCustomer() {
        return isDodgyCustomer;
    }

    public enum Status {
        IN_PROGRESS,
        DONE,
        PAID,
        DROPPED;
    }

    public static final class Builder {
        private final UUID orderId = UUID.randomUUID();
        private final List<ItemCode> items = new ArrayList<>();
        private int tableNumber;
        private long timeToLive;
        private boolean isDodgyCustomer = true;

        private Builder() {
        }


        public Builder withTableNumber(int val) {
            tableNumber = val;
            return this;
        }

        public Builder isDodgyCustomer() {
            isDodgyCustomer = true;
            return this;
        }


        public Builder addItems(List<ItemCode> val) {
            items.addAll(val);
            return this;
        }

        public Builder addItem(ItemCode val) {
            items.add(val);
            return this;
        }

        public Builder addTimeToLive(long val) {
            timeToLive = val;
            return this;
        }

        public Order build() {
            return new Order(this);
        }


    }
}
