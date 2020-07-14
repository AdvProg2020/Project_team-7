package Main.server.serverRequestProcessor;

import Main.server.ServerMain;
import Main.server.controller.GeneralController;
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
}
