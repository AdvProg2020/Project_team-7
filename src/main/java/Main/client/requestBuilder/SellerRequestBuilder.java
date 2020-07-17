package Main.client.requestBuilder;

import Main.client.ClientMain;
import Main.client.graphicView.GraphicMain;
import java.util.ArrayList;

public class SellerRequestBuilder {

    public static String buildCommentRequest(String title, String content){
        String request = GraphicMain.token + "#addComment#" + title + content;
        return ClientMain.client.sendRequest(request);
    }

    public static  String getListItemsForAddOffPage(){
        String request = GraphicMain.token + "#getListItemsForAddOffPage";
        return ClientMain.client.sendRequest(request);
    }

    public static String buildAddOffRequest(ArrayList<String> productIdList, ArrayList<String> offInfo){
        StringBuilder request = new StringBuilder();
        request.append(GraphicMain.token + "#addOff#");
        for (String id : productIdList) {
            request.append(id + "#");
        }
        request.append("offInfo");
        for (String info : offInfo) {
            request.append( "#" + info);
        }
        return ClientMain.client.sendRequest(request.toString());
    }

    public static String buildAddProductRequest(ArrayList<String> productInfo){
        StringBuilder request = new StringBuilder();
        request.append(GraphicMain.token + "#addProduct");
        for (String info : productInfo) {
            request.append("#" + info);
        }
        return ClientMain.client.sendRequest(request.toString());
    }
}
