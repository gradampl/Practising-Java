package Bank;

public class Bank {

    protected static Account AccountsRegister(int id){
        int maxNumOfAccounts = 4;

        Account[] account = new Account[maxNumOfAccounts];

        for(int i = 0; i<maxNumOfAccounts;i++){
            account[i] = new Account(i+1, 223000, 2678+i);
        }

        return account[id-1];
    }
}

