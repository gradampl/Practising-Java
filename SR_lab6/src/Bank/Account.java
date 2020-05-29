package Bank;

public class Account {
    protected int id;
    protected double balance;
    protected int pinCode;

    public Account(int id, float initialBalance, int startPin){
        this.id=id;
        balance=initialBalance;
        pinCode=startPin;
    }

    public double SetBalance(double amount){
        double newBalance = balance + amount;
        balance = newBalance;
        return balance;
    }
}
