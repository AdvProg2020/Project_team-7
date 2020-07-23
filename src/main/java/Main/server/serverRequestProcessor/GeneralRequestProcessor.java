package Main.server.serverRequestProcessor;

import Main.server.BankClient;
import Main.server.ServerMain;
import Main.server.model.Auction;
import Main.server.model.CartProduct;
import Main.server.model.Product;
import Main.server.model.ShopFinance;
import Main.server.model.accounts.*;
import Main.server.model.exceptions.AccountsException;
import Main.server.model.requests.CreateSellerAccountRequest;
import Main.server.model.requests.Request;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

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
            if (!ServerMain.server.validateToken(splitRequest[0], ManagerAccount.class)) {
                return "loginNeeded";
            }
            ManagerAccount.addManager(managerAccount);
            return "success#ordinary";
        } else {
            ManagerAccount.addManager(managerAccount);
            String token = ServerMain.server.addToken(managerAccount);
            return "success#chief#" + token;
        }
    }

    public static String getAllDataForProductPage(String[] splitRequest) {
        return ServerMain.sellerController.getProductImagePath(splitRequest[2]) + "#" +
                ServerMain.sellerController.getProductAverageScore(splitRequest[2]) + "#" +
                ServerMain.generalController.makeGeneralFeatures(splitRequest[2]) + "#" +
                ServerMain.generalController.getProductCategoryInfo(splitRequest[2]) + "#" +
                ServerMain.generalController.getProductSpecialFeaturesInfo(splitRequest[2]) + "#" +
                ServerMain.generalController.showCommentsOfProduct(splitRequest[2]);
    }

    public static String selectSeller(String[] splitRequest) {
        try {
            CartProduct cartProduct = ServerMain.generalController.selectSellerWithUsername(splitRequest[2], splitRequest[0], splitRequest[3]);
            return "success#" + ServerMain.generalController.addProductToCart(cartProduct, splitRequest[0]);
        } catch (Exception e) {
            return "error#" + e.getMessage();
        }
    }

    public static String buildRateProductPermissionResponse(String[] splitRequest) {
        BuyerAccount buyerAccount = (BuyerAccount) Server.getServer().getTokenInfo(splitRequest[0]).getUser();
        try {
            if (!buyerAccount.hasBuyerBoughtProduct(Product.getProductWithId(splitRequest[2]))) {
                return "you should have bought this product";
            } else {
                return "success";
            }
        } catch (Exception e) {
            return "error";
        }
    }

    public static String buildRateProductResponse(String[] splitRequest) {
        try {
            ServerMain.buyerController.rateProductWithId(splitRequest[2], splitRequest[3], splitRequest[0]);
            return "success";
        } catch (Exception e) {
            return "error#" + e.getMessage();
        }
    }

    public static String sendMessage(String[] splitRequest) {
        Auction auction = Auction.getAuctionById(splitRequest[3]);

        if (ServerMain.server.validateToken(splitRequest[0], BuyerAccount.class)) {
            BuyerAccount buyerAccount = (BuyerAccount) ServerMain.server.getTokenInfo(splitRequest[0]).getUser();
            try {
                if(!auction.isAuctionValid()){
                    throw new Exception();
                }
                auction.getAuctionUsage().addMessage("\"" + buyerAccount.getUserName() + "\"\n" + splitRequest[4]);
                return "success";
            } catch (Exception e) {
                return "auctionOver";
            }
        }


        if (ServerMain.server.validateToken(splitRequest[0], SellerAccount.class)) {
            SellerAccount sellerAccount = (SellerAccount) ServerMain.server.getTokenInfo(splitRequest[0]).getUser();
            SellerAccount auctionSeller = null;
            try {
                auctionSeller = auction.getAuctionUsage().getSellerAccount();
                if (sellerAccount.equals(auctionSeller)) {
                    auction.getAuctionUsage().addMessage("\"<<Seller>> " + sellerAccount.getUserName() + "\"\n" + splitRequest[4]);
                    return "success";
                }
            } catch (Exception e) {
                return "auctionOver";
            }
        }
        return "loginNeeded";
    }

    public static String buildCompareProductResponse(String[] splitRequest) {
        try {
            return "success#" +
                    ServerMain.generalController.compareProductWithProductWithId(splitRequest[2], splitRequest[3]);
        } catch (Exception e) {
            return "error#" + e.getMessage();
        }
    }

    public static String setUpFinanceRequestProcessor(String[] splitRequest) {
        if (!(ServerMain.server.validateToken(splitRequest[0], ManagerAccount.class) &&
                ServerMain.server.getTokenInfo(splitRequest[0]).getUser() == ManagerAccount.getAllManagers().get(0))) {
            return "loginNeeded";
        }
        String response = BankClient.getResponseFromBankServer("create_account shop shop " + splitRequest[2] + " " + splitRequest[3] + " " + splitRequest[3]);
        if (response.equals("username is not available")) {
            return "duplicateUsername";
        }
        ShopFinance.getInstance().setAccountID(response);
        ShopFinance.getInstance().setUsername(splitRequest[2]);
        ShopFinance.getInstance().setPassword(splitRequest[3]);
        ShopFinance.getInstance().setMinimumWalletBalance(Double.parseDouble(splitRequest[4]));
        ShopFinance.getInstance().setCommission(Double.parseDouble(splitRequest[5]));
        return "success";
    }

    public static String signUpSupporterRequestProcessor(String[] splitRequest) {
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

        SupporterAccount supporterAccount = new SupporterAccount(username, firstName, lastName,
                email, phoneNumber, password, imagePath);

        return "success#supporter";
    }

    public static String openChatWithRequestProcessor(String[] splitRequest) throws Exception {
        String myToken = splitRequest[0];
        String theirUsername = splitRequest[2];
        BuyerAccount buyerAccount;
        SupporterAccount supporterAccount;

        if (Server.getServer().validateToken(myToken, BuyerAccount.class)) {
            buyerAccount = ((BuyerAccount) Server.getServer().getTokenInfo(myToken).getUser());
            ((SupporterAccount) Account.getUserWithUserName(theirUsername)).addBuyerToChatList(buyerAccount.getUserName());
            return "meBuyer#" + buyerAccount.getUserName();
        } else {
            supporterAccount = ((SupporterAccount) Server.getServer().getTokenInfo(myToken).getUser());
            return "meSupporter#" + supporterAccount.getUserName();
        }
    }

    public static String initializeSupporterPanelRequestProcessor(String[] splitRequest) throws Exception {
        SupporterAccount supporterAccount = ((SupporterAccount) Server.getServer().getTokenInfo(splitRequest[0]).getUser());
        ArrayList<String> chats = ((SupporterAccount) Account.getUserWithUserName(supporterAccount.getUserName())).myChatsList();
        String response = "";
        if (chats.isEmpty())
            return response;
        for (String chat : chats) {
            response = response.concat(chat);
            response = ManagerRequestProcessor.concatOnlineStatus(response, chat);
            response = response.concat("#");
        }
        response = response.substring(0, response.length() - 1);
        return response;
    }

    public static String setChatMessagesRequestBuilder(String[] splitRequest) {
        try {
            String myToken = splitRequest[0];
            String theirUsername = splitRequest[2];
            BuyerAccount buyerAccount;
            SupporterAccount supporterAccount;
            if (Server.getServer().validateToken(myToken, BuyerAccount.class)) {
                buyerAccount = ((BuyerAccount) Server.getServer().getTokenInfo(myToken).getUser());
                supporterAccount = ((SupporterAccount) Account.getUserWithUserName(theirUsername));
            } else {
                supporterAccount = ((SupporterAccount) Server.getServer().getTokenInfo(myToken).getUser());
                buyerAccount = BuyerAccount.getBuyerWithUserName(theirUsername);
            }
            String massagesAsString = "";
            if (!supporterAccount.getMyChatsMessages().containsKey(buyerAccount.getUserName()))
                supporterAccount.getMyChatsMessages().put(buyerAccount.getUserName(), new ArrayList<>());
            for (String s : supporterAccount.getMyChatsMessages().get(buyerAccount.getUserName())) {
                massagesAsString = massagesAsString.concat(s);
                massagesAsString = massagesAsString.concat("#");
            }
            if (massagesAsString.equals(""))
                return massagesAsString;
            massagesAsString = massagesAsString.substring(0, massagesAsString.length() - 1);
            return massagesAsString;
        } catch (Exception e) {
            System.out.println("I AM THE ERROR");
            e.printStackTrace();
            System.err.println(e.getMessage());
            return "error" + e.getMessage();
        }
    }

    public static String saveChatMessagesRequestBuilder(String[] splitRequest) {
        try {
            String myToken = splitRequest[0];
            String theirUsername = splitRequest[2];
            BuyerAccount buyerAccount;
            SupporterAccount supporterAccount;
            if (Server.getServer().validateToken(myToken, BuyerAccount.class)) {
                buyerAccount = ((BuyerAccount) Server.getServer().getTokenInfo(myToken).getUser());
                supporterAccount = ((SupporterAccount) Account.getUserWithUserName(theirUsername));
            } else {
                supporterAccount = ((SupporterAccount) Server.getServer().getTokenInfo(myToken).getUser());
                buyerAccount = BuyerAccount.getBuyerWithUserName(theirUsername);
            }
            if (!splitRequest[3].equals("")) {
                String[] messages = splitRequest[3].split("&&&");
                supporterAccount.setMyChatsMessages(buyerAccount.getUserName(), new ArrayList<>(Arrays.asList(messages)));
            }
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
            return "error" + e.getMessage();
        }
    }

    public static String chargeWalletRequestProcessor(String[] splitRequest) {
        if (ServerMain.server.validateToken(splitRequest[0], SellerAccount.class)) {
            SellerAccount sellerAccount = (SellerAccount) ServerMain.server.getTokenInfo(splitRequest[0]).getUser();
            if (!sellerAccount.getUserName().equals(splitRequest[2])) {
                return "invalidUsername";
            }
            if (!sellerAccount.getPassWord().equals(splitRequest[3])) {
                return "invalidPassword";
            }
            String token = BankClient.getResponseFromBankServer("get_token " + splitRequest[2] + " " + splitRequest[3]);
            String receiptID = BankClient.getResponseFromBankServer("create_receipt " + token + " withdraw " + Double.parseDouble(splitRequest[4]) + " " + sellerAccount.getBankAccountID() + " -1 chargeWallet");
            String result = BankClient.getResponseFromBankServer("pay " + receiptID);
            if (result.equals("invalid token") || result.equals("token expired")) {
                return "failure";
            }
        }
        if (ServerMain.server.validateToken(splitRequest[0], BuyerAccount.class)) {
            BuyerAccount buyerAccount = (BuyerAccount) ServerMain.server.getTokenInfo(splitRequest[0]).getUser();
            if (!buyerAccount.getUserName().equals(splitRequest[2])) {
                return "invalidUsername";
            }
            if (!buyerAccount.getPassWord().equals(splitRequest[3])) {
                return "invalidPassword";
            }
            String token = BankClient.getResponseFromBankServer("get_token " + splitRequest[2] + " " + splitRequest[3]);
            String receiptID = BankClient.getResponseFromBankServer("create_receipt " + token + " withdraw " + Double.parseDouble(splitRequest[4]) + " " + buyerAccount.getBankAccountID() + " -1 chargeWallet");
            String result = BankClient.getResponseFromBankServer("pay " + receiptID);
            if (result.equals("invalid token") || result.equals("token expired")) {
                return "failure";
            }
        }
        return "success";
    }

    public static String withdrawFromWalletRequestProcessor(String[] splitRequest) {
        if (ServerMain.server.validateToken(splitRequest[0], SellerAccount.class)) {
            return "loginNeeded";
        }
        SellerAccount sellerAccount = (SellerAccount) ServerMain.server.getTokenInfo(splitRequest[0]).getUser();
        if (!sellerAccount.getUserName().equals(splitRequest[2])) {
            return "invalidUsername";
        }
        if (!sellerAccount.getPassWord().equals(splitRequest[3])) {
            return "invalidPassword";
        }
        if(sellerAccount.getWalletBalance()<Double.parseDouble(splitRequest[4])){
            return "insufficientBalance";
        }
        String token = BankClient.getResponseFromBankServer("get_token " + splitRequest[2] + " " + splitRequest[3]);
        String receiptID = BankClient.getResponseFromBankServer("create_receipt " + token + " deposit " + Double.parseDouble(splitRequest[4]) + " " + " -1 " + sellerAccount.getBankAccountID() + " withdrawFromWallet");
        String result = BankClient.getResponseFromBankServer("pay " + receiptID);
        if (result.equals("invalid token") || result.equals("token expired")) {
            return "failure";
        }
        return "success";
    }
}

