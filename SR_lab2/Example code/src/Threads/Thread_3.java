package Threads;

/*
Program demonstruje utworzenie klasy z interfejsem Runnable
w celu umozliwienia dziedziczenia i uruchomienie takiego obiektu
jako watku
*/

class Foo{
    private String name;

    public Foo(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}

public class Thread_3 extends Foo implements Runnable{

    public Thread_3(String name){
        super(name);
    }

    public void run() {
        System.out.println("Thread " + this.getName() + " is running.");
    }

    public static void main(String[] args) throws InterruptedException{
        Thread_3 runnable = new Thread_3("theRunnable");
        Thread thread = new Thread(runnable);
        thread.start();
    }
}
