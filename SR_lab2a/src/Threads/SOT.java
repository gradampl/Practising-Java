package Threads;

import java.util.ArrayList;
import java.util.Random;

public class SOT extends Thread {

    private ArrayList<Double> arr;

    private int low, high;
    private double partial;


    public SOT(ArrayList<Double> arr, int low, int high) {

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
            total += Math.tan(arr.get(i));
        }

        return total;
    }



    public static void parallelSum(ArrayList<Double> arr)//, int threads)
    {
        int threads = 0;
        double[] results = new double[6];
        long startTime = System.currentTimeMillis();
        long stopTime = System.currentTimeMillis();
        long[] times = new long[6];

        for (int i = 0; i <= 5; i++) {

            startTime = System.currentTimeMillis();

            threads = (int) Math.pow(2, i);

            int size = (int) Math.ceil(arr.size() * 1.0 / threads);

            SOT[] sums = new SOT[threads];

            for (int k = 0; k < threads; k++) {
                sums[k] = new SOT(arr, k * size, (k + 1) * size);
                sums[k].start();
            }

            try {
                for (SOT sum : sums) {
                    sum.join();
                }
            } catch (InterruptedException e) {
            }

            double total = 0;

            for (SOT sum : sums) {
                total += sum.getPartialSum();
            }

            results[i] = total;
            stopTime = System.currentTimeMillis();
            times[i] = (stopTime - startTime);
        }


        for (int j = 0; j <= 5; j++) {
            System.out.println("Rezultat obliczeń dla 2^" + j + " wątków: " + results[j]);
            System.out.println("Czas obliczeń dla 2^" + j + " wątków: " + times[j]);
        }
    }


    /******************************************************************/
    /* Koniec metod - poczatek funkcji main: */

    /******************************************************************/

    public static void main(String[] args) {

        ArrayList<Double> array = new ArrayList();

        Random random = new Random();

        long startTime = System.currentTimeMillis();
        long stopTime = System.currentTimeMillis();

        for (int j = 0; j <=2; j++) {

            int tableSize = (int) Math.pow(100, j + 1);

            for (int i = 0; i < tableSize; i++) {
                array.add(random.nextDouble());
            }

            System.out.println();
            System.out.println("======================================");
            System.out.println("Obliczenia dla tablicy wielkości 100^" + (j + 1));
            System.out.println("======================================");

            startTime = System.currentTimeMillis();
            SOT.parallelSum(array);
            stopTime = System.currentTimeMillis();
            long time = stopTime - startTime;

            System.out.println("\nDla rozmiaru tablicy 100^(" + j + "+1) czas= " + time);
        }
    }
}