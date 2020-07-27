package Main.server.model;

import Main.server.controller.GeneralController;
import Main.server.model.accounts.BuyerAccount;
import Main.server.model.accounts.SellerAccount;
import Main.server.model.logs.BuyLog;
import Main.server.model.logs.DeliveryStatus;
import Main.server.model.logs.Log;
import Main.server.model.logs.SellLog;
import com.gilecode.yagson.com.google.gson.stream.JsonReader;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static java.util.Arrays.asList;

public class Auction implements Serializable{
    private static StringBuilder lastUsedAuctionID;
    private String id;
    private static ArrayList<Auction> allAuctions = new ArrayList<>();
    private String productStringRecord;
    private Product product;
    private double highestOffer;
    private String startDate;
    private String endDate;
    private AuctionUsage auctionUsage;
    private SellerAccount sellerAccount;
    private String sellerStringRecord;
    private BuyerAccount lastOfferBuyer;
    private String lastBuyerStringRecord;
    private ArrayList<String> allMessages = new ArrayList<>();
    private String receiverInfo = "";

    private static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private static String inputDateFormat = "yyyy/MM/dd HH:mm:ss";
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(inputDateFormat);


    public Auction(Product product, String startDate, String endDate, SellerAccount sellerAccount) {
        this.id = IDGenerator.getNewID(lastUsedAuctionID);
        this.highestOffer = product.getPrice();
        this.product = product;
        this.productStringRecord = product.getProductId();
        //TODO validate date
        this.startDate = startDate;
        this.endDate = endDate;
        this.auctionUsage = new AuctionUsage();
        this.sellerAccount = sellerAccount;
        this.sellerStringRecord = sellerAccount.getUserName();
        product.isOnAuction = true;
    }

    public AuctionUsage getAuctionUsage() throws Exception {
        if (isAuctionOver()) {
            throw new Exception("auction is over");
        }
        return auctionUsage;
    }

    public class AuctionUsage {

        public Product getProduct() {
            return product;
        }

        public double getHighestOffer() {
            return highestOffer;
        }

        public String getStartDateInStringFormat() {
            return startDate;
        }

        public String getEndDateInStringFormat() {
            return endDate;
        }

        public String getId() {
            return id;
        }

