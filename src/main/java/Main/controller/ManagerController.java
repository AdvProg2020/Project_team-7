package Main.controller;

import Main.model.Product;
import Main.model.accounts.Account;
import Main.model.accounts.BuyerAccount;
import Main.model.accounts.ManagerAccount;
import Main.model.accounts.SellerAccount;
import Main.model.exceptions.InvalidInputException;

import java.util.ArrayList;

public class ManagerController {
    public ManagerAccount chiefManager = null;

    public String showUsersList() {
        return ManagerAccount.showManagersList() + SellerAccount.showSellersList() + BuyerAccount.showBuyersList();
    }

    public String viewUserWithUserName(String userName) {
        Account account = Account.getUserWithUserName(userName);
        if (account == null) {
            return "There is no user with given userName !";
        } else if (account instanceof ManagerAccount) {
            ManagerAccount managerAccount = (ManagerAccount) account;
            return managerAccount.viewMe();
        } else if (account instanceof SellerAccount) {
            SellerAccount sellerAccount = (SellerAccount) account;
            return sellerAccount.viewMe();
        } else {
            BuyerAccount buyerAccount = (BuyerAccount) account;
            return buyerAccount.viewOrders();
        }
    }

    public void deleteUserWithUserName(String userName) throws Exception {
        Account account = Account.getUserWithUserName(userName);
        if (account == null) {
            throw new Exception("There is no user with given userName !");
        } else if (account instanceof ManagerAccount) {
            ManagerAccount managerAccount = (ManagerAccount) account;
            ManagerAccount.deleteManager(managerAccount);
        } else if (account instanceof SellerAccount) {
            SellerAccount sellerAccount = (SellerAccount) account;
            sellerAccount.deleteSeller();
        } else {
            BuyerAccount buyerAccount = (BuyerAccount) account;
            buyerAccount.deleteBuyer();
        }
    }

    public void createManagerProfile(ArrayList<String> managerInfo) throws InvalidInputException {
        ManagerAccount managerAccount = new ManagerAccount(managerInfo.get(0), managerInfo.get(1), managerInfo.get(2),
                managerInfo.get(3), managerInfo.get(4), managerInfo.get(5));
        ManagerAccount.addManager(managerAccount);
    }

    public void removeProductWithId(String productId) throws Exception {
        Product product =  Product.getProductWithId(productId);
        if(product==null){
            throw new Exception("There is no product with given ID !");
        }
        product.removeProduct();

    }

    public void createDiscountCode(ArrayList<String> discountInfo) {

    }

    public String showAllDiscountCodes() {
        return null;
    }

    public String viewDiscountCodeWithCode(String code) {
        return null;
    }

    public void editDiscountCodeWithCode(String code) {

    }

    public void removeDiscountCodeWithCode(String code) {

    }

    public String showAllRequests() {
        return null;
    }

    public String showRequestDetailsWithId(String requestId) {
        return null;
    }

    public void acceptRequestWithId(String requestId) {

    }

    public void declineRequestWithId(String requestId) {

    }

    public void editCategoryWithId(String categoryId) {

    }

    public void addCategoryWithId(String categoryId) {

    }

    public void getCategoryInformation(ArrayList<String> categoryInfo) {

    }

    public void removeCategoryWithId(String categoryId) {

    }

    public void createDiscountCode(String discountCode) {

    }
}
