package Main.model.discountAndOffTypeService;

import Main.controller.BuyerController;
import Main.model.IDGenerator;
import Main.model.accounts.BuyerAccount;
import Main.model.exceptions.DiscountAndOffTypeServiceException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class DiscountCode extends DiscountAndOffTypeService {
    private static StringBuilder lastUsedCodeID = new StringBuilder("@");
    private String code;
    private double percent;
    private double maxAmount;
    private int maxNumberOfUse;
    private HashMap<BuyerAccount, Integer> users = new HashMap<BuyerAccount, Integer>();
    private static ArrayList<DiscountCode> allDiscountCodes = new ArrayList<DiscountCode>();

    public DiscountCode(String startDate, String endDate, String percent, String maxAmount, String maxNumberOfUse, ArrayList<BuyerAccount> users) throws Exception {
        super(startDate, endDate);
        this.code = IDGenerator.getNewID(lastUsedCodeID);
        DiscountAndOffTypeServiceException.validateInputPercent(percent);
        this.percent = Double.parseDouble(percent);
        DiscountAndOffTypeServiceException.validateInputAmount(maxAmount);
        this.maxAmount = Double.parseDouble(maxAmount);
        DiscountAndOffTypeServiceException.validateInputMaxNumOfUse(maxNumberOfUse);
        this.maxNumberOfUse = Integer.parseInt(maxNumberOfUse);
        for (BuyerAccount user : users) {
            this.users.put(user, 0);
        }
    }

    public static void addDiscountCode(DiscountCode discountCode) {
        allDiscountCodes.add(discountCode);
    }

    public static String showAllDiscountCodes() {
        StringBuilder list = new StringBuilder();
        list.append("List of discount codes:");
        for (DiscountCode discountCode : allDiscountCodes) {
            if(discountCode.getDiscountOrOffStat().equals(DiscountAndOffStat.EXPIRED)){
                discountCode.removeDiscountCode();
            }else{
                list.append("\n" + discountCode.getCode());
            }
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
                        "\n\tstart date:" + dateFormat.format(startDate) +
                        "\n\tend date: " + dateFormat.format(endDate) + "\n";
    }

    public String viewMeAsBuyer(BuyerAccount buyerAccount) {
        return
                "code: " + code +
                        "\n\tpercent: " + percent + "%" +
                        "\n\tmaximum amount to be decreased: " + maxAmount +
                        "\n\tmaximum number of use for each user: " + maxNumberOfUse +
                        "\n\ttimes you have used this code so far : " + users.get(buyerAccount) +
                        "\n\tstart date:" + dateFormat.format(startDate) +
                        "\n\tend date: " + dateFormat.format(endDate) + "\n";
    }

    public String makeListOfBuyers() {
        StringBuilder list = new StringBuilder();
        for (BuyerAccount user : users.keySet()) {
            list.append("\n" + user.getUserName());
        }
        return list.toString();
    }

    public static DiscountCode getDiscountCodeWithCode(String code) throws Exception {
        DiscountCode foundDiscountCode=null;
        for (DiscountCode discountCode : allDiscountCodes) {
            if (discountCode.code.equals(code)){
                foundDiscountCode = discountCode;
            }
        }
        if(foundDiscountCode==null){
            throw new Exception("There is no discount code with given code !\n");
        }
        if(foundDiscountCode.getDiscountOrOffStat().equals(DiscountAndOffStat.EXPIRED)){
            foundDiscountCode.removeDiscountCode();
            throw new Exception("sorry this discount code is expired !\n");
        }
        return foundDiscountCode;
    }

    public void removeDiscountCode() {
        allDiscountCodes.remove(this);
        for (BuyerAccount buyerAccount : users.keySet()) {
            buyerAccount.removeDiscountCode(this);
        }
        BuyerController.deselectDiscountCode();
    }

    public String getCode() {
        return code;
    }

    public void removeUser(BuyerAccount buyerAccount) {
        users.remove(buyerAccount);
        buyerAccount.removeDiscountCode(this);
    }

    public double getDiscountCodeAmount() {
        return percent;
    }

    public void removeDiscountCodeIfBuyerHasUsedUpDiscountCode(BuyerAccount buyerAccount) {
        if (users.get(buyerAccount) == maxNumberOfUse) {
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

    public void addUser(BuyerAccount buyerAccount) {
        users.put(buyerAccount, 0);
        buyerAccount.addDiscountCode(this);
    }

    public boolean isThereBuyerWithReference(BuyerAccount buyerAccount) {
        return users.containsKey(buyerAccount);
    }

    public void expireIfNeeded() {
        if(this.getDiscountOrOffStat().equals(DiscountAndOffStat.EXPIRED)){
            removeDiscountCode();
        }
    }
}
