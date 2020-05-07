package Main.controller;

import Main.model.*;
import Main.model.accounts.BuyerAccount;
import Main.model.accounts.SellerAccount;
import Main.model.logs.BuyLog;
import Main.model.logs.DeliveryStatus;
import Main.model.logs.Log;
import Main.model.logs.SellLog;

import java.util.Date;
import java.util.HashMap;

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
        return currentBuyersCart.toStringForBuyer();
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
        return "Cart's Total Price Considering Offs : " + currentBuyersCart.getCartTotalPriceConsideringOffs();
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
        if (product == null) {
            throw new Exception("There is no product with given ID !\n");
        }
        Rate rate = new Rate(currentBuyer, product, score);
        product.addRate(rate);
    }

    public void setReceiverInformation(String receiverInformation) {
        this.receiverInformation = receiverInformation;
    }

    public void setPurchaseDiscountCode(String code) throws Exception {
        DiscountCode discountCode = DiscountCode.getDiscountCodeWithCode(code);
        if (discountCode == null) {
            throw new Exception("There is no discount code with given code !\n");
        }
        if (getToTalPaymentConsideringDiscount() > discountCode.getMaxAmount()) {
            throw new Exception("This discount code cant be applied on your cart because it's total cost exceeds discount max amount !\n");
        }
        this.discountCode = discountCode;
    }

    public String showPurchaseInfo() {
        return "Purchase Information :" + "\n\nReceiver Information : \n\t" + receiverInformation + "\n\n" +
                currentBuyersCart.toStringForBuyer() + "\n\ntotal amount you got to pay : " + getToTalPaymentConsideringDiscount() +
                "\nDiscount Code : " + (discountCode == null ? "no discount code applied yet !" : "" + discountCode.getDiscountCodeAmount());
    }

    private double getToTalPaymentConsideringDiscount() {
        return currentBuyersCart.getCartTotalPriceConsideringOffs() * (100 - discountCode.getDiscountCodeAmount()) / 100;
    }

    public void finalizePurchaseAndPay() throws Exception {
        pay();
        createPurchaseHistoryElements();
        currentBuyersCart.emptyCart();
    }

    private void pay() throws Exception {
        buyerPayment();
        sellersPayment();
    }

    private void buyerPayment() throws Exception {
        if(currentBuyer.getBalance()<getToTalPaymentConsideringDiscount()){
            throw new Exception("Your balance isn't enough ! Purchase couldn't be done !\n");
        }
        currentBuyer.decreaseBalanceBy(getToTalPaymentConsideringDiscount());
        discountCode.removeDiscountCodeIfBuyerHasUsedUpDiscountCode(currentBuyer);
    }

    private void sellersPayment(){
        HashMap<SellerAccount,Cart> sellers = currentBuyersCart.getAllSellersCarts();
        for (SellerAccount sellerAccount : sellers.keySet()) {
            sellerAccount.increaseBalanceBy(sellers.get(sellerAccount).getCartTotalPriceConsideringOffs());
        }
    }

    private void createPurchaseHistoryElements(){
        String logID = IDGenerator.getNewID(Log.getLastUsedLogID());
        Date date = new Date();
        createPurchaseHistoryElementsForBuyer(date,logID);
        createPurchaseHistoryElementsForSellers(date,logID);
    }

    private void createPurchaseHistoryElementsForBuyer(Date date, String logID) {
        BuyLog buyLog = new BuyLog(logID, date, getToTalPaymentConsideringDiscount(),
                discountCode.getDiscountCodeAmount(), currentBuyersCart.toStringForBuyer(), DeliveryStatus.PENDING_DELIVERY, receiverInformation);
        currentBuyer.addLog(buyLog);
        currentBuyer.addCartsProductsToBoughtProducts();
    }

    private void createPurchaseHistoryElementsForSellers(Date date,String logID) {
        HashMap<SellerAccount, Cart> allSellersCart = currentBuyersCart.getAllSellersCarts();
        for (SellerAccount sellerAccount : allSellersCart.keySet()) {
            Cart cart = allSellersCart.get(sellerAccount);
            SellLog sellLog = new SellLog(logID,date,cart.getCartTotalPriceConsideringOffs(),cart.toStringForSeller(),
                    currentBuyer,cart.calculateCartTotalOffs(),DeliveryStatus.PENDING_DELIVERY,receiverInformation);
            sellerAccount.addLog(sellLog);
        }
    }
}
