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
    ArrayList teams = new ArrayList();
    Hashtable<Integer,Runner> team;

    ArrayList<Double> arr = new ArrayList<>();
    private long arrSize = 0;
    long repetitions;
    Random random = new Random();
    long startTime = System.currentTimeMillis();
    long stopTime = System.currentTimeMillis();
    long runTime;
    ScoreTable table;

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

            for (int k = 0; k < arrSize; k++) {
                arr.add(random.nextDouble());
            }

            startTeams();
        }
    }

    public void setTeams(){
        for(int i = 0; i < 10; i++){
            team = new Hashtable<>();
            for(int k = 0; k < 4; k++){
                runner[i][k] = new Runner(arr);
                team.put(i, runner[i][k]);
            }
            teams.add(team);
        }
        teams.forEach((team)->{System.out.println("Zawodnicy drużyny " + teams.indexOf(team) +" to: "+ runner.toString());});
    }

    public void startTeams(){
        setTeams();
        teams.forEach((team)->{
            startTime = System.currentTimeMillis();
            for(int r = 0; r < 4; r++){
            runner[r].start();
            try {
                System.out.println("Zawodnik " + r + " drużyny " + teams.indexOf(team) + " rozpoczął bieg.");
                runner[r].join();
                System.out.println("Zawodnik " + r + " drużyny " + teams.indexOf(team) + " zakończył bieg.");
            }
            catch (InterruptedException e){}
        }
            stopTime = System.currentTimeMillis();
            System.out.println("Drużyna " + teams.indexOf(team) + " zakończyła bieg.");
            runTime = stopTime - startTime;
            table = new ScoreTable(teams.indexOf(team), runTime);
            table.recordScore();
            table.printScores();
        });
    }
}

class ScoreTable{

    private int nthTeam;
    long score;

    public ScoreTable(int nthTeam, long score){
        this.nthTeam = nthTeam;
        this.score = score;
    }

    Hashtable<Integer, Long> scores = new Hashtable<>();

    public void recordScore(){
        scores.put(nthTeam,score);
    }

    public void printScores(){
        scores.forEach((nthTeam, score) -> {System.out.println("Drużyna " + nthTeam + ", czas = " + score);});
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
