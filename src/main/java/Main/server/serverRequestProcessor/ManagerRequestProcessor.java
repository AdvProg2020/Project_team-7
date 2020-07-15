package Main.server.serverRequestProcessor;

import Main.server.ServerMain;
import Main.server.controller.GeneralController;
import Main.server.model.Product;
import Main.server.model.accounts.Account;
import Main.server.model.accounts.ManagerAccount;
import Main.server.model.accounts.SellerAccount;
import Main.server.model.logs.BuyLog;
import Main.server.model.logs.Log;
import Main.server.model.requests.Request;

import java.util.ArrayList;

public class ManagerRequestProcessor {
    public static String managerPersonalInfoRequestProcessor(String[] data) {
        ManagerAccount managerAccount = ((ManagerAccount) Server.getServer().getTokenInfo(data[0]).getUser());
        String string = managerAccount.getFirstName() + "#"
                + managerAccount.getLastName() + "#"
                + managerAccount.getUserName() + "#"
                + managerAccount.getEmail() + "#"
                + managerAccount.getPhoneNumber() + "#"
                + managerAccount.getPassWord() + "#"
                + managerAccount.getProfileImagePath();
        return string;
    }

    public static String editManagerPersonalInformationRequestProcessor(String[] data){
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
        } else if (true) {
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
        for (String request : requests) {
            response = response.concat(request);
            response = response.concat("#");
        }
        response = response.substring(0, response.length()-1);
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
        for (String product : products) {
            response = response.concat(product);
            response = response.concat("#");
        }
        response = response.substring(0, response.length()-1);
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
        }catch (Exception e){
            System.out.println(e.getMessage());
            return "Error deleting product";
        }
    }
}
