package RMICalculator;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class CalculatorClient {
    public static void main(String[] args) {
        try {
            System.out.println("Getting registry");
            Registry reg = LocateRegistry.getRegistry("localhost");
            System.out.println("Getting interface");
            Calculator c = (Calculator) reg.lookup("CalculatorServer");
            System.out.println("Got interface");

            System.out.println(c.sub(4, 3));
            System.out.println(c.add(4, 5));
            System.out.println(c.mul(3, 6));
            System.out.println(c.div(9, 3));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
