package Main.model.accounts;

import Main.model.Cart;
import Main.model.DiscountCode;
import Main.model.Product;
import Main.model.logs.BuyLog;

import java.util.ArrayList;

public class BuyerAccount extends Account {
    private Cart cart = null;
    private ArrayList<BuyLog> buyHistory = new ArrayList<BuyLog>();
    private ArrayList<DiscountCode> discountCodes = new ArrayList<DiscountCode>();

    public BuyerAccount(String userName, String firstName, String lastName, String email, String phoneNumber, String passWord, double balance) {

    }

    public boolean hasBuyerBoughtProduct(Product product) {
        return false;
    }

    public static boolean isThereBuyerWithUserName(String userName) {
        return false;
    }

    public static String showBuyersList() {
        return null;
    }

    public String viewMe() {
        return null;
    }

    public static BuyerAccount getBuyerWithUserName(String userName) {
        return null;
    }

    public static void deleteBuyer(BuyerAccount buyer) {

    }

    public BuyLog getLogWithId(String logId) {
        return null;
    }

    public void addDiscountCode(DiscountCode discountCode) {

    }

    public void removeDiscountCode(DiscountCode discountCode) {

    }

    public void addLog(BuyLog buyLog) {

    }

    public void emptyCart() {

    }

    public String viewBalance() {
        return null;
    }

    public String viewOrders() {
        return null;
    }

    public void decreaseBalanceBy(double money) {

    }
}
