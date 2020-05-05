package Main.model.accounts;

import Main.model.exceptions.InvalidInputException;

import java.util.ArrayList;

public abstract class Account {
    protected String userName;
    protected String firstName;
    protected String lastName;
    protected String email;
    protected String phoneNumber;
    protected String passWord;
    protected static ArrayList<Account> allAccounts = new ArrayList<Account>();

    public Account(String userName, String firstName, String lastName, String email, String phoneNumber, String passWord) throws InvalidInputException {
        InvalidInputException.validateNameTypeInfo("user name", userName);
        InvalidInputException.validateUsernameUniqueness(userName);
        this.userName = userName;
        InvalidInputException.validateNameTypeInfo("first name", firstName);
        this.firstName = firstName;
        InvalidInputException.validateNameTypeInfo("last name", lastName);
        this.lastName = lastName;
        InvalidInputException.validateEmail(email);
        this.email = email;
        InvalidInputException.validatePhoneNumber(phoneNumber);
        this.phoneNumber = phoneNumber;
        InvalidInputException.validatePassWord(passWord);
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

    public static Account getUserWithUserName(String userName) {
        for (Account account : allAccounts) {
            if (account.userName.equals(userName)) {
                return account;
            }
        }
        return null;
    }
}
