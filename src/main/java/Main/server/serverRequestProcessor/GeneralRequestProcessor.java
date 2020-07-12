package Main.server.serverRequestProcessor;

import Main.server.model.accounts.Account;
import Main.server.model.exceptions.AccountsException;

public class GeneralRequestProcessor {
    public static String loginRequestProcessor(String[] splitRequest) {
        if (splitRequest.length != 4 || !splitRequest[2].contains(":") || !splitRequest[3].contains(":")) {
            return "invalidRequest";
        }
        String username = splitRequest[2].split(":")[1];
        String password = splitRequest[3].split(":")[1];
        if (!Account.isThereUserWithUserName(username)) {
            return "userNameNotFound";
        }
        try {
            if (!Account.getUserWithUserName(username).isPassWordCorrect(password)) {
                return "passwordWrong";
            }
        } catch (Exception e) {
        }
        return "success";
    }

    public static String signUpRequestProcessor(String[] splitRequest) {
        if (splitRequest.length != 4 || !splitRequest[2].contains(":") || !splitRequest[3].contains(":")) {
            return "invalidRequest";
        }
        String username = splitRequest[2].split(":")[1];
        try {
            AccountsException.validateUsernameUniqueness(username);
        } catch (AccountsException e) {
            return "duplicateUserName";
        }
        return "success";
    }
}
