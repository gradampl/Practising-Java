package Threads;

/*Program demonstruje sposob tworzenia i uruchomienia pojedynczego watku*/

public class Thread_1 extends Thread{
    public void run() {
        System.out.println("He l l o  World");
    }

    public static void main(String[] args){
        Thread_1 thread = new Thread_1();
        thread.start();
    }
}
