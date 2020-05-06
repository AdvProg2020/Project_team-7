package Main.controller;

import Main.model.Off;
import Main.model.Product;
import Main.model.accounts.SellerAccount;

import java.util.ArrayList;

public class SellerController {
    public String viewCompanyInformation() {
        return ((SellerAccount) GeneralController.currentUser).viewCompanyInformation();
    }

    public String viewSalesHistory() {
        return ((SellerAccount) GeneralController.currentUser).viewSalesHistory();
    }

    public String showSellerProducts() {
        return ((SellerAccount) GeneralController.currentUser).showSellerProducts();
    }

    public String viewSellerProductWithId(String productId) {
        return ((SellerAccount) GeneralController.currentUser).viewSellerProductWithId(productId);
    }

    public void editProductWithId(String productId) {

    }

    public String viewBuyersOfProductWithId(String productId) {
        return Product.getProductWithId(productId).viewBuyers();
    }

    public void addProduct(ArrayList<String> productInfo) {

    }

    public String viewSellerOffs() {
        return ((SellerAccount) GeneralController.currentUser).viewSellerOffs();
    }

    public String viewAllOffs() {
        return Off.viewAllOffs();
    }

    public String viewOffWithId(String offId) {
        return Off.getOffWithId(offId).viewMe();
    }

    public void editOffWithId(String offId) {

    }

    public void addOff(ArrayList<String> offInfo) {

    }

    public String viewSellerBalance() {
        return ((SellerAccount) GeneralController.currentUser).viewBalance();
    }
}
