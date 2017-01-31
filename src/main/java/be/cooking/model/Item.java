package be.cooking.model;

public enum Item {
    JUPILER(ItemCode.JUPILER, "Bier"),
    WINE(ItemCode.WINE, "Bottle"),
    FRIETEN(ItemCode.FRIETEN, Recipes.PATATEN),
    BITTER_BALLEN(ItemCode.BITTER_BALLEN, Recipes.MEAT_LEFT_OVERS),
    SOEP(ItemCode.SOEP, Recipes.GROETEN + " , " + Recipes.BOUILLON),
    SPAGHETTI(ItemCode.SPAGHETTI, Recipes.PASTA + " , " + Recipes.GEHAKT),
    STEAK(ItemCode.STEAK, Recipes.STEAK);
    private final ItemCode itemCode;
    private final String ingredienten;

    private Item(ItemCode itemCode, String ingredienten) {
        this.itemCode = itemCode;
        this.ingredienten = ingredienten;
    }

    public static Item getItem(ItemCode code) {
        for (Item item : Item.values()) {
            if (item.getItemCode().equals(code))
                return item;
        }
        throw new RuntimeException("Unknown item code " + code);
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemCode='" + itemCode + '\'' +
                ", ingredienten='" + ingredienten + '\'' +
                '}';
    }

    public String getIngredienten() {
        return ingredienten;
    }

    public ItemCode getItemCode() {
        return itemCode;
    }
}
