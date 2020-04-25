package Main.model.logs;

import Main.model.accounts.BuyerAccount;

import java.util.Date;

public class SellLog extends Log {
    private double offAmount;
    private BuyerAccount buyer;

    public SellLog(String logId, Date date, double receivedAmount, String soldProducts, BuyerAccount buyer, double offAmount, DeliveryStatus deliveryStatus) {
        super(logId, date, soldProducts, deliveryStatus);
        this.totalCost = receivedAmount;
        this.buyer = buyer;
        this.offAmount = offAmount;
    }

    @Override
    public String viewLog() {
        return "Buy Log :" + "\n\tLog ID : " + logId + "\n\tTotal Received Amount : " + totalCost + "\n\tOff Amount : " +
                offAmount + "\n\tDate : " + dateFormat.format(date) + "\n\tBuyer : " + buyer.viewMe() + products +
                "\n\tDelivery Status : " + deliveryStatus;
    }

}
