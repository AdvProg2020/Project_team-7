package Main.model.accounts;

import Main.model.exceptions.invalidInputException;

import java.util.ArrayList;

public abstract class Account {
    protected String userName;
    protected String firstName;
    protected String lastName;
    protected String email;
    protected String phoneNumber;
    protected String passWord;
    protected static ArrayList<Account> allAccounts = new ArrayList<Account>();

    public Account (String userName, String firstName, String lastName, String email, String phoneNumber, String passWord) throws invalidInputException {
        invalidInputException.validateNameTypeInfo("user name", userName);
        invalidInputException.validateUsernameUniqueness(userName);
        this.userName = userName;
        invalidInputException.validateNameTypeInfo("first name", firstName);
        this.firstName = firstName;
        invalidInputException.validateNameTypeInfo("last name", lastName);
        this.lastName = lastName;
        invalidInputException.validateEmail(email);
        this.email = email;
        invalidInputException.validatePhoneNumber(phoneNumber);
        this.phoneNumber = phoneNumber;
        invalidInputException.validatePassWord(passWord);
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
}
