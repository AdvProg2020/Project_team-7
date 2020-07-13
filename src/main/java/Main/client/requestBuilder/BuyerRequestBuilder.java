package Main.client.requestBuilder;

import Main.client.ClientMainFORBUYERTEST;

public class BuyerRequestBuilder {
    public static String buildInitializeBuyerPanelRequest() {
        return ClientMainFORBUYERTEST.client.sendRequest("0#buyerBalance");
    }

    public static String buildBuyerPersonalInformationRequest() {
        return ClientMainFORBUYERTEST.client.sendRequest("1#buyerPersonalInfo");
    }
}
