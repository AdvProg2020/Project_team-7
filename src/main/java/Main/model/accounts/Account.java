package Main.model.accounts;

import Main.model.exceptions.AccountsException;

import java.util.ArrayList;

public abstract class Account {
    protected String userName;
    protected String firstName;
    protected String lastName;
    protected String email;
    protected String phoneNumber;
    protected String passWord;
    protected static ArrayList<Account> allAccounts = new ArrayList<Account>();

    public Account(String userName, String firstName, String lastName, String email, String phoneNumber, String passWord) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.passWord = passWord;
    }

    public static boolean isThereUserWithUserName(String userName) {
        for (Account account : allAccounts) {
            if (account.userName.equals(userName)) {
                return true;
            }
        }
        return false;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getType() {
        if (this instanceof BuyerAccount)
            return "Buyer";
        else if (this instanceof SellerAccount)
            return "Seller";
        else if (this instanceof ManagerAccount)
            return "Manager";
        return null;
    }

    public String getUserName() {
        return userName;
    }

    public static Account getUserWithUserName(String userName) throws Exception {
        for (Account account : allAccounts) {
            if (account.userName.equals(userName)) {
                return account;
            }
        }
        throw new Exception("There is no user with userName : " + userName +"\n");
    }

    public boolean isPassWordCorrect(String passWord) {
        if (passWord.equals(this.passWord))
            return true;
        else
            return false;
    }

    public abstract String editPersonalInfo(String field, String newContent);

    public abstract String viewMe();
}
