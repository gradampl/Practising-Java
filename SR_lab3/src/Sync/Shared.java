package Sync;

public class Shared {
    public void foo ( String threadName , long time ) {
        System.out.println ( threadName +
                "  i s   running   foo ( )   f o r  "+time+" ms ,   c t : " +
                System.currentTimeMillis( ) ) ;
        long t = System.currentTimeMillis( ) ;
        while ( System.currentTimeMillis() - t < time ) {
            Math.atan ( System.currentTimeMillis( ) ) ;
        }
    }
    public void bar ( String threadName , long time ) {
        System.out.println ( threadName +
                "  i s   running  bar ( )   f o r  "+time+" ms ,   c t : " +
                System.currentTimeMillis( ) ) ;
        long t = System.currentTimeMillis( ) ;
        while ( System.currentTimeMillis()-t < time ) {
            Math . atan ( System.currentTimeMillis( ) ) ;
        }
    }
}

class ThreadA extends Thread {
        Shared shared ;
        ThreadA( Shared instance ) {
            this.shared = instance ;
        }
        public void run ( ) {
            shared.foo("A",5000) ;
            shared.bar("A",1000) ;
            System.out.println ( "A  i s  done" ) ;
        }
}

class ThreadB extends Thread {
        Shared shared ;
        ThreadB(Shared instance) {
            this.shared = instance ;
        }
        public void run ( ) {
            shared.foo( "B" , 1000 );
            shared.bar( "B" , 2000 );
            System.out.println ( "B  is  done" );
        }
}

public class JavaApplication1{

    public static void main(String[]args) {
// TODO code application logic here
        Shared shared = new Shared();
        ThreadA th1 = new ThreadA(shared);
        ThreadB th2 = new ThreadB(shared);
        long t = System.currentTimeMillis();
        th1.start();
        th2.start();
        try {
            th1.join();
            th2.join();
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        System.out.println("done in " + (System.currentTimeMillis() - t) + "ms");
    }
}

