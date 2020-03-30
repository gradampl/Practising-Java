package Sync;

public class SimpleCounter {
    private int value ;
    public SimpleCounter ( ) {
        value = 0 ;
    }
    public synchronized void inc ( ) {
        value++;
    }
    public synchronized int getValue ( ) {
        return value ;
    }

    public static void main(String[] args) {
        SimpleCounter counter = new SimpleCounter();
        long startTime;
        long stoptime;
        boolean stop = false;
        startTime = System.currentTimeMillis();

        do {
            counter.inc();

            stoptime = System.currentTimeMillis();

        }while(stoptime-startTime<2000);

        System.out.println("W ciągu 2 sekund policzyłem do: " + counter.getValue());
        System.out.println("Zacząłem o "+ startTime);
        System.out.println("Skończyłem o "+stoptime);
    }
}
