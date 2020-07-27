package Main.server.serverRequestProcessor;

import Main.server.ServerMain;
import Main.server.consoleViewOld.CategoryManagerMenu;
import Main.server.controller.GeneralController;
import Main.server.model.Auction;
import Main.server.model.Category;
import Main.server.model.Product;
import Main.server.model.accounts.SellerAccount;
import Main.server.model.exceptions.CreateProductException;
import Main.server.model.requests.EditOffRequest;
import Main.server.model.requests.EditProductRequest;

import java.util.ArrayList;
import java.util.Arrays;

public class SellerRequestProcessor {

    public static Product productToBeAddedToCategory;

    public static String process(String[] splitRequest) {
        if (splitRequest[2].equals("createAuction")) {
            return createAuction(splitRequest);
        }
        return null;
    }

    private static String createAuction(String[] splitRequest) {
        if (!ServerMain.server.validateToken(splitRequest[0], SellerAccount.class)) {
            return "loginNeeded";
        }
        SellerAccount sellerAccount = (SellerAccount) ServerMain.server.getTokenInfo(splitRequest[0]).getUser();
        try {
            Auction auction = new Auction(Product.getProductWithId(splitRequest[5]), splitRequest[3], splitRequest[4], sellerAccount);
            Auction.addAuction(auction);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "failure";
        }
    }

    public static String buildCommentResponse(String[] splitRequest) {
        String title = splitRequest[2];
        String content = splitRequest[3];
        try {
            //TODO ALERT: watch out for the null arguments :D
            ServerMain.generalController.addComment(title, content, splitRequest[0], null);
            return "success";
        } catch (Exception e) {
            return "error#" + e.getMessage();
        }
    }

