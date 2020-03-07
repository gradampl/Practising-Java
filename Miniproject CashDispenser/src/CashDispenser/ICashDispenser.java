package CashDispenser;

public interface ICashDispenser {

    boolean isNumeric(String strNum);

    String getcNumber();

    String getPin();

    boolean isPinOk(Account account, String pin);

    String getChoice();

    boolean checkChoice(Account account, String choice);

    void Start(Account account, String choice);
}
