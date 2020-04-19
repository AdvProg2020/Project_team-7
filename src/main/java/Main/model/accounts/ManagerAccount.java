package Main.model.accounts;

public class ManagerAccount extends Account {
    public ManagerAccount(String userName, String firstName, String lastName, String email, String phoneNumber, String passWord) {

    }

    public static boolean isThereManagerWithUserName(String userName) {
        return false;
    }

    public static String showManagersList() {
        return null;
    }

    public static ManagerAccount getManagerWithUserName(String userName) {
        return null;
    }

    public static void deleteManager(ManagerAccount managerAccount) {

    }

    public static void addManager(ManagerAccount managerAccount) {

    }
}
