package Threads;
import java.util.*;

class Runner3 extends Thread{

    private ArrayList<Double> arrayReceived;

    public Runner3(ArrayList array){
        this.arrayReceived = array;
    }

    public void run(){
        double total = 0;
        for(int i = 0; i < arrayReceived.size(); i++ ){
            total += Math.tan(arrayReceived.get(i));
        }
    }
}

class Team3{

    Runner3[][] runner = new Runner3[10][4];

    ArrayList<Double> arr = new ArrayList<>();
    private long arrSize = 0;
    int repetitions = 100;
    Random random = new Random();
    long startTime = System.currentTimeMillis();
    long stopTime = System.currentTimeMillis();
    double runTime;
    ScoreTable3 table = new ScoreTable3();

    public void startRacing(){
        for(int p = 3; p <= 6; p++) {
            arrSize = (long) Math.pow(10, p);

            System.out.println();
            System.out.println("\n======================================\n");
            System.out.println("Obliczenia dla tablicy długości " + arrSize+"\n");
            System.out.println("======================================\n");
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
                runner[i][k] = new Runner3(arr);
            }
        }
    }

    public void startTeams(){

        for(int k = 0; k < 4; k++){
            for(int i = 0; i < 10; i++){
                runner[i][k].start();
            }
        }
        double[] times = new double[10];
        int sequence[] = new int[10];
        for(int j = 0; j < times.length; j++){
            times[j] = 0;
            sequence[j] = j;
        }
        int s;
        boolean start,finish;

        for(int r = 0; r < 4; r++){
            start = (r==0);
            finish = (r==3);
            if(start){
                System.out.println("Wystartowali pierwsi zawodnicy wszystkich 10 drużyn.\n");
            }

            for(int i = 0; i < 10; i++){
                s = sequence[i];
                long rep = 0;
                startTime = System.currentTimeMillis();
                do {
                    try {
                        for (int j = 1; j <= repetitions; j++) {
                            runner[s][r].join();
                        }
                    }
                    catch (InterruptedException e) {
                    }

                    rep++;
                    stopTime = System.currentTimeMillis();
                }while (stopTime-startTime<1000);

                runTime = (double)(stopTime - startTime)/rep/repetitions;
                times[s] += runTime;
            } // koniec petli dla druzyn

            //sortowanie tabeli times (na jej kopii), wyniki w tabeli sequence
            double[] times_2 = new double[10];

            for(int k = 0; k < times.length; k++){
                times_2[k] = times[k];
            }

            int n = times.length-1;
            double min;
            int k;

            for(int j = 0; j < times.length; j++){
                sequence[j] = j;
            }

            for(int j = 0; j < n; j++){
                min = times_2[j];
                k = j;
                for(int i = j+1; i <= n; i++){
                    if(times_2[i]<min){
                        min = times_2[i];
                        k = i;
                    }
                }
                if(k!=j){
                    times_2[k] = times_2[j];
                    times_2[j] = min;
                    int i = sequence[j];
                    sequence[j] = sequence[k];
                    sequence[k] = i;
                    System.out.println("");
                }
            }

            // teraz będzie wydruk komunikatów
            for(int j = 0; j < 10; j++){
                s = sequence[j];
                System.out.println("Zawodnik " + (r) + " drużyny " + (s) + " ukończył bieg.");
                if(finish){
                    System.out.println("\n===================================");
                    System.out.println("Drużyna " + (s) + " ukończyła bieg.");
                    System.out.println("===================================\n");
                }
                else {
                    System.out.println("Rozpoczął bieg zawodnik " + (r+1)+ " drużyny " + (s) + ".");
                }
            }
        } // koniec petli dla zawodnkow

        double a;
        for(int k = 0; k < times.length; k++){
            s = sequence[k];
            a = times[s];
            table.recordScore(s,a);
        }

    }
}

class ScoreTable3{

    LinkedHashMap<Integer,Double> scores = new LinkedHashMap<>();

    public void recordScore(int nthTeam,double score){
        scores.put(nthTeam, score);
    }

    public void printScores(long arrSize){
        System.out.println("\n============ WYNIKI ===============\n");
        System.out.println("Tablica długości: "+arrSize+"\n");
        System.out.println("===================================\n");

        Set set = scores.entrySet();
        Iterator i = set.iterator();
        while(i.hasNext()){
            Map.Entry me = (Map.Entry)i.next();
            System.out.println("Drużyna " + me.getKey() + ", czas (ms) = " + me.getValue());
        }
    }

    public void clearTable(){
        scores.clear();
    }
}

public class Relay_Race_3 {
    public static void main(String[] args) {
        System.out.println("\n\nZadanie 3 - sztafeta wątków\n\n");
        System.out.println("Aby zmniejszyć błąd pomiaru czasów, operacje są powtarzane tak długo,");
        System.out.println("aż mierzony czas będzie nie krótszy niż jedna sekunda." +
                "\nCzas jednego przebiegu to iloraz czasu mierzonego przez liczbę powtórzeń.\n");
        Team3 racing = new Team3();
        racing.startRacing();
    }
}
