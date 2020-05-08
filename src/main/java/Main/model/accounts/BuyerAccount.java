package Main.model.accounts;

import Main.model.Cart;
import Main.model.DiscountCode;
import Main.model.Product;
import Main.model.exceptions.InvalidInputException;
import Main.model.logs.BuyLog;

import java.util.ArrayList;

public class BuyerAccount extends Account {
    private Cart cart = null;
    private ArrayList<BuyLog> buyHistory = new ArrayList<BuyLog>();
    private ArrayList<DiscountCode> discountCodes = new ArrayList<DiscountCode>();
    private ArrayList<Product> boughtProducts = new ArrayList<Product>();
    private double balance;
    private static ArrayList<BuyerAccount> allBuyers = new ArrayList<BuyerAccount>();

    public BuyerAccount(String userName, String firstName, String lastName, String email, String phoneNumber, String passWord, double balance) throws InvalidInputException {
        super(userName, firstName, lastName, email, phoneNumber, passWord);
        this.balance = balance;
    }

    public boolean hasBuyerBoughtProduct(Product product) {
        return boughtProducts.contains(product);
    }

    public static boolean isThereBuyerWithUserName(String userName) {
        for (BuyerAccount buyer : allBuyers) {
            if (buyer.userName.equals(userName)) {
                return true;
            }
        }
        return false;
    }

    public static String showBuyersList() {
        StringBuilder buyersList = new StringBuilder();
        buyersList.append("Buyers :\n");
        for (BuyerAccount buyer : allBuyers) {
            String tempBuyerInfo = "\tuser name : " + buyer.userName + "\tfull name : " + buyer.firstName + " " + buyer.lastName + "\n";
            buyersList.append(tempBuyerInfo);
        }
        return buyersList.toString();
    }

    public String viewMe() {
        return "Buyer :\n\tfirst name : " + this.firstName + "\n\tlast name : " + this.lastName + "\n\tuser name : " + this.userName +
                "\n\temail : " + this.email + "\n\tphone number : " + this.phoneNumber + "\n";
    }

    public static BuyerAccount getBuyerWithUserName(String userName) throws Exception {
        for (BuyerAccount buyer : allBuyers) {
            if (buyer.userName.equals(userName)) {
                return buyer;
            }
        }
        throw new Exception("There is no buyer with this user name");
    }

    public void deleteBuyer() throws Exception {
        allBuyers.remove(this);
        allAccounts.remove(this);
        for (DiscountCode discountCode : discountCodes) {
            discountCode.removeUser(this);
        }
    }

    public BuyLog getLogWithId(String logId) throws Exception {
        for (BuyLog buyLog : buyHistory) {
            if (buyLog.getLogId().equals(logId)) {
                return buyLog;
            }
        }
        throw new Exception("There is no product with given ID !\n");
    }

    public void addDiscountCode(DiscountCode discountCode) {
            discountCodes.add(discountCode);
    }

    public void removeDiscountCode(DiscountCode discountCode) {
        discountCodes.remove(discountCode);
    }

    public void addLog(BuyLog buyLog) {
            buyHistory.add(buyLog);
    }

    public String viewBalance() {
        return "balance : " + balance + "\n";
    }

    public String viewOrders() {
        StringBuilder orders = new StringBuilder();
        orders.append("Orders :\n");
        for (BuyLog buyLog : buyHistory) {
            orders.append(buyLog.viewLog());
        }
        return orders.toString();
    }

    public void decreaseBalanceBy(double money) throws Exception {
        if(balance<money){
            throw new Exception("Your balance isn't enough ! Purchase couldn't be done !\n");
        }
        this.balance -= money;
    }

    public void addCartsProductsToBoughtProducts() {
        ArrayList<Product> cartsProductList = cart.getCartsProductList();
        for (Product product : cartsProductList) {
            if (!boughtProducts.contains(product)) {
                boughtProducts.add(product);
            }
        }
    }

    public static void addBuyer(BuyerAccount buyer) {
            allBuyers.add(buyer);
            allAccounts.add(buyer);
    }

    public double getBalance() {
        return balance;
    }

    public Cart getCart() {
        return cart;
    }

    public boolean isThereLogWithID(String logID) {
        for (BuyLog buyLog : buyHistory) {
            if (buyLog.getLogId().equals(logID)) {
                return true;
            }
        }
        return false;
    }

    public String viewAllDiscountCodes() {
        StringBuilder allDiscountCodesDisplay = new StringBuilder("Available discount codes :\n");
        for (DiscountCode discountCode : discountCodes) {
            allDiscountCodesDisplay.append(discountCode.viewMeAsBuyer(this));
        }
        return allDiscountCodesDisplay.toString();
    }
}
