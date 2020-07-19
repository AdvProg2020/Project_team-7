package Main.client.requestBuilder;

import Main.client.ClientMain;
import Main.client.graphicView.GraphicMain;

import java.util.ArrayList;

public class ManagerRequestBuilder {

    public static String buildManagerPersonalInformationRequest() {
        return ClientMain.client.sendRequest(GraphicMain.token + "#managerPersonalInfo");
    }

    public static String buildEditManagerPersonalInformationRequest(String newFirstName, String newLastName, String newEmail, String newPhoneNumber, String newPassWord) {
        return ClientMain.client.sendRequest(GraphicMain.token + "#editManagerPersonalInfo#" + newFirstName + "#" + newLastName + "#" + newEmail + "#" + newPhoneNumber + "#" + newPassWord);
    }

    public static String buildMarkDeliveredRequest(String logID) {
        String markDeliveredRequest = GraphicMain.token + "#manager#markDelivered#" + logID;
        return ClientMain.client.sendRequest(markDeliveredRequest);
    }

    public static String buildInitializeManageRequestsRequest() {
        return ClientMain.client.sendRequest(GraphicMain.token + "#initializeManageRequests");
    }

    public static String buildShowRequestWithIdRequest(String id) {
        return ClientMain.client.sendRequest(GraphicMain.token + "#showRequestWithId#" + id);
    }

    public static String buildAcceptRequestWithIdRequest(String id) {
        return ClientMain.client.sendRequest(GraphicMain.token + "#acceptRequestWithId#" + id);
    }

    public static String buildDeclineRequestWithIdRequest(String id) {
        return ClientMain.client.sendRequest(GraphicMain.token + "#declineRequestWithId#" + id);
    }

    public static String buildInitializeManageProductsRequest() {
        return ClientMain.client.sendRequest(GraphicMain.token + "#initializeManageProducts");
    }

    public static String buildShowProductDigestWithIdRequest(String id) {
        return ClientMain.client.sendRequest(GraphicMain.token + "#showProductDigestWithId#" + id);
    }

    public static String buildDeleteProductWithIdRequest(String id) {
        return ClientMain.client.sendRequest(GraphicMain.token + "#deleteProductWithId#" + id);
    }

    public static String buildInitializeManageUsersRequest() {
        return ClientMain.client.sendRequest(GraphicMain.token + "#initializeManageUsers");
    }

    public static String buildAccountViewMeWithUserNameRequest(String userName) {
        return ClientMain.client.sendRequest(GraphicMain.token + "#userViewMeWithUserName#" + userName);
    }

    public static String buildGetMyUserNameRequest() {
        return ClientMain.client.sendRequest(GraphicMain.token + "#getMyUserName");
    }

    public static String buildDeleteUserWithUserNameRequest(String userName) {
        return ClientMain.client.sendRequest(GraphicMain.token + "#deleteUserWithUserName#" + userName);
    }

    public static String buildinitializeManageCategoriesRequest() {
        return ClientMain.client.sendRequest(GraphicMain.token + "#initializeManageCategories");
    }

    public static String buildShowCategoryInformationRequest(String categoryName) {
        return ClientMain.client.sendRequest(GraphicMain.token + "#showCategoryInformation#" + categoryName);
    }

    public static String buildRemoveCategoryWithNameRequest(String categoryName) {
        return ClientMain.client.sendRequest(GraphicMain.token + "#removeCategoryWithName#" + categoryName);
    }

    public static String buildEditCategoryRequest(String categoryName, String newContent, String editOption) {
        return ClientMain.client.sendRequest(GraphicMain.token + "#editCategory#" + categoryName + "#" + newContent + "#" + editOption);
    }

    public static String buildCreateCategoryRequest(String name, ArrayList<String> specials, String path) {
        String specialsString = "";
        for (String special : specials) {
            specialsString = specialsString.concat(special);
            specialsString = specialsString.concat("&");
        }
        specialsString = specialsString.substring(0, specialsString.length() - 1);
        return ClientMain.client.sendRequest(GraphicMain.token + "#createCategory#" + name + "#" + specialsString + "#" + path);
    }

    public static String buildInitializeManageDiscountsRequest() {
        return ClientMain.client.sendRequest(GraphicMain.token + "#initializeManageDiscounts");
    }

    public static String buildViewDiscountAsManager(String code) {
        return ClientMain.client.sendRequest(GraphicMain.token + "#viewDiscountAsManager#" + code);
    }

    public static String buildRemoveDiscountCodeRequest(String code) {
        return ClientMain.client.sendRequest(GraphicMain.token + "#removeDiscountCode#" + code);
    }

    public static String buildGetDiscountDataRequest(String code) {
        return ClientMain.client.sendRequest(GraphicMain.token + "#getDiscountData#" + code);
    }

    public static String buildEditDiscountRequest(String code, String newContent, String editOption) {
        return ClientMain.client.sendRequest(GraphicMain.token + "#editDiscount#" + code + "#" + newContent + "#" + editOption);
    }

    public static String buildCreateDiscountRequest(ArrayList<String> buyersList, ArrayList<String> discountInfo) {
        String buyers = "";
        for (String s : buyersList) {
            buyers = buyers.concat(s);
            buyers = buyers.concat("&");
        }
        buyers = buyers.substring(0, buyers.length() - 1);
        String info = "";
        for (String s : discountInfo) {
            info = info.concat(s);
            info = info.concat("&");
        }
        info = info.substring(0, info.length() - 1);
        return ClientMain.client.sendRequest(GraphicMain.token + "#createDiscount#" + buyers + "#" + info);
    }
}
