package Main.model.accounts;

import java.util.ArrayList;

public abstract class Account {
    protected String userName = null;
    protected String firstName = null;
    protected String lastName = null;
    protected String email = null;
    protected String phoneNumber = null;
    protected String passWord = null;
    protected static ArrayList<Account> allAccounts = new ArrayList<Account>();

    public static boolean isThereUserWithUserName(String userName) {
        for (Account account : allAccounts) {
            if (account.userName.equals(userName)) {
                return true;
            }
        }
        return false;
    }
}
