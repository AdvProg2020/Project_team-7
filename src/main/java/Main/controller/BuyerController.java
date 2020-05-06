package Main.controller;

import Main.model.Cart;
import Main.model.DiscountCode;
import Main.model.Product;
import Main.model.Rate;
import Main.model.accounts.BuyerAccount;

import java.util.ArrayList;

public class BuyerController {
    private BuyerAccount currentBuyer = null;
    private Cart currentBuyersCart = null;
    private String receiverInformation = null;
    private DiscountCode discountCode = null;

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
        throw new Exception("There is no product with given Id in the cart !\n");
    }

    public void decreaseProductWithId(String productId) throws Exception {
        if (currentBuyersCart.isThereProductWithID(productId)) {
            currentBuyersCart.getCartProductByProductId(productId).decreaseNumberByOne();
            return;
        }
        throw new Exception("There is no product with given ID in the cart !\n");
    }

    public String showTotalCartPrice() {
        return "Cart's Total Price Considering Offs : " + currentBuyersCart.calculateCartTotalPriceConsideringOffs();
    }

    public String viewBuyerBalance() {
        return "balance : " + currentBuyer.getBalance();
    }

    public String viewBuyerDiscountCodes() {
        return currentBuyer.viewAllDiscountCodes();
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

    public void rateProductWithId(String productId, double score) throws Exception {
        Product product = Product.getProductWithId(productId);
        if(product==null){
            throw new Exception("There is no product with given ID !\n");
        }
        Rate rate = new Rate(currentBuyer, product, score);
        product.addRate(rate);
    }

    public void setReceiverInformation(String receiverInformation) {
        this.receiverInformation = receiverInformation;
    }

    public void setPurchaseDiscountCode(String code){
        //TODO : Exeptions
        this.discountCode = DiscountCode.getDiscountCodeWithCode(code);
    }

    public String showPurchaseInfo(){
        return "Purchase Information :" + "\n\nReceiver Information : \n\t" + receiverInformation + "\n\n" +
                currentBuyersCart.viewMe() + "\n\ntotal amount you got to pay : " + getToTalPaymentConsideringDiscount() +
                "\nDiscount Code : " + (discountCode==null?"no discount code applied yet !":"" + discountCode.getDiscountCodeAmount());
    }

    private double getToTalPaymentConsideringDiscount(){
        return currentBuyersCart.calculateCartTotalPriceConsideringOffs()*(100-discountCode.getDiscountCodeAmount())/100;
    }

    public void finalizePurchaseAndPay() {
        finalizePurchase();
        pay();
    }

    private void finalizePurchase(){

    }

    private void pay(){

    }
}
