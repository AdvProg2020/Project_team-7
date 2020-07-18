package Main.server.serverRequestProcessor;

import Main.client.requestBuilder.BuyerRequestBuilder;
import Main.server.ServerMain;
import Main.server.model.Auction;
import Main.server.model.accounts.BuyerAccount;
import Main.server.model.accounts.ManagerAccount;
import Main.server.model.accounts.SellerAccount;
import Main.server.model.discountAndOffTypeService.DiscountCode;
import Main.server.model.logs.BuyLog;
import Main.server.model.requests.Request;

import java.util.ArrayList;

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

    public static String editBuyerPersonalInformationRequestProcessor(String[] data) {
        BuyerAccount buyerAccount = ((BuyerAccount) Server.getServer().getTokenInfo(data[0]).getUser());
        buyerAccount.setFirstName(data[2]);
        buyerAccount.setLastName(data[3]);
        buyerAccount.setEmail(data[4]);
        buyerAccount.setPhoneNumber(data[5]);
        buyerAccount.setPassWord(data[6]);
        return "success";
    }

    public static String initializeMyOrdersRequestProcessor(String[] data) {
        BuyerAccount buyerAccount = ((BuyerAccount) Server.getServer().getTokenInfo(data[0]).getUser());
        ArrayList<String> logs = buyerAccount.buyLogsList();
        String response = "";
        for (String log : logs) {
            response = response.concat(log);
            response = response.concat("#");
        }
        response = response.substring(0, response.length() - 1);
        return response;
    }

    public static String getBuyLogInfoRequestProcessor(String[] splitRequest) {
        try {
            return BuyLog.getLogWithID(splitRequest[2]).viewLog();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return e.getMessage();
        }
    }

    public static String initializeBuyerDiscountsRequestProcessor(String[] data) {
        BuyerAccount buyerAccount = ((BuyerAccount) Server.getServer().getTokenInfo(data[0]).getUser());
        ArrayList<String> codes = buyerAccount.getDiscountsList();
        String response = "";
        for (String code : codes) {
            response = response.concat(code);
            response = response.concat("#");
        }
        response = response.substring(0, response.length() - 1);
        return response;
    }

    public static String showDiscountInfoAsBuyerRequestProcessor(String[] splitRequest) {
        BuyerAccount buyerAccount = ((BuyerAccount) Server.getServer().getTokenInfo(splitRequest[0]).getUser());
        try {
            return DiscountCode.getDiscountCodeWithCode(splitRequest[2]).viewMeAsBuyer(buyerAccount);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return e.getMessage();
        }
    }
}
