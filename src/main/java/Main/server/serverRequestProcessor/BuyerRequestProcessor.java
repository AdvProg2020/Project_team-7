package Main.server.serverRequestProcessor;

import Main.server.controller.BuyerController;
import Main.server.controller.GeneralController;
import Main.server.model.accounts.BuyerAccount;
import Main.server.model.accounts.SellerAccount;

public class BuyerRequestProcessor {
    public static String initializeBuyerPanelRequestProcessor() {
        String string = Double.toString(((BuyerAccount) GeneralController.currentUser).getBalance());
        return string;
    }

    public static String buyerPersonalInfoRequestProcessor() {
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
