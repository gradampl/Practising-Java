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
        waitForNotFull ( ) ;
        current++;
        data [current ] = number ;
        System . out . format ( "Kucharz przygotował posiłek " + number+ "\n" ) ;
        notifyAll ( ) ;
    }

    public synchronized int pop ( ) throws InterruptedException
    {
        waitForNotEmpty();
        int value = data [current] ;
        System . out . format ("Konsument "
                        + Thread.currentThread().getName()
                        +" zjadł posiłek "
                        + value +".\n");
        current--;
        notifyAll ( ) ;
        return value ;
    }

    private synchronized void waitForNotEmpty ( ) throws InterruptedException
    {
        while (current==-1) {
            System.out.println ("Konsument "
                    +Thread . currentThread ( ) . getName ( )
                    + " czeka na posiłek." ) ;
            wait();
        }
    }

    private synchronized void waitForNotFull ( ) throws InterruptedException
    {
        while (current==capacity-1) {
            System . out . println ("Kucharz "
                    +Thread . currentThread ( ) . getName ( )
                    + " przygotowuje posiłek.") ;
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