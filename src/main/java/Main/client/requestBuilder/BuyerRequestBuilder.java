package Main.client.requestBuilder;

import Main.client.ClientMain;
import Main.client.graphicView.GraphicMain;
import javafx.collections.ObservableList;

import java.util.List;

public class BuyerRequestBuilder {

    public static String buildInitializeBuyerPanelRequest() {
        return ClientMain.client.sendRequest(GraphicMain.token + "#buyerBalance");
    }

    public static String buildBuyerPersonalInformationRequest() {
        return ClientMain.client.sendRequest(GraphicMain.token + "#buyerPersonalInfo");
    }

    public static String buildEditBuyerPersonalInformationRequest(String newEmail, String newFirstName, String newLastName, String newPhoneNumber, String newPassword) {
        return ClientMain.client.sendRequest(GraphicMain.token + "#editBuyerPersonalInformation#" + newFirstName + "#" + newLastName + "#" + newEmail + "#" + newPhoneNumber + "#" + newPassword);
    }

    public static String buildIncreaseAuctionPriceRequest(String incrementAmount, String address) {
        double amount = 0;
        try {
            amount = Double.parseDouble(incrementAmount);
            if (amount <= 0) {
                throw new Exception("");
            }
        } catch (Exception e) {
            return "invalidNo";
        }
        String increaseAmountRequest = GraphicMain.token + "#buyer#increaseAuction#" + GraphicMain.currentAuctionId + "#" + amount + "#" + address;
        return ClientMain.client.sendRequest(increaseAmountRequest);
    }

    public static String buildSendMessageRequest(String text) {
        if (text.equals("")) {
            return "emptyString";
        }
        String sendMessageRequest = GraphicMain.token + "#buyer#sendMessage#" + GraphicMain.currentAuctionId + "#" + text;
        return ClientMain.client.sendRequest(sendMessageRequest);
    }

    public static String buildInitializeMyOrdersRequest() {
        return ClientMain.client.sendRequest(GraphicMain.token + "#initializeMyOrders");
    }

    public static String buildGetBuyLogInfo(String logId) {
        return ClientMain.client.sendRequest(GraphicMain.token + "#getBuyLogInfo#" + logId);
    }

    public static String buildInitializeBuyerDiscountsRequest() {
        return ClientMain.client.sendRequest(GraphicMain.token + "#initializeBuyerDiscounts");
    }

    public static String buildShowDiscountInfoAsBuyerRequest(String code) {
        return ClientMain.client.sendRequest(GraphicMain.token + "#showDiscountInfoAsBuyer#" + code);
    }

    public static String buildInitializeCartAndPriceRequest() {
        return ClientMain.client.sendRequest(GraphicMain.token + "#initializeCartAndPrice");
    }

    public static List buildGetCartProductsRequest() throws ClassNotFoundException {
        return ClientMain.client.sendRequestObject(GraphicMain.token + "#getCartProducts");
    }

    public static String buildDecreaseCartProductRequest(String productId) {
        return ClientMain.client.sendRequest(GraphicMain.token + "#decreaseCartProduct#" + productId);
    }

    public static String buildIncreaseCartProductRequest(String productId) {
        return ClientMain.client.sendRequest(GraphicMain.token + "#increaseCartProduct#" + productId);
    }

    public static void buildSetReceiverInformationRequest(String receiverInformation) {
        ClientMain.client.sendRequest(GraphicMain.token + "#setReceiverInformation#" + receiverInformation);
    }

    public static String buildSetPurchaseDiscountRequest(String text) {
        return ClientMain.client.sendRequest(GraphicMain.token + "#setPurchaseDiscount#" + text);
    }

    public static String buildShowPurchaseInfoRequest() {
        return ClientMain.client.sendRequest(GraphicMain.token + "#showPurchaseInfo");
    }

    public static String buildFinalizePaymentRequestProcessor() {
        return ClientMain.client.sendRequest(GraphicMain.token + "#finalizePayment");
    }
}
