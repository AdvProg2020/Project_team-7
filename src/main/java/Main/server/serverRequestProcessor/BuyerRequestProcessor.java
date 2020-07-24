package Main.server.serverRequestProcessor;

import Main.server.ServerMain;
import Main.server.controller.BuyerController;
import Main.server.model.Auction;
import Main.server.model.Product;
import Main.server.model.ShopFinance;
import Main.server.model.accounts.BuyerAccount;
import Main.server.model.accounts.SupporterAccount;
import Main.server.model.discountAndOffTypeService.DiscountCode;
import Main.server.model.logs.BuyLog;
import javafx.collections.ObservableList;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

public class BuyerRequestProcessor {
    public static String process(String[] splitRequest) {
        if (splitRequest[2].equals("increaseAuction")) {
            return increaseAuctionAmount(splitRequest);
        }
        return null;
    }

    private static String increaseAuctionAmount(String[] splitRequest) {
        if (!ServerMain.server.validateToken(splitRequest[0], BuyerAccount.class)) {
            return "loginNeeded";
        }
        BuyerAccount buyerAccount = (BuyerAccount) ServerMain.server.getTokenInfo(splitRequest[0]).getUser();
        if (buyerAccount.getWalletBalance() < Double.parseDouble(splitRequest[4]) + ShopFinance.getInstance().getMinimumWalletBalance()) {
            return "insufficientBalance";
        }
        if (buyerAccount.isOnAuction != null && !buyerAccount.isOnAuction.equals(splitRequest[3])) {
            return "alreadyOnAuction";
        }
        Auction auction = Auction.getAuctionById(splitRequest[3]);
        double highestOffer = 0;
        try {
            if(!auction.isAuctionValid()){
                throw new Exception();
            }
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
        return Double.toString(((BuyerAccount) Server.getServer().getTokenInfo(data[0]).getUser()).getWalletBalance());
    }

    public static String buyerPersonalInfoRequestProcessor(String[] data) {
        BuyerAccount buyerAccount = ((BuyerAccount) Server.getServer().getTokenInfo(data[0]).getUser());
        return buyerAccount.getFirstName() + "#"
                + buyerAccount.getLastName() + "#"
                + buyerAccount.getUserName() + "#"
                + buyerAccount.getEmail() + "#"
                + buyerAccount.getPhoneNumber() + "#"
                + buyerAccount.getPassWord() + "#"
                + buyerAccount.getProfileImagePath();
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
        if (logs.isEmpty())
            return response;
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
        if (codes.isEmpty())
            return response;
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

    public static String initializeCartAndPriceRequestProcessor(String[] splitRequest) {
        BuyerAccount buyerAccount = ((BuyerAccount) Server.getServer().getTokenInfo(splitRequest[0]).getUser());
        BuyerController.setBuyerController(buyerAccount);
        return Double.toString(buyerAccount.getCart().getCartTotalPriceConsideringOffs());
    }

    public static ObservableList<Product> getCartProductsRequestProcessor(String[] splitRequest) {
        BuyerAccount buyerAccount = ((BuyerAccount) Server.getServer().getTokenInfo(splitRequest[0]).getUser());
        return Product.getCartProductsAsPro(buyerAccount);
    }

    public static String buildIncreaseCartProductResponse(String[] splitRequest) {
        try {
            Product.getProductWithId(splitRequest[2]).getTempCartProduct().increaseNumberByOne();
            return "increased";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return e.getMessage();
        }
    }

    public static String buildDecreaseCartProductResponse(String[] splitRequest) {
        try {
            Product.getProductWithId(splitRequest[2]).getTempCartProduct().decreaseNumberByOne();
            return "decreased";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return e.getMessage();
        }
    }

    public static String setReceiverInformationRequestProcessor(String[] splitRequest) {
        BuyerController.setBuyerController(((BuyerAccount) Server.getServer().getTokenInfo(splitRequest[0]).getUser()));
        ServerMain.buyerController.setReceiverInformation(splitRequest[2]);
        return "set receiver information successfully";
    }

    public static String setPurchaseDiscountRequestProcessor(String[] splitRequest) {
        BuyerController.setBuyerController(((BuyerAccount) Server.getServer().getTokenInfo(splitRequest[0]).getUser()));
        try {
            ServerMain.buyerController.setPurchaseDiscountCode(splitRequest[2]);
            return "set code successfully";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return e.getMessage();
        }
    }

    public static String showPurchaseInfoRequestProcessor(String[] splitRequest) {
        BuyerController.setBuyerController(((BuyerAccount) Server.getServer().getTokenInfo(splitRequest[0]).getUser()));
        return ServerMain.buyerController.showPurchaseInfo();
    }

    public static String finalizeBankPaymentRequestProcessor(String[] splitRequest) {
        BuyerAccount buyerAccount = ((BuyerAccount) Server.getServer().getTokenInfo(splitRequest[0]).getUser());
        BuyerController.setBuyerController(buyerAccount);
        return ServerMain.buyerController.finalizeBankPurchaseAndPay(buyerAccount);
    }

    public static String finalizeWalletPaymentRequestProcessor(String[] splitRequest) {
        BuyerAccount buyerAccount = ((BuyerAccount) Server.getServer().getTokenInfo(splitRequest[0]).getUser());
        BuyerController.setBuyerController(buyerAccount);
        return ServerMain.buyerController.finalizeWalletPurchaseAndPay(buyerAccount);
    }

    public static String initializeHelpCenterRequestProcessor(String[] splitRequest) {
        ArrayList<String> supporters = SupporterAccount.allSupportersForHelpCenter();
        String response = "";
        if (supporters.isEmpty())
            return response;
        for (String supporter : supporters) {
            response = response.concat(supporter);
            response = ManagerRequestProcessor.concatOnlineStatus(response,supporter);
            response = response.concat("#");
        }
        response = response.substring(0, response.length() - 1);
        return response;
    }

    public static String downloadFilesRequestProcessor(String[] splitRequest) {
        return "downloading files in progress";
    }
}
