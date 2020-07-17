package Main.server.serverRequestProcessor;

import Main.client.graphicView.GraphicMain;
import Main.server.ServerMain;
import Main.server.controller.GeneralController;
import Main.server.model.Category;
import Main.server.model.Product;
import Main.server.model.exceptions.CreateProductException;
import Main.server.model.requests.EditOffRequest;
import Main.server.model.requests.EditProductRequest;

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

    public static String getProductForProductEditPage(String[] splitRequest){
        try {
            return GeneralController.yagsonMapper.toJson(Product.getProductWithId(splitRequest[2]), Product.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String buildEditProductResponse(String[] splitRequest){
        ArrayList<String> allData = new ArrayList<>();
        for(int i=0; i<splitRequest.length; i++){
            allData.add(splitRequest[i]);
        }
        try {
            EditProductRequest editProductRequest = ServerMain.sellerController.getProductToEdit(splitRequest[2], splitRequest[0]);
            if(allData.contains("name")){
                editProductRequest.addEditedFieldTitle("name");
                editProductRequest.setName(allData.get(allData.indexOf("name")+1));
            }
            if(allData.contains("brand")){
                editProductRequest.addEditedFieldTitle("brand");
                editProductRequest.setBrand(allData.get(allData.indexOf("brand")+1));
            }
            if(allData.contains("availability")){
                editProductRequest.addEditedFieldTitle("availability");
                editProductRequest.setAvailability(allData.get(allData.indexOf("availability")+1));
            }
            if(allData.contains("description")){
                editProductRequest.addEditedFieldTitle("description");
                editProductRequest.setDescription(allData.get(allData.indexOf("description")+1));
            }
            if(allData.contains("price")){
                editProductRequest.addEditedFieldTitle("price");
                editProductRequest.setPrice(allData.get(allData.indexOf("price")+1));
            }
            if(allData.contains("off")){
                editProductRequest.addEditedFieldTitle("off");
                editProductRequest.setOffID(allData.get(allData.indexOf("off")+1));
            }
            ServerMain.sellerController.submitProductEdits(editProductRequest, splitRequest[0]);
            return "success";
        } catch (Exception e) {
            return "error#" + e.getMessage();
        }
    }

    public static String buildEditPersonalInformationResponse(String[] splitRequest){
        return ServerMain.generalController.editPersonalInfo(splitRequest[2],splitRequest[3],splitRequest[0]);
    }

    public static String buildEditOffResponse(String[] splitRequest){
        ArrayList<String> allData = new ArrayList<>();
        for(int i=0; i<splitRequest.length; i++){
            allData.add(splitRequest[i]);
        }
        try {
            EditOffRequest editOffRequest = ServerMain.sellerController.getOffToEdit(splitRequest[2],splitRequest[0]);
            if(allData.contains("start date")){
                editOffRequest.addEditedFieldTitle("start date");
                editOffRequest.setStartDate(allData.get(allData.indexOf("start date")+1));
            }
            if(allData.contains("end date")){
                editOffRequest.addEditedFieldTitle("end date");
                editOffRequest.setEndDate(allData.get(allData.indexOf("end date")+1));
            }
            if(allData.contains("off amount")){
                editOffRequest.addEditedFieldTitle("off amount");
                editOffRequest.setOffAmount(allData.get(allData.indexOf("off amount")+1));
            }
            if(allData.contains("add product")){
                editOffRequest.addEditedFieldTitle("add product");
                editOffRequest.addProductIDToBeAdded(allData.get(allData.indexOf("add product")+1));
            }
            if(allData.contains("remove product")){
                editOffRequest.addEditedFieldTitle("remove product");
                editOffRequest.addProductIDToBeRemoved(allData.get(allData.indexOf("remove product")+1));
            }
            ServerMain.sellerController.submitOffEdits(editOffRequest,splitRequest[0]);
            return "success";
        } catch (Exception e) {
            return "error#" + e.getMessage();
        }
    }
}
