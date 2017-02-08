package be.cooking;

import org.junit.Test;

public class MainTest {
    @Test
    public void main() {
        System.setProperty(Sleep.TEST_MODE,"true");
        Main.main(new String[]{"1"});
    }

}