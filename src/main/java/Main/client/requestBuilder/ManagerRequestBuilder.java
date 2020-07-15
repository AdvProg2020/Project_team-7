package Main.client.requestBuilder;

import Main.client.ClientMainFORMANAGERTEST;

import Main.client.ClientMain;
import Main.client.graphicView.GraphicMain;

public class ManagerRequestBuilder {

    public static String buildManagerPersonalInformationRequest() {
        return ClientMain.client.sendRequest(GraphicMain.token+"#managerPersonalInfo");
    }

    public static String buildEditManagerPersonalInformationRequest(String newFirstName, String newLastName, String newEmail, String newPhoneNumber, String newPassWord){
        return  ClientMain.client.sendRequest(GraphicMain.token+"#editManagerPersonalInfo#"+newFirstName+"#"+newLastName+"#"+newEmail+"#"+newPhoneNumber+"#"+newPassWord);
    }

    public static String buildMarkDeliveredRequest(String logID) {
        String markDeliveredRequest = GraphicMain.token + "#manager#markDelivered#" + logID;
        return ClientMain.client.sendRequest(markDeliveredRequest);
    }

    public static String buildInitializeManageRequestsRequest(){
        return ClientMain.client.sendRequest(GraphicMain.token+"#initializeManageRequests");
    }

    public static String buildShowRequestWithIdRequest(String id) {
        return ClientMain.client.sendRequest(GraphicMain.token+"#showRequestWithId#"+id);
    }

    public static String buildAcceptRequestWithIdRequest(String id) {
        return ClientMain.client.sendRequest(GraphicMain.token+"#acceptRequestWithId#"+id);
    }

    public static String buildDeclineRequestWithIdRequest(String id) {
        return ClientMain.client.sendRequest(GraphicMain.token+"#declineRequestWithId#"+id);
    }
}
