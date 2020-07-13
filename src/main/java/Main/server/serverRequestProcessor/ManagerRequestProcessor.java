package Main.server.serverRequestProcessor;

import Main.server.controller.GeneralController;

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
}
