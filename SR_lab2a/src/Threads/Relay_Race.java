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
            total += Math.tan(arrayReceived.get(i));
        }
    }
}

class Team{

    Runner[][] runner = new Runner[10][4];

    ArrayList<Double> arr = new ArrayList<>();
    private long arrSize = 0;
    int repetitions = 100;
    Random random = new Random();
    long startTime = System.currentTimeMillis();
    long stopTime = System.currentTimeMillis();
    double runTime;
    ScoreTable table = new ScoreTable();

    public void startRacing(){
        for(int p = 3; p <= 6; p++) {
            arrSize = (long) Math.pow(10, p);

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
            table.printScores(arrSize);
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
        boolean message;
        for(int i = 0; i < 10; i++){
            long rep = 0;
            message = true;
            startTime = System.currentTimeMillis();
            stopTime = startTime;
            do{
                for(int r = 0; r < 4; r++){
                    try {
                        if(message){
                            System.out.println("Zawodnik " + (r+1) + " drużyny " + (i+1) + " rozpoczął bieg.");
                        }
                        for(int j = 1; j <= repetitions; j++){
                            runner[i][r].join();
                        }
                        if(message){
                            System.out.println("Zawodnik " + (r+1) + " drużyny " + (i+1) + " zakończył bieg.");
                        }
                    }
                    catch (InterruptedException e){}
                }
                message = false;
                rep++;
                stopTime = System.currentTimeMillis();
            }while(stopTime-startTime<1000);

            System.out.println("\nDrużyna " + (i+1) + " zakończyła bieg.\n");
            runTime = (double)(stopTime - startTime)/(rep * repetitions);
            table.recordScore(i,runTime);
        }
    }
}

class ScoreTable{

    Hashtable<Integer, Double> scores = new Hashtable<>();

    public void recordScore(int nthTeam, double score){
        scores.put(nthTeam,score);
    }

    public void printScores(long arrSize){
        System.out.println("\n============ WYNIKI ===============\n");
        System.out.println("Tablica długości: "+arrSize+"\n");
        scores.forEach((nthTeam, score) -> {System.out.format("Drużyna " + (nthTeam +1) + ", czas (ms) = %.2e%n ", score);});
        System.out.println("\n===================================\n");
    }

    public void clearTable(){
        scores.clear();
    }
}

public class Relay_Race {
    public static void main(String[] args) {
        System.out.println("\n\nZadanie 3 - sztafeta wątków\n\n");
        System.out.println("Aby zmniejszyć błąd pomiaru czasów, operacje są powtarzane tak długo,");
        System.out.println("aż mierzony czas będzie nie krótszy niż jedna sekunda." +
                "\nCzas jednego przebiegu to iloraz czasu mierzonego przez liczbę powtórzeń.\n");
        Team racing = new Team();
        racing.startRacing();
    }
}
