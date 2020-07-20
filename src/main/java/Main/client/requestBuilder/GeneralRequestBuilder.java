package Main.client.requestBuilder;

import Main.client.ClientMain;
import Main.client.graphicView.GraphicMain;
import Main.server.ServerMain;
import Main.server.model.exceptions.AccountsException;
import Main.server.serverRequestProcessor.Server;

public class GeneralRequestBuilder {

    public static String buildLogoutRequest() {
        String logoutRequest = GraphicMain.token + "#logout";
        return ClientMain.client.sendRequest(logoutRequest);
    }

    public static String buildLoginRequest(String userName, String password) {
        try {
            AccountsException.validateUserName(userName);
            AccountsException.validatePassWord(password);
        } catch (AccountsException e) {
            return "invalidCharacter#" + e.getErrorMessage();
        }
        String loginRequest = GraphicMain.token + "#login#username:" + userName + "#password:" + password;
        return ClientMain.client.sendRequest(loginRequest);
    }

    public static String buildSignUpRequest(String username, String password) {
        try {
            AccountsException.validateUserName(username);
            AccountsException.validatePassWord(password);
        } catch (AccountsException e) {
            return "invalidCharacter#" + e.getErrorMessage();
        }
        String signUpRequest = GraphicMain.token + "#signUp#userName:" + username + "#password:" + password;
        return ClientMain.client.sendRequest(signUpRequest);
    }

    public static String buildBuyerCompleteSignUpRequest(String userName, String firstName, String lastName, String email, String phoneNumber, String password, String imagePath) {
        try {
            AccountsException.validateNameTypeInfo("firstName", firstName);
            AccountsException.validateNameTypeInfo("lastName", lastName);
            AccountsException.validateNameTypeInfo("firstName", firstName);
        } catch (AccountsException e) {
            return "invalidCharacter#" + e.getErrorMessage();
        }
        try {
            AccountsException.validateEmail(email);
        } catch (AccountsException e) {
            return "invalidEmail#" + e.getErrorMessage();
        }
        try {
            AccountsException.validatePhoneNumber(phoneNumber);
        } catch (AccountsException e) {
            return "invalidPhoneNumber#" + e.getErrorMessage();
        }

        String signUpRequest = GraphicMain.token + "#signUpBuyer#userName:" + userName + "#firstName:" + firstName + "#lastName:" + lastName + "#email:" + email + "#phoneNumber:" + phoneNumber + "#password:" + password + "#imagePath:" + imagePath;
        return ClientMain.client.sendRequest(signUpRequest);
    }

    public static String buildSellerCompleteSignUpRequest(String userName, String firstName, String lastName, String email, String phoneNumber, String password, String companyName, String companyInfo, String imagePath) {
        try {
            AccountsException.validateNameTypeInfo("firstName", firstName);
            AccountsException.validateNameTypeInfo("lastName", lastName);
            AccountsException.validateNameTypeInfo("firstName", firstName);
            AccountsException.validateNameTypeInfo("companyName", companyName);
            AccountsException.validateNameTypeInfo("companyInformation", companyInfo);
        } catch (AccountsException e) {
            return "invalidCharacter#" + e.getErrorMessage();
        }
        try {
            AccountsException.validateEmail(email);
        } catch (AccountsException e) {
            return "invalidEmail#" + e.getErrorMessage();
        }
        try {
            AccountsException.validatePhoneNumber(phoneNumber);
        } catch (AccountsException e) {
            return "invalidPhoneNumber#" + e.getErrorMessage();
        }

        String signUpRequest = GraphicMain.token + "#signUpSeller#userName:" + userName + "#firstName:" + firstName +
                "#lastName:" + lastName + "#email:" + email + "#phoneNumber:" + phoneNumber + "#password:" + password + "#companyName:" + companyName + "#companyInfo:" + companyInfo + "#imagePath:" + imagePath;
        return ClientMain.client.sendRequest(signUpRequest);
    }

