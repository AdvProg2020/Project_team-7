package Main.client.requestBuilder;

import Main.client.ClientMain;
import Main.server.model.exceptions.AccountsException;

public class DataRequestBuilder {
    public static String buildAllProductsRequest() {
        String allProductsDataRequest = "0000#data#allProducts";
        return ClientMain.client.sendRequest(allProductsDataRequest);
    }

    public static String buildAllCategoriesRequest() {
        String allCategoriesDataRequest = "0000#data#allCategories";
        return ClientMain.client.sendRequest(allCategoriesDataRequest);
    }

    public static String buildAllSellersRequest() {
        String allSellersDataRequest = "0000#data#allSellers";
        return ClientMain.client.sendRequest(allSellersDataRequest);
    }

    public static String buildAllOffsRequest() {
        String allOffsDataRequest = "0000#data#allOffs";
        return ClientMain.client.sendRequest(allOffsDataRequest);
    }
}