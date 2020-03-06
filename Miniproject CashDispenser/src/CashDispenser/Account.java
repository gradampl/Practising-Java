package CashDispenser;

public class Account {

    protected String cardNumber;
    private double balance;
    protected String pinCode;

    Account(String id, float initialBalance, String startPin){
        cardNumber=id;
        balance=initialBalance;
        pinCode=startPin;
    }

    double CheckBalance(){
        return balance;
    }

    boolean canWithdraw(double amountToWithdraw){
        if(amountToWithdraw<=balance){
            return true;
        }else
            return false;
    }

    double SetBalance(double amountToWithdraw){
        double newBalance = balance - amountToWithdraw;
        balance = newBalance;
        return balance;
    }
}
