package Threads;

import java.util.ArrayList;
import java.util.Random;

          /******************************************************************/

                                    /* METHODS : */

          /******************************************************************/


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



    public static void parallelSum(ArrayList<Double> arr)
    {
        int threads = 0;
        double[] results = new double[6];
        long startTime;
        long controlTime;
        long stopTime;
        double[] times = new double[6];
        long rep[] = new long[6];

        long repetitions = 10;
        long a;

        for (int i = 0; i <= 5; i++) {
            startTime = System.currentTimeMillis();
            a=0;
            do {
                for (int r = 1; r <= repetitions; r++) {
                    threads = (int) Math.pow(2, i);
                    int size = (int) Math.ceil(arr.size() * 1.0 / threads);
                    SOT[] sums = new SOT[threads];
                    for (int k = 0; k < threads; k++) {
                        sums[k] = new SOT(arr, k * size, Math.min((k+1)*size, arr.size()));
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
                }

                a++;
                controlTime = System.currentTimeMillis();

            }while ((controlTime - startTime)<1000);

            stopTime = System.currentTimeMillis();
            times[i] = ((double)(stopTime - startTime))/(a * repetitions);
            rep[i]=a * repetitions;
        }

        double totalTime = 0;

            for (int t = 0; t <= 5; t++) {
                System.out.println("");
                System.out.format("Rezultat obliczeń dla " + ((int)Math.pow(2,t)) + " wątków: %.2e%n", results[t]);
                System.out.format("Czas obliczeń dla " + ((int)Math.pow(2,t)) + " wątków (ms): %.2e%n", times[t]);
                System.out.println("Liczba powtórzeń dla " + ((int)Math.pow(2,t)) + " wątków: " + rep[t]);
                System.out.println("");
                totalTime += times[t];
            }
        System.out.format("\nF-cja parallelSum - suma czasów obliczeń, podzielonych przez liczbę powtórzeń,\n" +
                "dla wszystkich podziałów (w milisekundach) " +
                "= %.2e%n", totalTime, "\n");
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

        System.out.println("\n\n--------------- Zadanie 1 - suma tangensów -------------------\n");
        System.out.println("Aby zmniejszyć błąd pomiaru czasów, operacje są powtarzane tak długo,");
        System.out.println("aż mierzony czas będzie nie krótszy niż jedna sekunda." +
                "\nCzas jednego przebiegu to iloraz czasu mierzonego przez liczbę powtórzeń.\n");

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
            SOT.parallelSum(array);
            stopTime = System.currentTimeMillis();
            long time = stopTime - startTime;

            System.out.println("Dla rozmiaru tablicy " + tableSize +" czas (w milisekundach, mierzony w f-cji main) = " + time +"\n");
        }
    }
}