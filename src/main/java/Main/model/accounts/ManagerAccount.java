package Main.model.accounts;

import Main.controller.GeneralController;
import Main.model.sorting.UsersSort;
import com.gilecode.yagson.com.google.gson.stream.JsonReader;

import java.io.*;
import java.util.ArrayList;

import static java.util.Arrays.asList;

public class ManagerAccount extends Account {

    private static ArrayList<ManagerAccount> allManagers = new ArrayList<>();

    public ManagerAccount(String userName,
                          String firstName,
                          String lastName,
                          String email,
                          String phoneNumber,
                          String passWord) {
        super(userName, firstName, lastName, email, phoneNumber, passWord);
    }

    public static ArrayList<ManagerAccount> getAllManagers() {
        return allManagers;
    }

    public static ArrayList<String> allManagersForGraphic(){
        ArrayList<String> allManagersInfo = new ArrayList<>();
        for (ManagerAccount manager : allManagers) {
            String managerInfo = "";
            managerInfo = managerInfo.concat("MANAGER: @" + manager.userName + "\n" + manager.firstName + " " + manager.lastName);
            allManagersInfo.add(managerInfo);
        }
        return allManagersInfo;
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
        if (GeneralController.currentUserSort.equalsIgnoreCase("first name A-Z"))
            allManagers.sort(new UsersSort.usersSortByFirstNameAscending());
        else if (GeneralController.currentUserSort.equalsIgnoreCase("first name Z-A"))
            allManagers.sort(new UsersSort.usersSortByFirstNameDescending());
        else if (GeneralController.currentUserSort.equalsIgnoreCase("last name A-Z"))
            allManagers.sort(new UsersSort.usersSortByLastNameAscending());
        else if (GeneralController.currentUserSort.equalsIgnoreCase("last name Z-A"))
            allManagers.sort(new UsersSort.usersSortByLastNameDescending());
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
        throw new Exception("There is no manager with user name : " + userName + "\n");
    }

    public static void deleteManager(ManagerAccount managerAccount) {
        allManagers.remove(managerAccount);
        allAccounts.remove(managerAccount);
    }

    public static boolean isThereAChiefManager(){
        return !allManagers.isEmpty();
    }

    public static String readData() {
        try {
            GeneralController.jsonReader = new JsonReader(new FileReader(new File("src/main/JSON/managers.json")));
            ManagerAccount[] allMan = GeneralController.yagsonMapper.fromJson(GeneralController.jsonReader, ManagerAccount[].class);
            allManagers = (allMan == null) ? new ArrayList<>() : new ArrayList<>(asList(allMan));
            allAccounts.addAll(allManagers);
            return "Read Managers Data Successfully.";
        } catch (FileNotFoundException e) {
            return "Problem loading data from managers.json";
        }
    }

    public static String writeData() {
        try {
            GeneralController.fileWriter = new FileWriter("src/main/JSON/managers.json");
            ManagerAccount[] allMan = new ManagerAccount[allManagers.size()];
            allMan = allManagers.toArray(allMan);
            GeneralController.yagsonMapper.toJson(allMan, ManagerAccount[].class, GeneralController.fileWriter);
            GeneralController.fileWriter.close();
            return "Saved Managers Data Successfully.";
        } catch (IOException e) {
            return "Problem saving managers data.";
        }
    }
}
