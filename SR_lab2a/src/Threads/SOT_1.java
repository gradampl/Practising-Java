package Threads;

import java.util.ArrayList;
import java.util.Random;

          /******************************************************************/

                                      /* METHODS : */

         /******************************************************************/


public class SOT_1 extends Thread {

    private ArrayList<Double> arr;
    private int low, high;
    private double partial;

    public SOT_1(ArrayList<Double> arr, int low, int high) {
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
        long startTime;
        long stopTime;
        double[] times = new double[6];
        long[] rep = new long[6];
        long repetitions = 1000;

        for (int i = 0; i <= 5; i++) {
            threads = (int) Math.pow(2, i);
            int size = (int) Math.ceil(arr.size() * 1.0 / threads);
            SOT_1[] sums = new SOT_1[threads];

            for (int k = 0; k < threads; k++) {
                sums[k] = new SOT_1(arr, k * size, Math.min((k+1)*size, arr.size()));
                sums[k].start();
            }
            double maxTime = 0;
            long repMax = 0;
            long a;
            try {
                for (SOT_1 sum : sums) {
                    startTime = System.currentTimeMillis();
                    a=0;
                    do {
                        for(int k = 1; k <= repetitions; k++){
                            sum.join();
                        }
                        a++;
                        stopTime = System.currentTimeMillis();
                    }while ((stopTime - startTime)<1000);

                    double interTime = ((double)(stopTime - startTime))/(a * repetitions);

                    if(interTime>maxTime){
                        maxTime = interTime;
                        repMax = a;
                    }
                }
            } catch (InterruptedException e) {
            }

            times[i] = maxTime;
            rep[i] = repMax;

            double total = 0;
            for (SOT_1 sum : sums) {
                total += sum.getPartialSum();
            }
            results[i] = total;
        }

        double totalTime = 0;

        for (int t = 0; t <= 5; t++) {
            System.out.println("");
            System.out.format("Rezultat obliczeń dla " + ((int)Math.pow(2,t)) + " wątków: %.2e%n", results[t]);
            System.out.format("Czas obliczeń dla " + ((int)Math.pow(2,t)) + " wątków: %.2e%n", times[t]);
            System.out.println("Liczba powtórzeń dla " + ((int)Math.pow(2,t)) + " wątków: " + rep[t] + " * 1000");
            System.out.println("");
            totalTime += times[t];
        }
        System.out.format("\nCzas (w milisekundach) obliczeń dla wszystkich podziałów (mierzony w f-cji parallelSum) = %.2e%n", totalTime, " (już podzielony przez liczbę powtórzeń).");
    }

             /******************************************************************/

                                          /* MAIN : */

             /******************************************************************/

    public static void main(String[] args) {

        ArrayList<Double> array = new ArrayList();

        Random random = new Random();

        long startTime;
        long stopTime;
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
            SOT_1.parallelSum(array);
            stopTime = System.currentTimeMillis();
            long time = stopTime - startTime;

            System.out.println("Dla rozmiaru tablicy " + tableSize +" czas (w milisekundach, mierzony w f-cji main) = " + time +"\n");
        }
    }
}