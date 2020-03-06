package CashDispenser;

import java.util.ArrayList;
import java.util.Scanner;

public class Bank {

    public static void main(String[] args) {

        Account account1 = new Account("1", 223000, "2678");
        Account account2 = new Account("2", 23000, "4390");
        Account account3 = new Account("3", 413312, "5421");

        ArrayList<Account> accounts = new ArrayList<Account>();

        accounts.add(account1);
        accounts.add(account2);
        accounts.add(account3);

        CashDispenser cashDispenser = new CashDispenser();

        String cNumber = cashDispenser.getcNumber();

        while ( !cashDispenser.isNumeric(cNumber)
                || (Integer.parseInt(cNumber)) < 1
                || (Integer.parseInt(cNumber) > accounts.size()
                || cNumber.isEmpty()))
        {
            cNumber = cashDispenser.getcNumber();
        }


        Account account = accounts.get(Integer.parseInt(cNumber) - 1);

        String pin;

        int attempts = 0;

        do {
            ++attempts;
            pin = cashDispenser.getPin();
        }
        while (!cashDispenser.isPinOk(account,pin) && attempts < 3);

        if (!cashDispenser.isPinOk(account,pin)) {
            System.out.println("Twoja karta została zablokowana.");
        } else {
            while (true) {
                String choice = cashDispenser.getChoice();

                if (!cashDispenser.checkChoice(account,choice)) {
                    System.out.println("Proszę wybrać jedną z wartości: 1, 2 lub 3!");
                }
                else {
                    cashDispenser.Start(account, choice);
                }
            }
        }
    }
}


