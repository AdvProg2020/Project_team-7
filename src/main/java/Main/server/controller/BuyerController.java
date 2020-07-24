package Main.server.controller;

import Main.server.BankClient;
import Main.server.model.*;
import Main.server.model.accounts.BuyerAccount;
import Main.server.model.accounts.SellerAccount;
import Main.server.model.discountAndOffTypeService.DiscountAndOffStat;
import Main.server.model.discountAndOffTypeService.DiscountCode;
import Main.server.model.logs.BuyLog;
import Main.server.model.logs.DeliveryStatus;
import Main.server.model.logs.Log;
import Main.server.model.logs.SellLog;
import Main.server.serverRequestProcessor.Server;

import java.util.Date;
import java.util.HashMap;

public class BuyerController {
    private static BuyerAccount currentBuyer = null;
    private static Cart currentBuyersCart = null;
    private String receiverInformation = null;
    private DiscountCode discountCode = null;

    public static void setBuyerController(BuyerAccount buyerAccount) {
        currentBuyer = buyerAccount;
        currentBuyersCart = currentBuyer.getCart();
    }

    public String showCartProducts() {
        return currentBuyersCart.toStringForBuyer();
    }

    public void increaseProductWithId(String productId) throws Exception {
        currentBuyersCart.getCartProductByProductId(productId).increaseNumberByOne();
    }

    public void decreaseProductWithId(String productId) throws Exception {
        currentBuyersCart.getCartProductByProductId(productId).decreaseNumberByOne();
    }

    public String showTotalCartPrice() {
        return "Cart's Total Price Considering Offs : " + currentBuyersCart.getCartTotalPriceConsideringOffs();
    }

    public String viewBuyerBalance() {
        return "balance : " + currentBuyer.getWalletBalance();
    }

    public String viewBuyerDiscountCodes() {
        return currentBuyer.viewAllDiscountCodes();
    }

    public String viewBuyerOrders() {
        return currentBuyer.viewOrders();
    }

