package Main.model.accounts;

import Main.model.exceptions.invalidInputException;

import java.util.ArrayList;

public class ManagerAccount extends Account {

    private static ArrayList<ManagerAccount> allManagers = new ArrayList<ManagerAccount>();

    public ManagerAccount(String userName, String firstName, String lastName, String email, String phoneNumber, String passWord) throws invalidInputException {

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

    public static void addManager(ManagerAccount managerAccount) {
    }

    public static boolean isThereManagerWithUserName(String userName) {
        return false;
    }

    public static String showManagersList() {

        return null;
    }

    public String viewMe() {
        return null;
    }

    public static ManagerAccount getManagerWithUserName(String userName) {
        return null;
    }

    public static void deleteManager(ManagerAccount managerAccount) {

    }

}
