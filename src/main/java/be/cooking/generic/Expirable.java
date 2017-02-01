package be.cooking.generic;

public interface Expirable {
    boolean isExpired();
    void drop();
}
