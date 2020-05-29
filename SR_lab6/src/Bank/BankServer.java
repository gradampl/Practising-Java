package Bank;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class BankServer extends UnicastRemoteObject
        implements BankService {

    public BankServer() throws RemoteException {
    }

    public Account getAccount(String token) throws RemoteException{
        int accId = Integer.parseInt(token.substring(0,1));
        Account acc = Bank.AccountsRegister(accId);
        return acc;
    }

    public String authorize (int account, int pin) throws RemoteException{
        Account acc = Bank.AccountsRegister(account);
        boolean isPinOk = (pin == acc.pinCode);
        if(isPinOk){
            String token = String.valueOf(account);
            token += (String)TokenGenerator.generateNewToken();
            return token;
        }
        else
        return null;
    }

    public double getBalance (String token)throws RemoteException{
        Account acc = getAccount(token);
        return acc.balance;
    }

    public void deposit(String token,double amount)throws RemoteException{
        Account acc = getAccount(token);
        System.out.println("Na konto nr "+acc.id+" wpłacono "+amount+" zł.");
    }

    public boolean withdraw (String token, double value)throws RemoteException{
        Account acc = getAccount(token);
        boolean canWithdraw = true;
        if(acc.balance<value)
            canWithdraw = false;
        return canWithdraw;
    }

    public boolean transfer(String token, int account, double value)throws RemoteException{
        Account acc = getAccount(token);
        boolean canTransfer = false;
        if(withdraw(token,value))
           canTransfer = true;
        return canTransfer;
    }

    public String bye (String token)throws RemoteException{
        return null;
    }

    public static void main(String args[]) throws RemoteException {
        try {
            BankServer obj = new BankServer();
            UnicastRemoteObject.unexportObject(obj,true);
            System.out.println("Exporting...");
            BankService stub = (BankService) UnicastRemoteObject.exportObject(obj, 0);
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
            registry.rebind("BankServer", stub);
            System.out.println("Bank bound, ready ");
        } catch (Exception e) {
            System.err.println("Bank exception: ");
            e.printStackTrace();
        }
    }
}
