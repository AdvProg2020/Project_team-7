package Main.server.serverRequestProcessor;

import Main.server.ServerMain;
import Main.server.model.Auction;
import Main.server.model.accounts.BuyerAccount;

public class BuyerRequestProcessor {
    public static String process(String[] splitRequest) {
        if (splitRequest[2].equals("increaseAuction")) {
            return increaseAuctionAmount(splitRequest);
        } else if (true) {
        }
        return null;
    }

    private static String increaseAuctionAmount(String[] splitRequest) {
        if (!ServerMain.server.validateToken(splitRequest[0], BuyerAccount.class)) {
            return "loginNeeded";
        }
        BuyerAccount buyerAccount = (BuyerAccount) ServerMain.server.getTokenInfo(splitRequest[0]).getUser();
        if (buyerAccount.getBalance() < Double.parseDouble(splitRequest[4])) {
            return "insufficientBalance";
        }
        if(buyerAccount.isOnAuction!=null&&!buyerAccount.isOnAuction.equals(splitRequest[3])){
            return "alreadyOnAuction";
        }
        Auction auction = Auction.getAuctionById(splitRequest[3]);
        try {
            auction.getAuctionUsage().setLastOfferBuyer(buyerAccount);
            auction.getAuctionUsage().increaseHighestOfferBy(Double.parseDouble(splitRequest[4]));
        } catch (Exception e) {
            return "auctionOver";
        }
        buyerAccount.isOnAuction = splitRequest[3];
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
