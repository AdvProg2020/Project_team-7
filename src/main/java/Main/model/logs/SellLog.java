package Main.model.logs;

import Main.model.Product;
import Main.model.accounts.BuyerAccount;

import java.sql.Time;
import java.util.ArrayList;

public class SellLog extends Log {
    private double offAmount;
    private BuyerAccount buyer;

    public SellLog(String logId, Time time, double recievedAmount, ArrayList<Product> soldProducts, BuyerAccount buyer, DeliveryStatus deliveryStatus){

    }
}
