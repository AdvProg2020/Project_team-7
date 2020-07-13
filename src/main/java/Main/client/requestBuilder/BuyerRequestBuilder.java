package Main.client.requestBuilder;

import Main.client.ClientMain;
import Main.client.ClientMainFORBUYERTEST;

public class BuyerRequestBuilder {
    public static String buildInitializeBuyerPanelRequest() {
        return ClientMainFORBUYERTEST.client.sendRequest("0000/buyerBalance");
    }
}
