package Main.client.requestBuilder;

import Main.client.ClientMain;
import Main.server.controller.GeneralController;
import Main.server.model.exceptions.AccountsException;

public class GeneralRequestBuilder {

    public static String buildLoginRequest(String userName, String password) {
        try {
            AccountsException.validateUserName(userName);
            AccountsException.validatePassWord(password);
        } catch (AccountsException e) {
            return "invalidCharacter#" + e.getErrorMessage();
        }
        String loginRequest = "0000#login#username:" + userName + "#password:" + password;
        return ClientMain.client.sendRequest(loginRequest);
    }

    public static String buildSignUpRequest(String username, String password) {
        try {
            AccountsException.validateUserName(username);
            AccountsException.validatePassWord(password);
        } catch (AccountsException e) {
            return "invalidCharacter#" + e.getErrorMessage();
        }
        String signUpRequest = "0000#signUp#userName:" + username + "#password:" + password;
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

        String signUpRequest = "0000#signUpBuyer#userName:" + userName + "#firstName:" + firstName + "#lastName:" + lastName + "#email:" + email + "#phoneNumber:" + phoneNumber + "#password:" + password + "#imagePath:" + imagePath;
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

        String signUpRequest = "0000#signUpSeller#userName:" + userName + "#firstName:" + firstName + "#lastName:" + lastName + "#email:" + email + "#phoneNumber:" + phoneNumber + "#password:" + password + "#imagePath:" + imagePath;
        return ClientMain.client.sendRequest(signUpRequest);
    }

    public static String buildManagerCompleteSignUpRequest(String userName, String firstName, String lastName, String email, String phoneNumber, String password, String imagePath) {
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

        String token = (GeneralController.currentUser == null ? "0000" : GeneralController.currentUser.getToken());
        String signUpRequest = token + "#signUpManager#userName:" + userName + "#firstName:" + firstName + "#lastName:" + lastName + "#email:" + email + "#phoneNumber:" + phoneNumber + "#password:" + password + "#imagePath:" + imagePath;
        return ClientMain.client.sendRequest(signUpRequest);
    }
}
