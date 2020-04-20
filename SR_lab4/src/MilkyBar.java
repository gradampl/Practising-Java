class MilkyBar {
    int[] data ;
    int capacity;
    int current=-1;

    public MilkyBar ( int count ) {
        capacity = count ;
        data = new int [ count ] ;
    }

    public synchronized void push ( int number ) throws InterruptedException
    {
        String name = Thread . currentThread ( ) . getName ( ) ;
        System . out . format ("%s: entered push()\n" , name ) ;
        waitForNotFull ( ) ;
        System . out . format ("%s: can push() \n", name ) ;
        current++;
        data [current ] = number ;
        System . out . format ( "%s pushed %d to [%d]\n" , name , number , current ) ;
        notifyAll ( ) ;
        System . out . format ( "%s: ended push()\n" , name ) ;
    }

    public synchronized int pop ( ) throws InterruptedException
    {
        String name = Thread . currentThread ( ) . getName ( ) ;
        System . out . format ("%s: entered  pop()\n" , name ) ;
        waitForNotEmpty();
        System . out . format ("%s: can pop\n" , name ) ;
        int value = data [current] ;
        System . out . format ("%s: popped %d from [%d] \n" , name , value , current ) ;
        current--;
        notifyAll ( ) ;
        System . out . format ( "%s:  ended pop() \n" , name ) ;
        return value ;
    }

    private synchronized void waitForNotEmpty ( ) throws InterruptedException
    {
        while (current==-1) {
//            System.out.println (Thread . currentThread ( ) . getName ( )
//                    + " is waiting to pop" ) ;
            wait();
        }
    }

    private synchronized void waitForNotFull ( ) throws InterruptedException
    {
        while (current==capacity-1) {
//            System . out . println (Thread . currentThread ( ) . getName ( )
//                    + " is waiting to push") ;
            wait ( ) ;
        }
    }
}

class Cook extends Thread {
    MilkyBar stack ;
    int value = 0 ;

    public Cook (MilkyBar meal ) {
        stack = meal ;
    }

    public void run ( ) {
        try {
            while ( true ) {
                sleep ( 100 ) ;
                stack.push(++value ) ;
                yield ( ) ;
            }
        }
        catch ( InterruptedException e ) {
            System.out.println (Thread . currentThread ( ) . getName ( )
                    + "  interrupted " ) ;
        }
    }
}

class Consumer extends Thread {
    MilkyBar stack ;
    int countMeals = 0;

    public Consumer (MilkyBar meal) {
        stack = meal ;
    }
    public void run ( ) {
        try {
            while ( true ) {
//                sleep ( 100 ) ;
                stack.pop() ;
                ++countMeals;
                yield ( ) ;
            }
        }
        catch ( InterruptedException e ) {
            System.out.println ("Konsument "
                    +Thread.currentThread().getName()
                    +" zjadł " + countMeals +" posiłków.\n"
                    +Thread.currentThread().getName()
                    + "  interrupted " ) ;
        }
    }
}



class MilkyBarDemo{
    public static void main(String [] args) throws Exception{
        MilkyBar meal = new MilkyBar( 10 ) ;
        Cook pu = new Cook (meal) ;
        Consumer po1 = new Consumer(meal) ;
        Consumer po2 = new Consumer(meal) ;
        Consumer po3 = new Consumer(meal) ;
        pu.start();
        po1.start();
        po2.start();
        po3.start();
        Thread.sleep(1000);
        pu.interrupt();
        po1.interrupt();
        po2.interrupt();
        po3.interrupt();
    }
}