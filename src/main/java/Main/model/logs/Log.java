package Main.model.logs;

import Main.model.Product;

import java.sql.Time;
import java.util.ArrayList;

public abstract class Log {
    protected String logId;
    protected Time time;
    protected double totalCost;
    protected ArrayList<Product> products = new ArrayList<Product>();
    protected static ArrayList<Log> allLogs = new ArrayList<Log>();
    protected DeliveryStatus deliveryStatus;

    public void addLog(Log log){

    }

    public String viewLog(){
        return null;
    }

    public void setDeliveryStatus(DeliveryStatus deliveryStatus) {

    }
}
