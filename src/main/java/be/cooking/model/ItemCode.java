package be.cooking.model;

public enum ItemCode {
    JUPILER("Jupiler"),
    FRIETEN("Frietje"),
    BITTER_BALLEN("Bitterballen"),
    SOEP("Soep"),
    STEAK("Steak");

    ItemCode(String name) {
        this.name = name;
    }

    private final String name;
}
