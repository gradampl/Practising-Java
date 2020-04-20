import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;



class Car1 {
    boolean waxed = false;
    boolean washed = false;
    boolean polished = false;

    public synchronized void wax() throws InterruptedException{
        waitUntilWashed();
        TimeUnit.MILLISECONDS.sleep(2000);
        waxed = true;
        polished = false;
        washed = false;
        notifyAll();
    }
    public synchronized void polish() throws InterruptedException{
        waitUntilWaxed();
        TimeUnit.MILLISECONDS.sleep(2000);
        waxed = false;
        polished = true;
        washed = false;
        notifyAll();
    }

    public synchronized void wash() throws InterruptedException{
        waitUntilPolished();
        TimeUnit.MILLISECONDS.sleep(2000);
        washed = true;
        polished = false;
        waxed = false;
        notifyAll();
    }


    public synchronized boolean isWaxed(){
        return waxed;
    }

    public synchronized boolean isPolished(){
        return polished;
    }

    public synchronized boolean isWashed(){
        return washed;
    }

    synchronized void waitUntilWaxed()
            throws InterruptedException{
        while (!isWaxed()){
            System.out.println(Thread.currentThread().getName()
                    + " is waiting until waxed");
            wait();
        }
    }

    synchronized void waitUntilPolished()
            throws InterruptedException{
        while (!isPolished()){
            System.out.println(Thread.currentThread().getName()
                    + " is waiting until polished");
            wait();
        }
    }

    synchronized void waitUntilWashed()
            throws InterruptedException{
        while (!isWashed()){
            System.out.println(Thread.currentThread().getName()
                    + " is waiting until washed");
            wait();
        }
    }
}

class Waxer extends Thread {
    Car1 car;
    public Waxer(Car1 c) {
        car = c;
    }
    public void run() {
        try {
            while(true) {
                System.out.println(Thread.currentThread().getName()
                        + " is waxing...");
                car.wax();
                System.out.println(Thread.currentThread().getName()
                        + " finished waxing");
            }
        } catch(InterruptedException e) {
            System.out.println("Waxer interrupted");
        }
    }
}


class Polisher extends Thread {
    Car1 car;
    public Polisher(Car1 c) {
        car = c;
    }
    public void run() {
        try {
            while(true) {
                System.out.println(Thread.currentThread().getName()
                        + " is polishing...");
                car.polish();
                System.out.println(Thread.currentThread().getName()
                        + " finished polishing");
            }
        } catch(InterruptedException e) {
            System.out.println("Polisher interrupted");
        }
    }
}

class Washer extends Thread {
    Car1 car;

    public Washer(Car1 c) {
        car = c;
    }

    public void run() {
        try {
            while (true) {
                System.out.println(Thread.currentThread().getName()
                        + " is washing...");
                car.wash();
                System.out.println(Thread.currentThread().getName()
                        + " finished washing");
            }
        } catch (InterruptedException e) {
            System.out.println("Washer interrupted");
        }
    }
}
public class WaxOMatic1{
    public static void main(String[] args) throws Exception {
        Car1 car = new Car1();
        Waxer waxer = new Waxer(car);
        Polisher polisher = new Polisher(car);
        Washer washer = new Washer(car);
        waxer.start();
        polisher.start();
        washer.start();
        Thread.sleep(1000);
        waxer.interrupt();
        polisher.interrupt();
        washer.interrupt();

//        ExecutorService exec = Executors.newCachedThreadPool();
//        exec.execute(new Waxer(car));
//        exec.execute(new Polisher(car));
//        exec.execute(new Washer(car));
//        TimeUnit.SECONDS.sleep(5); // Run for a while...
//        exec.shutdownNow(); // Interrupt all tasks
    }
}




