package be.cooking.generic;

import java.util.UUID;

public class UUIDGenerator {

    public static final UUID FIRST = UUID.fromString("6d4b892e-3a38-4a49-bcf5-10105fec8d41");
    private static int number = 0;

    public static UUID randomUUID() {
        if (number++ == 0)
            return FIRST;
        return UUID.randomUUID();
    }
}
