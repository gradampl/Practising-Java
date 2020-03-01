package HelloWorld;
import java.util.*;
public class HelloWorld {


    int[] myNum;

    public void ReadNumber(){
        Scanner in = new Scanner(System.in);
        System.out.println("Podaj liczbę całkowitą");
        int a = in.nextInt();
        System.out.println("Podales liczbę: " + a + ".");
    }

    public void FillArray(){

        myNum = new int[11];

        for(int i=0;i<=10;i++){
            myNum [i]=i;
        }
    }

    public void ReadInversely(){
        System.out.println("================================");
        System.out.println("Wypiszę teraz tablicę od końca.");
        System.out.println("================================");
        for(int i=10; i>=0;i--){
            System.out.println(myNum[i]);
        }
    }


    public static void main(String[] args) {
        System.out.println("Hello World !!");

        HelloWorld h = new HelloWorld();

        h.ReadNumber();
        h.FillArray();
        h.ReadInversely();
    }
}
