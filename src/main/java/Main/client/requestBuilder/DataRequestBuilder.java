package Main.client.requestBuilder;

import Main.client.ClientMain;
import Main.client.graphicView.GraphicMain;
import Main.server.model.exceptions.AccountsException;

public class DataRequestBuilder {
    public static String buildAllProductsRequest() {
        String allProductsDataRequest = GraphicMain.token + "#data#allProducts";
        return ClientMain.client.sendRequest(allProductsDataRequest);
    }

    public static String buildAllCategoriesRequest() {
        String allCategoriesDataRequest = GraphicMain.token + "#data#allCategories";
        return ClientMain.client.sendRequest(allCategoriesDataRequest);
    }

    public static String buildAllSellersRequest() {
        String allSellersDataRequest = GraphicMain.token + "#data#allSellers";
        return ClientMain.client.sendRequest(allSellersDataRequest);
    }

    public static String buildAllOffsRequest() {
        String allOffsDataRequest = GraphicMain.token + "#data#allOffs";
        return ClientMain.client.sendRequest(allOffsDataRequest);
    }

    public static String buildAllBuyersRequest() {
        String allBuyersDataRequest = GraphicMain.token + "#data#allBuyers";
        return ClientMain.client.sendRequest(allBuyersDataRequest);
    }

    public static String buildLogRequestWithID(String logID) {
        String logDataRequest = GraphicMain.token + "#data#log#" + logID;
        return ClientMain.client.sendRequest(logDataRequest);
    }
}
