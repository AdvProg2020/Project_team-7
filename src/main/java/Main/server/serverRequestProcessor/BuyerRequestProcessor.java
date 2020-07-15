package Main.server.serverRequestProcessor;

import Main.client.graphicView.GraphicMain;
import Main.server.controller.BuyerController;
import Main.server.controller.GeneralController;
import Main.server.model.accounts.BuyerAccount;
import Main.server.model.accounts.SellerAccount;

public class BuyerRequestProcessor {
    public static String initializeBuyerPanelRequestProcessor(String[] data) {
        String string = Double.toString(((BuyerAccount) Server.getServer().getTokenInfo(data[0]).getUser()).getBalance());
        return string;
    }

    public static String buyerPersonalInfoRequestProcessor(String[] data) {
        BuyerAccount buyerAccount = ((BuyerAccount) Server.getServer().getTokenInfo(data[0]).getUser());
        String string = buyerAccount.getFirstName() + "#"
                + buyerAccount.getLastName() + "#"
                + buyerAccount.getUserName() + "#"
                + buyerAccount.getEmail() + "#"
                + buyerAccount.getPhoneNumber() + "#"
                + buyerAccount.getPassWord() + "#"
                + buyerAccount.getProfileImagePath();
        return string;
    }
}
