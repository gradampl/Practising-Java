package Threads;

public class Thread_5 extends Thread{
    int[] data;
    int first,last;
    int result;

    public int getResult(){
        return this.result;
    }

    public Thread_5(int[] data){
        this.result=0;
        this.data = data;
        this.first = 0;
        this.last = data.length-1;
    }

    public Thread_5(int[] data, int first, int last){
        this.result=0;
        this.data = data;
        this.first = first;
        this.last = last;
    }

    public void run(){
        if(first == last){
            result = data[first];
        }
        else {
            int middle = (first+last)/2;

            Thread_5 c1 = new Thread_5(data,first,middle);
            Thread_5 c2 = new Thread_5(data,middle+1,last);

            c1.start();
            c2.start();

            try{
                c1.join();
                c2.join();
            }
            catch (InterruptedException e){};
            result = c1.getResult() + c2.getResult();
        }
    }

    public static void main(String[] args){
        int[] data = {1,1,2,6,24,120,720,5040};

        Thread_5 c = new Thread_5(data);
        c.start();

        // to zadziała niepoprawnie:
        System.out.println("Sum is " + c.getResult());

        // to zadziała poprawnie:
        try{
            c.join();
        }catch (InterruptedException e){};

        System.out.println("Sum is " + c.getResult());

    }
}
