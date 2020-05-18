package Main.model.accounts;

import Main.controller.GeneralController;
import Main.model.Cart;
import Main.model.Product;
import Main.model.discountAndOffTypeService.DiscountAndOffStat;
import Main.model.discountAndOffTypeService.DiscountCode;
import Main.model.logs.BuyLog;
import Main.model.logs.Log;
import Main.model.sorting.UsersSort;
import com.gilecode.yagson.com.google.gson.stream.JsonReader;

import java.io.*;
import java.util.ArrayList;

import static java.util.Arrays.asList;

public class BuyerAccount extends Account {

    //TODO : not allowing more than one editRequest???

    private Cart cart;
    private ArrayList<BuyLog> buyHistory = new ArrayList<>();
    private ArrayList<DiscountCode> discountCodes = new ArrayList<>();
    private ArrayList<Product> boughtProducts = new ArrayList<>();
    private double balance;
    private int numberOfBoughtProductsForBonus;
    private static ArrayList<BuyerAccount> allBuyers = new ArrayList<>();

    private ArrayList<String> buyHistoryStringRecord = new ArrayList<>();
    private ArrayList<String> discountCodesStringRecord = new ArrayList<>();
    private ArrayList<String> boughtProductsStringRecord = new ArrayList();

    public BuyerAccount(String userName,
                        String firstName,
                        String lastName,
                        String email,
                        String phoneNumber,
                        String passWord,
                        double balance) {
        super(userName, firstName, lastName, email, phoneNumber, passWord);
        this.balance = balance;
        this.cart = new Cart();
    }

    public String editPersonalInfo(String field, String newContent) {
        if (field.equalsIgnoreCase("username"))
            return "you are not allowed to edit this";
        else {
            if (field.equalsIgnoreCase("first name"))
                firstName = newContent;
            else if (field.equalsIgnoreCase("last name"))
                lastName = newContent;
            else if (field.equalsIgnoreCase("email"))
                email = newContent;
            else if (field.equalsIgnoreCase("phone number"))
                phoneNumber = newContent;
            else if (field.equalsIgnoreCase("password"))
                passWord = newContent;
            else if (field.equalsIgnoreCase("balance"))
                balance = Double.parseDouble(newContent);
            else return "wrong field to edit";
            return "edit done successfully.";
        }
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
        if (GeneralController.currentUserSort.equalsIgnoreCase("first name A-Z"))
            allBuyers.sort(new UsersSort.usersSortByFirstNameAscending());
        else if (GeneralController.currentUserSort.equalsIgnoreCase("first name Z-A"))
            allBuyers.sort(new UsersSort.usersSortByFirstNameDescending());
        else if (GeneralController.currentUserSort.equalsIgnoreCase("last name A-Z"))
            allBuyers.sort(new UsersSort.usersSortByLastNameAscending());
        else if (GeneralController.currentUserSort.equalsIgnoreCase("last name Z-A"))
            allBuyers.sort(new UsersSort.usersSortByLastNameDescending());
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
        throw new Exception("There is no buyer with user name : " + userName + "\n");
    }

    public void deleteBuyer() {
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
        throw new Exception("There is no product with ID : " + logId + "\n");
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
        if (balance < money) {
            throw new Exception("Your balance isn't enough ! Purchase couldn't be done !\n");
        }
        this.balance -= money;
    }

