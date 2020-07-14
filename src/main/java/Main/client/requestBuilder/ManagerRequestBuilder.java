package Main.client.requestBuilder;

//import Main.client.ClientMainFORMANAGERTEST;

import Main.client.ClientMain;

public class ManagerRequestBuilder {
    public static String buildManagerPersonalInformationRequest() {
        //return ClientMainFORMANAGERTEST.client.sendRequest("1#managerPersonalInfo");
   return null;
    }

    public static String buildMarkDeliveredRequest(String logID) {
        String markDeliveredRequest = "0000#manager#markDelivered#" + logID;
        return ClientMain.client.sendRequest(markDeliveredRequest);
    }
}
