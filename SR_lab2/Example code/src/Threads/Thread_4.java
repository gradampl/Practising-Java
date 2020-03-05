package Threads;

/*
Wywołanie metoda x.join() watku x, pozwala na wstrzymanie wykonywania
biezacego watku do czasu az watek x sie zakonczy. Ilustruje to ponizszy przykład,
w którym watek główny tworzy 2 watki poboczne - zadaniem pierwszego
jest policzenie sumy elementów nieparzystych w tablicy, zadaniem drugiego jest
zliczenie elementów nieparzystych w tablicy. Watek główny uruchamia 2 watki
poboczne i czeka az skoncza prace. Nastepnie oblicza srednia na podstawie
wyników obliczonych przez watki poboczne.
 */

class OddCountThread extends Thread{
    protected int[] numbers;
    protected int result = 0;

    public OddCountThread(int[] numbers){
        this.numbers = numbers;
    }

    public boolean isOdd(int element){
        return element%2==1;
    }

    public void run(){
        for(int element : numbers){
            if(isOdd(element))
                result++;
        }
    }

    public int getResult(){
        return result;
    }
}

class OddSumThread extends OddCountThread{
    public OddSumThread(int[] numbers){
        super(numbers);
    }

    @Override
    public void run() {
        for(int element : numbers){
            if(isOdd(element)){
                result+=element;
            }
        }
    }
}

public class Thread_4 {

    public static void main(String[] args) {

        int[] numbers = {1,8,5,3,2,5,99,6};

        OddCountThread countJob = new OddCountThread(numbers);
        OddSumThread sumJob = new OddSumThread(numbers);

        countJob.start();
        sumJob.start();

        try{
            sumJob.join();
            countJob.join();
            float oddAverage = (float)sumJob.getResult()/(float)countJob.getResult();
            System.out.println("Srednia z elementów nieparzystych to: " + oddAverage);
        }
        catch (InterruptedException e){
            System.err.println(e);
        }
    }
}
