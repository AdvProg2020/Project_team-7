package Main.model;

import Main.model.accounts.BuyerAccount;

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
    private int numberOfUse;
    private ArrayList<BuyerAccount> users = new ArrayList<BuyerAccount>();
    private static ArrayList<DiscountCode> allDiscountCodes = new ArrayList<DiscountCode>();

    public DiscountCode(String code, Time startTime, Time endTime, double percent, double maxAmount, int maxNumberOfUse, int numberOfUse, ArrayList<BuyerAccount> users) {

    }

    public void addDiscountCode(DiscountCode discountCode) {

    }

    public static String showAllDiscountCodes() {
        return null;
    }

    public String viewMe() {
        return null;
    }

    public static DiscountCode getDiscountCodeWithCode(String code) {
        return null;
    }

    public void edit(HashMap<String, String> changes) {

    }

    public static void removeDiscountCode(DiscountCode discountCode) {

    }
}