        public Date getStartDate() {
            Date date = new Date();
            try {
                date = simpleDateFormat.parse(startDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return date;
        }

        public Date getEndDate() {
            Date date = new Date();
            try {
                date = simpleDateFormat.parse(endDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return date;
        }

        public String viewSummary() {
            return "@" + id + " " + product.getName() + "\tstart :\t" + startDate + "\tend :\t" + endDate;
        }

        public BuyerAccount getLastBuyer() {
            return lastOfferBuyer;
        }

        public SellerAccount getSellerAccount() {
            return sellerAccount;
        }

        public void increaseHighestOfferBy(double amount) {
            highestOffer += amount;
        }

        public void setLastOfferBuyer(BuyerAccount buyerAccount) {
            lastOfferBuyer = buyerAccount;
            lastBuyerStringRecord = buyerAccount.getUserName();
        }

        public void setReceiverInfo(String info) {
            receiverInfo = info;
        }

        public ArrayList<String> getAllMessages() {
            return allMessages;
        }

        public void addMessage(String message) {
            allMessages.add(message);
        }
    }

    public static void addAuction(Auction auction) {
        allAuctions.add(auction);
    }

    public static Auction getAuctionById(String id) {
        for (Auction auction : allAuctions) {
            if (auction.id.equals(id)) {
                return auction;
            }
        }
        return null;
    }

    private void finishAuction() {
        allAuctions.remove(this);
        product.isOnAuction = false;
        if (lastOfferBuyer != null) {
            buildLogs();
            sellerAccount.increaseBalanceBy(highestOffer*(100-ShopFinance.getInstance().getCommission())/100);
            product.decreaseAvailabilityBy(1);
            lastOfferBuyer.isOnAuction = null;
            try {
                lastOfferBuyer.decreaseBalanceBy(highestOffer);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void buildLogs() {
        String id = IDGenerator.getNewID(Log.getLastUsedLogID());
        Date dateNow = new Date();

        SellLog sellLog = new SellLog(id, dateNow, highestOffer, new CartProduct(product, sellerAccount, null).toStringForSellLog(), lastOfferBuyer, 0, DeliveryStatus.PENDING_DELIVERY, receiverInfo);
        sellerAccount.addLog(sellLog);
        Log.addLog(sellLog);

        BuyLog buyLog = new BuyLog(id, dateNow, highestOffer, 0, new CartProduct(product, sellerAccount, null).toStringForBuyLog(), DeliveryStatus.PENDING_DELIVERY, receiverInfo);
        lastOfferBuyer.addLog(buyLog);
        Log.addLog(buyLog);
    }

    public static String readData() {
        try {
            GeneralController.jsonReader = new JsonReader(new FileReader(new File("src/main/JSON/auctions.json")));
            Auction[] allAuc = GeneralController.yagsonMapper.fromJson(GeneralController.jsonReader, Auction[].class);
            allAuctions = (allAuc == null) ? new ArrayList<>() : new ArrayList<>(asList(allAuc));
            setLastUsedAuctionID();
            setStringRecords();
            return "Read Auctions Data Successfully.";
        } catch (FileNotFoundException e) {
            return "Problem loading data from auctions.json";
        }
    }

    public static void setLastUsedAuctionID() {
        if (allAuctions.size() == 0) {
            lastUsedAuctionID = new StringBuilder("@");
        } else {
            lastUsedAuctionID = new StringBuilder(allAuctions.get(allAuctions.size() - 1).id);
        }
    }

    public static String writeData() {
        try {
            GeneralController.fileWriter = new FileWriter("src/main/JSON/auctions.json");
            Auction[] allAuc = new Auction[allAuctions.size()];
            allAuc = allAuctions.toArray(allAuc);
            GeneralController.yagsonMapper.toJson(allAuc, Auction[].class, GeneralController.fileWriter);
            GeneralController.fileWriter.close();
            return "Saved Auctions Data Successfully.";
        } catch (IOException e) {
            return "Problem saving auctions data.";
        }
    }

    public static void setStringRecords() {
        for (int i = 0; i < allAuctions.size(); i++) {
            Auction auction = allAuctions.get(i);
            try {
                auction.product = Product.getProductWithId(auction.productStringRecord);
                auction.sellerAccount = SellerAccount.getSellerWithUserName(auction.sellerStringRecord);
            } catch (Exception e) {
                allAuctions.remove(auction);
                if(i>0){
                    i--;
                }
            }
            try {
                auction.lastOfferBuyer = BuyerAccount.getBuyerWithUserName(auction.lastBuyerStringRecord);
            } catch (Exception e) {
                auction.lastOfferBuyer = null;
                auction.highestOffer = auction.product.getPrice();
            }
        }
    }


    public boolean isAuctionOver() {
        Date dateNow = new Date();
        if (auctionUsage.getEndDate().compareTo(dateNow) < 0) {
            finishAuction();
            return true;
        }
        return false;
    }

    public boolean isAuctionValid() {
        try {
            Product.getProductWithId(productStringRecord);
            SellerAccount.getSellerWithUserName(sellerStringRecord);
        } catch (Exception e) {
            allAuctions.remove(this);
            return false;
        }
        return true;
    }

    public boolean hasAuctionBeenStarted() {
        Date dateNow = new Date();
        return auctionUsage.getStartDate().compareTo(dateNow) < 0;
    }

    public static ArrayList<Auction> getAllAuctions() {
        for (int i = 0; i < allAuctions.size(); i++) {
            Auction auction = allAuctions.get(i);
            if (auction.isAuctionOver()) {
                if(i>0){
                    i--;
                }
            }
            if (!auction.isAuctionValid()) {
                allAuctions.remove(auction);
                if(i>0){
                    i--;
                }
            }
        }
        return allAuctions;
    }
}
