package Main.server.serverRequestProcessor;

import Main.client.graphicView.GraphicMain;
import Main.server.ServerMain;
import Main.server.model.CartProduct;
import Main.server.model.Product;
import Main.server.model.accounts.Account;
import Main.server.model.accounts.BuyerAccount;
import Main.server.model.accounts.ManagerAccount;
import Main.server.model.accounts.SellerAccount;
import Main.server.model.exceptions.AccountsException;
import Main.server.model.requests.CreateSellerAccountRequest;
import Main.server.model.requests.Request;

public class GeneralRequestProcessor {
    public static String loginRequestProcessor(String[] splitRequest) {
        /**if (splitRequest.length != 4 || !splitRequest[2].contains(":") || !splitRequest[3].contains(":")) {
         return "invalidRequest";
         }*/
        String username = splitRequest[2].split(":")[1];
        String password = splitRequest[3].split(":")[1];
        if (!Account.isThereUserWithUserName(username)) {
            return "userNameNotFound";
        }

        Account account = null;
        try {
            account = Account.getUserWithUserName(username);
            if (!account.isPassWordCorrect(password)) {
                return "passwordWrong";
            }
        } catch (Exception e) {
        }

        String token = ServerMain.server.addToken(account);
        return "success#" + token;
    }

    public static String signUpRequestProcessor(String[] splitRequest) {
        /**if (splitRequest.length != 4 || !splitRequest[2].contains(":") || !splitRequest[3].contains(":")) {
         return "invalidRequest";
         }*/

        String username = splitRequest[2].split(":")[1];
        try {
            AccountsException.validateUsernameUniqueness(username);
        } catch (AccountsException e) {
            return "duplicateUserName";
        }
        return "success";
    }

    public static String signUpBuyerRequestProcessor(String[] splitRequest) {
        /**
         if (splitRequest.length != 9) {
         return "invalidRequest";
         }
         for (String s : splitRequest) {
         if(!s.contains(":")||s.length()>60){
         return "invalidRequest";
         }
         }
         */
        String username = splitRequest[2].split(":")[1];
        String firstName = splitRequest[3].split(":")[1];
        String lastName = splitRequest[4].split(":")[1];
        String email = splitRequest[5].split(":")[1];
        String phoneNumber = splitRequest[6].split(":")[1];
        String password = splitRequest[7].split(":")[1];
        String imagePath = splitRequest[8].split(":")[1];

        BuyerAccount buyerAccount = new BuyerAccount(username, firstName,
                lastName, email, phoneNumber, password, 1000000, imagePath);
        BuyerAccount.addBuyer(buyerAccount);

        return "success";
    }

    public static String signUpSellerRequestProcessor(String[] splitRequest) {
        String username = splitRequest[2].split(":")[1];
        String firstName = splitRequest[3].split(":")[1];
        String lastName = splitRequest[4].split(":")[1];
        String email = splitRequest[5].split(":")[1];
        String phoneNumber = splitRequest[6].split(":")[1];
        String password = splitRequest[7].split(":")[1];
        String companyName = splitRequest[8].split(":")[1];
        String companyInfo = splitRequest[9].split(":")[1];
        String imagePath = splitRequest[10].split(":")[1];

        SellerAccount sellerAccount = new SellerAccount(username, firstName,
                lastName, email, phoneNumber, password, companyName, companyInfo, 1000000, imagePath);
        CreateSellerAccountRequest createSellerAccountRequest = new CreateSellerAccountRequest(sellerAccount, "create seller account");
        Request.addRequest(createSellerAccountRequest);
        Account.getReservedUserNames().add(username);

        return "success";
    }

    public static String signUpManagerRequestProcessor(String[] splitRequest) {
        /**
         if (splitRequest.length != 9) {
         return "invalidRequest";
         }
         for (String s : splitRequest) {
         if(!s.contains(":")||s.length()>60){
         return "invalidRequest";
         }
         }
         */
        String username = splitRequest[2].split(":")[1];
        String firstName = splitRequest[3].split(":")[1];
        String lastName = splitRequest[4].split(":")[1];
        String email = splitRequest[5].split(":")[1];
        String phoneNumber = splitRequest[6].split(":")[1];
        String password = splitRequest[7].split(":")[1];
        String imagePath = splitRequest[8].split(":")[1];

        try {
            AccountsException.validateUsernameUniqueness(username);
        } catch (AccountsException e) {
            return "duplicateUserName";
        }

        ManagerAccount managerAccount = new ManagerAccount(username, firstName, lastName,
                email, phoneNumber, password, imagePath);

        if (ManagerAccount.isThereAChiefManager()) {
            if(!ServerMain.server.validateToken(splitRequest[0],ManagerAccount.class)){
                return "loginNeeded";
            }
            ManagerAccount.addManager(managerAccount);
            return "success#ordinary";
        } else {
            ManagerAccount.addManager(managerAccount);
            return "success#chief";
        }
    }

    public static String getAllDataForProductPage(String[] splitRequest){
        return ServerMain.sellerController.getProductImagePath(splitRequest[2]) + "#" +
                ServerMain.sellerController.getProductAverageScore(splitRequest[2]) + "#" +
                ServerMain.generalController.makeGeneralFeatures(splitRequest[2]) + "#" +
                ServerMain.generalController.getProductCategoryInfo(splitRequest[2]) + "#" +
                ServerMain.generalController.getProductSpecialFeaturesInfo(splitRequest[2])  + "#" +
                ServerMain.generalController.showCommentsOfProduct(splitRequest[2]);
    }

    public static String selectSeller(String[] splitRequest){
        try {
            CartProduct cartProduct = ServerMain.generalController.selectSellerWithUsername(splitRequest[2],splitRequest[0],splitRequest[3]);
            return "success#" + ServerMain.generalController.addProductToCart(cartProduct, splitRequest[0]);
        }catch (Exception e){
            return "error#" + e.getMessage();
        }
    }

    public static String buildRateProductPermissionResponse(String[] splitRequest){
        BuyerAccount buyerAccount = (BuyerAccount) Server.getServer().getTokenInfo(splitRequest[0]).getUser();
        try {
            if(!buyerAccount.hasBuyerBoughtProduct(Product.getProductWithId(splitRequest[2]))){
                return "you should have bought this product";
            }else{
                return "success";
            }
        } catch (Exception e) {
            return "error";
        }
    }

    public static String buildRateProductResponse(String[] splitRequest){
        try {
            GraphicMain.buyerController.rateProductWithId(splitRequest[2],splitRequest[3],splitRequest[0]);
            return "success";
        } catch (Exception e) {
            return "error#" + e.getMessage();
        }
    }

    public static String buildCompareProductResponse(String[] splitRequest){
        try {
            return "success#" +
                    ServerMain.generalController.compareProductWithProductWithId(splitRequest[2] , splitRequest[3]);
        } catch (Exception e) {
            return "error#" + e.getMessage();
        }
    }
}
