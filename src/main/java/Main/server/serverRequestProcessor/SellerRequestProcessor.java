package Main.server.serverRequestProcessor;

import Main.client.graphicView.GraphicMain;

import java.util.ArrayList;

public class SellerRequestProcessor {

    public static String buildCommentResponse(String[] splitRequest){
        String title = splitRequest[2];
        String content = splitRequest[3];
        try {
            GraphicMain.generalController.addComment(title, content, splitRequest[0]);
            return "success";
        }catch (Exception e){
            return "error#" + e.getMessage();
        }
    }

    public static String getListItemsForAddOffPage(String token){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("productNames");
        for (String productName : GraphicMain.sellerController.getSellerProductNames(token)) {
            stringBuilder.append("#" + productName);
        }
        return stringBuilder.toString();
    }

    public static String buildAddOffResponse(String[] splitRequest){
        ArrayList<String> split = new ArrayList<>();
        ArrayList<String> productIdList = new ArrayList<>();
        ArrayList<String> offInfo = new ArrayList<>();
        for(int i=0; i<splitRequest.length; i++){
           split.add(splitRequest[i]);
        }
        for(int i=2; i<split.indexOf("offInfo"); i++){
            productIdList.add(split.get(i));
        }
        for(int i=(split.indexOf("offInfo")+1); i<split.size(); i++){
            offInfo.add(split.get(i));
        }
        try {
            GraphicMain.sellerController.addOff(productIdList, offInfo, splitRequest[0]);
            return "success";
        } catch (Exception e) {
            return "error#" + e.getMessage();
        }
    }
}
