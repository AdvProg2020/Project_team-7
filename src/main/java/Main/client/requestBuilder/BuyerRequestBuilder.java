package Main.client.requestBuilder;


import Main.client.ClientMain;
import Main.client.graphicView.GraphicMain;

public class BuyerRequestBuilder {

    public static String buildInitializeBuyerPanelRequest() {
        return ClientMain.client.sendRequest(GraphicMain.token+"#buyerBalance");
    }

    public static String buildBuyerPersonalInformationRequest() {
        return ClientMain.client.sendRequest(GraphicMain.token+"#buyerPersonalInfo");
    }

    public static String buildEditBuyerPersonalInformationRequest(String newEmail, String newFirstName, String newLastName, String newPhoneNumber, String newPassword) {
        return ClientMain.client.sendRequest(GraphicMain.token+"#editBuyerPersonalInformation#"+newFirstName+"#"+newLastName+"#"+newEmail+"#"+newPhoneNumber+"#"+newPassword);
    }

    public static String buildIncreaseAuctionPriceRequest(String incrementAmount) {
        double amount = 0;
        try {
            amount = Double.parseDouble(incrementAmount);
            if (amount <= 0) {
                throw new Exception("");
            }
        } catch (Exception e) {
            return "invalidNo";
        }
        String increaseAmountRequest = GraphicMain.token + "#buyer#increaseAuction#" + GraphicMain.currentAuctionId + "#" + amount;
        return ClientMain.client.sendRequest(increaseAmountRequest);
    }

    public static String buildInitializeMyOrdersRequest() {
        return ClientMain.client.sendRequest(GraphicMain.token+"#initializeMyOrders");
    }

    public static String buildGetBuyLogInfo(String logId) {
        return ClientMain.client.sendRequest(GraphicMain.token+"#getBuyLogInfo#"+logId);
    }
}
