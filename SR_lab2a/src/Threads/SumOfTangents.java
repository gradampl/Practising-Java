/* Zmodyfikuj ponizszy program tak, zeby suma tangensów była liczona w sposób
wielowatkowy. Zmien program tak, zeby łatwo mozna było zmienic liczbe watków
uzytych do obliczenia sumy. Sprawdz eksperymentalnie jaka jest zaleznosc
miedzy liczba watków a czasem wykonania programu. Przeprowadz eksperymenty
dla tablic o róznych wielkosciach (100, 10000, 1000000, ...) i dla róznej
wielkosci watków (1,2,4,8,16,32). */


//public class SumOfTangents {
//    public static void main(String[] args){
//
//        double[] array = new double[100*1000000];
//
//        Random random = new Random();
//
//        for(int i = 0; i<array.length; i++){
//            array[i] = random.nextDouble();
//        }
//
//        long startTime = System.currentTimeMillis();
//
//        double total = 0;
//
//        for(int i = 0; i < array.length; i++){
//            total += Math.tan(array[i]);
//        }
//
//        long stopTime = System.currentTimeMillis();
//
//        System.out.println("Total is: " + total);
//        System.out.println("Elapsed time: : " + (stopTime - startTime));
//
//    }
//}
package Threads;

import java.util.Random;

public class SumOfTangents extends Thread{

    private double[] arr;

    private int low, high;
    private double partial;

    public SumOfTangents(){

    }

    public SumOfTangents(double[] arr, int low, int high){

        this.arr = arr;
        this.low = low;
        this.high = Math.min(high,arr.length);
    }

    public double getPartialSum() {
        return partial;
    }

    public void run() {
        partial = sum(arr, low, high);
    }

    public static double sum(double[] arr)
    {
        return sum(arr, 0, arr.length);
    }

    public static double sum(double[] arr, int low, int high)
    {
        int total = 0;

        for(int i = low; i < high; i++){
            total += Math.tan(arr[i]);
        }

//        for (int i = (int) low; i < high; i++) {
//            total += arr[i];
//        }

        return total;
    }

//    public static double parallelSum(double[] arr)
//    {
//            System.out.println("Liczba dostępnych wątków to " + );
//            return parallelSum(arr, );
//    }

    public void parallelSum(double[] arr)//, int threads)
    {
        int threads = 0;
        double[] results = new double[6];
        long startTime = System.currentTimeMillis();
        long stopTime = System.currentTimeMillis();
        double[] times = new double[6];

        for (int i = 0; i <= 5; i++) {

            startTime = System.currentTimeMillis();

            threads = (int) Math.pow(2,i);

            int size = (int) Math.ceil(arr.length * 1.0 / threads);

            SumOfTangents[] sums = new SumOfTangents[threads];

            for (int k = 0; k < threads; k++) {
                sums[k] = new SumOfTangents(arr, k * size, (k + 1) * size);
                sums[k].start();
            }

            try {
                for (SumOfTangents sum : sums) {
                   sum.join();
                }
            } catch (InterruptedException e) {
            }

            double total = 0;

            for (SumOfTangents sum : sums) {
                total += sum.getPartialSum();
            }

            results[i] = total;
            stopTime = System.currentTimeMillis();
            times[i] = (stopTime - startTime);

        }

        for(int j = 0; j<=5; j++){
            System.out.println("Rezultat obliczeń dla 2^" + j + " wątków: " + results[j]);
            System.out.println("Czas obliczeń dla 2^" + j + " wątków: " + times[j]);
        }
    }


    /******************************************************************/
    /* Koniec metod - poczatek funkcji main: */
    /******************************************************************/

    public static void main(String[] args){

        double[] array = new double[10000];

        Random random = new Random();

        double startTime = System.currentTimeMillis();
        double stopTime = System.currentTimeMillis();

        for(int j = 0; j<=1; j++){

            int tableSize = (int)Math.pow(100, j+1);

            for(int i = 0; i<tableSize; i++){
                array[i] = random.nextDouble();
            }
            System.out.println();
            System.out.println("======================================");
            System.out.println("Obliczenia dla tablicy wielkości 100^"+(j+1));
            System.out.println("======================================");


            SumOfTangents calculation = new SumOfTangents();
            startTime = System.currentTimeMillis();
            calculation.parallelSum(array);
            stopTime = System.currentTimeMillis();
            double time = stopTime - startTime;

            System.out.println("\nDla rozmiaru tablicy 100^(" + j +"+1) czas= "+ time);
        }


//        long startTime = System.currentTimeMillis();

//        double total = 0;
//
//        for(int i = 0; i < array.length; i++){
//            total += Math.tan(array[i]);
//        }

        // long stopTime = System.currentTimeMillis();

//        double sum = SumOfTangents.sum(array);
//
//        long stopTime = System.currentTimeMillis();
//
//        System.out.println("Single thread: total is: " + sum);
//
//        System.out.println("Single, elapsed time: " + (stopTime - startTime)); // Single: 44
//
//        startTime = System.currentTimeMillis();
//
//        double sum1 = SumOfTangents.parallelSum(array);
//
//        stopTime = System.currentTimeMillis();
//
//        System.out.println("Multithread, total is: " + sum1);
//
//        SumOfTangents x = new SumOfTangents();
//
//        System.out.println("Parallel: " + (stopTime - startTime)); // Parallel: 25

        //System.out.println("Total is: " + total);
        //System.out.println("Elapsed time: : " + (stopTime - startTime));

    }
}