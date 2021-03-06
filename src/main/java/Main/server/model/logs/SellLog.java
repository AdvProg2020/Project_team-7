package Main.server.model.logs;

import Main.server.model.accounts.BuyerAccount;

import java.io.Serializable;
import java.util.Date;

public class SellLog extends Log implements Serializable {
    private double offAmount;
    protected BuyerAccount buyer;

    protected String buyerStringRecord;

    public SellLog(String logID, Date date, double receivedAmount, String soldProducts, BuyerAccount buyer, double offAmount
            , DeliveryStatus deliveryStatus, String receiverInfo) {
        super(logID, date, soldProducts, deliveryStatus, receiverInfo, receivedAmount);
        this.buyer = buyer;
        this.offAmount = offAmount;
    }

    @Override
    public String viewLog() {
        return "Sell Log :" +
                "\n\tLog ID : " +
                logId +
                "\n\tTotal Received Amount : " +
                totalCost +
                "\n\tOff Amount : " +
                offAmount +
                "\n\tDate : " +
                dateFormat.format(getDate()) +
                "\n\tBuyer : " +
                buyer.viewMe() +
                "\n" +
                products +
                "\n\tDelivery Status : " +
                deliveryStatus +
                "\n\tReceiver Information : " +
                receiverInfo + "\n";
    }

}
