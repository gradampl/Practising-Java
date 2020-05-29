package Bank;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface BankService extends Remote {
    String authorize (int account, int pin) throws RemoteException;
    double getBalance (String token) throws RemoteException;
    void deposit (String token, double value) throws RemoteException;
    boolean withdraw (String token, double value) throws RemoteException;
    boolean transfer(String token, int account, double value) throws RemoteException;
    String bye (String token) throws RemoteException;
}
