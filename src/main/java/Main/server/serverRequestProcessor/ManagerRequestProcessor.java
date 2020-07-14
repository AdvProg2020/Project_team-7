package Main.server.serverRequestProcessor;

import Main.server.controller.GeneralController;
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
            return markDeliveredResponse(splitRequest[3]);
        } else if (true) {
        }
        return null;
    }

    private static String markDeliveredResponse(String logID) {
        try {
            BuyLog buyLog = (BuyLog) Log.getLogWithID(logID);
            buyLog.markDelivered();
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "failure";
        }
    }
}
