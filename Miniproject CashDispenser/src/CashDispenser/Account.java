package CashDispenser;

public class Account implements IAccount{

    protected String cardNumber;
    private double balance;
    protected String pinCode;

    Account(String id, float initialBalance, String startPin){
        cardNumber=id;
        balance=initialBalance;
        pinCode=startPin;
    }

    public double CheckBalance(){
        return balance;
    }

    public boolean canWithdraw(double amountToWithdraw){
        if(amountToWithdraw<=balance){
            return true;
        }else
            return false;
    }

    public double SetBalance(double amountToWithdraw){
        double newBalance = balance - amountToWithdraw;
        balance = newBalance;
        return balance;
    }
}
