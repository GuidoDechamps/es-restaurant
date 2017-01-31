package be.cooking;

public class Sleep {

    public static void sleep(int milisec) {
        try {
            Thread.sleep(milisec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
