package Main.server.model.logs;

import java.util.Date;

public class BuyLog extends Log {
    private double discountAmount;

    public BuyLog(String logID, Date date, double paidAmountConsideringDiscount, double discountAmount, String boughtProducts
            , DeliveryStatus deliveryStatus, String receiverInfo) {
        super(logID, date, boughtProducts, deliveryStatus, receiverInfo, paidAmountConsideringDiscount);
        this.discountAmount = discountAmount;
    }

    @Override
    public String viewLog() {
        return "Buy Log :\n" +
                "\n\tLog ID : " +
                logId +
                "\n\tTotal Paid Amount (considering discount codes): " +
                totalCost +
                "\n\tDiscount Amount : %" +
                discountAmount + "\n\tDate : " +
                dateFormat.format(getDate()) + "\n" +
                products +
                "\n\tDelivery Status : " +
                deliveryStatus +
                "\n\tReceiver Information : " +
                receiverInfo + "\n";
    }

    public void markDelivered(){
        this.setDeliveryStatus(DeliveryStatus.DELIVERED);
        for (Log log : Log.allLogs) {
            if(log.getLogId().equals(this.getLogId())){
                log.deliveryStatus = DeliveryStatus.DELIVERED;
            }
        }
    }
}
