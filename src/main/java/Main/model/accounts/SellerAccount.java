package Main.model.accounts;

import Main.model.Off;
import Main.model.Product;
import Main.model.logs.SellLog;

import java.util.ArrayList;

public class SellerAccount extends Account {
    private String companyName = null;
    private ArrayList<SellLog> sellHistory = new ArrayList<SellLog>();
    private ArrayList<Product> products = new ArrayList<Product>();
    private ArrayList<Off> offList = new ArrayList<Off>();
    private double balance;

    public SellerAccount(String userName, String firstName, String lastName, String email, String phoneNumber, String passWord, String companyName, double balance) {

    }

    public static SellerAccount getSellerWithName(String name) {
        return null;
    }

    public static boolean isThereSellerWithName(String name) {
        return false;
    }

    public static String showSellersList() {
        return null;
    }

    public static SellerAccount getSellerWithUserName(String userName) {
        return null;
    }

    public String viewMe() {
        return null;
    }

    public static void deleteSeller(SellerAccount sellerAccount) {

    }

    public SellLog getLogWithId(String logId) {
        return null;
    }

    public String viewCompanyInformation() {
        return null;
    }

    public void addLog(SellLog sellLog) {

    }

    public String viewSalesHistory() {
        return null;
    }

    public String showSellerProducts() {
        return null;
    }

    public void increaseBalanceBy(double money) {

    }

    public String viewBalance() {
        return null;
    }

    public void addOff(Off off) {

    }

    public String viewSellerOffs() {
        return null;
    }
}
