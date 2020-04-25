package Main.model.accounts;

import Main.model.Off;
import Main.model.Product;
import Main.model.exceptions.InvalidInputException;
import Main.model.logs.SellLog;

import java.util.ArrayList;

public class SellerAccount extends Account {
    private String companyName;
    private ArrayList<SellLog> sellHistory = new ArrayList<SellLog>();
    private ArrayList<Product> products = new ArrayList<Product>();
    private ArrayList<Off> offList = new ArrayList<Off>();
    private static ArrayList<SellerAccount> allSellers = new ArrayList<SellerAccount>();
    private double balance;

    //TODO : add field for company extra information & manage related changes example in viewMe and TestUnits

    public SellerAccount(String userName, String firstName, String lastName, String email, String phoneNumber, String passWord, String companyName, double balance) throws InvalidInputException {
        super(userName, firstName, lastName, email, phoneNumber, passWord);
        InvalidInputException.validateNameTypeInfo("company name", companyName);
        this.companyName = companyName;
        this.balance = balance;
    }

    public static String showSellersList() {
        StringBuilder sellersList = new StringBuilder();
        sellersList.append("Sellers :\n");
        for (SellerAccount seller : allSellers) {
            String tempSellerInfo = "\tuser name : " + seller.userName + "\tfull name : " + seller.firstName + " " + seller.lastName + "\n";
            sellersList.append(tempSellerInfo);
        }
        return sellersList.toString();
    }

    public static SellerAccount getSellerWithUserName(String userName) {
        for (SellerAccount seller : allSellers) {
            if (seller.userName.equals(userName)) {
                return seller;
            }
        }
        return null;
    }

    public String viewMe() {
        return "Seller :\n\tfirst name : " + this.firstName + "\n\tlast name : " + this.lastName + "\n\tuser name : " + this.userName +
                "\n\tcompany name : " + this.companyName + "\n\temail : " + this.email + "\n\tphone number : " + this.phoneNumber + "\n";
    }

    public static void deleteSeller(SellerAccount sellerAccount) {
        allSellers.remove(sellerAccount);
        allAccounts.remove(sellerAccount);
    }

    public SellLog getLogWithId(String logId) {
        for (SellLog sellLog : sellHistory) {
            if (sellLog.getLogId().equals(logId)) {
                return sellLog;
            }
        }
        return null;
    }

    public String viewCompanyInformation() {
        return "company name : " + this.companyName + "\n";
    }

    public void addLog(SellLog sellLog) {
        sellHistory.add(sellLog);
    }

    public String viewSalesHistory() {
        StringBuilder salesHistory = new StringBuilder();
        salesHistory.append("sales history :\n");
        for (SellLog sellLog : sellHistory) {
            salesHistory.append(sellLog.viewLog());
        }
        return salesHistory.toString();
    }

    public String showSellerProducts() {
        StringBuilder productsList = new StringBuilder();
        productsList.append("Products :\n");
        for (Product product : products) {
            productsList.append(product.showProductDigest());
        }
        return productsList.toString();
    }

    public void increaseBalanceBy(double money) {
        this.balance += money;
    }

    public String viewBalance() {
        return "balance : " + balance + "\n";
    }

    public void addOff(Off off) {
        offList.add(off);
    }

    public String viewSellerOffs() {
        StringBuilder offListStr = new StringBuilder();
        offListStr.append("offs :\n");
        for (Off off : offList) {
            offListStr.append(off.viewMe());
        }
        return offListStr.toString();
    }

    public static void addSeller(SellerAccount seller) {
        allSellers.add(seller);
        allAccounts.add(seller);
    }

    public boolean isThereSellerWithUserName(String userName) {
        for (SellerAccount seller : allSellers) {
            if (seller.userName.equals(userName)) {
                return true;
            }
        }
        return false;
    }

    public double getBalance() {
        return balance;
    }
}
