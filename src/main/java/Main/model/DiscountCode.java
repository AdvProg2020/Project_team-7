package Main.model;

import Main.model.accounts.BuyerAccount;

import javax.swing.*;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;

public class DiscountCode {
    private String code;
    private Time startTime;
    private Time endTime;
    private double percent;
    private double maxAmount;
    private int maxNumberOfUse;
    private HashMap<BuyerAccount,Integer> users = new HashMap<BuyerAccount, Integer>();
    private static ArrayList<DiscountCode> allDiscountCodes = new ArrayList<DiscountCode>();

    public DiscountCode(String code, String startTime, String endTime, double percent, double maxAmount, int maxNumberOfUse, ArrayList<BuyerAccount> users) {
        this.code=code;
        this.setStartTime(startTime);
        this.setEndTime(endTime);
        this.percent=percent;
        this.maxAmount=maxAmount;
        this.maxNumberOfUse=maxNumberOfUse;
        for (BuyerAccount user : users) {
            this.users.put(user,maxNumberOfUse);
        }
    }

    public void addDiscountCode(DiscountCode discountCode) {
        allDiscountCodes.add(discountCode);
    }

    public static String showAllDiscountCodes() {
        StringBuilder list=new StringBuilder();
        list.append("List of discount codes:");
        for (DiscountCode discountCode : allDiscountCodes) {
            list.append("\n"+discountCode.getCode());
        }
        return list.toString();
    }

    public String viewMe() {
        return
                "code: " + code +
                "\npercent: " + percent + "%" +
                "\nmaximum amount to be decreased: " + maxAmount +
                "\nmaximum number of use for each user: " + maxNumberOfUse +
                "\nlist of users who have this discount code: " + makeListOfBuyers() +
                "\nstart time:" + startTime.toString() +
                "\nend time: " + endTime.toString();
    }

    public String makeListOfBuyers(){
        StringBuilder list = new StringBuilder();
        for (BuyerAccount user : users.keySet()) {
            list.append("\n"+user.getUserName());
        }
        return list.toString();
    }

    public static DiscountCode getDiscountCodeWithCode(String code) {
        for (DiscountCode discountCode : allDiscountCodes) {
            if(discountCode.code.equals(code))
                return discountCode;
        }
        return null;
        //TODO invalid code exception
    }

    public void edit(HashMap<String, String> changes) {

    }

    public static void removeDiscountCode(DiscountCode discountCode) {
        allDiscountCodes.remove(discountCode);
    }

    //time format: 2/27,14:50
    public void setStartTime(String startTime) {
        String[] splitted = startTime.split("[/,:]");
        this.startTime.setMonth(Integer.parseInt(splitted[0])-1);
        this.startTime.setDate(Integer.parseInt(splitted[1]));
        this.startTime.setHours(Integer.parseInt(splitted[2]));
        this.startTime.setMinutes(Integer.parseInt(splitted[3]));
    }

    public void setEndTime(String endTime) {
        String[] splitted = endTime.split("[/,:]");
        this.endTime.setMonth(Integer.parseInt(splitted[0])-1);
        this.endTime.setDate(Integer.parseInt(splitted[1]));
        this.endTime.setHours(Integer.parseInt(splitted[2]));
        this.endTime.setMinutes(Integer.parseInt(splitted[3]));

    }

    public String getCode() {
        return code;
    }

    public void removeUser(BuyerAccount buyerAccount){
        users.remove(buyerAccount);
    }

    //TODO : scheduledExecutorService
}
