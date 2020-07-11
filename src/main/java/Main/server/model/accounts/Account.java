package Main.server.model.accounts;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public abstract class Account {
    protected String userName;
    protected String firstName;
    protected String lastName;
    protected String email;
    protected String phoneNumber;
    protected String passWord;
    protected String profileImagePath;
    protected static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    protected static ArrayList<Account> allAccounts = new ArrayList<>();
    private static ArrayList<String> reservedUserNames = new ArrayList<>();

    public Account(String userName, String firstName, String lastName, String email, String phoneNumber, String passWord, String profileImagePath) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.passWord = passWord;
        this.profileImagePath = profileImagePath;
        if(profileImagePath==null){
            this.profileImagePath = "src/main/java/Main/graphicView/resources/images/avatars/1.png";
        }
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
        throw new Exception("There is no user with userName : " + userName + "\n");
    }

    public boolean isPassWordCorrect(String passWord) {
        return passWord.equals(this.passWord);
    }

    public abstract String editPersonalInfo(String field, String newContent);

    public abstract String viewMe();

    public static ArrayList<String> getReservedUserNames() {
        return reservedUserNames;
    }

    public static boolean isThereReservedUserName(String userName) {
        for (String reservedUserName : reservedUserNames) {
            if (reservedUserName.equals(userName))
                return true;
        }
        return false;
    }

    public String getPassWord() {
        return passWord;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setProfileImagePath(String profileImagePath) {
        this.profileImagePath = profileImagePath;
    }

    public static ArrayList<Account> getAllAccounts() {
        return allAccounts;
    }

    public String getProfileImagePath() {
        return profileImagePath;
    }
}
