package Main.model.logs;

import Main.model.Product;
import Main.model.accounts.BuyerAccount;

import java.sql.Time;
import java.util.ArrayList;

public class BuyLog extends Log {
    private double discountAmount;

    public BuyLog(String logId, Time time, double paidAmount, double discountAmount, ArrayList<Product> boughtProducts, BuyerAccount buyer, DeliveryStatus deliveryStatus) {

    }
}
