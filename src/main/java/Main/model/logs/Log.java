package Main.model.logs;

import Main.model.Cart;
import Main.model.Product;

import java.util.ArrayList;
import java.util.Date;

public abstract class Log {
    protected String logId;
    protected Date date;
    protected double totalCost;
    protected ArrayList<String> products = new ArrayList<String>();
    protected static ArrayList<Log> allLogs = new ArrayList<Log>();
    protected DeliveryStatus deliveryStatus;

    public Log(String logId, Date date, DeliveryStatus deliveryStatus) {
        this.logId = logId;
        this.date = date;
        setDeliveryStatus(deliveryStatus);
    }

    public void addLog(Log log) {

    }

    public abstract String viewLog();

    public void setDeliveryStatus(DeliveryStatus deliveryStatus) {

    }

    public String getLogId() {
        return logId;
    }

}
