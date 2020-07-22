package Main.server.model.accounts;

import java.util.ArrayList;
import java.util.HashMap;

public class SupporterAccount extends Account {
    private static ArrayList<SupporterAccount> allSupporters = new ArrayList<>();
    private ArrayList<BuyerAccount> myChats = new ArrayList<>();

    //first string buyer username, second the message number and third message text.
    private HashMap<String,ArrayList<String>> myChatsMessages = new HashMap<>();

    public HashMap<String, ArrayList<String>> getMyChatsMessages() {
        return myChatsMessages;
    }

    public void setMyChatsMessages(String buyerUserName, ArrayList<String> messages){
        myChatsMessages.put(buyerUserName, messages);
    }

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

    public void addBuyerToChatList(String buyerUserName) throws Exception {
        if (!myChats.contains(BuyerAccount.getBuyerWithUserName(buyerUserName)))
            myChats.add(BuyerAccount.getBuyerWithUserName(buyerUserName));
    }

    public ArrayList<String> myChatsList() {
        ArrayList<String> list = new ArrayList<>();
        for (BuyerAccount myChat : myChats) {
            list.add("@" + myChat.userName + "\n" + myChat.firstName + "\t" + myChat.lastName);
        }
        return list;
    }

    public static ArrayList<String> allSupportersForHelpCenter() {
        ArrayList<String> allSupportersInfo = new ArrayList<>();
        for (SupporterAccount supporterAccount : allSupporters) {
            String supporterInfo = "";
            supporterInfo = supporterInfo.concat("SUPPORTER: @" + supporterAccount.userName + "\n" + supporterAccount.firstName + " " + supporterAccount.lastName);
            allSupportersInfo.add(supporterInfo);
        }
        return allSupportersInfo;
    }

    @Override
    public String editPersonalInfo(String field, String newContent) {
        return null;
    }

    @Override
    public String viewMe() {
        return "Supporter :\n\tfirst name : " + this.firstName + "\n\tlast name : " + this.lastName + "\n\tuser name : " + this.userName +
                "\n\temail : " + this.email + "\n\tphone number : " + this.phoneNumber + "\n";
    }

    public void deleteSupporter() {
        allSupporters.remove(this);
        allAccounts.remove(this);
    }
}
