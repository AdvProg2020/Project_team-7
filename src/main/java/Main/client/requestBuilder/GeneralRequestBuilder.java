package Main.client.requestBuilder;

import Main.client.ClientMain;
import Main.client.graphicView.GraphicMain;
import Main.server.model.exceptions.AccountsException;

import java.util.ArrayList;
import java.util.Arrays;

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

    public static String buildSetUpFinanceRequest(String username, String password, String walletMinimumBalance, String commission) {
        try {
            AccountsException.validateUserName(username);
        } catch (AccountsException.invalidUserNameException e) {
            return "invalidUsername";
        }
        try {
            AccountsException.validatePassWord(password);
        } catch (AccountsException e) {
            return "invalidPassword";
        }
        try {
            double commission1 = Double.parseDouble(commission);
            if (commission1 >= 100) {
                throw new Exception();
            }
        } catch (Exception e) {
            return "invalidCommission";
        }
        try {
            double balance = Double.parseDouble(walletMinimumBalance);
        } catch (Exception e) {
            return "invalidWalletBalance";
        }

        String setUpFinanceRequest = GraphicMain.token + "#financeSetup#" + username + "#" + password + "#" + walletMinimumBalance + "#" + commission;
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

    public static String buildSendMessageRequest(String text) {
        if (text.equals("")) {
            return "emptyString";
        }
        String sendMessageRequest = GraphicMain.token + "#sendMessage#" + GraphicMain.currentAuctionId + "#" + text;
        return ClientMain.client.sendRequest(sendMessageRequest);
    }

    public static String buildOpenChatRequest(String myToken, String theirUsername) {
        return ClientMain.client.sendRequest(myToken + "#openChatWith#" + theirUsername);
    }

    public static String buildInitializeSupporterPanelRequest() {
        return ClientMain.client.sendRequest(GraphicMain.token + "#initializeSupporterPanel");
    }

    public static ArrayList<String> buildSetChatMessagesRequest(String theirUsername) throws Exception {
        String allMessages = ClientMain.client.sendRequest(GraphicMain.token + "#setChatMessages#" + theirUsername);
        if (allMessages.equals(""))
            return new ArrayList<>();
        if (allMessages.startsWith("error"))
            throw new Exception(allMessages);
        String[] messages = allMessages.split("#");
        ArrayList<String> messageList = new ArrayList<>(Arrays.asList(messages));
        return messageList;
    }

    public static void buildSaveChatMessages(String theirUsername, ArrayList<String> messages) {
        String messagesAsString = "";
        for (String message : messages) {
            messagesAsString = messagesAsString.concat(message);
            messagesAsString = messagesAsString.concat("&&&");
        }
        if (!messagesAsString.equals(""))
            messagesAsString = messagesAsString.substring(0, messagesAsString.length() - 3);
        ClientMain.client.sendRequest(GraphicMain.token + "#saveChatMessages#" + theirUsername + "#" + messagesAsString);
    }

    public static String buildChargeWalletRequest(String username, String password, String amount) {
        try {
            double amountOfCharge = Double.parseDouble(amount);
            if(amountOfCharge<=0){
                throw new Exception();
            }
        } catch (Exception e) {
            return "invalidAmount";
        }

        String chargeWalletRequest = GraphicMain.token + "#chargeWallet#" + username + "#" + password + "#" + amount;
        return ClientMain.client.sendRequest(chargeWalletRequest);

    }

    public static String buildWithdrawFromWalletRequest(String username, String password, String amount) {
        try {
            double amountOfCharge = Double.parseDouble(amount);
            if(amountOfCharge<=0){
                throw new Exception();
            }
        } catch (Exception e) {
            return "invalidAmount";
        }

        String withdrawFromWalletRequest = GraphicMain.token + "#withdrawFromWallet#" + username + "#" + password + "#" + amount;
        return ClientMain.client.sendRequest(withdrawFromWalletRequest);
    }

    public static String buildDisconnectionRequest() {
        String disconnectionRequest = GraphicMain.token + "#disconnect";
        return ClientMain.client.sendRequest(disconnectionRequest);
    }
}
