package RMICalculator;

public class CalculatorImpl extends
        java.rmi.server.UnicastRemoteObject
        implements Calculator{
    // Implementations must have an
    //explicit constructor
    // in order to declare the
    //RemoteException exception
    public CalculatorImpl()
            throws java.rmi.RemoteException {
        super();
    }

    public float add(float a, float b)
            throws java.rmi.RemoteException {
        return a + b;
    }

    public float sub(float a, float b)
            throws java.rmi.RemoteException {
        return a - b;
    }

    public float mul(float a, float b)
            throws java.rmi.RemoteException {
        return a * b;
    }

    public float div(float a, float b)
            throws java.rmi.RemoteException {
        return a / b;
    }
}
