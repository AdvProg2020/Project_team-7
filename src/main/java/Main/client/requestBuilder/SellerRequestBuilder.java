package Main.client.requestBuilder;

import Main.client.ClientMain;
import Main.client.graphicView.GraphicMain;
import Main.server.model.exceptions.DiscountAndOffTypeServiceException;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class SellerRequestBuilder {

    public static String buildCommentRequest(String title, String content) {
        String request = GraphicMain.token + "#addComment#" + title + content;
        return ClientMain.client.sendRequest(request);
    }

    public static String getSellerProductsList() {
        String request = GraphicMain.token + "#getSellerProductsList";
        return ClientMain.client.sendRequest(request);
    }

    public static String buildAddOffRequest(ArrayList<String> productIdList, ArrayList<String> offInfo) {
        StringBuilder request = new StringBuilder();
        request.append(GraphicMain.token + "#addOff#");
        for (String id : productIdList) {
            request.append(id + "#");
        }
        request.append("offInfo");
        for (String info : offInfo) {
            request.append("#" + info);
        }
        return ClientMain.client.sendRequest(request.toString());
    }

    public static String buildAddProductRequest(ArrayList<String> productInfo) {
        StringBuilder request = new StringBuilder();
        request.append(GraphicMain.token + "#addProduct");
        for (String info : productInfo) {
            request.append("#" + info);
        }
        return ClientMain.client.sendRequest(request.toString());
    }

    public static String buildAddFileRequest(ArrayList<String> productInfo, File file) {
        StringBuilder request = new StringBuilder();
        request.append(GraphicMain.token + "#addFileProduct");
        for (String info : productInfo) {
            request.append("#" + info);
        }
        return ClientMain.client.sendRequestFile(request.toString(), file);
    }

    public static String buildAddSpecialFeaturesRequest(ArrayList<String> specialFeatures, String productId) {
        StringBuilder request = new StringBuilder();
        request.append(GraphicMain.token + "#addSpecialFeatures" + "#" + productId);
        for (String specialFeature : specialFeatures) {
            request.append("#" + specialFeature);
        }
        return ClientMain.client.sendRequest(request.toString());
    }

    public static String getProductForProductEditPage(String productId) {
        return ClientMain.client.sendRequest(GraphicMain.token + "#getProductForProductEditPage#" + productId);
    }

    public static String buildEditProductRequest(String productId, ArrayList<String> titles, ArrayList<String> contents) {
        StringBuilder request = new StringBuilder();
        request.append(GraphicMain.token + "#editProduct#" + productId);
        if (titles.contains("name")) {
            request.append("#name#" + contents.get(titles.indexOf("name")));
        }
        if (titles.contains("brand")) {
            request.append("#brand#" + contents.get(titles.indexOf("brand")));
        }
        if (titles.contains("availability")) {
            request.append("#availability#" + contents.get(titles.indexOf("availability")));
        }
        if (titles.contains("description")) {
            request.append("#description#" + contents.get(titles.indexOf("description")));
        }
        if (titles.contains("price")) {
            request.append("#price#" + contents.get(titles.indexOf("price")));
        }
        if (titles.contains("off")) {
            request.append("#off#" + contents.get(titles.indexOf("off")));
        }
        return ClientMain.client.sendRequest(request.toString());
    }

    public static String buildEditPersonalInformationRequest(String fieldToEdit, String newContent) {
        return ClientMain.client.sendRequest(GraphicMain.token + "#editSellerPersonalInformation#" + fieldToEdit + "#" + newContent);
    }

    public static String buildEditOffRequest(String offId, ArrayList<String> titles, ArrayList<String> contents) {
        StringBuilder request = new StringBuilder();
        request.append(GraphicMain.token + "#editOff#" + offId);
        if (titles.contains("start date")) {
            request.append("#start date#" + contents.get(titles.indexOf("start date")));
        }
        if (titles.contains("end date")) {
            request.append("#end date#" + contents.get(titles.indexOf("end date")));
        }
        if (titles.contains("off amount")) {
            request.append("#off amount#" + contents.get(titles.indexOf("off amount")));
        }
        if (titles.contains("add product")) {
            request.append("#add product#" + contents.get(titles.indexOf("add product")));
        }
        if (titles.contains("remove product")) {
            request.append("#remove product#" + contents.get(titles.indexOf("remove product")));
        }
        return ClientMain.client.sendRequest(request.toString());
    }

    public static String buildCreateAuctionRequest(String startDate, String endDate, String productId) {
        try {
            DiscountAndOffTypeServiceException.validateInputDate(startDate);
            DiscountAndOffTypeServiceException.validateInputDate(endDate);
            DiscountAndOffTypeServiceException.compareStartAndEndDate(startDate, endDate);
        } catch (Exception e) {
            return "invalidDate";
        }
        String request = GraphicMain.token + "#seller#createAuction#" + startDate + "#" + endDate + "#" + productId;
        return ClientMain.client.sendRequest(request);
    }

    public static String getSellLogList() {
        return ClientMain.client.sendRequest(GraphicMain.token + "#getSellLogList");
    }

    public static String getLogDetails(String id) {
        return ClientMain.client.sendRequest(GraphicMain.token + "#getLogDetails#" + id);
    }

    public static String getSellerOffList() {
        return ClientMain.client.sendRequest(GraphicMain.token + "#getSellerOffList");
    }

    public static String getOffDetails(String offId) {
        return ClientMain.client.sendRequest(GraphicMain.token + "#getOffDetails#" + offId);
    }

    public static String getSellerPersonalInformation() {
        return ClientMain.client.sendRequest(GraphicMain.token + "#getSellerPersonalInformation");
    }

    public static String getSellerCompanyInformation() {
        return ClientMain.client.sendRequest(GraphicMain.token + "#getCompanyInformation");
    }

    public static String getSellerBalance() {
        return ClientMain.client.sendRequest(GraphicMain.token + "#getSellerBalance");
    }

    public static String getSellerCategories() {
        return ClientMain.client.sendRequest(GraphicMain.token + "#getSellerCategories");
    }

    public static String getAllProductDataForSellerProductPage(String productId) {
        return ClientMain.client.sendRequest(GraphicMain.token + "#getAllProductDataForSellerProductPage#" + productId);
    }

    public static String buildRemoveProductRequest(String productId) {
        return ClientMain.client.sendRequest(GraphicMain.token + "#removeProduct#" + productId);
    }

    public static ArrayList<String> buildGetCategorySpecialFeatures(String categoryName) {
        String features = ClientMain.client.sendRequest(GraphicMain.token + "#getCategorySpecialFeatures#" + categoryName);
        ArrayList<String> featureList = new ArrayList<>(Arrays.asList(features.split("#")));
        return featureList;
    }
}
