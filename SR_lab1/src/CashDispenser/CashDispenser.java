package CashDispenser;
import java.util.*;

class Account{
    protected int accountId;
    private double balance;
    protected int pin;

    Account(int id, float initialBalance, int startPin){
        accountId=id;
        balance=initialBalance;
        pin=startPin;
    }

    boolean IsPinOk(int customerPin){
        if(customerPin==pin){
            return true;
        }else
        return false;
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

/****************************************************************/

public class CashDispenser {

    public static void main(String[] args) {
        //System.out.println("Hello World !!");

        Account account1 = new Account(1,223000,2678);
        Account account2 = new Account(2,23000,4390);
        Account account3 = new Account(3,413312,5421);

        ArrayList<Account> accounts = new ArrayList<Account>();

        accounts.add(account1);
        accounts.add(account2);
        accounts.add(account3);

        System.out.println("Włóż kartę (tzn. podsj Id rachunku).");

        Scanner in = new Scanner(System.in);
        int Id = in.nextInt();
        boolean isPinOK = false;
        byte attempts = 0;

        do {
            ++attempts;
            System.out.println("Wprowadź pin.");

            Scanner inn = new Scanner(System.in);
            int inputPin = in.nextInt();

            //if(attempts<4){
                for(Account account : accounts){
                    if(account.accountId == Id){
                        isPinOK = account.IsPinOk(inputPin);
                        break;
                    }

                }
//            }else
//                break;

        }while(isPinOK == false && attempts<3);

        if(isPinOK == false){
            System.out.println("Twoja karta została zablokowana.");
        }
        else{
            while(true){
                System.out.println("Jaką operację chcesz wykonać?");
                System.out.println("Sprawdzić stan konta - wybierz 1.");
                System.out.println("Podjąć gotówkę z konta - wybierz 2.");
                System.out.println("Zakończyć - wybierz 3.");

                Scanner innn = new Scanner(System.in);
                int choice = in.nextInt();

                switch(choice){
                    case 1: System.out.println("Stan Twojego konta wynosi " + accounts.get(Id).CheckBalance());
                        break;
                    case 2: System.out.println("Jaką sumę chcesz podjąć?");
                        Scanner inn = new Scanner(System.in);
                        double sum = in.nextDouble();
                        boolean notTooMuch = false;
                        notTooMuch = accounts.get(Id).canWithdraw(sum);
                        if(notTooMuch){
                            System.out.println("Podjąłeś " + sum + ". Stan Twojego konta obecnie wynosi " + accounts.get(Id).SetBalance(sum));
                        }
                        else{
                            System.out.println("Nie możesz podjąć aż tyle, gdyż Stan Twojego konta wynosi " + accounts.get(Id).CheckBalance());
                        }
                        break;
                    case 3:  System.exit(0); break;
                    default: System.out.println("Nie ma takiej opcji."); break;
            }

            }
        }
    }
}
