package Threads;
import java.util.*;

class Athlete extends Thread{

    private ArrayList<Double> arrayReceived;

    public Athlete(ArrayList array){
        this.arrayReceived = array;
    }

    public void run(){
        double total = 0;
        for(int i = 0; i < arrayReceived.size(); i++ ){
            total += Math.tan(Math.tan(arrayReceived.get(i)/2));
        }
    }
}

public class Thread_Race {

    private int threads = 0;
    private long arrSize = 0;
    ArrayList<Athlete> athletes = new ArrayList<>();
    ArrayList<Double> arr = new ArrayList<>();
    Random random = new Random();
    long repetitions;
    long startTime = System.currentTimeMillis();
    long stopTime = System.currentTimeMillis();
    public long runTime;
    Score_Table table = new Score_Table();

    public void startRace(){
        for(int p = 3; p <= 6; p++) {

            arrSize = (long) Math.pow(10, p);
            repetitions = arrSize*((long)Math.pow(10,(8-(p+2))));

            arr.clear();

            for (int k = 0; k < arrSize; k++) {
                arr.add(random.nextDouble());
            }

            callAthletes();
        }
    }


    public void callAthletes(){
        for (int i = 5; i <= 30; i += 5) {

            threads = i;
            athletes.clear();

            for (int j = 0; j < threads; j++) {
                athletes.add(new Athlete(arr));
                athletes.get(j).start();
            }

            System.out.println("------------------ Tablica długości " + arrSize + " ----------------------");
            System.out.println("\n");
            System.out.println("------------- W wyścigu bierze udział " + threads + " zawodników. --------------");
            System.out.println("\n");

            int s = 0;

            try {
                for (int a = 0; a < threads; a++) {
                    startTime = System.currentTimeMillis();
                    for(int r = 1; r <= repetitions; r++){
                        athletes.get(a).join();
                    }
                    stopTime = System.currentTimeMillis();
                    runTime = stopTime - startTime;
                    int athleteNumber = a+1;
                    table.recordScore(athleteNumber, runTime);
                    s++;
                }
                table.printScores();
                if(s%5==0){System.out.println("\n\n");}
                table.clearTable();
            } catch (InterruptedException e) {

            }
        }
    }

    public static void main(String[] args) {
        Thread_Race racing = new Thread_Race();
        racing.startRace();
    }
}


class Score_Table{

    Hashtable<Integer, Long> scores = new Hashtable<>();

    public void recordScore(int nthAthlete, long score){
        scores.put(nthAthlete,score);
    }

    public void printScores(){
        scores.forEach((nthAthlete, score) -> {System.out.println("Zawodnik " + nthAthlete + ", czas = " + score);});
    }

    public void clearTable(){
        scores.clear();
    }
}