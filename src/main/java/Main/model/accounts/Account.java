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

    public Account(String userName, String firstName, String lastName, String email, String phoneNumber, String passWord) throws AccountsException {
        AccountsException.validateUserName(userName);
        AccountsException.validateUsernameUniqueness(userName);
        this.userName = userName;
        AccountsException.validateNameTypeInfo("first name", firstName);
        this.firstName = firstName;
        AccountsException.validateNameTypeInfo("last name", lastName);
        this.lastName = lastName;
        AccountsException.validateEmail(email);
        this.email = email;
        AccountsException.validatePhoneNumber(phoneNumber);
        this.phoneNumber = phoneNumber;
        AccountsException.validatePassWord(passWord);
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
        throw new Exception("There is no user with given userName !\n");
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
