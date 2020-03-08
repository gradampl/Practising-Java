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

    public static double parallelSum(double[] arr)
    {
        return parallelSum(arr, Runtime.getRuntime().availableProcessors());
    }

    public void getNumberOfThreads(){
        System.out.println("Liczba dostępnych procesorów to " + Runtime.getRuntime().availableProcessors());
    }

    public static double parallelSum(double[] arr, int threads)
    {
        int size = (int) Math.ceil(arr.length * 1.0 / threads);

        SumOfTangents[] sums = new SumOfTangents[threads];

        for (int i = 0; i < threads; i++) {
            sums[i] = new SumOfTangents(arr, i * size, (i + 1) * size);
            sums[i].start();
        }

        try {
            for (SumOfTangents sum : sums) {
                sum.join();
            }
        } catch (InterruptedException e) { }

        int total = 0;

        for (SumOfTangents sum : sums) {
            total += sum.getPartialSum();
        }

        return total;
    }

    /******************************************************************/
    /* Koniec metod - poczatek funkcji main: */
    /******************************************************************/

    public static void main(String[] args){

        double[] array = new double[100*1000000];

        Random random = new Random();

        for(int i = 0; i<array.length; i++){
            array[i] = random.nextDouble();
        }

        long startTime = System.currentTimeMillis();

//        double total = 0;
//
//        for(int i = 0; i < array.length; i++){
//            total += Math.tan(array[i]);
//        }

        // long stopTime = System.currentTimeMillis();

        double sum = SumOfTangents.sum(array);

        long stopTime = System.currentTimeMillis();

        System.out.println("Single thread: total is: " + sum);

        System.out.println("Single, elapsed time: " + (stopTime - startTime)); // Single: 44

        startTime = System.currentTimeMillis();

        double sum1 = SumOfTangents.parallelSum(array);

        stopTime = System.currentTimeMillis();

        System.out.println("Multithread, total is: " + sum1);

        SumOfTangents x = new SumOfTangents();

        x.getNumberOfThreads();

        System.out.println("Parallel: " + (stopTime - startTime)); // Parallel: 25

        //System.out.println("Total is: " + total);
        //System.out.println("Elapsed time: : " + (stopTime - startTime));

    }
}