package Main.model.accounts;

import Main.controller.GeneralController;
import Main.model.Category;
import Main.model.Product;
import Main.model.discountAndOffTypeService.DiscountAndOffStat;
import Main.model.discountAndOffTypeService.Off;
import Main.model.exceptions.AccountsException;
import Main.model.logs.SellLog;
import Main.model.sorting.UsersSort;
import com.gilecode.yagson.com.google.gson.stream.JsonReader;

import java.io.*;
import java.util.ArrayList;

import static java.util.Arrays.asList;

public class SellerAccount extends Account {
    private String companyName;
    private String companyExtraInformation;
    private ArrayList<SellLog> sellHistory = new ArrayList<SellLog>();
    private ArrayList<Product> products = new ArrayList<Product>();
    private ArrayList<Off> offList = new ArrayList<Off>();
    private static ArrayList<SellerAccount> allSellers = new ArrayList<SellerAccount>();
    private double balance;

    public SellerAccount(String userName,
                         String firstName,
                         String lastName,
                         String email,
                         String phoneNumber,
                         String passWord,
                         String companyName,
                         String companyExtraInformation,
                         double balance) {
        super(userName, firstName, lastName, email, phoneNumber, passWord);
        this.companyName = companyName;
        this.companyExtraInformation = companyExtraInformation;
        this.balance = balance;
    }

    public String editPersonalInfo(String field, String newContent) {
        if (field.equals("userName"))
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
            else if (field.equalsIgnoreCase("company name"))
                companyName = newContent;
            else if (field.equalsIgnoreCase("company extra information"))
                companyExtraInformation = newContent;
            else return "wrong field to edit";
            return "edit done successfully.";
        }
    }

    public static String showSellersList() {
        if (GeneralController.currentUserSort.equalsIgnoreCase("first name A-Z"))
            allSellers.sort(new UsersSort.usersSortByFirstNameAscending());
        else if (GeneralController.currentUserSort.equalsIgnoreCase("first name Z-A"))
            allSellers.sort(new UsersSort.usersSortByFirstNameDescending());
        else if (GeneralController.currentUserSort.equalsIgnoreCase("last name A-Z"))
            allSellers.sort(new UsersSort.usersSortByLastNameAscending());
        else if (GeneralController.currentUserSort.equalsIgnoreCase("last name Z-A"))
            allSellers.sort(new UsersSort.usersSortByLastNameDescending());
        StringBuilder sellersList = new StringBuilder();
        sellersList.append("Sellers :\n");
        for (SellerAccount seller : allSellers) {
            String tempSellerInfo = "\tuser name : " + seller.userName + "\tfull name : " + seller.firstName + " " + seller.lastName + "\n";
            sellersList.append(tempSellerInfo);
        }
        return sellersList.toString();
    }

    public static SellerAccount getSellerWithUserName(String userName) throws Exception {
        for (SellerAccount seller : allSellers) {
            if (seller.userName.equals(userName)) {
                return seller;
            }
        }
        throw new Exception("There is no seller with user name : " + userName + "\n");
    }

    public String viewMe() {
        return "Seller :\n\tfirst name : " + this.firstName + "\n\tlast name : " + this.lastName + "\n\tuser name : " + this.userName +
                "\n\tcompany name : " + this.companyName + "\n\tcompany information : " + companyExtraInformation +
                "\n\temail : " + this.email + "\n\tphone number : " + this.phoneNumber + "\n";
    }

    public void deleteSeller() {
        allSellers.remove(this);
        allAccounts.remove(this);
        for (Product product : products) {
            product.removeSeller(this);
        }
    }

    public SellLog getLogWithId(String logId) throws Exception {
        for (SellLog sellLog : sellHistory) {
            if (sellLog.getLogId().equals(logId)) {
                return sellLog;
            }
        }
        throw new Exception("there is no log with ID : " + logId + "\n");
    }

    public String viewCompanyInformation() {
        return "company name : " + this.companyName + "\n\tcompany information : " + companyExtraInformation + "\n";
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


    //TODO : use this in addOffRequest
    public void addOff(Off off) {
        offList.add(off);
    }

    public String viewSellerOffs() {
        StringBuilder offListStr = new StringBuilder();
        offListStr.append("offs :\n");
        for (Off off : offList) {
            if (off.getDiscountOrOffStat().equals(DiscountAndOffStat.EXPIRED)) {
                off.removeOff();
            } else {
                offListStr.append(off.viewMe());
            }
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

    public String getCompanyName() {
        return companyName;
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }

    public String viewSellerProductWithId(String id) {
        for (Product product : products) {
            if (product.getProductId().equals(id))
                return product.showProductAttributes();
        }
        return "invalid product id for this seller";
    }

    public boolean doesSellerHaveOffWithReference(Off off) {
        return offList.contains(off);
    }

    public boolean doesSellerHaveProductWithReference(Product product) {
        return products.contains(product);
    }

    public static String readData() {
        try {
            GeneralController.jsonReader = new JsonReader(new FileReader(new File("src/main/JSON/sellers.json")));
            SellerAccount[] allSel = GeneralController.yagsonMapper.fromJson(GeneralController.jsonReader, SellerAccount[].class);
            allSellers = (allSel == null) ? new ArrayList<>() : new ArrayList<>(asList(allSel));
            allAccounts.addAll(allSellers);
            return "Read Sellers Data Successfully.";
        } catch (FileNotFoundException e) {
            return "Problem loading data from sellers.json";
        }
    }

    public static String writeData() {
        try {
            GeneralController.fileWriter = new FileWriter("src/main/JSON/sellers.json");
            SellerAccount[] allSel = new SellerAccount[allSellers.size()];
            allSel = allSellers.toArray(allSel);
            GeneralController.yagsonMapper.toJson(allSel, SellerAccount[].class, GeneralController.fileWriter);
            GeneralController.fileWriter.close();
            return "Saved Sellers Data Successfully.";
        } catch (IOException e) {
            return "Problem saving sellers data.";
        }
    }
}
