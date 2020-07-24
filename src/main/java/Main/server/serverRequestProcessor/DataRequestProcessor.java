package Main.server.serverRequestProcessor;

import Main.server.ServerMain;
import Main.server.controller.GeneralController;
import Main.server.model.Auction;
import Main.server.model.Category;
import Main.server.model.Product;
import Main.server.model.accounts.Account;
import Main.server.model.accounts.BuyerAccount;
import Main.server.model.accounts.ManagerAccount;
import Main.server.model.accounts.SellerAccount;
import Main.server.model.discountAndOffTypeService.Off;
import Main.server.model.logs.BuyLog;
import Main.server.model.logs.Log;

import java.util.ArrayList;

public class DataRequestProcessor {
    public static String process(String[] splitRequest) {
        if (splitRequest[2].equals("allProducts")) {
            return allProductsResponse();
        } else if (splitRequest[2].equals("allCategories")) {
            return allCategoriesResponse();
        } else if (splitRequest[2].equals("allSellers")) {
            return allSellersResponse();
        } else if (splitRequest[2].equals("allBuyers")) {
            return allBuyersResponse();
        } else if (splitRequest[2].equals("log")) {
            return logResponseWithID(splitRequest[3]);
        } else if (splitRequest[2].equals("allOffs")) {
            return allOffsResponse();
        } else if (splitRequest[2].equals("auction")) {
            return auctionResponse(splitRequest[3]);
        } else if (splitRequest[2].equals("allAuctions")) {
            return allAuctionResponse();
        } else if (splitRequest[2].equals("userType")) {
            return userTypeResponse(splitRequest[0]);
        } else if (splitRequest[2].equals("allProductsForAuction")) {
            return allProductsForAuctionResponse(splitRequest[0]);
        } else if (splitRequest[2].equals("highestOffer")) {
            return highestOfferResponse(splitRequest);
        } else if (splitRequest[2].equals("messageNo")) {
            return messageNoResponse(splitRequest);
        } else if (splitRequest[2].equals("startMode")) {
            return startModeResponse();
        } else if (splitRequest[2].equals("categoryProducts")) {
            return categoryProductsResponse(splitRequest[3]);
        } else if (splitRequest[2].equals("walletBalance")) {
            return walletBalanceResponse(splitRequest[0]);
        }
        return null;
    }

    private static String walletBalanceResponse(String token) {
        Auction.getAllAuctions();
        if (ServerMain.server.validateToken(token, SellerAccount.class)) {
            SellerAccount sellerAccount = (SellerAccount) ServerMain.server.getTokenInfo(token).getUser();
            return sellerAccount.getWalletBalance() + "";
        }
        if (ServerMain.server.validateToken(token, BuyerAccount.class)) {
            BuyerAccount buyerAccount = (BuyerAccount) ServerMain.server.getTokenInfo(token).getUser();
            return buyerAccount.getWalletBalance() + "";
        } else {
            return "loginNeeded";
        }
    }

