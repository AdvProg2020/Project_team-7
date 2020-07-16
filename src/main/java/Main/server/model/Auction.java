package Main.server.model;

import Main.server.controller.GeneralController;
import com.gilecode.yagson.com.google.gson.stream.JsonReader;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static java.util.Arrays.asList;

public class Auction {
    private static StringBuilder lastUsedAuctionID;
    private String id;
    private static ArrayList<Auction> allAuctions = new ArrayList<>();
    private String productStringRecord;
    private Product product;
    private double highestPrice;
    private String startDate;
    private String endDate;
    private AuctionUsage auctionUsage;

    private static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private static String inputDateFormat = "yyyy/MM/dd HH:mm:ss";
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(inputDateFormat);


    public Auction(Product product, String startDate, String endDate) {
        this.id = IDGenerator.getNewID(lastUsedAuctionID);
        this.highestPrice = 0;
        this.product = product;
        this.productStringRecord = product.getProductId();
        this.startDate = startDate;
        this.endDate = endDate;
        this.auctionUsage = new AuctionUsage();
    }

    public AuctionUsage getAuctionUsage() throws Exception {
        Date dateNow = new Date();
        if (auctionUsage.getEndDate().compareTo(dateNow) < 0) {
            finishAuction();
            throw new Exception("auction is over");
        }
        return auctionUsage;
    }

    private class AuctionUsage {

        public Product getProduct() {
            return product;
        }

        public double getHighestPrice() {
            return highestPrice;
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
    }



    public void addAuction() {

    }

    public Auction getAuctionById(String id) {
        for (Auction auction : allAuctions) {
            if (auction.id.equals(id)) {
                return auction;
            }
        }
        return null;
    }

    private void finishAuction() {
        allAuctions.remove(this);
        //TODO : sure more is needed :)
    }

    public static String readData() {
        try {
            GeneralController.jsonReader = new JsonReader(new FileReader(new File("src/main/JSON/auctions.json")));
            Auction[] allAuc = GeneralController.yagsonMapper.fromJson(GeneralController.jsonReader, Auction[].class);
            allAuctions = (allAuc == null) ? new ArrayList<>() : new ArrayList<>(asList(allAuc));
            setLastUsedAuctionID();
            setStringRecordProduct();
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

    private static void setStringRecordProduct() {
        try {
            for (Auction auction : allAuctions) {
                auction.product = Product.getProductWithId(auction.productStringRecord);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
