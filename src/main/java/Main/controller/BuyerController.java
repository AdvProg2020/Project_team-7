package Main.controller;

import Main.model.Cart;
import Main.model.accounts.BuyerAccount;

import java.util.ArrayList;

public class BuyerController {
    private BuyerAccount currentBuyer = null;
    private Cart currentBuyersCart = null;

    public BuyerController() {
     if(GeneralController.currentUser instanceof BuyerAccount){
         BuyerAccount currentBuyer = (BuyerAccount) GeneralController.currentUser;
         currentBuyersCart = currentBuyer.getCart();
     }
    }

    public String showCartProducts(){
        return currentBuyersCart.viewMe();
    }

    public void increaseProductWithId(String productId) throws Exception {
        if(currentBuyersCart.isThereProductWithID(productId)){
            currentBuyersCart.getCartProductByProductId(productId).increaseNumberByOne();
            return;
        }
        throw new Exception("There is no product with given Id in the cart");
    }

    public void decreaseProductWithId(String productId) throws Exception {
        if(currentBuyersCart.isThereProductWithID(productId)){
            currentBuyersCart.getCartProductByProductId(productId).decreaseNumberByOne();
            return;
        }
        throw new Exception("There is no product with given Id in the cart");
    }

    public String showTotalCartPrice() {
        return null;
    }

    public void purchase() {

    }

    public void getReceiverInformation(ArrayList<String> receiverInfo) {

    }

    public void createDiscountCode(String discountCode) {

    }

    public void payment() {

    }

    public String viewBuyerBalance() {
        return null;
    }

    public String viewBuyerDiscountCodes() {
        return null;
    }

    public String viewBuyerOrders() {
        return null;
    }

    public String showOrderWithId(String orderId) {
        return null;
    }

    public void rateProductWithId(String productId, int score) {

    }
}
