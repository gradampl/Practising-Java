package CashDispenser;

import java.util.*;
import java.util.regex.Pattern;

public class CashDispenser implements ICashDispenser{

    Scanner in = new Scanner(System.in);

    private Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

    public boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }

    public String getcNumber() {
        System.out.println("Podaj numer karty.");
        String cNumber = in.next();
        return cNumber;
    }

    public String getPin() {
        System.out.println("Podaj PIN.");
        String pin = in.next();
        return pin;
    }

    public boolean isPinOk(Account account, String pin){
        if(pin.equals(account.pinCode))
            return true;
        else return false;
    }

    public String getChoice() {

        System.out.println("Jaką operację chcesz wykonać?");
        System.out.println("Sprawdzić stan konta - wybierz 1.");
        System.out.println("Podjąć gotówkę z konta - wybierz 2.");
        System.out.println("Zakończyć - wybierz 3.");

        String choice = in.next();
        return choice;
    }

    public boolean checkChoice(Account account, String choice) {
        if (!isNumeric(choice) || choice.isEmpty()) {
            return false;
        } else return true;
    }

    public void Start(Account account, String choice) {
        switch (choice) {
            case "1":
                System.out.println("Stan Twojego konta wynosi " + account.CheckBalance());
                break;
            case "2":
                System.out.println("Jaką sumę chcesz podjąć?");
                String howMuch = in.next();

                if(!isNumeric(howMuch)){
                    System.out.println("Podaj kwotę wpisując liczbę.");
                    break;
                }
                else{
                    double sum = Double.parseDouble(howMuch);
                    boolean notTooMuch = false;
                    notTooMuch = account.canWithdraw(sum);

                    if (notTooMuch && !(sum<= 0)) {
                        System.out.println("Podjąłeś " + Double.parseDouble(howMuch)
                                + ". Stan Twojego konta obecnie wynosi "
                                + account.SetBalance(Double.parseDouble(howMuch)));
                    }
                    else if(sum<=0){
                        System.out.println("Podaj kwotę większą od zera.");
                    }
                    else {
                        System.out.println("Nie możesz podjąć aż tyle, gdyż Stan Twojego konta wynosi "
                                + account.CheckBalance());
                    }
                    break;
                }
            case "3":
                System.exit(0);

            default:
                System.out.println("Nie ma takiej opcji.");
                break;
        }
    }
}





