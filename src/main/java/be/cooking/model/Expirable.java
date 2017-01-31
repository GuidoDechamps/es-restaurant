package be.cooking.model;

public interface Expirable {
    boolean isExpired();
    void drop();
}