    private static String categoryProductsResponse(String productID) {
        Product product = null;
        try {
            product = Product.getProductWithId(productID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Category category = product.getCategory();
        if (category == null) {
            return "nullCategory";
        }
        ArrayList<Product> allProducts = category.getProducts();

        Product[] allPro = new Product[allProducts.size()];
        allPro = allProducts.toArray(allPro);
        return GeneralController.yagsonMapper.toJson(allPro, Product[].class);
    }

    private static String startModeResponse() {
        if (!ManagerAccount.isThereAChiefManager()) {
            return "1";
        } else {
            return "2";
        }
    }

    private static String messageNoResponse(String[] splitRequest) {
        Auction auction = Auction.getAuctionById(splitRequest[3]);
        try {
            int messageNo = auction.getAuctionUsage().getAllMessages().size();
            if(!auction.isAuctionValid()){
                throw new Exception();
            }
            return messageNo + "";
        } catch (Exception e) {
            return "auctionOver";
        }
    }

    private static String highestOfferResponse(String[] splitRequest) {
        Auction auction = Auction.getAuctionById(splitRequest[3]);
        try {
            String highestOffer = auction.getAuctionUsage().getHighestOffer() + "";
            if(!auction.isAuctionValid()){
                throw new Exception();
            }
            return highestOffer;
        } catch (Exception e) {
            return "auction Over";
        }
    }

    private static String allProductsForAuctionResponse(String token) {
        if (!ServerMain.server.validateToken(token, SellerAccount.class)) {
            return "loginNeeded";
        }
        SellerAccount sellerAccount = (SellerAccount) ServerMain.server.getTokenInfo(token).getUser();

        Product[] allPro = new Product[sellerAccount.getProducts().size()];
        allPro = sellerAccount.getProducts().toArray(allPro);
        return GeneralController.yagsonMapper.toJson(allPro, Product[].class);
    }

    private static String userTypeResponse(String token) {
        if (!ServerMain.server.validateToken(token, Account.class)) {
            return "loginNeeded";
        }
        Account account = ServerMain.server.getTokenInfo(token).getUser();
        if (account instanceof BuyerAccount) {
            return "buyer";
        }
        if (account instanceof SellerAccount) {
            return "seller";
        }
        if (account instanceof ManagerAccount) {
            return "manager";
        } else
            return "supporter";
    }

    private static String allAuctionResponse() {
        ArrayList<Auction> allAuctions = Auction.getAllAuctions();
        Auction[] allAuc = new Auction[allAuctions.size()];
        allAuc = allAuctions.toArray(allAuc);
        return GeneralController.yagsonMapper.toJson(allAuc, Auction[].class);
    }

    private static String auctionResponse(String id) {
        Auction auction = Auction.getAuctionById(id);
        if(!auction.isAuctionValid()||auction.isAuctionOver()){
            return "auctionOver";
        }
        return GeneralController.yagsonMapper.toJson(auction, Auction.class);
    }

    private static String logResponseWithID(String logID) {
        try {
            BuyLog buyLog = (BuyLog) Log.getLogWithID(logID);
            return GeneralController.yagsonMapper.toJson(buyLog, BuyLog.class);
        } catch (Exception e) {
            e.printStackTrace();
            return "failure";
        }
    }

    private static String allBuyersResponse() {
        ArrayList<BuyerAccount> allBuyers = BuyerAccount.getAllBuyers();
        BuyerAccount[] allBu = new BuyerAccount[allBuyers.size()];
        allBu = allBuyers.toArray(allBu);
        return GeneralController.yagsonMapper.toJson(allBu, BuyerAccount[].class);
    }

    private static String allOffsResponse() {
        ArrayList<Off> allOffs = Off.getAllOffs();
        Off[] allOff = new Off[allOffs.size()];
        allOff = allOffs.toArray(allOff);
        return GeneralController.yagsonMapper.toJson(allOff, Off[].class);
    }

    private static String allSellersResponse() {
        ArrayList<SellerAccount> allSellers = SellerAccount.getAllSellers();
        SellerAccount[] allSe = new SellerAccount[allSellers.size()];
        allSe = allSellers.toArray(allSe);
        return GeneralController.yagsonMapper.toJson(allSe, SellerAccount[].class);
    }

    private static String allCategoriesResponse() {
        ArrayList<Category> allCategories = Category.getAllCategories();
        Category[] allCa = new Category[allCategories.size()];
        allCa = allCategories.toArray(allCa);
        return GeneralController.yagsonMapper.toJson(allCa, Category[].class);
    }

    private static String allProductsResponse() {
        ArrayList<Product> allProducts = Product.getAllProducts();
        Product[] allPr = new Product[allProducts.size()];
        allPr = allProducts.toArray(allPr);
        return GeneralController.yagsonMapper.toJson(allPr, Product[].class);
    }
}
