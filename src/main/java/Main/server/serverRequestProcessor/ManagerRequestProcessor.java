package Main.server.serverRequestProcessor;

import Main.server.ServerMain;
import Main.server.controller.GeneralController;
import Main.server.model.accounts.ManagerAccount;
import Main.server.model.logs.BuyLog;
import Main.server.model.logs.Log;

public class ManagerRequestProcessor {
    public static String managerPersonalInfoRequestProcessor() {
        String string = GeneralController.currentUser.getFirstName() + "#"
                + GeneralController.currentUser.getLastName() + "#"
                + GeneralController.currentUser.getUserName() + "#"
                + GeneralController.currentUser.getEmail() + "#"
                + GeneralController.currentUser.getPhoneNumber() + "#"
                + GeneralController.currentUser.getPassWord() + "#"
                + GeneralController.currentUser.getProfileImagePath();
        return string;
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
}
