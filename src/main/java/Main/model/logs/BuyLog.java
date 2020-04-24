package Main.model.logs;

import Main.model.Product;
import Main.model.accounts.BuyerAccount;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public class BuyLog extends Log {
    private double discountAmount;

    public BuyLog(String logId, Date date, double paidAmountWithoutDiscount, double discountAmount, ArrayList<Product> boughtProducts, BuyerAccount buyer, DeliveryStatus deliveryStatus) {
        super(logId, date, deliveryStatus);

    }

    @Override
    public String viewLog() {
        return null;
    }

    private void setLogTotalPrice(double paidAmountWithoutDiscount, double discountAmount) {
    }
}
