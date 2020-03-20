package Threads;

public class skokZegara {

    public static void main(String[] args) {
        long startTime;
        long stopTime;
        long t1;
        long t2;
        double czas;
        int k = 0;

        startTime = System.currentTimeMillis();
        t1 = System.currentTimeMillis();
        t2 = t1;
        while (k < 1000){
            do {
                t2 = System.currentTimeMillis();

            }while(t1==t2);

            k++;  t1 = t2;
        }
        stopTime = System.currentTimeMillis();
        czas = (double)((stopTime - startTime))/1000;
        System.out.println("Liczba zmian zegara = " + k);
        System.out.println("Czas jednej zmiany (w milisekundach): " + czas);

    }
}