    public String showOrderWithId(String orderId) {
        try {
            return currentBuyer.getLogWithId(orderId).viewLog();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public void rateProductWithId(String productId, String score, String token) throws Exception {
        validateInputRateInfo(productId, score);

        Product product = Product.getProductWithId(productId);
        Rate rate = new Rate((BuyerAccount) Server.getServer().getTokenInfo(token).getUser(), product, Double.parseDouble(score));
        product.addRate(rate);
    }

    private void validateInputRateInfo(String productId, String score) throws Exception {
        StringBuilder rateCreationErrors = new StringBuilder();

        try {
            Product.getProductWithId(productId);
        } catch (Exception e) {
            rateCreationErrors.append(e.getMessage());
        }
        try {
            Double.parseDouble(score);
        } catch (Exception e) {
            rateCreationErrors.append("score must be of double type !\n");
        }
        if (rateCreationErrors.length() != 0) {
            throw new Exception("there where some errors in rating : \n" + rateCreationErrors);
        }
    }

    public void setReceiverInformation(String receiverInformation) {
        this.receiverInformation = receiverInformation;
    }

    public void setPurchaseDiscountCode(String code) throws Exception {
        DiscountCode discountCode = DiscountCode.getDiscountCodeWithCode(code);

        if (discountCode.getDiscountOrOffStat().equals(DiscountAndOffStat.NOT_STARTED)) {
            throw new Exception("This discount code hasn't started yet !\n");
        }
        if (getToTalPaymentConsideringDiscount() > discountCode.getMaxAmount()) {
            throw new Exception("This discount code can not be applied on your cart because it's total cost exceeds discount max amount !\n");
        }
        this.discountCode = discountCode;
    }

    public String showPurchaseInfo() {
        if (discountCode != null && discountCode.getDiscountOrOffStat().equals(DiscountAndOffStat.EXPIRED)) {
            discountCode.removeDiscountCode();
            discountCode = null;
        }
        return "Purchase Information :" + "\nReceiver Information : \n\t" + receiverInformation + "\n" +
                currentBuyersCart.toStringForBuyer() + "Total amount you got to pay : " + getToTalPaymentConsideringDiscount() +
                "\nDiscount Code : " + (discountCode == null ? "no active discount code applied yet !\n" : "" +
                discountCode.getDiscountCodeAmount());
    }

    private double getToTalPaymentConsideringDiscount() {
        if (discountCode != null && discountCode.getDiscountOrOffStat().equals(DiscountAndOffStat.EXPIRED)) {
            discountCode.removeDiscountCode();
            discountCode = null;
        }
        double percentOfCostToBePaid = (discountCode == null ? 100 : 100 - discountCode.getDiscountCodeAmount());
        return currentBuyersCart.getCartTotalPriceConsideringOffs() * percentOfCostToBePaid / 100;
    }

    public String finalizeBankPurchaseAndPay(BuyerAccount buyerAccount) {
        String files = "";
        if (buyerAccount.getCart().getCartsProductList().isEmpty()) {
            return "your cart is empty!";
        }
        try {
            files = getProductsFromRepository();
            bankPay();
        } catch (Exception e) {
            return e.getMessage();
        }
        createPurchaseHistoryElements();
        currentBuyersCart.emptyCart();
        return "Purchase finished successfully.\n" + files;
    }

    public String finalizeWalletPurchaseAndPay(BuyerAccount buyerAccount) {
        String files = "";
        if (buyerAccount.getCart().getCartsProductList().isEmpty()) {
            return "your cart is empty!";
        }
        if (currentBuyer.getWalletBalance() < getToTalPaymentConsideringDiscount() + ShopFinance.getInstance().getMinimumWalletBalance()) {
            return "insufficient wallet balance";
        }
        try {
            files = getProductsFromRepository();
            walletPay();
        } catch (Exception e) {
            return e.getMessage();
        }
        createPurchaseHistoryElements();
        currentBuyersCart.emptyCart();
        return "Purchase finished successfully.\n" + files;
    }

    private void walletPay() throws Exception {
        buyerAndSellerWalletPayment();
        if (discountCode != null) {
            discountCode.removeDiscountCodeIfBuyerHasUsedUpDiscountCode(currentBuyer);
        }
    }

    private void buyerAndSellerWalletPayment() throws Exception {
        if (currentBuyer.isOnAuction != null) {
            throw new Exception("you have an offer in an auction ! you can't purchase now");
        }
        currentBuyer.decreaseBalanceBy(getToTalPaymentConsideringDiscount());
        HashMap<SellerAccount, Cart> sellers = currentBuyersCart.getAllSellersCarts();
        for (SellerAccount sellerAccount : sellers.keySet()) {
            sellerAccount.increaseBalanceBy(sellers.get(sellerAccount).getCartTotalPriceConsideringOffs() * (100 - ShopFinance.getInstance().getCommission()) / 100);
        }
    }

    private String getProductsFromRepository() throws Exception {
        String files = "";
        validateGettingProductsFromRepository();
        for (CartProduct cartProduct : currentBuyersCart.getCartProducts()) {
            if (cartProduct.getProduct().isOnAuction) {
                throw new Exception("this product is on an auction!you cant buy it now.");
            }
        }
        for (CartProduct cartProduct1 : currentBuyersCart.getCartProducts()) {
            cartProduct1.getProduct().decreaseAvailabilityBy(cartProduct1.getNumberOfProduct());
            if (cartProduct1.getProduct().getName().endsWith("___FILEPRODUCT"))
                files = files.concat("File detected: " + cartProduct1.getProduct().getName().substring(0, cartProduct1.getProduct().getName().indexOf("___")) + "\n");
        }
        return files;
    }

    private void validateGettingProductsFromRepository() throws Exception {
        StringBuilder gettingProductsFromRepositoryErrors = new StringBuilder();
        for (CartProduct cartProduct : currentBuyersCart.getCartProducts()) {
            if (!cartProduct.isProductAvailabilityEnough()) {
                gettingProductsFromRepositoryErrors.append("sorry we are out of product with ID : "
                        + cartProduct.getProduct().getProductId() + "\nthere is only " + cartProduct.getProduct().getAvailability() +
                        " of this product left !\n" + "please increase number of this product !\n");
            }
        }
        if (gettingProductsFromRepositoryErrors.length() != 0) {
            throw new Exception("there were some errors in purchase process : \n");
        }
    }

    private void bankPay() throws Exception {
        buyerAndSellerBankPayment();
        if (discountCode != null) {
            discountCode.removeDiscountCodeIfBuyerHasUsedUpDiscountCode(currentBuyer);
        }
    }

//    private void buyerPayment() throws Exception {
//        if (currentBuyer.isOnAuction != null) {
//            throw new Exception("you have an offer in an auction ! you can't purchase now");
//        }
//        currentBuyer.decreaseBalanceBy(getToTalPaymentConsideringDiscount());
//    }

    private void buyerAndSellerBankPayment() throws Exception {
        if (currentBuyer.isOnAuction != null) {
            throw new Exception("you have an offer in an auction ! you can't purchase now");
        }
        String token = BankClient.getResponseFromBankServer("get_token " + currentBuyer.getUserName() + " " + currentBuyer.getPassWord());
        String receiptID = BankClient.getResponseFromBankServer("create_receipt " + token + " withdraw " + getToTalPaymentConsideringDiscount() + " " + currentBuyer.getBankAccountID() + " -1 bankPurchase");
        String result = BankClient.getResponseFromBankServer("pay " + receiptID);
        HashMap<SellerAccount, Cart> sellers = currentBuyersCart.getAllSellersCarts();
        for (SellerAccount sellerAccount : sellers.keySet()) {
            String receiptID1 = BankClient.getResponseFromBankServer("create_receipt " + token + " deposit " + sellers.get(sellerAccount).getCartTotalPriceConsideringOffs() + " -1 " + ShopFinance.getInstance().getAccountID() + " bankPurchase");
            String result1 = BankClient.getResponseFromBankServer("pay " + receiptID1);
            sellerAccount.increaseBalanceBy(sellers.get(sellerAccount).getCartTotalPriceConsideringOffs() * (100 - ShopFinance.getInstance().getCommission()) / 100);
        }
    }

//    private void sellersPayment() {
//        HashMap<SellerAccount, Cart> sellers = currentBuyersCart.getAllSellersCarts();
//        for (SellerAccount sellerAccount : sellers.keySet()) {
//            sellerAccount.increaseBalanceBy(sellers.get(sellerAccount).getCartTotalPriceConsideringOffs());
//        }
//    }

    private void createPurchaseHistoryElements() {
        String logID = IDGenerator.getNewID(Log.getLastUsedLogID());
        Date date = new Date();
        createPurchaseHistoryElementsForBuyer(date, logID);
        createPurchaseHistoryElementsForSellers(date, logID);
    }

    private void createPurchaseHistoryElementsForBuyer(Date date, String logID) {
        BuyLog buyLog = new BuyLog(logID, date, getToTalPaymentConsideringDiscount(),
                (discountCode == null ? 0 : discountCode.getDiscountCodeAmount()), currentBuyersCart.toStringForBuyer(),
                DeliveryStatus.PENDING_DELIVERY, receiverInformation);
        Log.addLog(buyLog);
        currentBuyer.addLog(buyLog);
        currentBuyer.addCartsProductsToBoughtProducts();
        for (Product product : currentBuyer.getCart().getCartsProductList()) {
            product.buyers.add(currentBuyer);
        }
    }

    private void createPurchaseHistoryElementsForSellers(Date date, String logID) {
        HashMap<SellerAccount, Cart> allSellersCart = currentBuyersCart.getAllSellersCarts();
        for (SellerAccount sellerAccount : allSellersCart.keySet()) {
            Cart cart = allSellersCart.get(sellerAccount);
            SellLog sellLog = new SellLog(logID, date, cart.getCartTotalPriceConsideringOffs(), cart.toStringForSeller(),
                    currentBuyer, cart.calculateCartTotalOffs(), DeliveryStatus.PENDING_DELIVERY, receiverInformation);
            Log.addLog(sellLog);
            sellerAccount.addLog(sellLog);
        }
    }
}
