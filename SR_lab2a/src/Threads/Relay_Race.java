package Threads;
import java.util.*;

class Runner extends Thread{

    private ArrayList<Double> arrayReceived;

    public Runner(ArrayList array){
        this.arrayReceived = array;
    }

    public void run(){
        double total = 0;
        for(int i = 0; i < arrayReceived.size(); i++ ){
            total += Math.tan(Math.tan(arrayReceived.get(i)/2));
        }
    }
}

class Team{

    Runner[][] runner = new Runner[10][4];

    ArrayList<Double> arr = new ArrayList<>();
    private long arrSize = 0;
    long repetitions;
    Random random = new Random();
    long startTime = System.currentTimeMillis();
    long stopTime = System.currentTimeMillis();
    long runTime;
    ScoreTable table = new ScoreTable();

    public void startRacing(){
        for(int p = 3; p <= 6; p++) {
            arrSize = (long) Math.pow(10, p);
            repetitions = arrSize*((long)Math.pow(10,(8-(p+2))));

            System.out.println();
            System.out.println("======================================");
            System.out.println("Obliczenia dla tablicy długości " + arrSize);
            System.out.println("======================================");
            System.out.println();

            arr.clear();
            table.clearTable();

            for (int k = 0; k < arrSize; k++) {
                arr.add(random.nextDouble());
            }
            setTeams();
            startTeams();
            table.printScores();
        }
    }

    public void setTeams(){
        for(int i = 0; i < 10; i++){
            for(int k = 0; k < 4; k++){
                runner[i][k] = new Runner(arr);
                runner[i][k].start();
            }
        }
    }

    public void startTeams(){
        for(int i = 0; i < 10; i++){
            startTime = System.currentTimeMillis();
            for(int r = 0; r < 4; r++){
                try {
                    System.out.println("Zawodnik " + r + " drużyny " + i + " rozpoczął bieg.");
                    for(int j = 1; j <= repetitions; j++){
                        runner[i][r].join();
                    }
                    System.out.println("Zawodnik " + r + " drużyny " + i + " zakończył bieg.");
                }
                catch (InterruptedException e){}
            }
            stopTime = System.currentTimeMillis();
            System.out.println("\nDrużyna " + i + " zakończyła bieg.\n");
            runTime = stopTime - startTime;
            table.recordScore(i,runTime);
        }
    }
}

class ScoreTable{

    Hashtable<Integer, Long> scores = new Hashtable<>();

    public void recordScore(int nthTeam, long score){
        scores.put(nthTeam,score);
    }

    public void printScores(){
        System.out.println("\n============ WYNIKI ===============");
        scores.forEach((nthTeam, score) -> {System.out.println("\nDrużyna " + nthTeam + ", czas = " + score);});
        System.out.println("\n===================================\n");
    }

    public void clearTable(){
        scores.clear();
    }
}

public class Relay_Race {
    public static void main(String[] args) {
        Team racing = new Team();
        racing.startRacing();
    }
}
