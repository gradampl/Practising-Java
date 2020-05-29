package Bank;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            System.out.println("Getting registry");
            Registry reg = LocateRegistry.getRegistry("localhost");
            System.out.println("Getting interface");
            BankService atm = (BankService) reg.lookup("BankServer");
            System.out.println("Got interface");

            while (true) {
                Scanner in = new Scanner(System.in);
                System.out.println("\nPodaj numer konta: ");
                int num = in.nextInt();
                System.out.println("Podaj numer PIN: ");
                int pin = in.nextInt();
                String token = atm.authorize(num, pin);
                if (token == null) {
                    System.out.println("Wprowadzono błędne dane.");
                } else {
                    while (token != null) {

                        System.out.println("\n1. sprawdź stan konta");
                        System.out.println("2. wpłać gotówkę na konto");
                        System.out.println("3. zrób przelew");
                        System.out.println("4. zakończ");

                        String choice = in.next();
                        switch (choice) {

                            case "1":
                                double balance = atm.getBalance(token);
                                System.out.println("Stan konta: " + balance);
                                break;

                            case "2":
                                System.out.println("Ile chcesz wpłacić: ");
                                double inSum = in.nextDouble();
                                atm.deposit(token, inSum);
                                break;

                            case "3":
                                System.out.println("Ile chcesz przelać: ");
                                double sendSum = in.nextDouble();
                                System.out.println("Numer konta, na które chcesz przelać: ");
                                int nr = in.nextInt();
                                if(atm.transfer(token, nr, sendSum)){
                                    System.out.println("Na konto nr "+nr+" przelano "+sendSum+" zł.");
                                }
                                else
                                    System.out.println("Nie możesz tyle przelać.");
                                break;

                            case "4":
                                token = atm.bye(token);
                                break;

                            default:
                                System.out.println("Nie ma takiej opcji.");
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
