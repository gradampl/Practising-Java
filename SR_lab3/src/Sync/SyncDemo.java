package Sync;

public class SyncDemo extends Thread {
    SimpleCounter so ;
    public SyncDemo ( SimpleCounter obj ) {
        so = obj ;
    }

    public void run ( ) {
        for ( int i =0; i <100000; i++) {
            so.inc() ;
        }
    }

    public static void main (String args[]){
        SimpleCounter counter = new SimpleCounter();
        SyncDemo thread1 = new SyncDemo(counter);
        SyncDemo thread2 = new SyncDemo(counter);
        SyncDemo thread3 = new SyncDemo(counter);
        SyncDemo thread4 = new SyncDemo(counter);
        SyncDemo thread5 = new SyncDemo(counter);
        SyncDemo thread6 = new SyncDemo(counter);
        SyncDemo thread7 = new SyncDemo(counter);
        SyncDemo thread8 = new SyncDemo(counter);
        SyncDemo thread9 = new SyncDemo(counter);
        SyncDemo thread10 = new SyncDemo(counter);
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
        thread6.start();
        thread7.start();
        thread8.start();
        thread9.start();
        thread10.start();
        try{
            thread1.join();
            thread2.join();
            thread3.join();
            thread4.join();
            thread5.join();
            thread6.join();
            thread7.join();
            thread8.join();
            thread9.join();
            thread10.join();
        }
        catch(InterruptedException e){
            System.out.println("Interrupted");
        }
        System.out.println(counter.getValue());
    }
}