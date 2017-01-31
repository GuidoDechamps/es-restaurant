package be.cooking.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Order {
    private final UUID orderId;
    private int tableNumber;
    private List<Item> items;
    private int subtotal;
    private int tax;
    private int total;
    private int cookTime;
    private String ingredients;

    private Order(Builder builder) {
        orderId = builder.orderId;
        tableNumber = builder.tableNumber;
        items = builder.items;
        subtotal = builder.subtotal;
        tax = builder.tax;
        total = builder.total;
        cookTime = builder.cookTime;
        ingredients = builder.ingredients;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public UUID getOrderUUID() {
        return orderId;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public List<Item> getItems() {
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
                '}';
    }

    public static final class Builder {
        private UUID orderId = UUID.randomUUID();
        private int tableNumber;
        private List<Item> items = new ArrayList<>();
        private int subtotal;
        private int tax;
        private int total;
        private int cookTime;
        private String ingredients;

        private Builder() {
        }


        public Builder withTableNumber(int val) {
            tableNumber = val;
            return this;
        }

        public Builder addItems(List<Item> val) {
            items.addAll(val);
            return this;
        }

        public Builder addItem(Item val) {
            items.add(val);
            return this;
        }

        public Builder withSubtotal(int val) {
            subtotal = val;
            return this;
        }

        public Builder withTax(int val) {
            tax = val;
            return this;
        }

        public Builder withTotal(int val) {
            total = val;
            return this;
        }

        public Builder withCookTime(int val) {
            cookTime = val;
            return this;
        }

        public Builder withIngredients(String val) {
            ingredients = val;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }
}
