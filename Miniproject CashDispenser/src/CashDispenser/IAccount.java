package CashDispenser;

public interface IAccount {

    double CheckBalance();

    boolean canWithdraw(double amountToWithdraw);

    double SetBalance(double amountToWithdraw);
}