    public static String getSellerProductsList(String token) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("productNames");
        for (String productName : ServerMain.sellerController.getSellerProductNames(token)) {
            stringBuilder.append("#" + productName);
        }
        return stringBuilder.toString();
    }

    public static String buildAddOffResponse(String[] splitRequest) {
        ArrayList<String> split = new ArrayList<>();
        ArrayList<String> productIdList = new ArrayList<>();
        ArrayList<String> offInfo = new ArrayList<>();
        split.addAll(Arrays.asList(splitRequest));
        for (int i = 2; i < split.indexOf("offInfo"); i++) {
            productIdList.add(split.get(i));
        }
        for (int i = (split.indexOf("offInfo") + 1); i < split.size(); i++) {
            offInfo.add(split.get(i));
        }
        try {
            ServerMain.sellerController.addOff(productIdList, offInfo, splitRequest[0]);
            return "success";
        } catch (Exception e) {
            return "error#" + e.getMessage();
        }
    }

    public static String buildAddProductResponse(String[] splitRequest) {
        ArrayList<String> productInfo = new ArrayList<>();
        for (int i = 2; i < splitRequest.length; i++) {
            productInfo.add(splitRequest[i]);
        }
        try {
            ServerMain.sellerController.addProduct(productInfo, splitRequest[0]);
            return "success";
        } catch (CreateProductException.InvalidProductInputInfo e) {
            return "error#" + e.getMessage();
        } catch (CreateProductException.GetCategoryFromUser e) {
            if (productInfo.get(0).endsWith("___FILEPRODUCT"))
                return "success";
            else{
                productToBeAddedToCategory = e.getProduct();
                return GeneralController.yagsonMapper.toJson(e, CreateProductException.GetCategoryFromUser.class);
            }

        }
    }

    public static String buildAddSpecialFeaturesResponse(String[] splitRequest) {
        ArrayList<String> specialFeatures = new ArrayList<>();
        for (int i = 3; i < splitRequest.length; i++) {
            specialFeatures.add(splitRequest[i]);
        }
        ServerMain.sellerController.setSpecialFeatures(specialFeatures);
        return "success";
    }

    public static String getProductForProductEditPage(String[] splitRequest) {
        try {
            return GeneralController.yagsonMapper.toJson(Product.getProductWithId(splitRequest[2]), Product.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String buildEditProductResponse(String[] splitRequest) {
        ArrayList<String> allData = new ArrayList<>();
        allData.addAll(Arrays.asList(splitRequest));
        try {
            EditProductRequest editProductRequest = ServerMain.sellerController.getProductToEdit(splitRequest[2], splitRequest[0]);
            if (allData.contains("name")) {
                editProductRequest.addEditedFieldTitle("name");
                editProductRequest.setName(allData.get(allData.indexOf("name") + 1));
            }
            if (allData.contains("brand")) {
                editProductRequest.addEditedFieldTitle("brand");
                editProductRequest.setBrand(allData.get(allData.indexOf("brand") + 1));
            }
            if (allData.contains("availability")) {
                editProductRequest.addEditedFieldTitle("availability");
                editProductRequest.setAvailability(allData.get(allData.indexOf("availability") + 1));
            }
            if (allData.contains("description")) {
                editProductRequest.addEditedFieldTitle("description");
                editProductRequest.setDescription(allData.get(allData.indexOf("description") + 1));
            }
            if (allData.contains("price")) {
                editProductRequest.addEditedFieldTitle("price");
                editProductRequest.setPrice(allData.get(allData.indexOf("price") + 1));
            }
            if (allData.contains("off")) {
                editProductRequest.addEditedFieldTitle("off");
                editProductRequest.setOffID(allData.get(allData.indexOf("off") + 1));
            }
            ServerMain.sellerController.submitProductEdits(editProductRequest, splitRequest[0]);
            return "success";
        } catch (Exception e) {
            return "error#" + e.getMessage();
        }
    }

    public static String buildEditPersonalInformationResponse(String[] splitRequest) {
        return ServerMain.generalController.editPersonalInfo(splitRequest[2], splitRequest[3], splitRequest[0]);
    }

    public static String buildEditOffResponse(String[] splitRequest) {
        ArrayList<String> allData = new ArrayList<>();
        allData.addAll(Arrays.asList(splitRequest));
        try {
            EditOffRequest editOffRequest = ServerMain.sellerController.getOffToEdit(splitRequest[2], splitRequest[0]);
            if (allData.contains("start date")) {
                editOffRequest.addEditedFieldTitle("start date");
                editOffRequest.setStartDate(allData.get(allData.indexOf("start date") + 1));
            }
            if (allData.contains("end date")) {
                editOffRequest.addEditedFieldTitle("end date");
                editOffRequest.setEndDate(allData.get(allData.indexOf("end date") + 1));
            }
            if (allData.contains("off amount")) {
                editOffRequest.addEditedFieldTitle("off amount");
                editOffRequest.setOffAmount(allData.get(allData.indexOf("off amount") + 1));
            }
            if (allData.contains("add product")) {
                editOffRequest.addEditedFieldTitle("add product");
                editOffRequest.addProductIDToBeAdded(allData.get(allData.indexOf("add product") + 1));
            }
            if (allData.contains("remove product")) {
                editOffRequest.addEditedFieldTitle("remove product");
                editOffRequest.addProductIDToBeRemoved(allData.get(allData.indexOf("remove product") + 1));
            }
            ServerMain.sellerController.submitOffEdits(editOffRequest, splitRequest[0]);
            return "success";
        } catch (Exception e) {
            return "error#" + e.getMessage();
        }
    }

    public static String getSellLogList(String[] splitRequest) {
        StringBuilder response = new StringBuilder();
        if (ServerMain.sellerController.getSellLogIds(splitRequest[0]) == null) {
            return "empty";
        } else {
            for (String sellLogId : ServerMain.sellerController.getSellLogIds(splitRequest[0])) {
                response.append(sellLogId + "#");
            }
            return response.toString();
        }
    }

    public static String getLogDetails(String[] splitRequest) {
        return ServerMain.sellerController.viewLogDetails(splitRequest[2], splitRequest[0]);
    }

    public static String getSellerOffList(String[] splitRequest) {
        StringBuilder response = new StringBuilder();
        response.append("offIds");
        for (String offId : ServerMain.sellerController.getSellerOffIds(splitRequest[0])) {
            response.append("#" + offId);
        }
        return response.toString();
    }

    public static String getOffDetails(String[] splitRequest) {
        return ServerMain.sellerController.getOffDetails(splitRequest[0], splitRequest[2]);
    }

    public static String getSellerPersonalInformation(String[] splitRequest) {
        return ServerMain.generalController.viewPersonalInfo(splitRequest[0]) + "#" +
                ServerMain.generalController.getProfileImagePath(splitRequest[0]);
    }

    public static String getCompanyInformation(String[] splitRequest) {
        return ServerMain.sellerController.viewCompanyInformation(splitRequest[0]);
    }

    public static String getSellerBalance(String[] splitRequest) {
        return ServerMain.sellerController.viewSellerBalance(splitRequest[0]);
    }

    public static String getSellerCategories() {
        return ServerMain.generalController.showAllCategories();
    }

    public static String getAllProductDataForSellerProductPage(String[] splitRequest) {
        return ServerMain.sellerController.makeDigestLabel(splitRequest[2]) + "#" +
                ServerMain.sellerController.getProductImagePath(splitRequest[2]) + "#" +
                ServerMain.sellerController.getProductAverageScore(splitRequest[2]) + "#" +
                ServerMain.sellerController.viewProductBuyers(splitRequest[2]);
    }

    public static String buildRemoveProductResponse(String[] splitRequest) {
        try {
            ServerMain.sellerController.removeProductWithID(splitRequest[2], splitRequest[0]);
            return "success";
        } catch (Exception e) {
            return "error#" + e.getMessage();
        }
    }

    public static String getListItemsForAddOffPage(String s) {
        return null;
    }

    public static String getCategorySpecialFeatures(String[] splitRequest) {
        Category category;
        try {
            category = Category.getCategoryWithName(splitRequest[2]);
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
        String specials = "";
        for (String specialFeature : category.getSpecialFeatures()) {
            specials = specials.concat(specialFeature);
            specials = specials.concat("#");
        }
        if (specials.equals(""))
            return specials;
        specials = specials.substring(0, specials.length() - 1);
        return specials;
    }
}