    public void addCartsProductsToBoughtProducts() {
        ArrayList<Product> cartsProductList = cart.getCartsProductList();
        for (Product product : cartsProductList) {
            if (!boughtProducts.contains(product)) {
                boughtProducts.add(product);
                numberOfBoughtProductsForBonus++;
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
            if (discountCode.getDiscountOrOffStat().equals(DiscountAndOffStat.EXPIRED)) {
                discountCode.removeDiscountCode();
            } else {
                allDiscountCodesDisplay.append(discountCode.viewMeAsBuyer(this));
            }
        }
        return allDiscountCodesDisplay.toString();
    }

    public static ArrayList<BuyerAccount> getAllBuyers() {
        return allBuyers;
    }

    public int getBuyerBonusLevel() {
        if (numberOfBoughtProductsForBonus >= 50) {
            numberOfBoughtProductsForBonus -= 50;
            return 2;
        }
        if (numberOfBoughtProductsForBonus >= 10) {
            numberOfBoughtProductsForBonus -= 10;
            return 1;
        }
        return 0;
    }

    public static String readData() {
        try {
            GeneralController.jsonReader = new JsonReader(new FileReader(new File("src/main/JSON/buyers.json")));
            BuyerAccount[] allBuy = GeneralController.yagsonMapper.fromJson(GeneralController.jsonReader, BuyerAccount[].class);
            allBuyers = (allBuy == null) ? new ArrayList<>() : new ArrayList<>(asList(allBuy));
            allAccounts.addAll(allBuyers);
            return "Read Buyers Data Successfully.";
        } catch (FileNotFoundException e) {
            return "Problem loading data from buyers.json";
        }
    }

    public static String writeData() {
        try {
            GeneralController.fileWriter = new FileWriter("src/main/JSON/buyers.json");
            BuyerAccount[] allBuy = new BuyerAccount[allBuyers.size()];
            allBuy = allBuyers.toArray(allBuy);
            GeneralController.yagsonMapper.toJson(allBuy, BuyerAccount[].class, GeneralController.fileWriter);
            GeneralController.fileWriter.close();
            return "Saved Buyers Data Successfully.";
        } catch (IOException e) {
            return "Problem saving buyers data.";
        }
    }

    public static void setStringRecordObjects(){
        try {
            setStringRecordDiscounts();
            setStringRecordBuyHistory();
            setStringRecordBoughtProducts();
        }
        catch (Exception e){}
    }

    private static void setStringRecordDiscounts() throws Exception {
        for (BuyerAccount buyer : allBuyers) {
            buyer.discountCodes.clear();
            for (String discountID : buyer.discountCodesStringRecord) {
                buyer.discountCodes.add(DiscountCode.getDiscountCodeWithCode(discountID));
            }
        }
    }

    private static void setStringRecordBuyHistory() throws Exception {
        for (BuyerAccount buyer : allBuyers) {
            buyer.buyHistory.clear();
            for (String logID : buyer.buyHistoryStringRecord) {
                buyer.buyHistory.add((BuyLog) Log.getLogWithID(logID));
            }
        }
    }

    private static void setStringRecordBoughtProducts() throws Exception {
        for (BuyerAccount buyer : allBuyers) {
            buyer.boughtProducts.clear();
            for (String productID : buyer.boughtProductsStringRecord) {
                buyer.boughtProducts.add(Product.getProductWithId(productID));
            }
        }
    }

    public static void getObjectStringRecords(){
            getDiscountsStringRecord();
            getBuyHistoriesStringRecord();
            getBoughtProductsStringRecord();
    }

    private static void getDiscountsStringRecord(){
        for (BuyerAccount buyer : allBuyers) {
            buyer.buyHistoryStringRecord.clear();
            for (BuyLog buyLog : buyer.buyHistory) {
                for (BuyLog log : buyer.buyHistory) {
                    buyer.buyHistoryStringRecord.add(log.getLogId());
                }
            }
        }
    }

    private static void getBuyHistoriesStringRecord(){
        for (BuyerAccount buyer : allBuyers) {
            buyer.discountCodesStringRecord.clear();
            for (DiscountCode discountCode : buyer.discountCodes) {
                buyer.discountCodesStringRecord.add(discountCode.getCode());
            }
        }
    }

    private static void getBoughtProductsStringRecord(){
        for (BuyerAccount buyer : allBuyers) {
            buyer.boughtProductsStringRecord.clear();
            for (Product boughtProduct : buyer.boughtProducts) {
                buyer.boughtProductsStringRecord.add(boughtProduct.getProductId());
            }
        }
    }

}
