package be.cooking.model;

public interface Publisher {

    void publish(String topic, Order order);
}
