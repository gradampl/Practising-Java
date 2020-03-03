package CashDispenser;
import java.util.*;
import java.util.regex.Pattern;

class Account{
    protected String cardNumber;
    private double balance;
    protected String pinCode;

    Account(String id, float initialBalance, String startPin){
        cardNumber=id;
        balance=initialBalance;
        pinCode=startPin;
    }

    boolean numExists(String num){
        if(num.equals(cardNumber)){
            return true;
        }
        else
            return false;
    }

    boolean IsPinOk(String customerPin){
        if(customerPin.equals(pinCode)){
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

    private Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

    public boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }


    public static void main(String[] args) {
        //System.out.println("Hello World !!");

        Account account1 = new Account("1",223000,"2678");
        Account account2 = new Account("2",23000,"4390");
        Account account3 = new Account("3",413312,"5421");

        ArrayList<Account> accounts = new ArrayList<Account>();

        accounts.add(account1);
        accounts.add(account2);
        accounts.add(account3);


        System.out.println("Podaj numer karty.");
        Scanner in = new Scanner(System.in);
        String cNumber = in.next();
        CashDispenser numberCheck = new CashDispenser();
        boolean cardExists = true;

        for(Account account : accounts){
            if(!account.numExists(cNumber)){
                cardExists = false;
            }
            else{
                cardExists=true;
                break;
            }
        }

        while(cNumber.isEmpty() || ! numberCheck.isNumeric(cNumber) || !cardExists){
            System.out.println("Podaj numer karty.");
            cNumber = in.next();

            for(Account account : accounts){
                if(!account.numExists(cNumber)){
                    cardExists = false;
                }
                else{
                    cardExists=true;
                    break;
                }
            }
        }


        boolean isPinOK = false;
        byte attempts = 0;

        do {
            ++attempts;
            System.out.println("Wprowadź pin.");

            String inputPin = in.next();

            for(Account account : accounts){
                if(account.cardNumber.equals(cNumber)){
                    isPinOK = account.IsPinOk(inputPin);
                    break;
                }
            }


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

                String choice = in.next();

                CashDispenser choiceCheck = new CashDispenser();

                if(!choiceCheck.isNumeric(choice) || choice.isEmpty()){
                    System.out.println("Proszę wybrać jedną z wartości: 1, 2 lub 3!");
                }
                else{
                    switch(choice){
                        case "1": System.out.println("Stan Twojego konta wynosi " + accounts.get(Integer.parseInt(cNumber)).CheckBalance());
                            break;
                        case "2": System.out.println("Jaką sumę chcesz podjąć?");
                            Scanner inn = new Scanner(System.in);
                            double sum = in.nextDouble();
                            boolean notTooMuch = false;
                            notTooMuch = accounts.get(Integer.parseInt(cNumber)).canWithdraw(sum);
                            if(notTooMuch && !(sum<=0)){
                                System.out.println("Podjąłeś " + sum + ". Stan Twojego konta obecnie wynosi " + accounts.get(Integer.parseInt(cNumber)).SetBalance(sum));
                            }
                            else if(sum<=0){
                                System.out.println("Podaj kwotę, która będzie większa od zera.");
                            }
                            else{
                                System.out.println("Nie możesz podjąć aż tyle, gdyż Stan Twojego konta wynosi " + accounts.get(Integer.parseInt(cNumber)).CheckBalance());
                            }
                            break;
                        case "3":  System.exit(0); break;
                        default: System.out.println("Nie ma takiej opcji."); break;
                    }
                }
            }
        }
    }
}
