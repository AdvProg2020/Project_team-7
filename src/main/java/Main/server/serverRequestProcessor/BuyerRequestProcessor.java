package Main.server.serverRequestProcessor;

import Main.client.requestBuilder.BuyerRequestBuilder;
import Main.server.ServerMain;
import Main.server.model.Auction;
import Main.server.model.accounts.BuyerAccount;

public class BuyerRequestProcessor {
    public static String process(String[] splitRequest) {
        if (splitRequest[2].equals("increaseAuction")) {
            return increaseAuctionAmount(splitRequest);
        } else if (splitRequest[2].equals("sendMessage")) {
            return sendMessage(splitRequest);
        }
        return null;
    }

    private static String sendMessage(String[] splitRequest) {
        if (!ServerMain.server.validateToken(splitRequest[0], BuyerAccount.class)) {
            return "loginNeeded";
        }
        Auction auction = Auction.getAuctionById(splitRequest[3]);
        BuyerAccount buyerAccount = (BuyerAccount) ServerMain.server.getTokenInfo(splitRequest[0]).getUser();
        try {
            auction.getAuctionUsage().addMessage("\"" + buyerAccount.getUserName() + "\"\n" + splitRequest[4]);
            return "success";
        } catch (Exception e) {
            return "auctionOver";
        }
    }

    private static String increaseAuctionAmount(String[] splitRequest) {
        if (!ServerMain.server.validateToken(splitRequest[0], BuyerAccount.class)) {
            return "loginNeeded";
        }
        BuyerAccount buyerAccount = (BuyerAccount) ServerMain.server.getTokenInfo(splitRequest[0]).getUser();
        if (buyerAccount.getBalance() < Double.parseDouble(splitRequest[4])) {
            return "insufficientBalance";
        }
        if (buyerAccount.isOnAuction != null && !buyerAccount.isOnAuction.equals(splitRequest[3])) {
            return "alreadyOnAuction";
        }
        Auction auction = Auction.getAuctionById(splitRequest[3]);
        double highestOffer = 0;
        try {
            auction.getAuctionUsage().setReceiverInfo("first name : " + buyerAccount.getFirstName() + " last name : " +
                    buyerAccount.getLastName() + " address : " + splitRequest[5]);
            auction.getAuctionUsage().setLastOfferBuyer(buyerAccount);
            auction.getAuctionUsage().increaseHighestOfferBy(Double.parseDouble(splitRequest[4]));
            highestOffer = auction.getAuctionUsage().getHighestOffer() + Double.parseDouble(splitRequest[4]);
        } catch (Exception e) {
            return "auctionOver";
        }
        buyerAccount.isOnAuction = splitRequest[3];
        return "success#" + highestOffer;
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