    public static String buildManagerSignUpRequest(String userName, String firstName, String lastName, String email, String phoneNumber, String password, String imagePath) {
        try {
            AccountsException.validateUserName(userName);
            AccountsException.validatePassWord(password);
            AccountsException.validateNameTypeInfo("firstName", firstName);
            AccountsException.validateNameTypeInfo("lastName", lastName);
            AccountsException.validateNameTypeInfo("firstName", firstName);
        } catch (AccountsException e) {
            return "invalidCharacter#" + e.getErrorMessage();
        }
        try {
            AccountsException.validateEmail(email);
        } catch (AccountsException e) {
            return "invalidEmail#" + e.getErrorMessage();
        }
        try {
            AccountsException.validatePhoneNumber(phoneNumber);
        } catch (AccountsException e) {
            return "invalidPhoneNumber#" + e.getErrorMessage();
        }

        String signUpRequest = GraphicMain.token + "#signUpManager#userName:" + userName + "#firstName:" + firstName + "#lastName:" + lastName + "#email:" + email + "#phoneNumber:" + phoneNumber + "#password:" + password + "#imagePath:" + imagePath;
        return ClientMain.client.sendRequest(signUpRequest);
    }

    public static String getAllDataForProductPage() {
        return ClientMain.client.sendRequest(GraphicMain.token + "#getAllDataForProductPage#" + GraphicMain.currentProductId);
    }

    public static String selectSeller(String username) {
        return ClientMain.client.sendRequest(GraphicMain.token + "#selectSeller#" + username + "#" + GraphicMain.currentProductId);
    }

    public static String buildRateProductPermissionRequest() {
        return ClientMain.client.sendRequest(GraphicMain.token + "#rateProductPermission#" + GraphicMain.currentProductId);
    }

    public static String buildRateProductRequest(String score) {
        return ClientMain.client.sendRequest(GraphicMain.token + "#rateProduct#" + GraphicMain.currentProductId + "#" + score);
    }

    public static String buildCompareProductRequest(String id) {
        return ClientMain.client.sendRequest(GraphicMain.token + "#compareProduct#" + GraphicMain.currentProductId + "#" + id);
    }

    public static String buildSetUpFinanceRequest(String port, String IP, String walletMinimumBalance, String commission) {
        try{
            Integer.parseInt(port);
        } catch (NumberFormatException e) {
            return "invalidPort";
        }
        try {
            Double.parseDouble(walletMinimumBalance);
        } catch (NumberFormatException e) {
            return "invalidWalletBalance";
        }
        try {
            double commission1 = Double.parseDouble(commission);
            if(commission1>=100){
                throw new Exception();
            }
        } catch (Exception e) {
            return "invalidCommission";
        }

        String setUpFinanceRequest = GraphicMain.token + "#financeSetup#" + port + "#" + IP + "#" + walletMinimumBalance + "#" + commission;
        return ClientMain.client.sendRequest(setUpFinanceRequest);
    }

    public static String buildSupporterSignUpRequest(String userName, String firstName, String lastName, String email, String phoneNumber, String password, String imagePath) {
        try {
            AccountsException.validateUserName(userName);
            AccountsException.validatePassWord(password);
            AccountsException.validateNameTypeInfo("firstName", firstName);
            AccountsException.validateNameTypeInfo("lastName", lastName);
            AccountsException.validateNameTypeInfo("firstName", firstName);
        } catch (AccountsException e) {
            return "invalidCharacter#" + e.getErrorMessage();
        }
        try {
            AccountsException.validateEmail(email);
        } catch (AccountsException e) {
            return "invalidEmail#" + e.getErrorMessage();
        }
        try {
            AccountsException.validatePhoneNumber(phoneNumber);
        } catch (AccountsException e) {
            return "invalidPhoneNumber#" + e.getErrorMessage();
        }

        String signUpRequest = GraphicMain.token + "#signUpSupporter#userName:" + userName + "#firstName:" + firstName + "#lastName:" + lastName + "#email:" + email + "#phoneNumber:" + phoneNumber + "#password:" + password + "#imagePath:" + imagePath;
        return ClientMain.client.sendRequest(signUpRequest);
    }
}
