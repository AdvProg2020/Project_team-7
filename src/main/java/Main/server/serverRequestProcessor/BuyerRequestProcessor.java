package Main.server.serverRequestProcessor;

import Main.client.graphicView.GraphicMain;
import Main.server.ServerMain;
import Main.server.controller.BuyerController;
import Main.server.controller.GeneralController;
import Main.server.model.accounts.BuyerAccount;
import Main.server.model.accounts.SellerAccount;

public class BuyerRequestProcessor {
    public static String process(String[] splitRequest) {
        if (splitRequest[2].equals("increaseAuction")) {
            return increaseAuctionAmount(splitRequest);
        } else if (true) {
        }
        return null;
    }

    private static String increaseAuctionAmount(String[] splitRequest) {
        if(!ServerMain.server.validateToken(splitRequest[0],BuyerAccount.class)){
           return "loginNeeded";
        }
        BuyerAccount buyerAccount = (BuyerAccount) ServerMain.server.getTokenInfo(splitRequest[0]).getUser();
        if(buyerAccount.getBalance()<Double.parseDouble(splitRequest[3])){
            return "insufficientBalance";
        }
        return "success";
    }

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
