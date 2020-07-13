package Main.client.requestBuilder;

import Main.client.ClientMainFORMANAGERTEST;

public class ManagerRequestBuilder {
    public static String buildManagerPersonalInformationRequest() {
        return ClientMainFORMANAGERTEST.client.sendRequest("1#managerPersonalInfo");
    }
}
