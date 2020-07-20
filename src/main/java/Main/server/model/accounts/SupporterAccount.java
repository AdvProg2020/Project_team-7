package Main.server.model.accounts;

import java.util.ArrayList;

public class SupporterAccount extends Account {
    private static ArrayList<SupporterAccount> allSupporters = new ArrayList<>();

    public SupporterAccount(String userName,
                          String firstName,
                          String lastName,
                          String email,
                          String phoneNumber,
                          String passWord,
                          String profileImagePath) {
        super(userName, firstName, lastName, email, phoneNumber, passWord, profileImagePath);
        allSupporters.add(this);
        Account.allAccounts.add(this);
    }

    @Override
    public String editPersonalInfo(String field, String newContent) {
        return null;
    }

    @Override
    public String viewMe() {
        return null;
    }
}
