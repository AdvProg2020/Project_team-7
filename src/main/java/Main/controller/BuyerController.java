package Main.controller;

import Main.model.Cart;
import Main.model.Product;
import Main.model.Rate;
import Main.model.accounts.BuyerAccount;

import java.util.ArrayList;

public class BuyerController {
    private BuyerAccount currentBuyer = null;
    private Cart currentBuyersCart = null;

    public BuyerController() {
        if (GeneralController.currentUser instanceof BuyerAccount) {
            BuyerAccount currentBuyer = (BuyerAccount) GeneralController.currentUser;
            currentBuyersCart = currentBuyer.getCart();
        }
    }

    public String showCartProducts() {
        return currentBuyersCart.viewMe();
    }

    public void increaseProductWithId(String productId) throws Exception {
        if (currentBuyersCart.isThereProductWithID(productId)) {
            currentBuyersCart.getCartProductByProductId(productId).increaseNumberByOne();
            return;
        }
        throw new Exception("There is no product with given Id in the cart");
    }

    public void decreaseProductWithId(String productId) throws Exception {
        if (currentBuyersCart.isThereProductWithID(productId)) {
            currentBuyersCart.getCartProductByProductId(productId).decreaseNumberByOne();
            return;
        }
        throw new Exception("There is no product with given ID in the cart");
    }

    public String showTotalCartPrice() {
        return "Cart's Total Price Considering Offs : " + currentBuyersCart.calculateCartTotalPriceConsideringOffs();
    }

    public void purchase() {
//TODO
    }

    public void getReceiverInformation(ArrayList<String> receiverInfo) {
//TODO
    }

    public void payment() {
//TODO
    }

    public String viewBuyerBalance() {
        return "balance : " + currentBuyer.getBalance();
    }

    public String viewBuyerDiscountCodes() {
        //TODO
        return null;
    }

    public String viewBuyerOrders() {
        return currentBuyer.viewOrders();
    }

    public String showOrderWithId(String orderId) {
        if (currentBuyer.isThereLogWithID(orderId)) {
            return currentBuyer.getLogWithId(orderId).viewLog();
        }
        return "there is no order with this ID !";
    }

    public void rateProductWithId(String productId, double score) {
        Rate rate = new Rate(currentBuyer, Product.getProductWithId(productId), score);
        //TODO : SOME ACTION MUST BE TAKEN IN HERE!
    }
}
