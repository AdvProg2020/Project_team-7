package Main.model.logs;

import Main.model.IDGenerator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Log {
    private static StringBuilder lastUsedLogID = new StringBuilder("@");
    protected String logId;
    protected Date date;
    protected double totalCost;
    protected String products;
    protected DeliveryStatus deliveryStatus;
    protected String receiverInfo;
    protected static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public Log(String logID, Date date, String products, DeliveryStatus deliveryStatus, String receiverInfo, double totalCost) {
        this.logId = logID;
        this.date = date;
        this.products = products;
        this.receiverInfo = receiverInfo;
        this.totalCost = totalCost;
        setDeliveryStatus(deliveryStatus);
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
}
