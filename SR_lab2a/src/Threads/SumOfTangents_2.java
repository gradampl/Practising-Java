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
import java.util.Scanner;

public class SumOfTangents_2 extends Thread {

    private double[] arr;

    private int low, high;
    private double partial;

    public SumOfTangents_2(double[] arr, int low, int high) {

        this.arr = arr;
        this.low = low;
        this.high = Math.min(high, arr.length);
    }

    public double getPartialSum() {
        return partial;
    }

    public void run() {
        partial = sum(arr, low, high);
    }

    public static double sum(double[] arr) {
        return sum(arr, 0, arr.length);
    }

    public static double sum(double[] arr, int low, int high) {

        int total = 0;

        for (int i = low; i < high; i++) {
            total += Math.tan(arr[i]);
        }

        return total;
    }

    public static double parallelSum(double[] arr) {
        int threads = 0;

        Scanner in = new Scanner(System.in);

        System.out.println("Ile wątków mam uruchomić?");
        System.out.println("Jeśli jeden - wybierz a;");
        System.out.println("Jeśli dwa - wybierz b;");
        System.out.println("Jeśli cztery - wybierz c;");
        System.out.println("Jeśli osiem - wybierz d;");
        System.out.println("Jeśli szesnaście - wybierz e;");
        System.out.println("Jeśli trzydzieści dwa - wybierz f.");

        String choice = in.next().toLowerCase();

        switch (choice) {
            case "a":
                threads = 1;
                break;
            case "b":
                threads = 2;
                break;
            case "c":
                threads = 4;
                break;
            case "d":
                threads = 8;
                break;
            case "e":
                threads = 16;
                break;
            case "f":
                threads = 32;
                break;
            default:
                System.out.println("Nie ma takiej opcji.");
                break;
        }

        System.out.println("Liczba dostępnych wątków to " + threads);
        return parallelSum(arr, threads);
    }

    public static double parallelSum(double[] arr, int threads) {

            int size = (int) Math.ceil(arr.length * 1.0 / threads);

            SumOfTangents_2[] sums = new SumOfTangents_2[threads];

            for (int k = 0; k < threads; k++) {
                sums[k] = new SumOfTangents_2(arr, k * size, (k + 1) * size);
                sums[k].start();
            }

            try {
                for (SumOfTangents_2 sum : sums) {
                    sum.join();
                }
            } catch (InterruptedException e) {
            }

            double total = 0;

            for (SumOfTangents_2 sum : sums) {
                total += sum.getPartialSum();
            }


        return total;

    }


    /******************************************************************/
    /* Koniec metod - poczatek funkcji main: */
    /******************************************************************/


    public static void main(String[] args){

        double[] array = new double[1000000];

        Random random = new Random();

        boolean shouldContinue = true;

        int howManyIndexes = 0;
        int border = 0;


            System.out.println("Ile elementów ma mieć tablica?");
            System.out.println("Jeśli 100 - wybierz a;");
            System.out.println("Jeśli 10000 - wybierz b;");
            System.out.println("Jeśli 1000000 - wybierz c;");

            Scanner in = new Scanner(System.in);
            String choice = in.next().toLowerCase();

            switch (choice) {
                case "a":
                    howManyIndexes = 100;
                    border = 10000;
                    break;
                case "b":
                    howManyIndexes = 10000;
                    border = 100;
                    break;
                case "c":
                    howManyIndexes = 1000000;
                    border = 1;
                    break;
                case "z":
                    shouldContinue = false;
                    break;
                default:
                    System.out.println("Nie ma takiej opcji.");
                    break;
            }


            System.out.println("Obliczenia dla tablicy " + howManyIndexes + " elementów.");

            for (int j = 0; j < howManyIndexes; j++) {
                array[j] = random.nextDouble();
            }

            double sum = 0;

            long startTime = System.currentTimeMillis();

            for (int i = 0; i < border; i++) {
                sum = SumOfTangents_2.parallelSum(array);
            }

            long stopTime = System.currentTimeMillis();

            System.out.println("Total: " + sum);

            System.out.println("Elapsed time: " + (stopTime - startTime));

    }
}
