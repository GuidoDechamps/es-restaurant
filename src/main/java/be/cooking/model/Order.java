package be.cooking.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Order {
    private final UUID orderId;
    private int tableNumber;
    private List<ItemCode> items;
    private int subtotal;
    private int tax;
    private int total;
    private int cookTime;
    private String ingredients;
    private boolean paid;

    private Order(Builder builder) {
        orderId = builder.orderId;
        tableNumber = builder.tableNumber;
        items = builder.items;
        subtotal = -1;
        tax = -1;
        total = -1;
        cookTime = -1;
        ingredients = "UNKNOWN";
        paid = false;
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

    public void payed()
    {
        this.paid = true;
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
                ", paid=" + paid +
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

    public static final class Builder {
        private UUID orderId = UUID.randomUUID();
        private int tableNumber;
        private List<ItemCode> items = new ArrayList<>();
        private boolean paid = false;

        private Builder() {
        }


        public Builder withTableNumber(int val) {
            tableNumber = val;
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


        public Order build() {
            return new Order(this);
        }


    }
}
