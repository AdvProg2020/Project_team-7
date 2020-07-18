package Main.server.serverRequestProcessor;

import Main.server.ServerMain;
import Main.server.controller.GeneralController;
import Main.server.model.Auction;
import Main.server.model.Product;
import Main.server.model.accounts.Account;
import Main.server.model.accounts.BuyerAccount;
import Main.server.model.accounts.ManagerAccount;
import Main.server.model.accounts.SellerAccount;
import Main.server.model.logs.BuyLog;
import Main.server.model.logs.Log;
import com.gilecode.yagson.com.google.gson.stream.JsonReader;

import java.io.*;
import java.util.ArrayList;

import static java.util.Arrays.asList;
import static java.util.Arrays.spliterator;

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
        }
        return null;
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
        } else {
            return "manager";
        }
    }

    private static String allAuctionResponse() {
        try {
            InputStream inputStream = new FileInputStream(new File("src/main/JSON/auctions.json"));
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = bufferedReader.readLine();
            StringBuilder stringBuilder = new StringBuilder();
            while (line != null) {
                stringBuilder.append(line).append("\n");
                line = bufferedReader.readLine();
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            return "[]";
        }
    }

    private static String auctionResponse(String id) {
        return GeneralController.yagsonMapper.toJson(Auction.getAuctionById(id), Auction.class);
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
        try {
            InputStream inputStream = new FileInputStream(new File("src/main/JSON/buyers.json"));
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = bufferedReader.readLine();
            StringBuilder stringBuilder = new StringBuilder();
            while (line != null) {
                stringBuilder.append(line).append("\n");
                line = bufferedReader.readLine();
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            return "[]";
        }
    }

    private static String allOffsResponse() {
        try {
            InputStream inputStream = new FileInputStream(new File("src/main/JSON/offs.json"));
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = bufferedReader.readLine();
            StringBuilder stringBuilder = new StringBuilder();
            while (line != null) {
                stringBuilder.append(line).append("\n");
                line = bufferedReader.readLine();
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            return "[]";
        }
    }

    private static String allSellersResponse() {
        try {
            InputStream inputStream = new FileInputStream(new File("src/main/JSON/sellers.json"));
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = bufferedReader.readLine();
            StringBuilder stringBuilder = new StringBuilder();
            while (line != null) {
                stringBuilder.append(line).append("\n");
                line = bufferedReader.readLine();
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            return "[]";
        }
    }

    private static String allCategoriesResponse() {
        try {
            InputStream inputStream = new FileInputStream(new File("src/main/JSON/categories.json"));
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = bufferedReader.readLine();
            StringBuilder stringBuilder = new StringBuilder();
            while (line != null) {
                stringBuilder.append(line).append("\n");
                line = bufferedReader.readLine();
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            return "[]";
        }
    }

    private static String allProductsResponse() {
        try {
            InputStream inputStream = new FileInputStream(new File("src/main/JSON/products.json"));
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = bufferedReader.readLine();
            StringBuilder stringBuilder = new StringBuilder();
            while (line != null) {
                stringBuilder.append(line).append("\n");
                line = bufferedReader.readLine();
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            return "[]";
        }
    }


}
