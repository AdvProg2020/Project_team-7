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
        if(!allManagers.contains(managerAccount)) {
            allManagers.add(managerAccount);
            allAccounts.add(managerAccount);
        }
    }

    public static boolean isThereManagerWithUserName(String userName) {
        for (ManagerAccount manager : allManagers) {
            if (manager.userName.equals(userName)) {
                return true;
            }
        }
        return false;
    }

    public static String showManagersList() {
        StringBuilder managersList = new StringBuilder();
        managersList.append("Mangers :\n");
        for (ManagerAccount manager : allManagers) {
            String tempManagerInfo = "\tuser name : " + manager.userName + "\tfull name : " + manager.firstName + " " + manager.lastName + "\n";
            managersList.append(tempManagerInfo);
        }
        return managersList.toString();
    }

    public String viewMe() {
        return "Manager :\n\tfirst name : " + this.firstName + "\n\tlast name : " + this.lastName + "\n\tuser name : " + this.userName +
                "\n\temail : " + this.email + "\n\tphone number : " + this.phoneNumber + "\n";
    }

    public static ManagerAccount getManagerWithUserName(String userName) {
        for (ManagerAccount manager : allManagers) {
            if (manager.userName.equals(userName)) {
                return manager;
            }
        }
        return null;
    }

    public static void deleteManager(ManagerAccount managerAccount) {
        allManagers.remove(managerAccount);
        allAccounts.remove(managerAccount);
    }

}
