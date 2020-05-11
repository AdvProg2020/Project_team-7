package model.logs;

import Main.model.Cart;
import Main.model.CartProduct;
import Main.model.IDGenerator;
import Main.model.Product;
import Main.model.accounts.BuyerAccount;
import Main.model.accounts.SellerAccount;
import Main.model.exceptions.AccountsException;
import Main.model.logs.BuyLog;
import Main.model.logs.DeliveryStatus;
import Main.model.logs.Log;
import Main.model.logs.SellLog;
import org.junit.Assert;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogTest {

    @Test
    public void viewBuyLog() throws AccountsException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");


        Product product = new Product("laptop", "ASUS", 2, null,
                "nice thing it is:)", null, 100, null);
        SellerAccount sellerAccount = new SellerAccount("userName", "firstName", "last Name",
                "sampleEmail@sample.sample", "09001112233", "password123", "companyName"
                , "this company is great!", 100);
        Cart cart = new Cart();
        CartProduct cartProduct = new CartProduct(product, sellerAccount, cart);
        cart.addCartProduct(cartProduct);
        Date date = new Date();
        BuyLog buyLog = new BuyLog(IDGenerator.getNewID(Log.getLastUsedLogID()), date, 50
                , 50, cart.toStringForBuyer(), DeliveryStatus.PENDING_DELIVERY, "folani in address folan :(");

        Assert.assertEquals("Buy Log :\n" +
                "\tLog ID : A\n" +
                "\tTotal Paid Amount (considering discount codes): 50.0\n" +
                "\tDiscount Amount : %50.0\n" +
                "\tDate : "+ dateFormat.format(date) +"\n" +
                "Cart :\n" +
                "[Product ID : A\tProduct Name : laptop\tBrand : ASUS\n" +
                "Seller :\n" +
                "\tfirst name : firstName\n" +
                "\tlast name : last Name\n" +
                "\tuser name : userName\n" +
                "\tcompany name : companyName\n" +
                "\tcompany information : this company is great!\n" +
                "\temail : sampleEmail@sample.sample\n" +
                "\tphone number : 09001112233\n" +
                "  Price : 100.0\tOff : no off is set for this product]\n" +
                "\n" +
                "\n" +
                "cart total cost (not considering discount codes): 100.0\n" +
                "\n" +
                "\tDelivery Status : PENDING_DELIVERY\n" +
                "\tReceiver Information : folani in address folan :(\n",buyLog.viewLog());
    }

    @Test
    public void viewSellLog() throws AccountsException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

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

        Date date = new Date();
        SellLog sellLog = new SellLog(IDGenerator.getNewID(Log.getLastUsedLogID()), date, 50,
                cart.toStringForSeller(), buyerAccount, cart.getCartTotalPriceConsideringOffs(),
                DeliveryStatus.PENDING_DELIVERY, "folani in address folan :(");

        Assert.assertEquals("Sell Log :\n" +
                "\tLog ID : A\n" +
                "\tTotal Received Amount : 50.0\n" +
                "\tOff Amount : 100.0\n" +
                "\tDate : "+dateFormat.format(date) +"\n" +
                "\tBuyer : Buyer :\n" +
                "\tfirst name : firstName\n" +
                "\tlast name : last Name\n" +
                "\tuser name : userName\n" +
                "\temail : sampleEmail@sample.sample\n" +
                "\tphone number : 09001112233\n" +
                "\n" +
                "Sold products :\n" +
                "[Product ID : A\tProduct Name : laptop\tBrand : ASUS\n" +
                "Price : 100.0\tOff : no off is set for this product]\n" +
                "\n" +
                "\n" +
                "\tDelivery Status : PENDING_DELIVERY\n" +
                "\tReceiver Information : folani in address folan :(\n",sellLog.viewLog());
    }

}
