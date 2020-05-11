package Main.model;

import Main.model.accounts.BuyerAccount;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class DiscountCode {
    private static StringBuilder lastUsedCodeID = new StringBuilder("@");
    private String code;
    private Date startDate;
    private Date endDate;
    private double percent;
    private double maxAmount;
    private int maxNumberOfUse;
    private HashMap<BuyerAccount, Integer> users = new HashMap<BuyerAccount, Integer>();
    private static ArrayList<DiscountCode> allDiscountCodes = new ArrayList<DiscountCode>();

    public DiscountCode(String startDate, String endDate, double percent, double maxAmount, int maxNumberOfUse, ArrayList<BuyerAccount> users) throws ParseException {
        this.code = IDGenerator.getNewID(lastUsedCodeID);
        this.setStartDate(startDate);
        this.setEndDate(endDate);
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
                        "\n\tstart time:" + startDate.toString() +
                        "\n\tend time: " + endDate.toString() + "\n";
    }

    public String viewMeAsBuyer(BuyerAccount buyerAccount) {
        return
                "code: " + code +
                        "\n\tpercent: " + percent + "%" +
                        "\n\tmaximum amount to be decreased: " + maxAmount +
                        "\n\tmaximum number of use for each user: " + maxNumberOfUse +
                        "\n\ttimes you have used this code so far : " + users.get(buyerAccount) +
                        "\n\tstart time:" + startDate.toString() +
                        "\n\tend time: " + endDate.toString() + "\n";
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

    public void removeDiscountCode() {
        allDiscountCodes.remove(this);
        for (BuyerAccount buyerAccount : users.keySet()) {
            buyerAccount.removeDiscountCode(this);
        }
    }

    public void setStartDate(String startDate) throws ParseException {
        String dateFormat = "yyyy/MM/dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        this.startDate = simpleDateFormat.parse(startDate);
    }

    public void setEndDate(String endDate) throws ParseException {
        String dateFormat = "yyyy/MM/dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        this.endDate = simpleDateFormat.parse(endDate);
    }

    public String getCode() {
        return code;
    }

    public void removeUser(BuyerAccount buyerAccount) {
        users.remove(buyerAccount);
        buyerAccount.removeDiscountCode(this);
    }

    public double getDiscountCodeAmount (){
        return percent;
    }

    public void removeDiscountCodeIfBuyerHasUsedUpDiscountCode(BuyerAccount buyerAccount) {
        if(users.get(buyerAccount)==maxNumberOfUse){
            this.removeUser(buyerAccount);
            buyerAccount.removeDiscountCode(this);
        }
    }

    public double getMaxAmount() {
        return maxAmount;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public void setMaxAmount(double maxAmount) {
        this.maxAmount = maxAmount;
    }

    public void setMaxNumberOfUse(int maxNumberOfUse) {
        this.maxNumberOfUse = maxNumberOfUse;
    }

    public void addUser(BuyerAccount buyerAccount){
        users.put(buyerAccount,0);
        buyerAccount.addDiscountCode(this);
    }

    public boolean isThereBuyerWithReference(BuyerAccount buyerAccount){
        return users.containsKey(buyerAccount);
    }
    //TODO : scheduledExecutorService
}
