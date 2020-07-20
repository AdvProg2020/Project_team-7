package Main.client.requestBuilder;

import Main.client.ClientMain;
import Main.client.graphicView.GraphicMain;

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

    public static String buildAuctionRequestWithID(String auctionID) {
        String auctionDataRequest = GraphicMain.token + "#data#auction#" + auctionID;
        return ClientMain.client.sendRequest(auctionDataRequest);
    }

    public static String buildAllAuctionsRequest() {
        String allAuctionsDataRequest = GraphicMain.token + "#data#allAuctions";
        return ClientMain.client.sendRequest(allAuctionsDataRequest);
    }

    public static String buildUserTypeRequest() {
        String userTypeRequest = GraphicMain.token + "#data#userType";
        return ClientMain.client.sendRequest(userTypeRequest);
    }

    public static String buildAllProductsForAuctionRequest() {
        String allProductsForAuctionRequest = GraphicMain.token + "#data#allProductsForAuction";
        return ClientMain.client.sendRequest(allProductsForAuctionRequest);
    }

    public static String buildHighestOfferRequest() {
        String highestOfferRequest = GraphicMain.token + "#data#highestOffer#" + GraphicMain.currentAuctionId + "#timer";
        return ClientMain.client.sendRequest(highestOfferRequest);
    }

    public static String buildMessageNoRequest() {
        String messageNoRequest = GraphicMain.token + "#data#messageNo#" + GraphicMain.currentAuctionId + "#timer";
        return ClientMain.client.sendRequest(messageNoRequest);
    }
}
