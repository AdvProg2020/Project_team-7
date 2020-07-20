package Main.server.serverRequestProcessor;

import Main.server.ServerMain;
import Main.server.model.Category;
import Main.server.model.Product;
import Main.server.model.accounts.Account;
import Main.server.model.accounts.ManagerAccount;
import Main.server.model.discountAndOffTypeService.DiscountCode;
import Main.server.model.logs.BuyLog;
import Main.server.model.logs.Log;
import Main.server.model.requests.EditCategory;
import Main.server.model.requests.EditDiscountCode;
import Main.server.model.requests.Request;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ManagerRequestProcessor {
    public static String managerPersonalInfoRequestProcessor(String[] data) {
        ManagerAccount managerAccount = ((ManagerAccount) Server.getServer().getTokenInfo(data[0]).getUser());
        return managerAccount.getFirstName() + "#"
                + managerAccount.getLastName() + "#"
                + managerAccount.getUserName() + "#"
                + managerAccount.getEmail() + "#"
                + managerAccount.getPhoneNumber() + "#"
                + managerAccount.getPassWord() + "#"
                + managerAccount.getProfileImagePath();
    }

    public static String editManagerPersonalInformationRequestProcessor(String[] data) {
        ManagerAccount managerAccount = ((ManagerAccount) Server.getServer().getTokenInfo(data[0]).getUser());
        managerAccount.setFirstName(data[2]);
        managerAccount.setLastName(data[3]);
        managerAccount.setEmail(data[4]);
        managerAccount.setPhoneNumber(data[5]);
        managerAccount.setPassWord(data[6]);
        return "success";
    }

    public static String process(String[] splitRequest) {
        if (splitRequest[2].equals("markDelivered")) {
            return markDeliveredResponse(splitRequest);
        }
        return null;
    }

    private static String markDeliveredResponse(String[] splitRequest) {
        if (!ServerMain.server.validateToken(splitRequest[0], ManagerAccount.class)) {
            return "loginNeeded";
        } else {
            try {
                BuyLog buyLog = (BuyLog) Log.getLogWithID(splitRequest[3]);
                buyLog.markDelivered();
                return "success";
            } catch (Exception e) {
                e.printStackTrace();
                return "failure";
            }
        }
    }

    public static String initializeManageRequestsRequestProcessor() {
        ArrayList<String> requests = Request.summaryInfoOfRequests();
        String response = "";
        if (requests.isEmpty())
            return response;
        for (String request : requests) {
            response = response.concat(request);
            response = response.concat("#");
        }
        response = response.substring(0, response.length() - 1);
        return response;
    }

    public static String showRequestWithIdRequestProcessor(String[] splitRequest) {
        try {
            return Request.getRequestWithId(splitRequest[2]).showRequest();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Error in getting request information";
        }
    }

    public static String acceptRequestWithIdRequestProcessor(String[] splitRequest) {
        try {
            Request.getRequestWithId(splitRequest[2]).accept();
            return "Request accepted!";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Error in getting request information";
        }
    }

    public static String declineRequestWithIdRequestProcessor(String[] splitRequest) {
        try {
            Request.getRequestWithId(splitRequest[2]).decline();
            return "Request declined!";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Error in getting request information";
        }
    }

    public static String initializeManageProductsRequestProcessor() {
        ArrayList<String> products = Product.summaryProductInfo();
        String response = "";
        if (products.isEmpty())
            return response;
        for (String product : products) {
            response = response.concat(product);
            response = response.concat("#");
        }
        response = response.substring(0, response.length() - 1);
        return response;
    }

    public static String showProductDigestWithIdRequestProcessor(String[] splitRequest) {
        try {
            return Product.getProductWithId(splitRequest[2]).showProductDigest();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Error in getting product information";
        }
    }

    public static String deleteProductWithIdRequestProcessor(String[] splitRequest) {
        try {
            ServerMain.managerController.removeProductWithId(splitRequest[2]);
            return "product deleted successfully.";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Error deleting product";
        }
    }

    public static String initializeManageUsersRequestProcessor() {
        ArrayList<String> users = ServerMain.managerController.usersListForGraphic();
        String response = "";
        if (users.isEmpty())
            return response;
        for (String user : users) {
            response = response.concat(user);
            concatOnlineStatus(response, user);
            response = response.concat("#");
        }
        response = response.substring(0, response.length() - 1);
        return response;
    }

    private static void concatOnlineStatus(String response, String user) {
        String userName = user.substring(user.indexOf("@") + 1, user.indexOf("\n"));
        HashMap<String, TokenInfo> tokens = ServerMain.server.getTokens();
        for (TokenInfo value : tokens.values()) {
            if (value.getUser().getUserName().equals(userName)) {
                response = response.concat(" <<online>>");
                return;
            }
        }
        response = response.concat(" <<offline>>");
    }

    public static String userViewMeWithUserNameRequestProcessor(String[] splitRequest) {
        try {
            return Account.getUserWithUserName(splitRequest[2]).viewMe();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Error in getting user data";
        }
    }

    public static String getMyUserNameRequestProcessor(String[] splitRequest) {
        return Server.getServer().getTokenInfo(splitRequest[0]).getUser().getUserName();
    }

    public static String deleteUserWithUserNameRequestProcessor(String[] splitRequest) {
        try {
            ServerMain.managerController.deleteUserWithUserName(splitRequest[2]);
            return "user with username " + splitRequest[2] + " deleted successfully.";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Error in deleting user";
        }
    }

    public static String initializeManageCategoriesRequestProcessor() {
        ArrayList<String> categories = Category.categoriesList();
        String response = "";
        if (categories.isEmpty())
            return response;
        for (String category : categories) {
            response = response.concat(category);
            response = response.concat("#");
        }
        response = response.substring(0, response.length() - 1);
        return response;
    }

    public static String showCategoryInformationRequestProcessor(String[] splitRequest) {
        try {
            return Category.getCategoryWithName(splitRequest[2]).showSpecialFeatures();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Error getting category information";
        }
    }

    public static String removeCategoryWithNameRequestProcessor(String[] splitRequest) {
        try {
            ServerMain.managerController.removeCategoryWithName(splitRequest[2]);
            return "success";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "error removing the category";
        }
    }

    public static String editCategoryRequestProcessor(String[] splitRequest) throws Exception {
        EditCategory editCategory = ServerMain.managerController.getCategoryToEdit(splitRequest[2]);
        String editOption = splitRequest[4];
        String newContent = splitRequest[3];
        if (editOption.equals("Change Category Name")) {
            editCategory.setName(newContent);
        } else if (editOption.equals("Add a Product to Category")) {
            editCategory.addProductToBeAdded(newContent);
        } else if (editOption.equals("Remove a Product from Category")) {
            editCategory.addProductToBeRemoved(newContent);
        } else if (editOption.equals("Add a Special Feature")) {
            editCategory.addSpecialFeatureToBeAdded(newContent);
        } else if (editOption.equals("Remove a Special Feature")) {
            editCategory.addSpecialFeatureToBeRemoved(newContent);
        }
        ServerMain.managerController.submitCategoryEdits(editCategory);
        return "Category edited successfully.";
    }

    public static String createCategoryRequestProcessor(String[] splitRequest) {
        ArrayList<String> specials = new ArrayList<>(Arrays.asList(splitRequest[3].split("&")));
        try {
            return ServerMain.managerController.createCategory(splitRequest[2], specials, splitRequest[4]);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return e.getMessage();
        }
    }

    public static String initializeManageDiscountsRequestProcessor() {
        ArrayList<String> discounts = DiscountCode.getDiscountsList();
        String response = "";
        if (discounts.isEmpty())
            return response;
        for (String discount : discounts) {
            response = response.concat(discount);
            response = response.concat("#");
        }
        response = response.substring(0, response.length() - 1);
        return response;
    }

    public static String viewDiscountAsManagerRequestProcessor(String[] splitRequest) {
        try {
            return DiscountCode.getDiscountCodeWithCode(splitRequest[2]).viewMeAsManager();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return e.getMessage();
        }
    }

    public static String removeDiscountCodeRequestProcessor(String[] splitRequest) {
        try {
            DiscountCode.getDiscountCodeWithCode(splitRequest[2]).removeDiscountCode();
            return "Discount was removes successfully";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return e.getMessage();
        }
    }

    public static String getDiscountDataRequestProcessor(String[] splitRequest) throws Exception {
        DiscountCode discountCode = DiscountCode.getDiscountCodeWithCode(splitRequest[2]);
        return discountCode.getStartDateInStringFormat() + "#" + discountCode.getEndDateInStringFormat() + "#" + discountCode.getPercent() + "#" + discountCode.getMaxAmount() + "#" + discountCode.getMaxNumberOfUse();
    }

    public static String editDiscountRequestProcessor(String[] splitRequest) throws Exception {
        EditDiscountCode editDiscountCode = ServerMain.managerController.getDiscountCodeToEdit(splitRequest[2]);
        String editOption = splitRequest[4];
        String newContent = splitRequest[3];
        if (editOption.equals("Start Date")) {
            editDiscountCode.setStartDate(newContent);
        } else if (editOption.equals("End Date")) {
            editDiscountCode.setEndDate(newContent);
        } else if (editOption.equals("Percent")) {
            editDiscountCode.setPercent(newContent);
        } else if (editOption.equals("Max Amount")) {
            editDiscountCode.setMaxAmount(newContent);
        } else if (editOption.equals("Max Number Of Use")) {
            editDiscountCode.setMaxNumberOfUse(newContent);
        } else if (editOption.equals("Add Buyer UserName")) {
            editDiscountCode.addUserToBeAdded(newContent);
        } else if (editOption.equals("Remove Buyer UserName")) {
            editDiscountCode.addUserToBeRemoved(newContent);
        }
        ServerMain.managerController.submitDiscountCodeEdits(editDiscountCode);
        return "Discount was edited successfully";
    }

    public static String createDiscountRequestProcessor(String[] splitRequest) {
        ArrayList<String> buyers = new ArrayList<>(Arrays.asList(splitRequest[2].split("&")));
        ArrayList<String> info = new ArrayList<>(Arrays.asList(splitRequest[3].split("&")));
        try {
            return ServerMain.managerController.createDiscountCode(buyers, info);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return e.getMessage();
        }
    }
}
