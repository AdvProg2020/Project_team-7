package Main.model.logs;

import Main.model.Product;
import Main.model.accounts.BuyerAccount;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public class SellLog extends Log {
    private double offAmount;
    private BuyerAccount buyer;

    public SellLog(String logId, Date date, double receivedAmount, ArrayList<Product> soldProducts, BuyerAccount buyer, DeliveryStatus deliveryStatus) {
        super(logId, date, deliveryStatus);
    }

    @Override
    public String viewLog() {
        return null;
    }

}
