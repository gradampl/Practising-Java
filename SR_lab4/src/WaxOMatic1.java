import java.util.concurrent.TimeUnit;



class Car1 {

    public enum Stage{
        waxed,
        washed,
        polished
    }

    Stage stage = Stage.washed;

    public synchronized void wax() throws InterruptedException{
        waitUntilWashed();
        stage = Stage.waxed;
        TimeUnit.MILLISECONDS.sleep(2000);
        notifyAll();
    }
    public synchronized void polish() throws InterruptedException{
        waitUntilWaxed();
        stage = Stage.polished;
        TimeUnit.MILLISECONDS.sleep(2000);
        notifyAll();
    }

    public synchronized void wash() throws InterruptedException{
        waitUntilPolished();
        stage = Stage.washed;
        TimeUnit.MILLISECONDS.sleep(10000);
        notifyAll();
    }

    synchronized void waitUntilWaxed()
            throws InterruptedException{
        while(stage!=Stage.waxed){
            System.out.println(Thread.currentThread().getName()
                    + " is waiting until waxed");
            wait();
        }
    }

    synchronized void waitUntilPolished()
            throws InterruptedException{
        while(stage!=Stage.polished){
            System.out.println(Thread.currentThread().getName()
                    + " is waiting until polished");
            wait();
        }
    }

    synchronized void waitUntilWashed()
            throws InterruptedException{
        while(stage!=Stage.washed){
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
    }
}




