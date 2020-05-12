package Main.model.accounts;

import Main.model.Product;
import Main.model.discountAndOffTypeService.DiscountAndOffStat;
import Main.model.discountAndOffTypeService.Off;
import Main.model.exceptions.AccountsException;
import Main.model.logs.SellLog;

import java.util.ArrayList;

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
                         double balance) throws AccountsException {
        super(userName, firstName, lastName, email, phoneNumber, passWord);
        AccountsException.validateNameTypeInfo("company name", companyName);
        this.companyName = companyName;
        this.companyExtraInformation = companyExtraInformation;
        this.balance = balance;
    }

    public String editPersonalInfo(String field, String newContent) {
        if (field.equals("userName"))
            return "you are not allowed to edit this";
        else {
            if (field.equals("firstName"))
                firstName = newContent;
            else if (field.equals("lastName"))
                lastName = newContent;
            else if (field.equals("email"))
                email = newContent;
            else if (field.equals("phoneNumber"))
                phoneNumber = newContent;
            else if (field.equals("passWord"))
                passWord = newContent;
            else if (field.equals("companyName"))
                companyName = newContent;
            else if (field.equals("companyExtraInformation"))
                companyExtraInformation = newContent;
            else return "wrong field to edit";
            return "edit done successfully.";
        }
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

    public static SellerAccount getSellerWithUserName(String userName) throws Exception {
        for (SellerAccount seller : allSellers) {
            if (seller.userName.equals(userName)) {
                return seller;
            }
        }
        throw new Exception("There is no seller with this user name");
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

    public SellLog getLogWithId(String logId) {
        for (SellLog sellLog : sellHistory) {
            if (sellLog.getLogId().equals(logId)) {
                return sellLog;
            }
        }
        return null;
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
}
