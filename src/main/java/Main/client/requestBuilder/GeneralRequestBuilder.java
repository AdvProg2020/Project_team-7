package Main.client.requestBuilder;

import Main.client.ClientMain;
import Main.server.model.exceptions.AccountsException;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class GeneralRequestBuilder {

    public static String buildLoginRequest(String userName, String password){
        try {
            AccountsException.validateUserName(userName);
            AccountsException.validatePassWord(password);
        } catch (AccountsException e) {
            return "invalidCharacter/" + e.getErrorMessage();
        }
        String loginRequest = "0000/login/username:" + userName + "/password:" + password;
        return ClientMain.client.sendRequest(loginRequest);
    }

    public static String buildSignUpRequest(String username, String password){
        try {
            AccountsException.validateUserName(username);
            AccountsException.validatePassWord(password);
        } catch (AccountsException e) {
            return "invalidCharacter/" + e.getErrorMessage();
        }
        String signUpRequest = "0000/signUp/userName:" + username + "/password:" + password;
        return ClientMain.client.sendRequest(signUpRequest);
    }
}
