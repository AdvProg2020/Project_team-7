package model.logs;

import Main.model.Cart;
import Main.model.CartProduct;
import Main.model.IDGenerator;
import Main.model.Product;
import Main.model.accounts.BuyerAccount;
import Main.model.accounts.SellerAccount;
import Main.model.exceptions.InvalidInputException;
import Main.model.logs.BuyLog;
import Main.model.logs.DeliveryStatus;
import Main.model.logs.Log;
import Main.model.logs.SellLog;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;

public class LogTest {

    @Test
    public void viewBuyLog() throws InvalidInputException {
        Product product = new Product("laptop", "ASUS", 2, null,
                "nice thing it is:)", null, 100, null);
        SellerAccount sellerAccount = new SellerAccount("userName", "firstName", "last Name",
                "sampleEmail@sample.sample", "09001112233", "password123", "companyName"
                , "this company is great!", 100);
        Cart cart = new Cart();
        CartProduct cartProduct = new CartProduct(product, sellerAccount, cart);
        cart.addCartProduct(cartProduct);
        BuyLog buyLog = new BuyLog(IDGenerator.getNewID(Log.getLastUsedLogID()), new Date(), 50
                , 50, cart.toStringForBuyer(), DeliveryStatus.PENDING_DELIVERY, "folani in address folan :(");

        System.out.println(buyLog.viewLog());
    }

    @Test
    public void viewSellLog() throws InvalidInputException {
        Product product = new Product("laptop", "ASUS", 2, null,
                "nice thing it is:)", null, 100, null);
        SellerAccount sellerAccount = new SellerAccount("userName", "firstName", "last Name",
                "sampleEmail@sample.sample", "09001112233", "password123", "companyName"
                , "this company is great!", 100);

        BuyerAccount buyerAccount = new BuyerAccount("userName", "firstName", "last Name",
                "sampleEmail@sample.sample", "09001112233", "password123", 100);

        Cart cart = new Cart();
        CartProduct cartProduct = new CartProduct(product, sellerAccount, cart);
        cart.addCartProduct(cartProduct);

        SellLog sellLog = new SellLog(IDGenerator.getNewID(Log.getLastUsedLogID()), new Date(), 50,
                cart.toStringForSeller(), buyerAccount, cart.getCartTotalPriceConsideringOffs(),
                DeliveryStatus.PENDING_DELIVERY, "folani in address folan :(");

        System.out.println(sellLog.viewLog());
    }

}
