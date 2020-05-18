package Main.model.logs;

import Main.controller.GeneralController;
import Main.model.Product;
import Main.model.accounts.BuyerAccount;
import Main.model.accounts.SellerAccount;
import Main.model.discountAndOffTypeService.Off;
import com.gilecode.yagson.com.google.gson.stream.JsonReader;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static java.util.Arrays.asList;

public abstract class Log {
    private static StringBuilder lastUsedLogID;
    protected String logId;
    protected Date date;
    protected double totalCost;
    protected String products;
    protected DeliveryStatus deliveryStatus;
    protected String receiverInfo;
    protected static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private static ArrayList<Log> allLogs = new ArrayList<>();

    public Log(String logID, Date date, String products, DeliveryStatus deliveryStatus, String receiverInfo, double totalCost) {
        this.logId = logID;
        this.date = date;
        this.products = products;
        this.receiverInfo = receiverInfo;
        this.totalCost = totalCost;
        setDeliveryStatus(deliveryStatus);
    }

    public static void addLog(Log log) {
        allLogs.add(log);
    }

    public abstract String viewLog();

    public void setDeliveryStatus(DeliveryStatus deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public String getLogId() {
        return logId;
    }

    public static StringBuilder getLastUsedLogID() {
        return lastUsedLogID;
    }

    public static Log getLogWithID(String logID) throws Exception {
        for (Log log : allLogs) {
            if(log.logId.equals(logID)){
                return log;
            }
        }
        throw new Exception("there is no log with id : " +logID + "\n");
    }

    public static String readData() {
        try {
            GeneralController.jsonReader = new JsonReader(new FileReader(new File("src/main/JSON/logs.json")));
            Log[] allLog = GeneralController.yagsonMapper.fromJson(GeneralController.jsonReader, Log[].class);
            allLogs = (allLog == null) ? new ArrayList<>() : new ArrayList<>(asList(allLog));
            setLastUsedLogID();
            return "Read Logs Data Successfully.";
        } catch (FileNotFoundException e) {
            return "Problem loading data from logs.json";
        }
    }

    public static void setLastUsedLogID() {
        if (allLogs.size() == 0) {
            lastUsedLogID = new StringBuilder("@");
        } else {
            lastUsedLogID = new StringBuilder(allLogs.get(allLogs.size() - 1).getLogId());
        }
    }

    public static String writeData() {
        try {
            GeneralController.fileWriter = new FileWriter("src/main/JSON/logs.json");
            Log[] allLog = new Log[allLogs.size()];
            allLog = allLogs.toArray(allLog);
            GeneralController.yagsonMapper.toJson(allLog, Log[].class, GeneralController.fileWriter);
            GeneralController.fileWriter.close();
            return "Saved Logs Data Successfully.";
        } catch (IOException e) {
            return "Problem saving logs data.";
        }
    }


    public static void setStringRecordObjects(){
        try {
            setStringRecordBuyersForSellLog();
        }
        catch (Exception e){}
    }

    private static void setStringRecordBuyersForSellLog() throws Exception {
        for (Log log : allLogs) {
            if(log instanceof SellLog){
                SellLog sellLog = (SellLog)log;
                sellLog.buyer = BuyerAccount.getBuyerWithUserName(sellLog.buyerStringRecord);
            }
        }
    }

    public static void getObjectStringRecords(){
        getBuyerForSellLogStringRecord();
    }

    private static void getBuyerForSellLogStringRecord(){
        for (Log log : allLogs) {
            if(log instanceof SellLog){
                SellLog sellLog = (SellLog) log;
                sellLog.buyerStringRecord = sellLog.buyer.getUserName();
            }
        }
    }

}
