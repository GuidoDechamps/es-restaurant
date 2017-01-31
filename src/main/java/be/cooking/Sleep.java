package be.cooking;

public class Sleep {

     static final String TEST_MODE = "test.mode";

    public static void sleep(int miliSec) {
        if (inTestMode())
            return;
        else
            relaySleep(miliSec);
    }

    private static boolean inTestMode() {
        final String property = System.getProperty(TEST_MODE);
        return "true".equalsIgnoreCase(property);
    }

    private static void relaySleep(int milisec) {
        try {
            Thread.sleep(milisec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
