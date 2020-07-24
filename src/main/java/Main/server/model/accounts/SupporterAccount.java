package Main.server.model.accounts;

import Main.server.controller.GeneralController;
import com.gilecode.yagson.com.google.gson.stream.JsonReader;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

import static java.util.Arrays.asList;

public class SupporterAccount extends Account {
    private static ArrayList<SupporterAccount> allSupporters = new ArrayList<>();
    private ArrayList<String> myChats = new ArrayList<>();
    private HashMap<String, ArrayList<String>> myChatsMessages = new HashMap<>();

    public HashMap<String, ArrayList<String>> getMyChatsMessages() {
        return myChatsMessages;
    }

    public void setMyChatsMessages(String buyerUserName, ArrayList<String> messages) {
        if (messages.size() >= myChatsMessages.get(buyerUserName).size())
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
        if (!myChats.contains(buyerUserName))
            myChats.add(buyerUserName);
    }

    public ArrayList<String> myChatsList() throws Exception {
        ArrayList<String> list = new ArrayList<>();
        for (String myChat : myChats) {
            BuyerAccount buyerAccount = BuyerAccount.getBuyerWithUserName(myChat);
            list.add("@" + myChat + "\n" + buyerAccount.firstName + "\t" + buyerAccount.lastName);
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

    public static String readData() {
        try {
            GeneralController.jsonReader = new JsonReader(new FileReader(new File("src/main/JSON/supporters.json")));
            SupporterAccount[] allSup = GeneralController.yagsonMapper.fromJson(GeneralController.jsonReader, SupporterAccount[].class);
            allSupporters = (allSup == null) ? new ArrayList<>() : new ArrayList<>(asList(allSup));
            allAccounts.addAll(allSupporters);
            return "Read Supporters Data Successfully.";
        } catch (FileNotFoundException e) {
            return "Problem loading data from supporters.json";
        }
    }

    public static String writeData() {
        try {
            GeneralController.fileWriter = new FileWriter("src/main/JSON/supporters.json");
            SupporterAccount[] allSup = new SupporterAccount[allSupporters.size()];
            allSup = allSupporters.toArray(allSup);
            GeneralController.yagsonMapper.toJson(allSup, SupporterAccount[].class, GeneralController.fileWriter);
            GeneralController.fileWriter.close();
            return "Saved Supporters Data Successfully.";
        } catch (IOException e) {
            return "Problem saving supporters data.";
        }
    }
}
