package Main.model.logs;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Log {
    //TODO : is it good practice to save histories as plain String ?
    protected String logId;
    protected Date date;
    protected double totalCost;
    protected String products;
    protected DeliveryStatus deliveryStatus;
    protected String receiverInfo;
    protected static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public Log(String logId, Date date, String products, DeliveryStatus deliveryStatus, String receiverInfo, double totalCost) {
        this.logId = logId;
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

}
