package Main.client.requestBuilder;

//import Main.client.ClientMainFORBUYERTEST;

import Main.client.ClientMain;
import Main.client.graphicView.GraphicMain;

public class BuyerRequestBuilder {

    public static String buildInitializeBuyerPanelRequest() {
       // return ClientMainFORBUYERTEST.client.sendRequest("0#buyerBalance");
        return null;
    }

    public static String buildBuyerPersonalInformationRequest() {
       // return ClientMainFORBUYERTEST.client.sendRequest("0#buyerPersonalInfo");
    return null;
    }

    public static void buildEditBuyerPersonalInformationRequest(String text, String text1, String text2, String text3, String text4) {

    }

    public static String buildIncreaseAuctionPriceRequest(String incrementAmount) {
        double amount=0;
        try {
            amount = Double.parseDouble(incrementAmount);
            if(amount<=0){
                throw new Exception("");
            }
        } catch (Exception e) {
            return "invalidNo";
        }
        String  increaseAmountRequest = GraphicMain.token + "#buyer#increaseAuction#" + amount;
        return ClientMain.client.sendRequest(increaseAmountRequest);
    }
}
