package Main.client.requestBuilder;

import Main.client.ClientMain;
import Main.client.graphicView.GraphicMain;

import java.util.ArrayList;

public class SellerRequestBuilder {

    public static String buildCommentRequest(String title, String content) {
        String request = GraphicMain.token + "#addComment#" + title + content;
        return ClientMain.client.sendRequest(request);
    }

    public static String getListItemsForAddOffPage() {
        String request = GraphicMain.token + "#getListItemsForAddOffPage";
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

    public static String buildAddSpecialFeaturesRequest(ArrayList<String> specialFeatures, String productId) {
        StringBuilder request = new StringBuilder();
        request.append(GraphicMain.token + "#" + productId + "#addSpecialFeatures");
        for (String specialFeature : specialFeatures) {
            request.append("#" + specialFeature);
        }
        return ClientMain.client.sendRequest(request.toString());
    }

    public static String getProductForProductEditPage(String productId) {
        StringBuilder request = new StringBuilder();
        request.append(GraphicMain.token + "#getProductForProductEditPage#" + productId);
        return ClientMain.client.sendRequest(request.toString());
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

    public static String buildEditPersonalInformationRequest(String fieldToEdit, String newContent){
        StringBuilder request = new StringBuilder();
        request.append(GraphicMain.token + "#editSellerPersonalInformation#" + fieldToEdit + "#" + newContent);
        return ClientMain.client.sendRequest(request.toString());
    }

    public static String buildEditOffRequest(String offId, ArrayList<String> titles, ArrayList<String> contents){
        StringBuilder request = new StringBuilder();
        request.append(GraphicMain.token + "#editOff#" + offId);
        if(titles.contains("start date")){
            request.append("#start date#" + contents.get(titles.indexOf("start date")));
        }
        if(titles.contains("end date")){
            request.append("#end date#" + contents.get(titles.indexOf("end date")));
        }
        if(titles.contains("off amount")){
            request.append("#off amount#" + contents.get(titles.indexOf("off amount")));
        }
        if(titles.contains("add product")){
            request.append("#add product#" + contents.get(titles.indexOf("add product")));
        }
        if(titles.contains("remove product")){
            request.append("#remove product#" + contents.get(titles.indexOf("remove product")));
        }
        return ClientMain.client.sendRequest(request.toString());
    }

    public static String getSellLogList(){
        return ClientMain.client.sendRequest(GraphicMain.token + "#getSellLogList");
    }

    public static String getLogDetails(String id){
        return ClientMain.client.sendRequest(GraphicMain.token + "#getLogDetails#" + id);
    }

    public static String getSellerOffList(){
        return ClientMain.client.sendRequest(GraphicMain.token + "#getSellerOffList");
    }

    public static String getOffDetails(String offId){
        return ClientMain.client.sendRequest(GraphicMain.token + "#getOffDetails#" + offId);
    }
}
