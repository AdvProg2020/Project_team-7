package Main.server.serverRequestProcessor;

import Main.client.graphicView.GraphicMain;
import Main.server.ServerMain;
import Main.server.controller.GeneralController;
import Main.server.model.Category;
import Main.server.model.exceptions.CreateProductException;

import java.util.ArrayList;

public class SellerRequestProcessor {

    public static String buildCommentResponse(String[] splitRequest){
        String title = splitRequest[2];
        String content = splitRequest[3];
        try {
            //TODO ALERT: watch out for the null arguments :D
            ServerMain.generalController.addComment(title, content, splitRequest[0],null);
            return "success";
        }catch (Exception e){
            return "error#" + e.getMessage();
        }
    }

    public static String getListItemsForAddOffPage(String token){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("productNames");
        for (String productName : ServerMain.sellerController.getSellerProductNames(token)) {
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
            ServerMain.sellerController.addOff(productIdList, offInfo, splitRequest[0]);
            return "success";
        } catch (Exception e) {
            return "error#" + e.getMessage();
        }
    }

    public static String buildAddProductResponse(String[] splitRequest){
        ArrayList<String> productInfo = new ArrayList<>();
        for(int i=2; i<splitRequest.length; i++){
            productInfo.add(splitRequest[i]);
        }
        try {
            ServerMain.sellerController.addProduct(productInfo,splitRequest[0]);
            return "success";
        } catch (CreateProductException.InvalidProductInputInfo e) {
            return "error#" +e.getMessage();
        } catch (CreateProductException.GetCategoryFromUser e) {
            return GeneralController.yagsonMapper.toJson(e, CreateProductException.GetCategoryFromUser.class);
        }
    }

    public static String buildAddSpecialFeaturesResponse(String[] splitRequest){
        ArrayList<String> specialFeatures = new ArrayList<>();
        for(int i=3; i<splitRequest.length; i++){
            specialFeatures.add(splitRequest[i]);
        }
        ServerMain.sellerController.setSpecialFeatures(splitRequest[1],specialFeatures);
        return  "success";
    }
}
