package Threads;

import java.util.ArrayList;
import java.util.Random;

public class SOT_2 extends Thread {

    private ArrayList<Double> arr;
    private int low, high;
    private double partial;

    public SOT_2(ArrayList<Double> arr, int low, int high) {
        this.arr = arr;
        this.low = low;
        this.high = Math.min(high, arr.size());
    }

    public double getPartialSum() {
        return partial;
    }

    public void run() {
        partial = sum(arr, low, high);
    }

    public static double sum(ArrayList<Double> arr) {
        return sum(arr, 0, arr.size());
    }

    public static double sum(ArrayList<Double> arr, int low, int high) {
        double total = 0;
        for (int i = low; i < high; i++) {
            total += Math.tan(Math.tan(arr.get(i)/2));
        }
        return total;
    }

    public static void parallelSum(ArrayList<Double> arr)
    {
        int threads = 0;
        double[] results = new double[6];
        long startTime = System.currentTimeMillis();
        long stopTime = System.currentTimeMillis();
        double[] times = new double[6];
        long repetitions;
        repetitions = 1000000000/arr.size();
        if(arr.size()>100){
            repetitions *= 10;
        }
        if(arr.size()>1000){
            repetitions *= 10;
        }
        if(arr.size()>10000){
            repetitions *= 10;
        }
        System.out.print("\nLiczba powtórzeń = " + repetitions + "\n\n");

        for (int i = 0; i <= 5; i++) {
                threads = (int) Math.pow(2, i);
                int size = (int) Math.ceil(arr.size() * 1.0 / threads);
                SOT_2[] sums = new SOT_2[threads];

                for (int k = 0; k < threads; k++) {
                    sums[k] = new SOT_2(arr, k * size, Math.min((k+1)*size, arr.size()));
                    sums[k].start();
                }
            long maxTime = 0;
                try {
                    for (SOT_2 sum : sums) {
                        startTime = System.currentTimeMillis();
                        for(long r = 1; r <= repetitions; r++){
                        sum.join();
                        }
                        stopTime = System.currentTimeMillis();
                        long interTime = (stopTime - startTime);
                        if(interTime>maxTime)
                                    maxTime = interTime;

                    }
                } catch (InterruptedException e) {
                }

                times[i] = (double)maxTime/repetitions;

                double total = 0;
                for (SOT_2 sum : sums) {
                    total += sum.getPartialSum();
                }
                results[i] = total;
        }

        double czas = 0;

        for (int t = 0; t <= 5; t++) {
            System.out.println("Rezultat obliczeń dla 2^" + t + " wątków: " + results[t]);
            System.out.println("Czas obliczeń dla 2^" + t + " wątków: " + times[t]);
            czas += times[t];
        }
        System.out.println("\nCzas (w milisekundach) obliczeń dla wszystkich podziałów = " + czas +" (już podzielony przez liczbę powtórzeń).");
        System.out.println("\nLiczba powtórzeń każdego wątku = " + repetitions);
    }

    /******************************************************************/
    /* Koniec metod - poczatek funkcji main: */

    /******************************************************************/

    public static void main(String[] args) {

        ArrayList<Double> array = new ArrayList();

        Random random = new Random();

        long startTime = System.currentTimeMillis();
        long stopTime = System.currentTimeMillis();
        long tableSize = 10;

        for (int j = 1; j<=5; j++) {
            tableSize *= 10;
            System.out.println("\n");
            System.out.println("----------------NOWA TABLICA----------------------\n");

            array.clear();

            for (long i = 0; i < tableSize; i++) {
                array.add(random.nextDouble());
            }

            System.out.println();
            System.out.println("======================================");
            System.out.println("Obliczenia dla tablicy wielkości " + array.size());
            System.out.println("======================================");

            startTime = System.currentTimeMillis();
            SOT_2.parallelSum(array);
            stopTime = System.currentTimeMillis();
            long time = stopTime - startTime;

            System.out.println("Dla rozmiaru tablicy " + tableSize +" czas (w milisekundach, mierzony w f-cji main) = " + time +"\n");
        }
    }
}