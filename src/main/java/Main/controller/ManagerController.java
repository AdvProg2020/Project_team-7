package Main.controller;

import Main.model.Category;
import Main.model.DiscountCode;
import Main.model.Product;
import Main.model.accounts.Account;
import Main.model.accounts.BuyerAccount;
import Main.model.accounts.ManagerAccount;
import Main.model.accounts.SellerAccount;
import Main.model.exceptions.InvalidInputException;
import Main.model.requests.EditDiscountCode;
import Main.model.requests.Request;

import java.util.ArrayList;

public class ManagerController {
    public ManagerAccount chiefManager = null;

    public String showUsersList() {
        return ManagerAccount.showManagersList() + SellerAccount.showSellersList() + BuyerAccount.showBuyersList();
    }

    public String viewUserWithUserName(String userName) {
        Account account = null;
        try {
            account = Account.getUserWithUserName(userName);
        } catch (Exception e) {
            return e.getMessage();
        }

        if (account instanceof ManagerAccount) {
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

         if (account instanceof ManagerAccount) {
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
        Product product = Product.getProductWithId(productId);

        product.removeProduct();
    }

    public void createDiscountCode(ArrayList<String> discountInfo) throws Exception {
        ArrayList<BuyerAccount> buyersList = extractDiscountBuyersList(discountInfo);
        DiscountCode discountCode = new DiscountCode(discountInfo.get(1), discountInfo.get(2),
                Double.parseDouble(discountInfo.get(3)), Double.parseDouble(discountInfo.get(4)), Integer.parseInt(discountInfo.get(5)),
                buyersList);

        DiscountCode.addDiscountCode(discountCode);
        for (BuyerAccount buyerAccount : buyersList) {
            buyerAccount.addDiscountCode(discountCode);
        }
    }

    private ArrayList<BuyerAccount> extractDiscountBuyersList(ArrayList<String> discountInfo) throws Exception {
        ArrayList<BuyerAccount> buyersList = new ArrayList<BuyerAccount>();

        int discountInfoSize = discountInfo.size();
        for (int i = 6; i < discountInfoSize; i++) {
            BuyerAccount buyerAccount = BuyerAccount.getBuyerWithUserName(discountInfo.get(i));
            if (buyerAccount == null) {
                throw new Exception("There are some invalid user names in the given information !\n" +
                        "Please check user names and try again !\n");
            } else {
                buyersList.add(buyerAccount);
            }
        }
        return buyersList;
    }

    public String showAllDiscountCodes() {
        return DiscountCode.showAllDiscountCodes();
    }

    public String viewDiscountCodeWithCode(String code) {
        DiscountCode discountCode = null;
        try {
            discountCode = DiscountCode.getDiscountCodeWithCode(code);
        } catch (Exception e) {
            return e.getMessage();
        }

        return discountCode.viewMeAsManager();
    }

    public void removeDiscountCodeWithCode(String code) throws Exception {
        DiscountCode discountCode = DiscountCode.getDiscountCodeWithCode(code);

        discountCode.removeDiscountCode();
    }

    public String showAllRequests() {
        return Request.showAllRequests();
    }

    public String showRequestDetailsWithId(String requestId) {
        Request request = null;
        try {
            request = Request.getRequestWithId(requestId);
        } catch (Exception e) {
            return e.getMessage();
        }

        return request.showRequest();
    }

    public void acceptRequestWithId(String requestId) throws Exception {
        Request request = Request.getRequestWithId(requestId);

            request.accept();
    }

    public void declineRequestWithId(String requestId) throws Exception {
        Request request = Request.getRequestWithId(requestId);

            request.decline();
    }

    public void createCategory(String name, ArrayList<String> specialFeatures) {
        Category category = new Category(name, specialFeatures);
        Category.addCategory(category);
    }

    public void removeCategoryWithId(String categoryId) throws Exception {
        Category category = Category.getCategoryWithName(categoryId);

        category.removeCategory();
    }

    public void editDiscountCodeWithCode(EditDiscountCode editDiscountCode) throws Exception {
        editDiscountCode.acceptRequest();
    }

    public void editCategoryWithId(String categoryId) {
        //TODO
    }
}
