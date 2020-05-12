package Main.model.accounts;

import Main.model.exceptions.AccountsException;

import java.util.ArrayList;

public class ManagerAccount extends Account {

    private static ArrayList<ManagerAccount> allManagers = new ArrayList<ManagerAccount>();

    public ManagerAccount(String userName,
                          String firstName,
                          String lastName,
                          String email,
                          String phoneNumber,
                          String passWord) throws AccountsException {
        super(userName, firstName, lastName, email, phoneNumber, passWord);
    }

    public static ArrayList<ManagerAccount> getAllManagers() {
        return allManagers;
    }

    public String editPersonalInfo(String field, String newContent) {
        if (field.equalsIgnoreCase("username"))
            return "you are not allowed to edit this";
        else {
            if (field.equalsIgnoreCase("first name"))
                firstName = newContent;
            else if (field.equalsIgnoreCase("last name"))
                lastName = newContent;
            else if (field.equalsIgnoreCase("email"))
                email = newContent;
            else if (field.equalsIgnoreCase("phone number"))
                phoneNumber = newContent;
            else if (field.equalsIgnoreCase("passWord"))
                passWord = newContent;
            else return "wrong field to edit";
            return "edit done successfully.";
        }
    }

    public static void addManager(ManagerAccount managerAccount) {
        allManagers.add(managerAccount);
        allAccounts.add(managerAccount);
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

    public static ManagerAccount getManagerWithUserName(String userName) throws Exception {
        for (ManagerAccount manager : allManagers) {
            if (manager.userName.equals(userName)) {
                return manager;
            }
        }
        throw new Exception("There is no manager with this user name");
    }

    public static void deleteManager(ManagerAccount managerAccount) {
        allManagers.remove(managerAccount);
        allAccounts.remove(managerAccount);
    }

}
