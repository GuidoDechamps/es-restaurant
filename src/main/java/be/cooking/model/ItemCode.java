package be.cooking.model;

public enum ItemCode {
    JUPILER("Jupiler"),
    FRIETEN("Frietje"),
    BITTER_BALLEN("Bitterballen"),
    SOEP("Soep"),
    STEAK("Steak"), WINE("RedRedWine"), SPAGHETTI("Spaghetti");

    private final String name;

    ItemCode(String name) {
        this.name = name;
    }
}
