package Threads;

/* Program demonstruje uruchomienie 2 rownleglych watkow i glownego programu*/

public class Thread_2 extends Thread{

    private String threadName;

    public Thread_2(String name){
        this.threadName = name;
    }

    public void run(){
        for(int i = 0; i<10; i++){
            System.out.println(this.threadName);
        }
    }

    public static void main(String[] args) throws InterruptedException{

        Thread_2 thread_a = new Thread_2("A");
        Thread_2 thread_b = new Thread_2("B");

        thread_a.start();
        thread_b.start();

        for(int i = 0; i<10; i++){
            System.out.print("M");
        }
    }


}
