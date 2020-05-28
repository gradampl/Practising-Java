package RMICalculator;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class CalculatorServer extends UnicastRemoteObject
        implements Calculator {
    public CalculatorServer() throws RemoteException {
    }

    public float add(float a, float b)
            throws RemoteException {
        return a + b;
    }

    public float sub(float a, float b)
            throws RemoteException {
        return a - b;
    }

    public float mul(float a, float b)
            throws RemoteException {
        return a * b;
    }

    public float div(float a, float b)
            throws RemoteException {
        return a / b;
    }

    public static void main(String args[]) throws RemoteException {
        try {
            CalculatorServer obj = new CalculatorServer();
            UnicastRemoteObject.unexportObject(obj, true);
            System.out.println("Exporting...");
            Calculator stub = (Calculator) UnicastRemoteObject.exportObject(obj, 0);
            Registry registry;
            if (true) {
// stworzenie nowego rejestru na localhoscie
                registry = LocateRegistry.createRegistry(1099);
                System.out.println("New registry created on localhost");
            } else {
                System.out.println("Locating registry...");
// podlaczenie do istniejacego rejestru na localhoscie
                registry = LocateRegistry.getRegistry("localhost ");
                System.out.println("Registry located");
            }
            registry.rebind("CalculatorServer", stub);
            System.out.println("Calculator bound, ready ");
        } catch (Exception e) {
            System.err.println("Calculator exception: ");
            e.printStackTrace();
        }
    }
}


