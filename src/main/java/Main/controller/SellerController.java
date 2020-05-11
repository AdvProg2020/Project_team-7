package Main.controller;

import Main.model.Category;
import Main.model.discountAndOffTypeService.*;
import Main.model.Product;
import Main.model.accounts.SellerAccount;
import Main.model.discountAndOffTypeService.Off;
import Main.model.discountAndOffTypeService.OffStatus;
import Main.model.requests.AddProductRequest;
import Main.model.requests.Request;

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

    public String viewBuyersOfProductWithId(String productId) throws Exception{
        return Product.getProductWithId(productId).viewBuyers();
    }

    public void addProduct(ArrayList<String> productInfo) throws Exception{
        Product product = new Product(productInfo.get(0), productInfo.get(1), Integer.parseInt(productInfo.get(2)), Category.getCategoryWithName(productInfo.get(3)), productInfo.get(4), null, Double.parseDouble(productInfo.get(5)), ((ArrayList) productInfo.subList(6, productInfo.size())));
        Request request = new AddProductRequest(product, "Add product request");
        Request.addRequest(request);
    }

    public String viewSellerOffs() {
        return ((SellerAccount) GeneralController.currentUser).viewSellerOffs();
    }

    public String viewAllOffs() {
        return Off.viewAllOffs();
    }

    public String viewOffWithId(String offId) throws Exception{
        return Off.getOffWithId(offId).viewMe();
    }

    public void editOffWithId(String offId) {

    }

    public void addOff(ArrayList<String> offInfo) throws Exception{
        //TODO
        //Off off = new Off(, OffStatus.PENDING_CREATION_OFF,offInfo.get(0),offInfo.get(1),offInfo.get(2),SellerAccount.getSellerWithUserName(offInfo.get(3)));
    }

    public String viewSellerBalance() {
        return ((SellerAccount) GeneralController.currentUser).viewBalance();
    }
}
