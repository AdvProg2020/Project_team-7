package Main.model;

import Main.model.accounts.BuyerAccount;

import javax.swing.*;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;

public class DiscountCode {
    private static StringBuilder lastUsedCodeID = new StringBuilder("@");
    private String code;
    private Time startTime;
    private Time endTime;
    private double percent;
    private double maxAmount;
    private int maxNumberOfUse;
    private HashMap<BuyerAccount, Integer> users = new HashMap<BuyerAccount, Integer>();
    private static ArrayList<DiscountCode> allDiscountCodes = new ArrayList<DiscountCode>();

    public DiscountCode( String startTime, String endTime, double percent, double maxAmount, int maxNumberOfUse, ArrayList<BuyerAccount> users) {
        this.code = IDGenerator.getNewID(lastUsedCodeID);
        this.setStartTime(startTime);
        this.setEndTime(endTime);
        this.percent = percent;
        this.maxAmount = maxAmount;
        this.maxNumberOfUse = maxNumberOfUse;
        for (BuyerAccount user : users) {
            this.users.put(user, maxNumberOfUse);
        }
    }

    public static void addDiscountCode(DiscountCode discountCode) {
        allDiscountCodes.add(discountCode);
    }

    public static String showAllDiscountCodes() {
        StringBuilder list = new StringBuilder();
        list.append("List of discount codes:");
        for (DiscountCode discountCode : allDiscountCodes) {
            list.append("\n" + discountCode.getCode());
        }
        return list.toString();
    }

    public String viewMeAsManager() {
        return
                "code: " + code +
                        "\n\tpercent: " + percent + "%" +
                        "\n\tmaximum amount to be decreased: " + maxAmount +
                        "\n\tmaximum number of use for each user: " + maxNumberOfUse +
                        "\n\tlist of users who have this discount code: " + makeListOfBuyers() +
                        "\n\tstart time:" + startTime.toString() +
                        "\n\tend time: " + endTime.toString() + "\n";
    }

    public String viewMeAsBuyer(BuyerAccount buyerAccount) {
        return
                "code: " + code +
                        "\n\tpercent: " + percent + "%" +
                        "\n\tmaximum amount to be decreased: " + maxAmount +
                        "\n\tmaximum number of use for each user: " + maxNumberOfUse +
                        "\n\ttimes you have used this code so far : " + users.get(buyerAccount) +
                        "\n\tstart time:" + startTime.toString() +
                        "\n\tend time: " + endTime.toString() + "\n";
    }

    public String makeListOfBuyers() {
        StringBuilder list = new StringBuilder();
        for (BuyerAccount user : users.keySet()) {
            list.append("\n" + user.getUserName());
        }
        return list.toString();
    }

    public static DiscountCode getDiscountCodeWithCode(String code) throws Exception {
        for (DiscountCode discountCode : allDiscountCodes) {
            if (discountCode.code.equals(code))
                return discountCode;
        }
        throw new Exception("There is no discount code with given code !\n");
    }

    public void edit(HashMap<String, String> changes) {

    }

    public void removeDiscountCode() {
        allDiscountCodes.remove(this);
        for (BuyerAccount buyerAccount : users.keySet()) {
            buyerAccount.removeDiscountCode(this);
        }
    }

    //time format: 2/27,14:50
    public void setStartTime(String startTime) {
        String[] splitted = startTime.split("[/,:]");
        this.startTime.setMonth(Integer.parseInt(splitted[0]) - 1);
        this.startTime.setDate(Integer.parseInt(splitted[1]));
        this.startTime.setHours(Integer.parseInt(splitted[2]));
        this.startTime.setMinutes(Integer.parseInt(splitted[3]));
        //TODO : add Exception
    }

    public void setEndTime(String endTime) {
        String[] splitted = endTime.split("[/,:]");
        this.endTime.setMonth(Integer.parseInt(splitted[0]) - 1);
        this.endTime.setDate(Integer.parseInt(splitted[1]));
        this.endTime.setHours(Integer.parseInt(splitted[2]));
        this.endTime.setMinutes(Integer.parseInt(splitted[3]));
        //TODO : add Exception
    }

    public String getCode() {
        return code;
    }

    public void removeUser(BuyerAccount buyerAccount) throws Exception {
        if(!users.containsKey(buyerAccount)){
            throw new Exception("Buyer eith user name \"" + buyerAccount.getUserName() + "\" isn't in this discount code's user list ! \n" );
        }
        users.remove(buyerAccount);
        buyerAccount.removeDiscountCode(this);
    }

    public double getDiscountCodeAmount (){
        return percent;
    }

    public void removeDiscountCodeIfBuyerHasUsedUpDiscountCode(BuyerAccount buyerAccount) throws Exception {
        if(users.get(buyerAccount)==maxNumberOfUse){
            this.removeUser(buyerAccount);
            buyerAccount.removeDiscountCode(this);
        }
    }

    public double getMaxAmount() {
        return maxAmount;
    }

    public void setPercent(double percent) throws Exception {
        if(maxAmount<=0){
            throw new Exception("discount percent must be a positive double");
        }
        this.percent = percent;
    }

    public void setMaxAmount(double maxAmount) throws Exception {
        if(maxAmount<=0){
            throw new Exception("max amount must be a positive double");
        }
        this.maxAmount = maxAmount;
    }

    public void setMaxNumberOfUse(int maxNumberOfUse) throws Exception {
        if(maxNumberOfUse<=0){
            throw new Exception("max number of use must be a positive Integer");
        }
        this.maxNumberOfUse = maxNumberOfUse;
    }

    public void addUser(BuyerAccount buyerAccount){
        users.put(buyerAccount,0);
        buyerAccount.addDiscountCode(this);
    }
    //TODO : scheduledExecutorService
}
